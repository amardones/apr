/*==============================================================*/
/* DBMS name:      PostgreSQL 8                                 */
/* Created on:     06-12-2015 16:44:31                          */
/*==============================================================*/

/*
drop table AVISO_COBRO;

drop table BOLETA;

drop table COBRO_CUOTA;

drop table CUENTA;

drop table CUENTA_SUBSIDIO;

drop table DETALLE_AVISO_COBRO;

drop table MEDIDOR;

drop table PERIODO;

drop table REGISTRO_COBRO;

drop table REGISTRO_COBRO_CUOTA;

drop table REGISTRO_ESTADO;

drop table SALDO_CUENTA;

drop table SOCIO;

drop table SUBSIDIO;

drop table TIPO_COBRO;

drop table TIPO_MOVIMIENTO;

drop table VALORES_PARAMETRICOS;

drop table VALOR_TRAMO_M3;
*/
/*==============================================================*/
/* Table: AVISO_COBRO                                           */
/*==============================================================*/
create table AVISO_COBRO (
   ID_PERIODO           INT4                 not null,
   ID_CUENTA            INT4                 not null,
   TOTAL_PERIODO        INT4                 not null,
   TOTAL_PENDIENTE      INT4                 not null,
   TOTAL                INT4                 not null,
   FECHA_CREACION       TIMESTAMP WITH TIME ZONE null,
   constraint PK_AVISO_COBRO primary key (ID_PERIODO, ID_CUENTA)
);

/*==============================================================*/
/* Table: BOLETA                                                */
/*==============================================================*/
create table BOLETA (
   ID_BOLETA            SERIAL               not null,
   ID_PERIODO           INT4                 not null,
   ID_CUENTA            INT4                 not null,
   NUMERO_BOLETA        VARCHAR(15)          not null,
   FECHA_CREACION       TIMESTAMP WITH TIME ZONE null,
   TOTAL                INT4                 not null,
   constraint PK_BOLETA primary key (ID_BOLETA)
);

/*==============================================================*/
/* Table: COBRO_CUOTA                                           */
/*==============================================================*/
create table COBRO_CUOTA (
   ID_REGISTRO_COBRO    INT4                 not null,
   NUMERO_CUOTA         INT4                 not null,
   VALOR_CUOTA          INT4                 not null,
   MES                  INT4                 not null,
   ANIO                 INT4                 not null,
   PAGADO               BOOL                 not null,
   constraint PK_COBRO_CUOTA primary key (NUMERO_CUOTA, ID_REGISTRO_COBRO)
);

/*==============================================================*/
/* Table: CUENTA                                                */
/*==============================================================*/
create table CUENTA (
   ID_CUENTA            SERIAL               not null,
   RUT                  VARCHAR(15)          not null,
   NUMERO_MEDIDOR       VARCHAR(20)          not null,
   DIRECCION            VARCHAR(50)          not null,
   GPS_LATITUD          NUMERIC              null,
   GPS_LONGITUD         NUMERIC              null,
   FECHA_CREACION       DATE                 null,
   ACTIVA               BOOL                 not null,
   constraint PK_CUENTA primary key (ID_CUENTA)
);

/*==============================================================*/
/* Table: CUENTA_SUBSIDIO                                       */
/*==============================================================*/
create table CUENTA_SUBSIDIO (
   ID_CUENTA            INT4                 not null,
   ID_SUBSIDIO          INT4                 not null,
   constraint PK_CUENTA_SUBSIDIO primary key (ID_CUENTA)
);

/*==============================================================*/
/* Table: DETALLE_AVISO_COBRO                                   */
/*==============================================================*/
create table DETALLE_AVISO_COBRO (
   ID_DETALLE_AVISO_COBRO SERIAL               not null,
   ID_PERIODO           INT4                 not null,
   ID_CUENTA            INT4                 not null,
   ID_TIPO_COBRO        INT4                 not null,
   ID_DETALLE_AVISO_COBRO_ANT INT4                 not null,
   SUB_TOTAL            INT4                 not null,
   DESCUENTO            INT4                 not null,
   TOTAL                INT4                 not null,
   DESCRIPCION          VARCHAR(500)         null,
   PAGADO               BOOL                 not null,
   constraint PK_DETALLE_AVISO_COBRO primary key (ID_DETALLE_AVISO_COBRO)
);

