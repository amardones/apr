/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.apr.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author hmardones
 */
@Entity
@Table(name = "boleta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Boleta.findAll", query = "SELECT b FROM Boleta b"),
    @NamedQuery(name = "Boleta.findByIdBoleta", query = "SELECT b FROM Boleta b WHERE b.idBoleta = :idBoleta"),
    @NamedQuery(name = "Boleta.findByNumeroBoleta", query = "SELECT b FROM Boleta b WHERE b.numeroBoleta = :numeroBoleta"),
    @NamedQuery(name = "Boleta.findByFechaCreacion", query = "SELECT b FROM Boleta b WHERE b.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "Boleta.findByTotal", query = "SELECT b FROM Boleta b WHERE b.total = :total")})
public class Boleta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_boleta")
    private Integer idBoleta;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "numero_boleta")
    private String numeroBoleta;
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "total")
    private int total;
    @JoinColumns({
        @JoinColumn(name = "id_periodo", referencedColumnName = "id_periodo"),
        @JoinColumn(name = "id_cuenta", referencedColumnName = "id_cuenta")})
    @ManyToOne(optional = false)
    private AvisoCobro avisoCobro;

    public Boleta() {
    }

    public Boleta(Integer idBoleta) {
        this.idBoleta = idBoleta;
    }

    public Boleta(Integer idBoleta, String numeroBoleta, int total) {
        this.idBoleta = idBoleta;
        this.numeroBoleta = numeroBoleta;
        this.total = total;
    }

    public Integer getIdBoleta() {
        return idBoleta;
    }

    public void setIdBoleta(Integer idBoleta) {
        this.idBoleta = idBoleta;
    }

    public String getNumeroBoleta() {
        return numeroBoleta;
    }

    public void setNumeroBoleta(String numeroBoleta) {
        this.numeroBoleta = numeroBoleta;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public AvisoCobro getAvisoCobro() {
        return avisoCobro;
    }

    public void setAvisoCobro(AvisoCobro avisoCobro) {
        this.avisoCobro = avisoCobro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBoleta != null ? idBoleta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Boleta)) {
            return false;
        }
        Boleta other = (Boleta) object;
        if ((this.idBoleta == null && other.idBoleta != null) || (this.idBoleta != null && !this.idBoleta.equals(other.idBoleta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.apr.entity.Boleta[ idBoleta=" + idBoleta + " ]";
    }
    
}
