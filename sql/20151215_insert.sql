INSERT INTO tipo_movimiento(tipo_movimiento, descripcion) VALUES ('DEPOSITO', 'Suma valores a la cuenta');
INSERT INTO tipo_movimiento(tipo_movimiento, descripcion) VALUES ('RETIRO', 'Resta valores a la cuenta');

INSERT INTO tipo_cobro(id_tipo_cobro, codigo_tipo_cobro, nombre, acepta_pago_cuotas, acepta_registro_cobro) 
VALUES (1, 'CONSDEAGUA', 'CONSUMO AGUA POTABLE', false, false);

INSERT INTO tipo_cobro(id_tipo_cobro, codigo_tipo_cobro, nombre, acepta_pago_cuotas, acepta_registro_cobro) 
VALUES (2, 'CUOTSOCIAL', 'CUOTA SOCIAL', false, false);

INSERT INTO tipo_cobro(id_tipo_cobro, codigo_tipo_cobro, nombre, acepta_pago_cuotas, acepta_registro_cobro) 
VALUES (3, 'INTERES', 'INTERES Y REAJUSTE', false, false);

INSERT INTO tipo_cobro(id_tipo_cobro, codigo_tipo_cobro, nombre, acepta_pago_cuotas, acepta_registro_cobro) 
VALUES (4, 'MULTAOBL', 'MULTA OBLIGATORIA', false, true);

INSERT INTO tipo_cobro(id_tipo_cobro, codigo_tipo_cobro, nombre, acepta_pago_cuotas, acepta_registro_cobro) 
VALUES (5, 'MULTANOOBL', 'MULTA NO OBLIGATORIA', false, true);

INSERT INTO tipo_cobro(id_tipo_cobro, codigo_tipo_cobro, nombre, acepta_pago_cuotas, acepta_registro_cobro) 
VALUES (6, 'CORTEYREPO', 'CORTE Y REPOSICION', true, true);

INSERT INTO tipo_cobro(id_tipo_cobro, codigo_tipo_cobro, nombre, acepta_pago_cuotas, acepta_registro_cobro) 
VALUES (7, 'DEREINCORP', 'DERECHO DE INCORPORACION', true, true);

INSERT INTO tipo_cobro(id_tipo_cobro, codigo_tipo_cobro, nombre, acepta_pago_cuotas, acepta_registro_cobro) 
VALUES (8, 'OTROCOBRO', 'OTRO COBRO', true, true);



/*
CONSUMO AGUA POTABLE
CUOTA SOCIAL
INTERES Y REAJUSTE
MULTA OBLIGATORIA
MULTA NO OBLIGATORIA
CORTE Y REPOSICION
DERECHO DE INCORPORACION
OTRAS
*/
