-- Table: usuario

-- DROP TABLE usuario;

CREATE TABLE usuario
(
  id_usuario int2vector NOT NULL,
  mail character varying(30) NOT NULL,
  password character varying(10) NOT NULL,
  nombre character varying(20) NOT NULL,
  apellido character varying(20),
  CONSTRAINT usuario_pkey PRIMARY KEY (id_usuario)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE usuario
  OWNER TO postgres;
