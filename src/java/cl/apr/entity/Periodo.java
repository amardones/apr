/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.apr.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author hmardones
 */
@Entity
@Table(name = "periodo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Periodo.findAll", query = "SELECT p FROM Periodo p"),
    @NamedQuery(name = "Periodo.findByIdPeriodo", query = "SELECT p FROM Periodo p WHERE p.idPeriodo = :idPeriodo"),
    @NamedQuery(name = "Periodo.findByNombre", query = "SELECT p FROM Periodo p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "Periodo.findByFechaInicio", query = "SELECT p FROM Periodo p WHERE p.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "Periodo.findByFechaFin", query = "SELECT p FROM Periodo p WHERE p.fechaFin = :fechaFin"),
    @NamedQuery(name = "Periodo.findByFechaVencimiento", query = "SELECT p FROM Periodo p WHERE p.fechaVencimiento = :fechaVencimiento"),
    @NamedQuery(name = "Periodo.findByFechaTomaLectura", query = "SELECT p FROM Periodo p WHERE p.fechaTomaLectura = :fechaTomaLectura"),
    @NamedQuery(name = "Periodo.findByFechaEmision", query = "SELECT p FROM Periodo p WHERE p.fechaEmision = :fechaEmision")})
public class Periodo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_periodo")
    private Integer idPeriodo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_fin")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_vencimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaVencimiento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_toma_lectura")
    @Temporal(TemporalType.DATE)
    private Date fechaTomaLectura;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_emision")
    @Temporal(TemporalType.DATE)
    private Date fechaEmision;
    @JoinColumn(name = "id_valores_parametricos", referencedColumnName = "id_valores_parametricos")
    @ManyToOne(optional = false)
    private ValoresParametricos idValoresParametricos;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "periodo")
    private List<RegistroEstado> registroEstadoList;
    /*
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "periodo")
    private List<AvisoCobro> avisoCobroList;
*/
    public Periodo() {
    }

    public Periodo(Integer idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public Periodo(Integer idPeriodo, String nombre, Date fechaInicio, Date fechaFin, Date fechaVencimiento, Date fechaTomaLectura, Date fechaEmision) {
        this.idPeriodo = idPeriodo;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.fechaVencimiento = fechaVencimiento;
        this.fechaTomaLectura = fechaTomaLectura;
        this.fechaEmision = fechaEmision;
    }

    public Integer getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(Integer idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public Date getFechaTomaLectura() {
        return fechaTomaLectura;
    }

    public void setFechaTomaLectura(Date fechaTomaLectura) {
        this.fechaTomaLectura = fechaTomaLectura;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public ValoresParametricos getIdValoresParametricos() {
        return idValoresParametricos;
    }

    public void setIdValoresParametricos(ValoresParametricos idValoresParametricos) {
        this.idValoresParametricos = idValoresParametricos;
    }

    @XmlTransient
    public List<RegistroEstado> getRegistroEstadoList() {
        return registroEstadoList;
    }

    public void setRegistroEstadoList(List<RegistroEstado> registroEstadoList) {
        this.registroEstadoList = registroEstadoList;
    }
/*
    @XmlTransient
    public List<AvisoCobro> getAvisoCobroList() {
        return avisoCobroList;
    }

    public void setAvisoCobroList(List<AvisoCobro> avisoCobroList) {
        this.avisoCobroList = avisoCobroList;
    }
*/
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPeriodo != null ? idPeriodo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Periodo)) {
            return false;
        }
        Periodo other = (Periodo) object;
        if ((this.idPeriodo == null && other.idPeriodo != null) || (this.idPeriodo != null && !this.idPeriodo.equals(other.idPeriodo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.apr.entity.Periodo[ idPeriodo=" + idPeriodo + " ]";
    }
    
}
