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
@Table(name = "medidor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Medidor.findAll", query = "SELECT m FROM Medidor m"),
    @NamedQuery(name = "Medidor.findByNumeroMedidor", query = "SELECT m FROM Medidor m WHERE m.numeroMedidor = :numeroMedidor"),
    @NamedQuery(name = "Medidor.findByDescripcion", query = "SELECT m FROM Medidor m WHERE m.descripcion = :descripcion")})
public class Medidor implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "numero_medidor")
    private String numeroMedidor;
    @Size(max = 100)
    @Column(name = "descripcion")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "numeroMedidor")
    private List<Cuenta> cuentaList;

    public Medidor() {
    }

    public Medidor(String numeroMedidor) {
        this.numeroMedidor = numeroMedidor;
    }

    public String getNumeroMedidor() {
        return numeroMedidor;
    }

    public void setNumeroMedidor(String numeroMedidor) {
        this.numeroMedidor = numeroMedidor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public List<Cuenta> getCuentaList() {
        return cuentaList;
    }

    public void setCuentaList(List<Cuenta> cuentaList) {
        this.cuentaList = cuentaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numeroMedidor != null ? numeroMedidor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Medidor)) {
            return false;
        }
        Medidor other = (Medidor) object;
        if ((this.numeroMedidor == null && other.numeroMedidor != null) || (this.numeroMedidor != null && !this.numeroMedidor.equals(other.numeroMedidor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.apr.entity.Medidor[ numeroMedidor=" + numeroMedidor + " ]";
    }
    
}
