/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.apr.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
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
@Table(name = "registro_estado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RegistroEstado.findAll", query = "SELECT r FROM RegistroEstado r"),
    @NamedQuery(name = "RegistroEstado.findByIdPeriodo", query = "SELECT r FROM RegistroEstado r WHERE r.registroEstadoPK.idPeriodo = :idPeriodo"),
    @NamedQuery(name = "RegistroEstado.findByIdCuenta", query = "SELECT r FROM RegistroEstado r WHERE r.registroEstadoPK.idCuenta = :idCuenta"),
    @NamedQuery(name = "RegistroEstado.findByEstadoAnterior", query = "SELECT r FROM RegistroEstado r WHERE r.estadoAnterior = :estadoAnterior"),
    @NamedQuery(name = "RegistroEstado.findByEstadoActual", query = "SELECT r FROM RegistroEstado r WHERE r.estadoActual = :estadoActual"),
    @NamedQuery(name = "RegistroEstado.findByMetrosCubicos", query = "SELECT r FROM RegistroEstado r WHERE r.metrosCubicos = :metrosCubicos"),
    @NamedQuery(name = "RegistroEstado.findByFechaRegistro", query = "SELECT r FROM RegistroEstado r WHERE r.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "RegistroEstado.findByDescripcion", query = "SELECT r FROM RegistroEstado r WHERE r.descripcion = :descripcion")})
public class RegistroEstado implements Serializable {
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "registroEstado")
    private AvisoCobro avisoCobro;
    @JoinColumn(name = "id_cuenta", referencedColumnName = "id_cuenta", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Cuenta cuenta;
    @JoinColumn(name = "id_periodo", referencedColumnName = "id_periodo", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Periodo periodo;
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RegistroEstadoPK registroEstadoPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_anterior")
    private int estadoAnterior;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_actual")
    private int estadoActual;
    @Basic(optional = false)
    @NotNull
    @Column(name = "metros_cubicos")
    private int metrosCubicos;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Size(max = 100)
    @Column(name = "descripcion")
    private String descripcion;

    public RegistroEstado() {
    }

    public RegistroEstado(RegistroEstadoPK registroEstadoPK) {
        this.registroEstadoPK = registroEstadoPK;
    }

    public RegistroEstado(RegistroEstadoPK registroEstadoPK, int estadoAnterior, int estadoActual, int metrosCubicos) {
        this.registroEstadoPK = registroEstadoPK;
        this.estadoAnterior = estadoAnterior;
        this.estadoActual = estadoActual;
        this.metrosCubicos = metrosCubicos;
    }

    public RegistroEstado(int idPeriodo, int idCuenta) {
        this.registroEstadoPK = new RegistroEstadoPK(idPeriodo, idCuenta);
    }

    public RegistroEstadoPK getRegistroEstadoPK() {
        return registroEstadoPK;
    }

    public void setRegistroEstadoPK(RegistroEstadoPK registroEstadoPK) {
        this.registroEstadoPK = registroEstadoPK;
    }

    public int getEstadoAnterior() {
        return estadoAnterior;
    }

    public void setEstadoAnterior(int estadoAnterior) {
        this.estadoAnterior = estadoAnterior;
    }

    public int getEstadoActual() {
        return estadoActual;
    }

    public void setEstadoActual(int estadoActual) {
        this.estadoActual = estadoActual;
    }

    public int getMetrosCubicos() {
        return metrosCubicos;
    }

    public void setMetrosCubicos(int metrosCubicos) {
        this.metrosCubicos = metrosCubicos;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (registroEstadoPK != null ? registroEstadoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RegistroEstado)) {
            return false;
        }
        RegistroEstado other = (RegistroEstado) object;
        if ((this.registroEstadoPK == null && other.registroEstadoPK != null) || (this.registroEstadoPK != null && !this.registroEstadoPK.equals(other.registroEstadoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.apr.entity.RegistroEstado[ registroEstadoPK=" + registroEstadoPK + " ]";
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public AvisoCobro getAvisoCobro() {
        return avisoCobro;
    }

    public void setAvisoCobro(AvisoCobro avisoCobro) {
        this.avisoCobro = avisoCobro;
    }
    
}
