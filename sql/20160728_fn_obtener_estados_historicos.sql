-- Function: fn_obtener_estados_historicos(integer, integer)

-- DROP FUNCTION fn_obtener_estados_historicos(integer, integer);

CREATE OR REPLACE FUNCTION fn_obtener_estados_historicos("id_cuenta$" integer, "id_periodo$" integer)
  RETURNS refcursor AS
$BODY$
	DECLARE
		p_return refcursor;
	BEGIN 
      
	OPEN p_return FOR

	SELECT  T2.id_periodo, T1.mes, T2.metros_cubicos FROM
		(
		SELECT '01' as num_mes, 'DIC' as mes UNION
		SELECT '02' as num_mes, 'ENE' as mes UNION
		SELECT '03' as num_mes, 'FEB' as mes UNION
		SELECT '04' as num_mes, 'MAR' as mes UNION
		SELECT '05' as num_mes, 'ABR' as mes UNION
		SELECT '06' as num_mes, 'MAY' as mes UNION
		SELECT '07' as num_mes, 'JUN' as mes UNION
		SELECT '08' as num_mes, 'JUL' as mes UNION
		SELECT '09' as num_mes, 'AGO' as mes UNION
		SELECT '10' as num_mes, 'SEP' as mes UNION
		SELECT '11' as num_mes, 'OCT' as mes UNION
		SELECT '12' as num_mes, 'NOV' as mes UNION
		SELECT '' as num_mes, '-' as mes 
		) T1

		JOIN

		(
			SELECT 
				r.id_periodo, 			
				to_char(p.fecha_emision, 'MM') as num_mes,
				r.metros_cubicos::Integer
			from registro_estado r
			JOIN periodo p on r.id_periodo = p.id_periodo AND  p.id_periodo <=  id_periodo$
			where r.id_cuenta = id_cuenta$ 
			UNION ALL SELECT 0::Integer as id_periodo, ''::TEXT as num_mes, 0::Integer as metros_cubicos
			UNION ALL SELECT 0::Integer as id_periodo, ''::TEXT as num_mes, 0::Integer as metros_cubicos
			UNION ALL SELECT 0::Integer as id_periodo, ''::TEXT as num_mes, 0::Integer as metros_cubicos
			UNION ALL SELECT 0::Integer as id_periodo, ''::TEXT as num_mes, 0::Integer as metros_cubicos
			UNION ALL SELECT 0::Integer as id_periodo, ''::TEXT as num_mes, 0::Integer as metros_cubicos
			UNION ALL SELECT 0::Integer as id_periodo, ''::TEXT as num_mes, 0::Integer as metros_cubicos
			UNION ALL SELECT 0::Integer as id_periodo, ''::TEXT as num_mes, 0::Integer as metros_cubicos
			UNION ALL SELECT 0::Integer as id_periodo, ''::TEXT as num_mes, 0::Integer as metros_cubicos
			UNION ALL SELECT 0::Integer as id_periodo, ''::TEXT as num_mes, 0::Integer as metros_cubicos
			UNION ALL SELECT 0::Integer as id_periodo, ''::TEXT as num_mes, 0::Integer as metros_cubicos
			UNION ALL SELECT 0::Integer as id_periodo, ''::TEXT as num_mes, 0::Integer as metros_cubicos
			UNION ALL SELECT 0::Integer as id_periodo, ''::TEXT as num_mes, 0::Integer as metros_cubicos
			order by id_periodo desc limit 12
	
		) T2
		ON T1.num_mes = T2.num_mes
		order by T2.id_periodo;

		

	    
	RETURN p_return;
	END;	
	
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION fn_obtener_estados_historicos(integer, integer)
  OWNER TO postgres;
