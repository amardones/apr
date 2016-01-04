/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.apr.beans;

import cl.apr.entity.Cuenta;

/**
 *
 * @author Matias
 */
public class ItemReporte {
    private Integer idPago;
    private String cuenta;
    private String numeroDocumento;
    private Integer totalItem=0;
    private Integer consumoAgua=0;
    private Integer cuotaSocial=0;
    private Integer interes=0;
    private Integer multas=0;
    private Integer corteReposicion=0;
    private Integer derechoIncorporacion=0;
    private Integer otrosCobros=0;
    private String fechaCreacion;
    
    public ItemReporte(){
        
    }

    public Integer getIdPago() {
        return idPago;
    }

    public void setIdPago(Integer idPago) {
        this.idPago = idPago;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public Integer getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(Integer totalItem) {
        this.totalItem = totalItem;
    }

    public Integer getConsumoAgua() {
        return consumoAgua;
    }

    public void setConsumoAgua(Integer consumoAgua) {
        this.consumoAgua = consumoAgua;
    }

    public Integer getCuotaSocial() {
        return cuotaSocial;
    }

    public void setCuotaSocial(Integer cuotaSocial) {
        this.cuotaSocial = cuotaSocial;
    }

    public Integer getInteres() {
        return interes;
    }

    public void setInteres(Integer interes) {
        this.interes = interes;
    }

    public Integer getMultas() {
        return multas;
    }

    public void setMultas(Integer multas) {
        this.multas = multas;
    }

    public Integer getCorteReposicion() {
        return corteReposicion;
    }

    public void setCorteReposicion(Integer corteReposicion) {
        this.corteReposicion = corteReposicion;
    }

    public Integer getDerechoIncorporacion() {
        return derechoIncorporacion;
    }

    public void setDerechoIncorporacion(Integer derechoIncorporacion) {
        this.derechoIncorporacion = derechoIncorporacion;
    }

    public Integer getOtrosCobros() {
        return otrosCobros;
    }

    public void setOtrosCobros(Integer otrosCobros) {
        this.otrosCobros = otrosCobros;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

   
    
    
}

