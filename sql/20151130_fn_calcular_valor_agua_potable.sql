-- Function: fn_calcular_valor_agua_potable(integer,integer)

-- DROP FUNCTION fn_calcular_valor_agua_potable(integer,integer);


CREATE OR REPLACE FUNCTION fn_calcular_valor_agua_potable("id_periodo$" integer,  "id_cuenta$" integer)
 RETURNS TABLE (sub_total integer, descuento integer, total integer, descripcion text, monto_descuento_sub integer, monto_descuento_int integer ) AS
$BODY$
DECLARE
    --integer
    --numeric
    f_tramos$ refcursor;
    p_date$ timestamp;
    
    --valores return
    descripcion$ text;
    sub_total$ integer;
    descuento$ integer;
    total$ integer;
    monto_descuento_sub$ integer; 
    monto_descuento_int$ integer; 

    --valores parametricos
    valor_cargo_fijo$ integer;
    valor_m3$ integer;
    m3_fijos$ integer;
    m3_limite_dcto_interno$ integer;
    porcentaje_dcto_interno$ numeric;

    --tramo
    m3_inicio$ integer;
    m3_final$ integer;
    porcentaje_recargo$ numeric;
    existen_tramos$ boolean;
     
    --cuanta 
    metros_cubicos_med$ integer;

    --subsidio
    nombre_sub$ character varying; 
    porcentaje_sub$ numeric; 
    metros_cubicos_tope_sub$ integer;
    metros_cubicos_aplica_sub$ integer;
     
    --calculo
    m3_tramo$ integer;
    valor_tramo$ integer;

    
