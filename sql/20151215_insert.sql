INSERT INTO tipo_movimiento(tipo_movimiento, descripcion) VALUES ('DEPOSITO', 'Suma valores a la cuenta');
INSERT INTO tipo_movimiento(tipo_movimiento, descripcion) VALUES ('RETIRO', 'Resta valores a la cuenta');

INSERT INTO tipo_cobro(id_tipo_cobro, codigo_tipo_cobro, nombre, acepta_pago_cuotas, acepta_registro_cobro, valor) 
VALUES (1, 'CONSDEAGUA', 'CONSUMO AGUA POTABLE', false, false,0);

INSERT INTO tipo_cobro(id_tipo_cobro, codigo_tipo_cobro, nombre, acepta_pago_cuotas, acepta_registro_cobro, valor) 
VALUES (2, 'CUOTSOCIAL', 'CUOTA SOCIAL', false, false,0);

INSERT INTO tipo_cobro(id_tipo_cobro, codigo_tipo_cobro, nombre, acepta_pago_cuotas, acepta_registro_cobro, valor) 
VALUES (3, 'INTERES', 'INTERES Y REAJUSTE', false, false,0);

INSERT INTO tipo_cobro(id_tipo_cobro, codigo_tipo_cobro, nombre, acepta_pago_cuotas, acepta_registro_cobro, valor) 
VALUES (4, 'MULTAOBL', 'MULTA OBLIGATORIA', false, true,0);

INSERT INTO tipo_cobro(id_tipo_cobro, codigo_tipo_cobro, nombre, acepta_pago_cuotas, acepta_registro_cobro, valor) 
VALUES (5, 'MULTANOOBL', 'MULTA NO OBLIGATORIA', false, true,0);

INSERT INTO tipo_cobro(id_tipo_cobro, codigo_tipo_cobro, nombre, acepta_pago_cuotas, acepta_registro_cobro, valor) 
VALUES (6, 'CORTEYREPO', 'CORTE Y REPOSICION', true, true,0);

INSERT INTO tipo_cobro(id_tipo_cobro, codigo_tipo_cobro, nombre, acepta_pago_cuotas, acepta_registro_cobro, valor) 
VALUES (7, 'DEREINCORP', 'DERECHO DE INCORPORACION', true, true,0);

INSERT INTO tipo_cobro(id_tipo_cobro, codigo_tipo_cobro, nombre, acepta_pago_cuotas, acepta_registro_cobro, valor) 
VALUES (8, 'OTROCOBRO', 'OTRO COBRO', true, true,0);



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
