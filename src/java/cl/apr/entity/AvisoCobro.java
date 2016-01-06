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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author hmardones
 */
@Entity
@Table(name = "aviso_cobro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AvisoCobro.findAll", query = "SELECT a FROM AvisoCobro a"),
    @NamedQuery(name = "AvisoCobro.findByIdPeriodo", query = "SELECT a FROM AvisoCobro a WHERE a.avisoCobroPK.idPeriodo = :idPeriodo"),
    @NamedQuery(name = "AvisoCobro.findByIdCuenta", query = "SELECT a FROM AvisoCobro a WHERE a.avisoCobroPK.idCuenta = :idCuenta"),
    @NamedQuery(name = "AvisoCobro.findByTotalPeriodo", query = "SELECT a FROM AvisoCobro a WHERE a.totalPeriodo = :totalPeriodo"),
    @NamedQuery(name = "AvisoCobro.findByTotalPendiente", query = "SELECT a FROM AvisoCobro a WHERE a.totalPendiente = :totalPendiente"),
    @NamedQuery(name = "AvisoCobro.findByTotal", query = "SELECT a FROM AvisoCobro a WHERE a.total = :total"),
    @NamedQuery(name = "AvisoCobro.findByFechaCreacion", query = "SELECT a FROM AvisoCobro a WHERE a.fechaCreacion = :fechaCreacion")})
public class AvisoCobro implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AvisoCobroPK avisoCobroPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "total_periodo")
    private int totalPeriodo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sub_total_periodo")
    private int subTotalPeriodo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "descuento_periodo")
    private int descuentoPeriodo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "total_pendiente")
    private int totalPendiente;
    @Basic(optional = false)
    @NotNull
    @Column(name = "total")
    private int total;
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    /*
    @JoinColumn(name = "id_cuenta", referencedColumnName = "id_cuenta", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Cuenta cuenta;
    @JoinColumn(name = "id_periodo", referencedColumnName = "id_periodo", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Periodo periodo;
   
    */
    @JoinColumns({
        @JoinColumn(name = "id_periodo", referencedColumnName = "id_periodo", insertable = false, updatable = false),
        @JoinColumn(name = "id_cuenta", referencedColumnName = "id_cuenta", insertable = false, updatable = false),
    })
    @ManyToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.EAGER)
    private RegistroEstado registroEstado;
    
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "avisoCobro", fetch = FetchType.EAGER)
    private List<DetalleAvisoCobro> detalleAvisoCobroList;
   

    public AvisoCobro() {
    }

    public AvisoCobro(AvisoCobroPK avisoCobroPK) {
        this.avisoCobroPK = avisoCobroPK;
    }

    public AvisoCobro(AvisoCobroPK avisoCobroPK, int totalPeriodo, int totalPendiente, int total) {
        this.avisoCobroPK = avisoCobroPK;
        this.totalPeriodo = totalPeriodo;
        this.totalPendiente = totalPendiente;
        this.total = total;
    }

    public AvisoCobro(int idPeriodo, int idCuenta) {
        this.avisoCobroPK = new AvisoCobroPK(idPeriodo, idCuenta);
    }

    public AvisoCobroPK getAvisoCobroPK() {
        return avisoCobroPK;
    }

    public void setAvisoCobroPK(AvisoCobroPK avisoCobroPK) {
        this.avisoCobroPK = avisoCobroPK;
    }

    public int getTotalPeriodo() {
        return totalPeriodo;
    }

    public void setTotalPeriodo(int totalPeriodo) {
        this.totalPeriodo = totalPeriodo;
    }

    public int getTotalPendiente() {
        return totalPendiente;
    }

    public void setTotalPendiente(int totalPendiente) {
        this.totalPendiente = totalPendiente;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public int getSubTotalPeriodo() {
        return subTotalPeriodo;
    }

    public void setSubTotalPeriodo(int subTotalPeriodo) {
        this.subTotalPeriodo = subTotalPeriodo;
    }

    public int getDescuentoPeriodo() {
        return descuentoPeriodo;
    }

    public void setDescuentoPeriodo(int descuentoPeriodo) {
        this.descuentoPeriodo = descuentoPeriodo;
    }

    
    
/*
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
    */
     public RegistroEstado getRegistroEstado() {
        return registroEstado;
    }

    public void setRegistroEstado(RegistroEstado registroEstado) {
        this.registroEstado = registroEstado;
    }

    @XmlTransient
    public List<DetalleAvisoCobro> getDetalleAvisoCobroList() {
        return detalleAvisoCobroList;
    }

    public void setDetalleAvisoCobroList(List<DetalleAvisoCobro> detalleAvisoCobroList) {
        this.detalleAvisoCobroList = detalleAvisoCobroList;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (avisoCobroPK != null ? avisoCobroPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AvisoCobro)) {
            return false;
        }
        AvisoCobro other = (AvisoCobro) object;
        if ((this.avisoCobroPK == null && other.avisoCobroPK != null) || (this.avisoCobroPK != null && !this.avisoCobroPK.equals(other.avisoCobroPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.apr.entity.AvisoCobro[ avisoCobroPK=" + avisoCobroPK + " ]";
    }
    
}
