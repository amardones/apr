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
public class RegistroEstadoPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_periodo")
    private int idPeriodo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_cuenta")
    private int idCuenta;

    public RegistroEstadoPK() {
    }

    public RegistroEstadoPK(int idPeriodo, int idCuenta) {
        this.idPeriodo = idPeriodo;
        this.idCuenta = idCuenta;
    }

    public int getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(int idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public int getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(int idCuenta) {
        this.idCuenta = idCuenta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idPeriodo;
        hash += (int) idCuenta;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RegistroEstadoPK)) {
            return false;
        }
        RegistroEstadoPK other = (RegistroEstadoPK) object;
        if (this.idPeriodo != other.idPeriodo) {
            return false;
        }
        if (this.idCuenta != other.idCuenta) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.apr.entity.RegistroEstadoPK[ idPeriodo=" + idPeriodo + ", idCuenta=" + idCuenta + " ]";
    }
    
}
