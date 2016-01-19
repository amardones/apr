-- Function: fn_reporte_libro_subsidio(date, date)

-- DROP FUNCTION fn_reporte_libro_subsidio(date, date);

CREATE OR REPLACE FUNCTION fn_reporte_libro_subsidio("fecha_inicio$" date, "fecha_fin$" date)
  RETURNS refcursor AS
$BODY$
	DECLARE
		p_return refcursor;
	BEGIN 
      
	OPEN p_return FOR
			SELECT 
			id_cuenta AS CUENTA
			,id_periodo AS PERIODO
			,to_char(fecha_creacion, 'dd/MM/yyyy')
			,descuento_periodo AS DESCUENTO
					FROM aviso_cobro
					WHERE descuento_periodo!=0 AND
						fecha_creacion::DATE >= fecha_inicio$ AND  
						fecha_creacion::DATE <= fecha_fin$;

			
			
	RETURN p_return;
	END;	
	
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION fn_reporte_libro_subsidio(date, date)
  OWNER TO postgres;