/*==============================================================*/
/* Table: MEDIDOR                                               */
/*==============================================================*/
create table MEDIDOR (
   NUMERO_MEDIDOR       VARCHAR(20)          not null,
   DESCRIPCION          VARCHAR(100)         null,
   constraint PK_MEDIDOR primary key (NUMERO_MEDIDOR)
);

/*==============================================================*/
/* Table: PERIODO                                               */
/*==============================================================*/
create table PERIODO (
   ID_PERIODO           SERIAL               not null,
   NOMBRE               VARCHAR(20)          not null,
   ID_VALORES_PARAMETRICOS INT4                 not null,
   FECHA_INICIO         DATE                 not null,
   FECHA_FIN            DATE                 not null,
   FECHA_VENCIMIENTO    DATE                 not null,
   FECHA_TOMA_LECTURA   DATE                 not null,
   FECHA_EMISION        DATE                 not null,
   constraint PK_PERIODO primary key (ID_PERIODO)
);

/*==============================================================*/
/* Table: REGISTRO_COBRO                                        */
/*==============================================================*/
create table REGISTRO_COBRO (
   ID_REGISTRO_COBRO    SERIAL               not null,
   ID_CUENTA            INT4                 not null,
   ID_TIPO_COBRO        INT4                 not null,
   CUOTAS               INT4                 not null,
   MONTO                INT4                 not null,
   DESCRIPCION          VARCHAR(50)          null,
   FECHA_CREACION       DATE                 null,
   MES_PRIMERA_CUOTA    INT4                 not null,
   constraint PK_REGISTRO_COBRO primary key (ID_REGISTRO_COBRO)
);

/*==============================================================*/
/* Table: REGISTRO_COBRO_CUOTA                                  */
/*==============================================================*/
create table REGISTRO_COBRO_CUOTA (
   ID_REGISTRO_COBRO    INT4                 not null,
   NUMERO_CUOTA         INT4                 not null,
   ID_DETALLE_AVISO_COBRO INT4                 not null,
   constraint PK_REGISTRO_COBRO_CUOTA primary key (NUMERO_CUOTA, ID_REGISTRO_COBRO, ID_DETALLE_AVISO_COBRO)
);

/*==============================================================*/
/* Table: REGISTRO_ESTADO                                       */
/*==============================================================*/
create table REGISTRO_ESTADO (
   ID_PERIODO           INT4                 not null,
   ID_CUENTA            INT4                 not null,
   ESTADO_ANTERIOR      INT4                 not null,
   ESTADO_ACTUAL        INT4                 not null,
   METROS_CUBICOS       INT4                 not null,
   FECHA_REGISTRO       TIMESTAMP WITH TIME ZONE null,
   DESCRIPCION          VARCHAR(100)         null,
   constraint PK_REGISTRO_ESTADO primary key (ID_PERIODO, ID_CUENTA)
);

/*==============================================================*/
/* Table: SALDO_CUENTA                                          */
/*==============================================================*/
create table SALDO_CUENTA (
   ID_SALDO_CUENTA      SERIAL               not null,
   ID_CUENTA            INT4                 not null,
   TIPO_MOVIMIENTO      VARCHAR(10)          not null,
   DESCRIPCION          VARCHAR(50)          null,
   FECHA                TIMESTAMP WITH TIME ZONE null,
   MONTO                INT4                 not null,
   constraint PK_SALDO_CUENTA primary key (ID_SALDO_CUENTA)
);

/*==============================================================*/
/* Table: SOCIO                                                 */
/*==============================================================*/
create table SOCIO (
   RUT                  VARCHAR(15)          not null,
   NOMBRE               VARCHAR(20)          not null,
   APELLIDO             VARCHAR(20)          not null,
   CELULAR              VARCHAR(15)          null,
   DIRECCION            VARCHAR(50)          null,
   constraint PK_SOCIO primary key (RUT)
);

