ALTER TABLE aviso_cobro
  ADD COLUMN informacion_aviso character varying(80) DEFAULT '';

ALTER TABLE cuenta
  DROP COLUMN informacion_aviso;