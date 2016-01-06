/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.apr.beans;

/**
 *
 * @author Matias
 */
public class cuentaSubsidiada {
    private Integer idCuenta;
    private String rut;
    private String nombre;
    private String direccion;
    private Integer monto=0;
    private Integer porcentaje=0;
    private String nombreSubsidio;
   public cuentaSubsidiada(){        
    }

    public String getNombreSubsidio() {
        return nombreSubsidio;
    }

    public void setNombreSubsidio(String nombreSubsidio) {
        this.nombreSubsidio = nombreSubsidio;
    }

    public Integer getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(Integer idCuenta) {
        this.idCuenta = idCuenta;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Integer getMonto() {
        return monto;
    }

    public void setMonto(Integer monto) {
        this.monto = monto;
    }

    public Integer getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Integer porcentaje) {
        this.porcentaje = porcentaje;
    }
   
}