/*==============================================================*/
/* Table: SUBSIDIO                                              */
/*==============================================================*/
create table SUBSIDIO (
   ID_SUBSIDIO          SERIAL               not null,
   NOMBRE               VARCHAR(20)          not null,
   DESCRIPCION          VARCHAR(100)         null,
   PORCENTAJE           NUMERIC              not null,
   METROS_CUBICOS_TOPE  INT4                 not null,
   constraint PK_SUBSIDIO primary key (ID_SUBSIDIO)
);

/*==============================================================*/
/* Table: TIPO_COBRO                                            */
/*==============================================================*/
create table TIPO_COBRO (
   ID_TIPO_COBRO        INT4                 not null,
   CODIGO_TIPO_COBRO    VARCHAR(10)          not null,
   NOMBRE               VARCHAR(50)          not null,
   ACEPTA_PAGO_CUOTAS   BOOL                 not null,
   ACEPTA_REGISTRO_COBRO BOOL                 not null,
   constraint PK_TIPO_COBRO primary key (ID_TIPO_COBRO)
);

/*==============================================================*/
/* Table: TIPO_MOVIMIENTO                                       */
/*==============================================================*/
create table TIPO_MOVIMIENTO (
   TIPO_MOVIMIENTO      VARCHAR(10)          not null,
   DESCRIPCION          VARCHAR(30)          null,
   constraint PK_TIPO_MOVIMIENTO primary key (TIPO_MOVIMIENTO)
);

/*==============================================================*/
/* Table: VALORES_PARAMETRICOS                                  */
/*==============================================================*/
create table VALORES_PARAMETRICOS (
   ID_VALORES_PARAMETRICOS SERIAL               not null,
   VALOR_CARGO_FIJO     INT4                 not null,
   VALOR_M3             INT4                 not null,
   VALOR_CUOTA_SOCIAL   INT4                 not null,
   VALOR_INTERES_DIA    INT4                 not null,
   VALOR_REUNION_OBLIGATORIA INT4                 not null,
   VALOR_REUNION_NO_OBLIGATORIA INT4                 not null,
   M3_FIJOS             INT4                 not null,
   M3_LIMITE_DCTO_INTERNO INT4                 not null,
   PORCENTAJE_DCTO_INTERNO NUMERIC              not null,
   FECHA_ACTUALIZACION  TIMESTAMP WITH TIME ZONE null,
   DESCRIPCION_CAMBIOS  VARCHAR(50)          null,
   DIA_VENCIMIENTO      INT4                 not null,
   DIA_LECTURA_MEDIDOR  INT4                 not null,
   DIA_EMISION          INT4                 not null,
   constraint PK_VALORES_PARAMETRICOS primary key (ID_VALORES_PARAMETRICOS)
);

/*==============================================================*/
/* Table: VALOR_TRAMO_M3                                        */
/*==============================================================*/
create table VALOR_TRAMO_M3 (
   ID_VALOR_TRAMO       SERIAL               not null,
   NOMBRE_TRAMO         VARCHAR(10)          not null,
   M3_INICIO            INT4                 not null,
   M3_FINAL             INT4                 not null,
   PORCENTAJE_RECARGO   NUMERIC              not null,
   constraint PK_VALOR_TRAMO_M3 primary key (ID_VALOR_TRAMO)
);

alter table AVISO_COBRO
   add constraint FK_AVISO_CO_REFERENCE_REGISTRO foreign key (ID_PERIODO, ID_CUENTA)
      references REGISTRO_ESTADO (ID_PERIODO, ID_CUENTA)
      on delete restrict on update restrict;

alter table BOLETA
   add constraint FK_BOLETA_REFERENCE_AVISO_CO foreign key (ID_PERIODO, ID_CUENTA)
      references AVISO_COBRO (ID_PERIODO, ID_CUENTA)
      on delete restrict on update restrict;

