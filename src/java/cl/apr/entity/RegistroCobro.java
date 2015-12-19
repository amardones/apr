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
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author hmardones
 */
@Entity
@Table(name = "registro_cobro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RegistroCobro.findAll", query = "SELECT r FROM RegistroCobro r"),
    @NamedQuery(name = "RegistroCobro.findByIdRegistroCobro", query = "SELECT r FROM RegistroCobro r WHERE r.idRegistroCobro = :idRegistroCobro"),
    @NamedQuery(name = "RegistroCobro.findByCuotas", query = "SELECT r FROM RegistroCobro r WHERE r.cuotas = :cuotas"),
    @NamedQuery(name = "RegistroCobro.findByMonto", query = "SELECT r FROM RegistroCobro r WHERE r.monto = :monto"),
    @NamedQuery(name = "RegistroCobro.findByDescripcion", query = "SELECT r FROM RegistroCobro r WHERE r.descripcion = :descripcion"),
    @NamedQuery(name = "RegistroCobro.findByFechaCreacion", query = "SELECT r FROM RegistroCobro r WHERE r.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "RegistroCobro.findByMesPrimeraCuota", query = "SELECT r FROM RegistroCobro r WHERE r.mesPrimeraCuota = :mesPrimeraCuota")})
public class RegistroCobro implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Basic(optional = false)
    @Column(name = "id_registro_cobro")
    private Integer idRegistroCobro;
    @Basic(optional = false)
    @NotNull
    @Min(1)
    @Column(name = "cuotas")
    private int cuotas;
    @Basic(optional = false)
    @NotNull
    @Min(1)
    @Column(name = "monto")
    private int monto;
    @Size(max = 50)
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "mes_primera_cuota")
    private int mesPrimeraCuota;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "registroCobro")
    private List<CobroCuota> cobroCuotaList;
    @JoinColumn(name = "id_cuenta", referencedColumnName = "id_cuenta")
    @ManyToOne(optional = false)
    private Cuenta idCuenta;
    @JoinColumn(name = "id_tipo_cobro", referencedColumnName = "id_tipo_cobro")
    @ManyToOne(optional = false)
    private TipoCobro idTipoCobro;

    public RegistroCobro() {
    }

    public RegistroCobro(Integer idRegistroCobro) {
        this.idRegistroCobro = idRegistroCobro;
    }

    public RegistroCobro(Integer idRegistroCobro, int cuotas, int monto, int mesPrimeraCuota) {
        this.idRegistroCobro = idRegistroCobro;
        this.cuotas = cuotas;
        this.monto = monto;
        this.mesPrimeraCuota = mesPrimeraCuota;
    }

    public Integer getIdRegistroCobro() {
        return idRegistroCobro;
    }

    public void setIdRegistroCobro(Integer idRegistroCobro) {
        this.idRegistroCobro = idRegistroCobro;
    }

    public int getCuotas() {
        return cuotas;
    }

    public void setCuotas(int cuotas) {
        this.cuotas = cuotas;
    }

    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public int getMesPrimeraCuota() {
        return mesPrimeraCuota;
    }

    public void setMesPrimeraCuota(int mesPrimeraCuota) {
        this.mesPrimeraCuota = mesPrimeraCuota;
    }

    @XmlTransient
    public List<CobroCuota> getCobroCuotaList() {
        return cobroCuotaList;
    }

    public void setCobroCuotaList(List<CobroCuota> cobroCuotaList) {
        this.cobroCuotaList = cobroCuotaList;
    }

    public Cuenta getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(Cuenta idCuenta) {
        this.idCuenta = idCuenta;
    }

    public TipoCobro getIdTipoCobro() {
        return idTipoCobro;
    }

    public void setIdTipoCobro(TipoCobro idTipoCobro) {
        this.idTipoCobro = idTipoCobro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRegistroCobro != null ? idRegistroCobro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RegistroCobro)) {
            return false;
        }
        RegistroCobro other = (RegistroCobro) object;
        if ((this.idRegistroCobro == null && other.idRegistroCobro != null) || (this.idRegistroCobro != null && !this.idRegistroCobro.equals(other.idRegistroCobro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.apr.entity.RegistroCobro[ idRegistroCobro=" + idRegistroCobro + " ]";
    }
    
}
