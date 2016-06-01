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
public class RegistroEstadoReporte {
    private String periodo;
    private String cuenta;
    private double estadoAnterior;
    private double estadoActual;
    private double metroCubico;
    
    public RegistroEstadoReporte(){
        
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public double getEstadoAnterior() {
        return estadoAnterior;
    }

    public void setEstadoAnterior(double estadoAnterior) {
        this.estadoAnterior = estadoAnterior;
    }

    public double getEstadoAcutal() {
        return estadoActual;
    }

    public void setEstadoActual(double estadoAcutal) {
        this.estadoActual = estadoAcutal;
    }

    public double getMetroCubico() {
        return metroCubico;
    }

    public void setMetroCubico(double metroCubico) {
        this.metroCubico = metroCubico;
    }
    
}
