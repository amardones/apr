-- Function: fn_calcular_interes(integer, integer, date)

-- DROP FUNCTION fn_calcular_interes(integer, integer, date);

CREATE OR REPLACE FUNCTION fn_calcular_interes("p_id_periodo$" integer, "id_cuenta$" integer, "fecha$" date)
  RETURNS integer AS
$BODY$
	DECLARE
		p_interes$ integer;
		p_id_periodo_actual$ integer;
		--p_id_periodo_anterior$ integer;
		p_count_pagos$ integer;
		p_count_detalles$ integer;
		p_count_interes$ integer;
		p_fecha_incio_interes$ date;
		
	BEGIN 
		p_interes$ := 0;
		IF p_id_periodo$ > 0 THEN
			p_id_periodo_actual$ := p_id_periodo$;
		ELSE
			SELECT id_periodo INTO p_id_periodo_actual$ from aviso_cobro ac where ac.id_cuenta = id_cuenta$ order by  ac.id_periodo desc limit 1;
		END IF;
		
		raise info 'CALCULO INTERES p_id_periodo_actual$: % ', p_id_periodo_actual$;
		
		IF p_id_periodo_actual$ IS NOT NULL THEN

			SELECT count(*) INTO p_count_detalles$  from aviso_cobro ac 
			where ac.id_periodo = p_id_periodo_actual$ and ac.id_cuenta = id_cuenta$;
		
			SELECT count(*) INTO p_count_pagos$ from aviso_cobro ac 
			join detalle_aviso_cobro dac on ac.id_periodo =dac.id_periodo and ac.id_cuenta = dac.id_cuenta and dac.pagado = true
			where ac.id_periodo = p_id_periodo_actual$ and ac.id_cuenta = id_cuenta$;

			

			raise info 'p_count_pagos$: % ', p_count_pagos$;
			IF p_count_detalles$ > 0 && p_count_pagos$ = 0 THEN
				--se obtiene cantidad para identificar si existen intereses anteriores asociados al aviso
				SELECT count(*)  INTO p_count_interes$ from aviso_cobro ac 
				join detalle_aviso_cobro dac on ac.id_periodo =dac.id_periodo and ac.id_cuenta = dac.id_cuenta 
				join tipo_cobro tp on dac.id_tipo_cobro = tp.id_tipo_cobro and tp.codigo_tipo_cobro = 'INTERES'
				where ac.id_periodo = p_id_periodo_actual$ and ac.id_cuenta = id_cuenta$;

				--si ya tenia interes, se agregan los dias adicionales hasta el pago desde la generación del aviso
				IF p_count_interes$ > 0 THEN
					SELECT  ac.fecha_creacion INTO  p_fecha_incio_interes$					
					FROM aviso_cobro ac 
					WHERE ac.id_periodo = p_id_periodo_actual$ and ac.id_cuenta =id_cuenta$;
				ELSE
				--en el cas ode no tenr intereses se considera el interes desde la fecha de vencimiento
					SELECT 
						p.fecha_vencimiento INTO  p_fecha_incio_interes$
					FROM periodo p 
					WHERE p.id_periodo = p_id_periodo_actual$;
				END IF;
				raise info 'CALCULO INTERES p_fecha_incio_interes$: % ', p_fecha_incio_interes$;
				p_interes$ := fecha$ - p_fecha_incio_interes$;

				IF p_interes$ < 0 THEN
					p_interes$ := 0;
				END IF;

			raise info 'p_interes$: % ', p_interes$;
			END IF;

		END IF; 


		RETURN p_interes$;
	END;	
	
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION fn_calcular_interes(integer, integer, date)
  OWNER TO postgres;
