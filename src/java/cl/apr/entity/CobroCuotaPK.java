/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.apr.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author hmardones
 */
@Embeddable
public class CobroCuotaPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_registro_cobro")
    private int idRegistroCobro;
    @Basic(optional = false)
    @NotNull
    @Column(name = "numero_cuota")
    private int numeroCuota;

    public CobroCuotaPK() {
    }

    public CobroCuotaPK(int idRegistroCobro, int numeroCuota) {
        this.idRegistroCobro = idRegistroCobro;
        this.numeroCuota = numeroCuota;
    }

    public int getIdRegistroCobro() {
        return idRegistroCobro;
    }

    public void setIdRegistroCobro(int idRegistroCobro) {
        this.idRegistroCobro = idRegistroCobro;
    }

    public int getNumeroCuota() {
        return numeroCuota;
    }

    public void setNumeroCuota(int numeroCuota) {
        this.numeroCuota = numeroCuota;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idRegistroCobro;
        hash += (int) numeroCuota;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CobroCuotaPK)) {
            return false;
        }
        CobroCuotaPK other = (CobroCuotaPK) object;
        if (this.idRegistroCobro != other.idRegistroCobro) {
            return false;
        }
        if (this.numeroCuota != other.numeroCuota) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.apr.entity.CobroCuotaPK[ idRegistroCobro=" + idRegistroCobro + ", numeroCuota=" + numeroCuota + " ]";
    }
    
}
