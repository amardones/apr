-- Function: fn_reporte_libro_ingresos(date, date)

-- DROP FUNCTION fn_reporte_libro_ingresos(date, date);

CREATE OR REPLACE FUNCTION fn_reporte_libro_ingresos("fecha_inicio$" date, "fecha_fin$" date)
  RETURNS refcursor AS
$BODY$
	DECLARE
		p_return refcursor;
	BEGIN 
      
	OPEN p_return FOR
		SELECT 
		pag.ID_PAGO
		,pag.NUMERO_DOCUMENTO
		,to_char(pag.FECHA_CREACION, 'dd/MM/yyyy')
		,c.ID_CUENTA
		,s.NOMBRE
		,s.APELLIDO
		,REP.CONSDEAGUA::Integer
		,REP.CUOTSOCIAL::Integer 
		,(REP.INTERES_PAGO::Integer +REP.INTERES::Integer ) AS INTERES
		,REP.MULTA::Integer
		,REP.CORTEYREPO::Integer
		,REP.DEREINCORP::Integer
		,REP.OTROCOBRO::Integer

		FROM cuenta c
		INNER JOIN SOCIO s on s.rut = c.rut
		INNER JOIN PAGO pag ON pag.id_cuenta = c.id_cuenta
		INNER JOIN 

			(
				SELECT 
				T.ID_PAGO
				,T.INTERES_PAGO
				,SUM(T.CONSDEAGUA) AS CONSDEAGUA
				,SUM(T.CUOTSOCIAL) AS CUOTSOCIAL 
				,SUM(T.INTERES) AS INTERES 
				,SUM(T.MULTA) AS MULTA 
				,SUM(T.CORTEYREPO) AS CORTEYREPO
				,SUM(T.DEREINCORP) AS DEREINCORP
				,SUM(T.OTROCOBRO) AS OTROCOBRO
			
				FROM 	
				(
				SELECT  
					p.ID_PAGO
					,p.INTERES AS INTERES_PAGO
					,(CASE WHEN tp.CODIGO_TIPO_COBRO = 'CONSDEAGUA' THEN  SUM(dac.TOTAL) ELSE 0 END) AS CONSDEAGUA
					,(CASE WHEN tp.CODIGO_TIPO_COBRO = 'CUOTSOCIAL' THEN  SUM(dac.TOTAL) ELSE 0 END) AS CUOTSOCIAL
					,(CASE WHEN tp.CODIGO_TIPO_COBRO = 'INTERES' THEN  SUM(dac.TOTAL) ELSE 0 END) AS INTERES
					,(CASE WHEN tp.CODIGO_TIPO_COBRO = 'MULTAOBL' OR tp.CODIGO_TIPO_COBRO = 'MULTANOOBL' THEN  SUM(dac.TOTAL) ELSE 0 END) AS MULTA
					,(CASE WHEN tp.CODIGO_TIPO_COBRO = 'CORTEYREPO' THEN  SUM(dac.TOTAL) ELSE 0 END) AS CORTEYREPO
					,(CASE WHEN tp.CODIGO_TIPO_COBRO = 'DEREINCORP' THEN  SUM(dac.TOTAL) ELSE 0 END) AS DEREINCORP
					,(CASE WHEN tp.CODIGO_TIPO_COBRO = 'OTROCOBRO' THEN  SUM(dac.TOTAL) ELSE 0 END) AS OTROCOBRO

					from pago  p
					inner join pago_detalle_aviso pda on pda.id_pago = p.id_pago
					inner join detalle_aviso_cobro dac on pda.id_detalle_aviso_cobro =  dac.id_detalle_aviso_cobro
					inner join tipo_cobro tp on tp.id_tipo_cobro = dac.id_tipo_cobro
					WHERE p.fecha_creacion::DATE >= fecha_inicio$ AND  p.fecha_creacion::DATE <= fecha_fin$
					GROUP BY p.ID_PAGO, p.INTERES,tp.CODIGO_TIPO_COBRO

				UNION ALL
				SELECT  
					p.ID_PAGO
					,p.INTERES AS INTERES_PAGO
					,(CASE WHEN tp.CODIGO_TIPO_COBRO = 'CONSDEAGUA' THEN  SUM(tc.TOTAL) ELSE 0 END) AS CONSDEAGUA
					,(CASE WHEN tp.CODIGO_TIPO_COBRO = 'CUOTSOCIAL' THEN  SUM(tc.TOTAL) ELSE 0 END) AS CUOTSOCIAL
					,(CASE WHEN tp.CODIGO_TIPO_COBRO = 'INTERES' THEN  SUM(tc.TOTAL) ELSE 0 END) AS INTERES
					,(CASE WHEN tp.CODIGO_TIPO_COBRO = 'MULTAOBL' OR tp.CODIGO_TIPO_COBRO = 'MULTANOOBL' THEN  SUM(tc.TOTAL) ELSE 0 END) AS MULTA
					,(CASE WHEN tp.CODIGO_TIPO_COBRO = 'CORTEYREPO' THEN  SUM(tc.TOTAL) ELSE 0 END) AS CORTEYREPO
					,(CASE WHEN tp.CODIGO_TIPO_COBRO = 'DEREINCORP' THEN  SUM(tc.TOTAL) ELSE 0 END) AS DEREINCORP
					,(CASE WHEN tp.CODIGO_TIPO_COBRO = 'OTROCOBRO' THEN  SUM(tc.TOTAL) ELSE 0 END) AS OTROCOBRO

					from pago  p
					inner join pago_tipo_cobro tc on tc.id_pago = p.id_pago
					inner join tipo_cobro tp on tp.id_tipo_cobro = tc.id_tipo_cobro
					WHERE p.fecha_creacion::DATE >= fecha_inicio$ AND  p.fecha_creacion::DATE <= fecha_fin$
					GROUP BY p.ID_PAGO, p.INTERES,tp.CODIGO_TIPO_COBRO
					
				
				) T

				GROUP BY T.ID_PAGO, T.INTERES_PAGO
			) REP
			ON pag.id_pago = rep.id_pago
			ORDER BY ID_PAGO;
	     
	RETURN p_return;
	END;	
	
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION fn_reporte_libro_ingresos(date, date)
  OWNER TO postgres;
