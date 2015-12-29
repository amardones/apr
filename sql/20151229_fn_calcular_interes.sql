-- Function: fn_calcular_interes(integer, date)

-- DROP FUNCTION fn_calcular_interes(integer, date);

CREATE OR REPLACE FUNCTION fn_calcular_interes("id_cuenta$" integer,"fecha$" date)
  RETURNS integer AS
$BODY$
	DECLARE
		p_interes$ integer;
		p_id_periodo_actual$ integer;
		p_id_periodo_anterior$ integer;
		p_count_pagos$ integer;
		
	BEGIN 
		p_interes$ := 0;
		select id_periodo INTO p_id_periodo_actual$ from aviso_cobro ac where ac.id_cuenta = id_cuenta$ order by  ac.id_periodo desc limit 1;
		raise info 'p_id_periodo_actual$: % ', p_id_periodo_actual$;
		
		IF p_id_periodo_actual$ IS NOT NULL THEN
			SELECT count(*) INTO p_count_pagos$ from aviso_cobro ac 
			join detalle_aviso_cobro dac on ac.id_periodo =dac.id_periodo and ac.id_cuenta = dac.id_cuenta and dac.pagado = true
			where ac.id_periodo = p_id_periodo_actual$ and ac.id_cuenta = id_cuenta$;

			raise info 'p_count_pagos$: % ', p_count_pagos$;
			IF p_count_pagos$ = 0 THEN
				SELECT 
					(case when (fecha$ - p.fecha_vencimiento) > 0 THEN (fecha$ - p.fecha_vencimiento) else 0 END) INTO  p_interes$
				FROM periodo p 
				WHERE id_periodo = p_id_periodo_actual$;

			raise info 'p_interes$: % ', p_interes$;
			END IF;

		END IF; 


		RETURN p_interes$;
	END;	
	
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION fn_calcular_interes(integer, date)
  OWNER TO postgres;

 --select fn_calcular_interes(3, CURRENT_DATE+40);

 --select * from detalle_aviso_cobro  
