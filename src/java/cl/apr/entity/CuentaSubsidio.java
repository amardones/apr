/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.apr.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author hmardones
 */
@Entity
@Table(name = "cuenta_subsidio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CuentaSubsidio.findAll", query = "SELECT c FROM CuentaSubsidio c"),
    @NamedQuery(name = "CuentaSubsidio.findByIdCuenta", query = "SELECT c FROM CuentaSubsidio c WHERE c.idCuenta = :idCuenta")})
public class CuentaSubsidio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_cuenta")
    private Integer idCuenta;
    @JoinColumn(name = "id_cuenta", referencedColumnName = "id_cuenta", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Cuenta cuenta;
    @JoinColumn(name = "id_subsidio", referencedColumnName = "id_subsidio")
    @ManyToOne(optional = false)
    private Subsidio idSubsidio;

    public CuentaSubsidio() {
    }

    public CuentaSubsidio(Integer idCuenta) {
        this.idCuenta = idCuenta;
    }

    public Integer getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(Integer idCuenta) {
        this.idCuenta = idCuenta;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public Subsidio getIdSubsidio() {
        return idSubsidio;
    }

    public void setIdSubsidio(Subsidio idSubsidio) {
        this.idSubsidio = idSubsidio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCuenta != null ? idCuenta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CuentaSubsidio)) {
            return false;
        }
        CuentaSubsidio other = (CuentaSubsidio) object;
        if ((this.idCuenta == null && other.idCuenta != null) || (this.idCuenta != null && !this.idCuenta.equals(other.idCuenta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.apr.entity.CuentaSubsidio[ idCuenta=" + idCuenta + " ]";
    }
    
}
