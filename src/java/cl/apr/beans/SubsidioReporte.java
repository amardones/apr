/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.apr.beans;

import cl.apr.entity.Cuenta;
import cl.apr.entity.Periodo;

/**
 *
 * @author Matias
 */
public class SubsidioReporte {  


    private String idPeriodo;
    private String idcuenta;   
    private String fechaCreacion;
    private Integer descuento_periodo;
    
    public SubsidioReporte(){
        
    }

    public String getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(String idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public String getIdcuenta() {
        return idcuenta;
    }

    public void setIdcuenta(String idcuenta) {
        this.idcuenta = idcuenta;
    }


    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Integer getDescuento_periodo() {
        return descuento_periodo;
    }

    public void setDescuento_periodo(Integer descuento_periodo) {
        this.descuento_periodo = descuento_periodo;
    }
    
}

