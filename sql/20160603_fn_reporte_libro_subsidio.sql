-- Function: fn_reporte_libro_subsidio(date, date)

-- DROP FUNCTION fn_reporte_libro_subsidio(date, date);

CREATE OR REPLACE FUNCTION fn_reporte_libro_subsidio(
    "fecha_inicio$" date,
    "fecha_fin$" date)
  RETURNS refcursor AS
$BODY$
	DECLARE
		p_return refcursor;
	BEGIN 
      
	OPEN p_return FOR
		SELECT 
			DISTINCT 
			a.id_cuenta AS CUENTA
			,a.id_periodo AS PERIODO
			,to_char(a.fecha_creacion, 'dd/MM/yyyy') 
			,d.descuento AS DESCUENTO
			,(CASE WHEN  d.descripcion like '%SUBSIDIO%' THEN 'EXTERNO' ELSE 'INTERNO' END) AS tipo

			 
					FROM aviso_cobro a INNER JOIN detalle_aviso_cobro d ON a.id_periodo=d.id_periodo AND a.id_cuenta=d.id_cuenta 
					
					WHERE d.descuento <> 0 
					AND fecha_creacion::DATE >= fecha_inicio$ AND  
						fecha_creacion::DATE <= fecha_fin$;
			
	RETURN p_return;
	END;	
	
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION fn_reporte_libro_subsidio(date, date)
  OWNER TO postgres;