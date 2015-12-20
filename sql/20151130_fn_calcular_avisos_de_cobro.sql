-- Function: fn_calcular_avisos_de_cobro(integer,integer)

-- DROP FUNCTION fn_calcular_avisos_de_cobro(integer,integer);

--SI id_cuenta$ > 0 considera el valor y solo calcula el aviso para esa cuenta, sino calcula todos los indicadores de todos los documentos
CREATE OR REPLACE FUNCTION fn_calcular_avisos_de_cobro("id_periodo$" integer,  "id_cuenta_in$" integer)
  RETURNS character varying AS
$BODY$
DECLARE
    --integer
    --numeric
    f_cuentas$ refcursor;
    f_calculo_agua$ refcursor;
    id_cuenta$ integer;
    p_date$ timestamp;
    id_valores_parametricos$ integer;
    fecha_emision$ date;

    id_periodo_ant$ integer;
    id_detalle_aviso_cobro$ integer;
    total_periodo$ integer;
    total_pendiente$ integer;

    sub_total$ integer;
    descuento$ integer;
    total$ integer;
    descripcion$ character varying;
    monto_descuento_sub$ integer;
    monto_descuento_int$ integer;

    valor_cuota_social$ integer;
    
BEGIN
  
       p_date$:= CURRENT_TIMESTAMP;
       raise info 'FECHA CALCULO: % ', p_date$;
      
	--SE RECORRE LISTADO DE CUENTAS PARA CALCULAR AVISO DE COBRO
	OPEN f_cuentas$ FOR    
		SELECT c.id_cuenta FROM cuenta c
		WHERE c.activa = true
		and case when id_cuenta_in$ > 0 then c.id_cuenta = id_cuenta_in$ else true end ;

	--seleccionamos datos ultimo periodo
	SELECT id_valores_parametricos, fecha_emision INTO id_valores_parametricos$, fecha_emision$ FROM periodo WHERE id_periodo = id_periodo$;
	
					
      LOOP
        FETCH f_cuentas$ INTO id_cuenta$;    
        EXIT WHEN NOT FOUND;	
	raise info 'cal id_cuenta$: % ', id_cuenta$;

	--1.- Obtenemos aviso de cobro anterior
	--SELECT id_periodo INTO id_periodo_ant$ FROM aviso_cobro WHERE id_cuenta = id_cuenta$;
	SELECT id_periodo INTO id_periodo_ant$ FROM periodo WHERE id_periodo < id_periodo$ ORDER BY id_periodo desc limit 1;
	IF id_periodo_ant$ IS NULL THEN
		id_periodo_ant$ := -1;
	END IF; 
	--2.- Eliminamos aviso de cobro si ya existe
		--se elimina relacion de detalle con cuota
	DELETE FROM registro_cobro_cuota WHERE id_detalle_aviso_cobro IN 
	(SELECT id_detalle_aviso_cobro FROM detalle_aviso_cobro 
		WHERE id_periodo = id_periodo$ AND id_cuenta = id_cuenta$
	);
	--se elimina detalle de cobro
	DELETE FROM detalle_aviso_cobro  WHERE id_periodo = id_periodo$ AND id_cuenta = id_cuenta$;
	--se elimina el aviso de cobro para generarlo nuevamente
	DELETE FROM aviso_cobro  WHERE id_periodo = id_periodo$ AND id_cuenta = id_cuenta$; 
	

	--SELECT count(*) FROM  registro_estado  WHERE id_periodo = 2 AND id_cuenta = 1 AND estado_actual >= 0
	--SI EXISTE UN REGISTRO DE ESTADO VALIDO
	IF (SELECT count(*) FROM  registro_estado  WHERE id_periodo = id_periodo$ AND id_cuenta = id_cuenta$ AND estado_actual >= 0) > 0 THEN
		--Insertamos nuevo aviso de cobro - se crea aviso si hay estado asociado
		INSERT INTO aviso_cobro(id_periodo, id_cuenta, total_periodo, total_pendiente, total, fecha_creacion) VALUES (id_periodo$, id_cuenta$, 0, 0, 0, NOW());
		--3.- Calcular tipos de pagos pendientes si periodo anterior existe
		IF id_periodo_ant$ IS NOT NULL  THEN
			--insertamos los pagos pendientes
			INSERT INTO detalle_aviso_cobro(id_detalle_aviso_cobro_ant, id_periodo, id_cuenta, id_tipo_cobro, sub_total, descuento, total, descripcion, pagado)
			(
				SELECT id_detalle_aviso_cobro, id_periodo$, id_cuenta, id_tipo_cobro,  sub_total, descuento, total, descripcion, false  FROM detalle_aviso_cobro 
				WHERE id_periodo = id_periodo_ant$ AND id_cuenta = id_cuenta$ AND pagado = false
			);
			--insertamso relacion detalle con cuotas asociados a los cobros pendientes
			INSERT INTO registro_cobro_cuota(id_registro_cobro, numero_cuota, id_detalle_aviso_cobro)
			(
				SELECT rcc.id_registro_cobro, rcc.numero_cuota, dac.id_detalle_aviso_cobro   
				FROM detalle_aviso_cobro dac
				INNER JOIN registro_cobro_cuota rcc 
				ON dac.id_detalle_aviso_cobro_ant =  rcc.id_detalle_aviso_cobro
				WHERE dac.id_periodo = id_periodo$ AND dac.id_cuenta = id_cuenta$ AND dac.id_detalle_aviso_cobro_ant > 0
			);
		
		END IF;	
		--4.- Calcular nuevos tipos de pagos
		DROP TABLE IF EXISTS detalle_aviso_cobro_tmp;
		CREATE TEMPORARY TABLE detalle_aviso_cobro_tmp  AS 
		       SELECT 
				nextval('detalle_aviso_cobro_id_detalle_aviso_cobro_seq') as id_detalle_aviso_cobro, 
				-1 as id_detalle_aviso_cobro_ant, 
				id_periodo$ as id_periodo, 
				id_cuenta$ as id_cuenta, 
				rc.id_tipo_cobro, 
				cc.valor_cuota as sub_total, 
				0 as descuento,
				cc.valor_cuota as total,
				(rc.descripcion || ' cuota '||cc.numero_cuota||'/'||rc.cuotas) as descripcion, 
				false as pagado,
				cc.id_registro_cobro,
				cc.numero_cuota
			FROM registro_cobro rc
			INNER JOIN cobro_cuota cc ON rc.id_registro_cobro = cc.id_registro_cobro
			WHERE cc.pagado = false
			AND rc.id_cuenta = id_cuenta$
			AND cc.mes <= (SELECT EXTRACT(MONTH FROM fecha_emision$))
			AND cc.anio = (SELECT EXTRACT(YEAR FROM fecha_emision$))
			AND (cc.id_registro_cobro, cc.numero_cuota) NOT IN
				(SELECT rcc2.id_registro_cobro, rcc2.numero_cuota 
					FROM registro_cobro_cuota rcc2
					INNER JOIN  cobro_cuota cc2 ON rcc2.id_registro_cobro = cc2.id_registro_cobro AND rcc2.numero_cuota = cc2.numero_cuota
					INNER JOIN  registro_cobro rc2 ON rc2.id_registro_cobro = cc2.id_registro_cobro 
					WHERE rc.id_cuenta = rc.id_cuenta AND rc.id_registro_cobro = rc2.id_registro_cobro 
					)  
			ORDER BY rc.id_registro_cobro, rc.id_tipo_cobro, cc.numero_cuota;
		
		--obtiene todas las cuotas que no estan pagadas y que no se encuentran asociadas a un aviso anterior
		INSERT INTO detalle_aviso_cobro(id_detalle_aviso_cobro, id_detalle_aviso_cobro_ant, id_periodo, id_cuenta, id_tipo_cobro, sub_total, descuento, total, descripcion, pagado)
		(
			SELECT id_detalle_aviso_cobro, id_detalle_aviso_cobro_ant, id_periodo, id_cuenta, id_tipo_cobro, sub_total, descuento, total, descripcion, pagado 
			FROM detalle_aviso_cobro_tmp
		);
		--insertamso relacion detalle con cuotas asociados a los nuevos cobros
		INSERT INTO registro_cobro_cuota(id_registro_cobro, numero_cuota, id_detalle_aviso_cobro)
		(
			SELECT id_registro_cobro, numero_cuota, id_detalle_aviso_cobro 
			FROM detalle_aviso_cobro_tmp
		);

		--5.- Calcular pago de agua
		OPEN f_calculo_agua$ FOR    
		SELECT * FROM fn_calcular_valor_agua_potable(id_periodo$,id_cuenta$);
		
		LOOP
					FETCH f_calculo_agua$ INTO sub_total$, descuento$, total$, descripcion$, monto_descuento_sub$, monto_descuento_int$;    
					EXIT WHEN NOT FOUND;
						raise info 'CALCULO AGUA $: % ', total$;
						INSERT INTO detalle_aviso_cobro
						(id_detalle_aviso_cobro_ant, id_periodo, id_cuenta, id_tipo_cobro, sub_total, descuento, total, descripcion, pagado)
						VALUES
						(
							-1
							,id_periodo$
							,id_cuenta$ 
							,(SELECT ID_TIPO_COBRO FROM TIPO_COBRO WHERE CODIGO_TIPO_COBRO = 'CONSDEAGUA')
							,sub_total$ 
							,descuento$ 
							,total$
							,descripcion$
							,false
						);
					
		END LOOP;
		CLOSE f_calculo_agua$;	
				
		--6.- AGREGAR CUOTA SOCIAL
		
		SELECT VALOR_CUOTA_SOCIAL INTO valor_cuota_social$ FROM valores_parametricos WHERE id_valores_parametricos = id_valores_parametricos$;
		
		
		INSERT INTO detalle_aviso_cobro
						(id_detalle_aviso_cobro_ant, id_periodo, id_cuenta, id_tipo_cobro, sub_total, descuento, total, descripcion, pagado)
						VALUES
						(
							-1
							,id_periodo$
							,id_cuenta$
							,(SELECT ID_TIPO_COBRO FROM TIPO_COBRO WHERE CODIGO_TIPO_COBRO = 'CUOTSOCIAL')
							,valor_cuota_social$
							,0 
							,valor_cuota_social$
							,'' 
							,false
						);
		--7.- CALCULAR UNTERES

		
		--UPDATE VALRES AVISO COBRO
		SELECT sum (total) INTO total_periodo$ FROM detalle_aviso_cobro WHERE id_periodo = id_periodo$ AND id_cuenta = id_cuenta$ AND (id_detalle_aviso_cobro_ant IS NULL OR id_detalle_aviso_cobro_ant <= 0);
		SELECT sum (total) INTO total_pendiente$ FROM detalle_aviso_cobro WHERE id_periodo = id_periodo$ AND id_cuenta = id_cuenta$ AND (id_detalle_aviso_cobro_ant > 0);
		IF total_periodo$ IS NULL THEN
			total_periodo$ := 0;
		END IF;
		IF  total_pendiente$ IS NULL THEN
			total_pendiente$ := 0;
		END IF;
	      
		UPDATE aviso_cobro
		SET	
			total_periodo = total_periodo$, 
			total_pendiente = total_pendiente$, 
			total= (total_periodo$ + total_pendiente$)
		WHERE id_periodo = id_periodo$ AND id_cuenta=id_cuenta$;
	END IF;
      END LOOP;
      CLOSE f_cuentas$;
      return 'OK';
	
	EXCEPTION
			WHEN OTHERS THEN
			RAISE NOTICE  'Falló la llamada a la función id_cuenta: %. El error fue: %',id_cuenta$,SQLERRM;
			RAISE EXCEPTION 'Falló la orden SQL: %. ',SQLERRM;
			return 'ERROR';
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION fn_calcular_avisos_de_cobro(integer,integer)
  OWNER TO postgres;

-- select fn_calcular_avisos_de_cobro(2,-1);
-- select * from detalle_aviso_cobro;

 /*
	--1. PRIMERO CREAMOS TABLA TEMPORAL VALOR OPERANDO MANUAL 
	DROP TABLE IF EXISTS calculo_operando_tmp;
	CREATE TEMPORARY TABLE calculo_operando_tmp  AS 
	select distinct ;
	
	--2.- CALCULAR TODOS LOS OPERANDO MANUAL
	INSERT INTO valor_operando_manual(id_operando_manual, valor) 
		(
		SELECT  co.id_operando_manual
			,(
				case when co.es_grilla then 
					(select fn_calcula_operando_manual_condicion_grilla(co.id_operando_manual,co.id_formulario,co.ID_BLOQUE,co.ID_METADATO,co.operador,co.fecha_desde_periodo, co.fecha_hasta_periodo)) 
				else 
					(select fn_calcula_operando_manual_condicion_metadato(co.id_operando_manual,co.id_formulario,co.ID_BLOQUE,co.ID_METADATO,co.operador,co.fecha_desde_periodo, co.fecha_hasta_periodo))
				end
			) as resultado

		FROM calculo_operando_tmp  co 
		);
	
	*/