BEGIN
  
        p_date$:= CURRENT_TIMESTAMP;
   
	OPEN f_tramos$ FOR    
		SELECT m3_inicio, m3_final, porcentaje_recargo FROM valor_tramo_m3 order by m3_inicio, m3_final;

	--seleccionamos valores parametricos	
	SELECT valor_cargo_fijo, valor_m3, m3_fijos, m3_limite_dcto_interno, porcentaje_dcto_interno
	INTO  valor_cargo_fijo$, valor_m3$, m3_fijos$, m3_limite_dcto_interno$, porcentaje_dcto_interno$
	FROM valores_parametricos WHERE id_valores_parametricos = (SELECT id_valores_parametricos FROM periodo WHERE id_periodo = id_periodo$);
	--OBTENEMOS METROS CUBICOS EL PERIODO
	SELECT metros_cubicos INTO metros_cubicos_med$ FROM registro_estado WHERE id_periodo = id_periodo$ AND  id_cuenta = id_cuenta$;
	--OBTENEMOS SUBSIDIO SI APLICA
	SELECT nombre, porcentaje, metros_cubicos_tope INTO nombre_sub$, porcentaje_sub$, metros_cubicos_tope_sub$ 
	FROM subsidio s 
	INNER JOIN cuenta_subsidio cs ON s.id_subsidio =  cs.id_subsidio AND cs.id_cuenta = id_cuenta$;

	monto_descuento_sub$ := 0;
	monto_descuento_int$ := 0;
	sub_total$ 	     := 0;
	descripcion$ := '';
	IF metros_cubicos_med$ IS NOT NULL THEN
		--APLICA CARGO FIJO
		sub_total$ := valor_cargo_fijo$;
		descripcion$ := ' # CALCULO AGUA ->'||metros_cubicos_med$ ||'m3';
		descripcion$ :=  descripcion$  || ' # Aplica cargo fijo '||m3_fijos$||'m3 -> $'||valor_cargo_fijo$;
		--raise info 'descripcion: %', descripcion$;
		IF metros_cubicos_med$ <= m3_fijos$	 THEN	     
		     --CALCULO SUBSIDIO
		     IF nombre_sub$ IS NOT NULL THEN
			descripcion$ :=  descripcion$ || ' # CALCULO SUBSIDIO';
			monto_descuento_sub$ 	:= round(sub_total$*(porcentaje_sub$/100));
			descripcion$ 		:= descripcion$ ||' # Aplica subsidio '||nombre_sub$||' ('||metros_cubicos_tope_sub$ ||'m3 , ' ||porcentaje_sub$ ||'%)  para '|| metros_cubicos_med$ ||'m3 -> $'||monto_descuento_sub$;
			
		     ELSE
			--CALCULO REGLA INTERNA
			IF metros_cubicos_med$ <= m3_limite_dcto_interno$ THEN
				descripcion$ :=  descripcion$ || ' # CALCULO REGLA INTERNA';
				monto_descuento_int$ 	:= round(sub_total$*(porcentaje_dcto_interno$/100));
				descripcion$ 		:= descripcion$ ||' # Aplica descuento interno -> '||porcentaje_dcto_interno$ ||'% descuento ->$'||monto_descuento_int$;
			END IF;
		     END IF;
		ELSE
			--CALCULO POR TRAMOS		 
			LOOP
				FETCH f_tramos$ INTO m3_inicio$, m3_final$, porcentaje_recargo$;    
				EXIT WHEN NOT FOUND;	
				--raise info 'cal id_cuenta$: % ', id_cuenta$;
				existen_tramos$ := true;
				IF metros_cubicos_med$ > m3_inicio$ THEN
					valor_tramo$ 	:= round((valor_m3$*(1+porcentaje_recargo$/100)));
					IF metros_cubicos_med$ >= m3_final$ THEN
						m3_tramo$ 	:=  m3_final$ - m3_inicio$;
					ELSE
						m3_tramo$ 	:= metros_cubicos_med$ - m3_inicio$;					
					END IF;
					sub_total$ 		:= sub_total$ + m3_tramo$ * valor_tramo$;
					/*
					raise info '-AGUA-';
					raise info 'metros_cubicos_med$: % ', metros_cubicos_med$;
					raise info 'm3_inicio$: % ', m3_inicio$;
					raise info 'm3_final$: % ', m3_final$;
					raise info 'm3_tramo$: % ', m3_tramo$;
					raise info 'valor_tramo$: % ', valor_tramo$;
					raise info 'sub_total$: % ', sub_total$;
					*/
				descripcion$ 	:= descripcion$ ||' # Aplica valor por tramo ('||m3_inicio$||'-'||m3_final$||') = ('||m3_tramo$||'m3 * $'||valor_tramo$||') -> $'||(m3_tramo$ * valor_tramo$);
				END IF;				
			END LOOP;	
						
			--EN EL CASO QUE NO EXISTAN TRAMOS, SE CALCULA EL RESTO EN BASE AL VALOR METRO CUBICO FIJO
			IF existen_tramos$ = false  THEN
				m3_tramo$ 	:= metros_cubicos_med$ - m3_fijos$;
				sub_total$ 	:= sub_total$ + m3_tramo$ * valor_m3$;
				descripcion$ 	:= descripcion$ || ' # Aplica valor por tramo ('||m3_tramo$||'m3 * $'||valor_m3$||') -> $'||(m3_tramo$ * valor_m3$);
			END IF;	

			CLOSE f_tramos$;

			OPEN f_tramos$ FOR    
			SELECT m3_inicio, m3_final, porcentaje_recargo FROM valor_tramo_m3 order by m3_inicio, m3_final;
		
			--CALCULAMOS DESCUENTO SUBSIDIO SI ES QUE APLICA
			IF nombre_sub$ IS NOT NULL THEN
				--VERIFICAMSO QUE CANTIDAD DE m3 DEEBN SER CALCULADOS COMO SUBSIDIO
				descripcion$ :=  descripcion$ || ' # CALCULO SUBSIDIO';
				IF metros_cubicos_med$ >=  metros_cubicos_tope_sub$ THEN
					metros_cubicos_aplica_sub$ := metros_cubicos_tope_sub$;
				ELSE
					metros_cubicos_aplica_sub$ := metros_cubicos_med$;
				END IF;
				--raise info 'cal metros_cubicos_aplica_sub$: % ', metros_cubicos_aplica_sub$;
				--CALCULAMOS MONTO SUBSIDIO A m3 DENTRO DEL TOPE
				monto_descuento_sub$ 	:= valor_cargo_fijo$;
				descripcion$ 		:= descripcion$ ||' # Aplica cargo fijo '||m3_fijos$||'m3 -> $'||valor_cargo_fijo$;
				--CALCULO POR TRAMOS		 
				LOOP
					FETCH f_tramos$ INTO m3_inicio$, m3_final$, porcentaje_recargo$;    
					EXIT WHEN NOT FOUND;	
					--raise info 'cal id_cuenta$: % ', id_cuenta$;
					IF metros_cubicos_aplica_sub$ > m3_inicio$ THEN
						IF metros_cubicos_aplica_sub$ >= m3_final$ THEN
							m3_tramo$ 		:=  m3_final$ - m3_inicio$;
							valor_tramo$ 		:= round((valor_m3$*(1+porcentaje_recargo$/100)));
							monto_descuento_sub$ 	:= monto_descuento_sub$ + m3_tramo$ * valor_tramo$;
						ELSE
							m3_tramo$ 		:= metros_cubicos_aplica_sub$ - m3_inicio$;
							valor_tramo$ 		:= round((valor_m3$*(1+porcentaje_recargo$/100)));
							monto_descuento_sub$	:= monto_descuento_sub$ + m3_tramo$ * valor_tramo$;					
						END IF;
						/*
						raise info '-SUBSIDIO-';
						raise info 'metros_cubicos_aplica_sub$: % ', metros_cubicos_aplica_sub$;
						raise info 'm3_inicio$: % ', m3_inicio$;
						raise info 'm3_final$: % ', m3_final$;
						raise info 'm3_tramo$: % ', m3_tramo$;
						raise info 'valor_tramo$: % ', valor_tramo$;
						raise info 'monto_descuento_sub$: % ', monto_descuento_sub$;
						*/
					END IF;
					--descripcion$ 	:= '- Aplica valor por tramo '||m3_tramo$||'m3 -> $'||valor_tramo$||' tramo -> '||(m3_tramo$ * valor_tramo$);
				END LOOP;	
							
				--EN EL CASO QUE NO EXISTAN TRAMOS, SE CALCULA EL RESTO EN BASE AL VALOR METRO CUBICO FIJO
				IF existen_tramos$ = false  THEN
					m3_tramo$ 		:= metros_cubicos_aplica_sub$ - m3_fijos$; 
					monto_descuento_sub$ 	:= monto_descuento_sub$ + m3_tramo$ * valor_m3$;
					--descripcion$ 	:= '- Aplica valor por tramo '||m3_tramo$||'m3 -> $'||valor_m3$||' tramo -> '||(m3_tramo$ * valor_m3$);
				END IF;	
				monto_descuento_sub$ := round(monto_descuento_sub$*(porcentaje_sub$/100));	
				descripcion$ 		:= descripcion$ ||' # Aplica subsidio '||nombre_sub$||' ('||metros_cubicos_tope_sub$ ||'m3 , ' ||porcentaje_sub$ ||'%)  para '|| metros_cubicos_aplica_sub$ ||'m3 -> $'||monto_descuento_sub$;			
			END IF;
	      END IF;
	END IF;

	descripcion$ := descripcion$ ||' ##sub_total:' || sub_total$ || ' #descuento:' || (monto_descuento_sub$ + monto_descuento_int$) ||'#total:'|| sub_total$-(monto_descuento_sub$ + monto_descuento_int$);
	raise info 'cal id_cuenta$: % id_periodo: %', id_cuenta$, id_periodo$;
	--raise info '##sub_total:' || sub_total$ || ' #descuento:' || (monto_descuento_sub$ + monto_descuento_int$) ||'#total:'|| sub_total$-(monto_descuento_sub$ + monto_descuento_int$);
	raise info 'descripcion: %', descripcion$;
	
	descuento$ 	:= (monto_descuento_sub$ + monto_descuento_int$);
	total$ 		:= sub_total$ - descuento$;

	--RETORAN VALORES CALCULADOS
	RETURN QUERY VALUES (sub_total$, descuento$, total$, descripcion$, monto_descuento_sub$, monto_descuento_int$);
	
	EXCEPTION
			WHEN OTHERS THEN
			--RAISE NOTICE  'Falló la llamada a la función id_cuenta: %. El error fue: %',id_cuenta$,SQLERRM;
			RAISE EXCEPTION 'Falló la orden SQL: %.', SQLERRM;
			--return f_return$;
END
$BODY$
  LANGUAGE plpgsql IMMUTABLE;
ALTER FUNCTION fn_calcular_valor_agua_potable(integer,integer)
  OWNER TO postgres;

--select * from fn_calcular_valor_agua_potable(1,1);
--select * from fn_calcular_valor_agua_potable(1,2);
