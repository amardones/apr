-- Function: fn_calcular_registro_estado_default(integer)

-- DROP FUNCTION fn_calcular_registro_estado_default(integer, integer);

CREATE OR REPLACE FUNCTION fn_calcular_registro_estado_default("id_periodo$" integer)
  RETURNS boolean AS
$BODY$
DECLARE

    p_return$ boolean;
    p_date$ timestamp;
    f_cuentas$ refcursor;
    id_cuenta$ integer;
    estado_anterior$ numeric;
    id_periodo_ant$ integer;
    
BEGIN

       p_return$ := false;
       
       p_date$:= CURRENT_TIMESTAMP;
       raise info 'FECHA CALCULO: % ', p_date$;
      
	--SE RECORRE LISTADO DE CUENTAS PARA CALCULAR AVISO DE COBRO
	OPEN f_cuentas$ FOR    
		SELECT c.id_cuenta FROM cuenta c
		WHERE c.activa = true;

	DELETE FROM registro_estado WHERE id_periodo = id_periodo$;
	
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

		SELECT estado_anterior INTO estado_anterior$ FROM registro_estado WHERE id_periodo = id_periodo_ant$ AND id_cuenta = id_cuenta$;

		IF estado_anterior$ IS NULL THEN
			estado_anterior$ := 0;
		END IF;

	
		INSERT INTO registro_estado(id_periodo, id_cuenta, estado_anterior, estado_actual, metros_cubicos, fecha_registro, descripcion)
		VALUES (id_periodo$, id_cuenta$, estado_anterior$, 0, 0, p_date$, '');
		
	END LOOP;
	CLOSE f_cuentas$;

	p_return$ := true;
	return p_return$;
	
	EXCEPTION
			WHEN OTHERS THEN
			RAISE NOTICE  'Falló la llamada a la función id_periodo: %. El error fue: %',id_periodo$,SQLERRM;
			RAISE EXCEPTION 'Falló la orden SQL: %. ',SQLERRM;
			return p_return$;
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION fn_calcular_registro_estado_default(integer)
  OWNER TO postgres;

--select fn_calcular_registro_estado_default(2);