alter table COBRO_CUOTA
   add constraint FK_COBRO_CU_REFERENCE_REGISTRO foreign key (ID_REGISTRO_COBRO)
      references REGISTRO_COBRO (ID_REGISTRO_COBRO)
      on delete restrict on update restrict;

alter table CUENTA
   add constraint FK_CUENTA_REFERENCE_SOCIO foreign key (RUT)
      references SOCIO (RUT)
      on delete restrict on update restrict;

alter table CUENTA
   add constraint FK_CUENTA_REFERENCE_MEDIDOR foreign key (NUMERO_MEDIDOR)
      references MEDIDOR (NUMERO_MEDIDOR)
      on delete restrict on update restrict;

alter table CUENTA_SUBSIDIO
   add constraint FK_CUENTA_S_REFERENCE_SUBSIDIO foreign key (ID_SUBSIDIO)
      references SUBSIDIO (ID_SUBSIDIO)
      on delete restrict on update restrict;

alter table CUENTA_SUBSIDIO
   add constraint FK_CUENTA_S_REFERENCE_CUENTA foreign key (ID_CUENTA)
      references CUENTA (ID_CUENTA)
      on delete restrict on update restrict;

alter table DETALLE_AVISO_COBRO
   add constraint FK_DETALLE__REFERENCE_TIPO_COB foreign key (ID_TIPO_COBRO)
      references TIPO_COBRO (ID_TIPO_COBRO)
      on delete restrict on update restrict;

alter table DETALLE_AVISO_COBRO
   add constraint FK_DETALLE__REFERENCE_AVISO_CO foreign key (ID_PERIODO, ID_CUENTA)
      references AVISO_COBRO (ID_PERIODO, ID_CUENTA)
      on delete restrict on update restrict;

alter table PERIODO
   add constraint FK_PERIODO_REFERENCE_VALORES_ foreign key (ID_VALORES_PARAMETRICOS)
      references VALORES_PARAMETRICOS (ID_VALORES_PARAMETRICOS)
      on delete restrict on update restrict;

alter table REGISTRO_COBRO
   add constraint FK_REGISTRO_REFERENCE_CUENTA foreign key (ID_CUENTA)
      references CUENTA (ID_CUENTA)
      on delete restrict on update restrict;

alter table REGISTRO_COBRO
   add constraint FK_REGISTRO_REFERENCE_TIPO_COB foreign key (ID_TIPO_COBRO)
      references TIPO_COBRO (ID_TIPO_COBRO)
      on delete restrict on update restrict;

alter table REGISTRO_COBRO_CUOTA
   add constraint FK_REGISTRO_REFERENCE_DETALLE_ foreign key (ID_DETALLE_AVISO_COBRO)
      references DETALLE_AVISO_COBRO (ID_DETALLE_AVISO_COBRO)
      on delete restrict on update restrict;

alter table REGISTRO_COBRO_CUOTA
   add constraint FK_REGISTRO_REFERENCE_COBRO_CU foreign key (NUMERO_CUOTA, ID_REGISTRO_COBRO)
      references COBRO_CUOTA (NUMERO_CUOTA, ID_REGISTRO_COBRO)
      on delete restrict on update restrict;

alter table REGISTRO_ESTADO
   add constraint FK_REGISTRO_REFERENCE_PERIODO foreign key (ID_PERIODO)
      references PERIODO (ID_PERIODO)
      on delete restrict on update restrict;

alter table REGISTRO_ESTADO
   add constraint FK_REGISTRO_REFERENCE_CUENTA foreign key (ID_CUENTA)
      references CUENTA (ID_CUENTA)
      on delete restrict on update restrict;

alter table SALDO_CUENTA
   add constraint FK_SALDO_CU_REFERENCE_CUENTA foreign key (ID_CUENTA)
      references CUENTA (ID_CUENTA)
      on delete restrict on update restrict;

alter table SALDO_CUENTA
   add constraint FK_SALDO_CU_REFERENCE_TIPO_MOV foreign key (TIPO_MOVIMIENTO)
      references TIPO_MOVIMIENTO (TIPO_MOVIMIENTO)
      on delete restrict on update restrict;

