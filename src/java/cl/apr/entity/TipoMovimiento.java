/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.apr.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author hmardones
 */
@Entity
@Table(name = "tipo_movimiento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoMovimiento.findAll", query = "SELECT t FROM TipoMovimiento t"),
    @NamedQuery(name = "TipoMovimiento.findByTipoMovimiento", query = "SELECT t FROM TipoMovimiento t WHERE t.tipoMovimiento = :tipoMovimiento"),
    @NamedQuery(name = "TipoMovimiento.findByDescripcion", query = "SELECT t FROM TipoMovimiento t WHERE t.descripcion = :descripcion")})
public class TipoMovimiento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "tipo_movimiento")
    private String tipoMovimiento;
    @Size(max = 30)
    @Column(name = "descripcion")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoMovimiento")
    private List<SaldoCuenta> saldoCuentaList;

    public TipoMovimiento() {
    }

    public TipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public List<SaldoCuenta> getSaldoCuentaList() {
        return saldoCuentaList;
    }

    public void setSaldoCuentaList(List<SaldoCuenta> saldoCuentaList) {
        this.saldoCuentaList = saldoCuentaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tipoMovimiento != null ? tipoMovimiento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoMovimiento)) {
            return false;
        }
        TipoMovimiento other = (TipoMovimiento) object;
        if ((this.tipoMovimiento == null && other.tipoMovimiento != null) || (this.tipoMovimiento != null && !this.tipoMovimiento.equals(other.tipoMovimiento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.apr.entity.TipoMovimiento[ tipoMovimiento=" + tipoMovimiento + " ]";
    }
    
}
