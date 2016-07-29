INSERT INTO tipo_movimiento(tipo_movimiento, descripcion) VALUES ('DEPOSITO', 'Suma valores a la cuenta');
INSERT INTO tipo_movimiento(tipo_movimiento, descripcion) VALUES ('RETIRO', 'Resta valores a la cuenta');

INSERT INTO tipo_cobro(id_tipo_cobro, codigo_tipo_cobro, nombre, acepta_pago_cuotas, acepta_registro_cobro, valor) 
VALUES (1, 'CONSDEAGUA', 'CONSUMO AGUA POTABLE', false, false,0);

INSERT INTO tipo_cobro(id_tipo_cobro, codigo_tipo_cobro, nombre, acepta_pago_cuotas, acepta_registro_cobro, valor) 
VALUES (2, 'CUOTSOCIAL', 'CUOTA SOCIAL', false, false,100);

INSERT INTO tipo_cobro(id_tipo_cobro, codigo_tipo_cobro, nombre, acepta_pago_cuotas, acepta_registro_cobro, valor) 
VALUES (3, 'INTERES', 'INTERES Y REAJUSTE', false, false,100);

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

INSERT INTO usuario( email, password, nombre, apellido)
    VALUES ('marcela@ubiobio.cl', 'marcela', 'Marcela', 'Pinto');

INSERT INTO valores_parametricos(
            id_valores_parametricos, valor_cargo_fijo, valor_m3, m3_fijos, 
            m3_limite_dcto_interno, porcentaje_dcto_interno, fecha_actualizacion, 
            descripcion_cambios, dia_vencimiento, dia_lectura_medidor, dia_emision)
    VALUES ('10', '3300', '330', '10', 
            '1','50','2015-12-20 23:53:19.418-03', 
            '', '20','3', '5');
INSERT INTO datos_comite(
            nombre, codigo, dato)
    VALUES ('NOMBRE COMITE','CAP','COMITE AGUA POTABLE QUILELTO EL MANZANAL');
INSERT INTO datos_comite(
            nombre, codigo, dato)
    VALUES ('TELFONO','TLF','76310437 - 97667763');
INSERT INTO datos_comite(
            nombre, codigo, dato)
    VALUES ('EMAIL','EMAIL','mail@comite.cl');
INSERT INTO datos_comite(
            nombre, codigo, dato)
    VALUES ('ATENCIÓN','ATC','LUNES, MIERCOLES Y VIERNES DE 09:00 a 13:00 HRS.');

INSERT INTO datos_comite(
            nombre, codigo, dato)
    VALUES ('IFORMACIÓN GENERAL 1','INFGEN1','Recuerde mantener sus cuentas al día para brindarle un mejor servicio');

INSERT INTO datos_comite(
            nombre, codigo, dato)
    VALUES ('IFORMACIÓN GENERAL 2','INFGEN2','RECUERDE PROTEGER SU MEDIDOR DE LAS HELADAS');

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
