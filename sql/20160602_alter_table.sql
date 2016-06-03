ALTER TABLE cuenta
  ADD COLUMN informacion_aviso character varying(50) default '';
ALTER TABLE cuenta
  ADD COLUMN aplica_cuota_social boolean DEFAULT true;