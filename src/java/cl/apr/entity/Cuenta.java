/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.apr.entity;

import java.io.Serializable;
import java.math.BigInteger;
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
import javax.persistence.OneToOne;
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
@Table(name = "cuenta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cuenta.findAll", query = "SELECT c FROM Cuenta c"),
    @NamedQuery(name = "Cuenta.findByIdCuenta", query = "SELECT c FROM Cuenta c WHERE c.idCuenta = :idCuenta"),
    @NamedQuery(name = "Cuenta.findByDireccion", query = "SELECT c FROM Cuenta c WHERE c.direccion = :direccion"),
    @NamedQuery(name = "Cuenta.findByGpsLatitud", query = "SELECT c FROM Cuenta c WHERE c.gpsLatitud = :gpsLatitud"),
    @NamedQuery(name = "Cuenta.findByGpsLongitud", query = "SELECT c FROM Cuenta c WHERE c.gpsLongitud = :gpsLongitud"),
    @NamedQuery(name = "Cuenta.findByFechaCreacion", query = "SELECT c FROM Cuenta c WHERE c.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "Cuenta.findByActiva", query = "SELECT c FROM Cuenta c WHERE c.activa = :activa")})
public class Cuenta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cuenta")
    private Integer idCuenta;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "gps_latitud")
    private BigInteger gpsLatitud;
    @Column(name = "gps_longitud")
    private BigInteger gpsLongitud;
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "activa")
    private boolean activa;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cuenta")
    private List<RegistroEstado> registroEstadoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCuenta")
    private List<SaldoCuenta> saldoCuentaList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "cuenta")
    private CuentaSubsidio cuentaSubsidio;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cuenta")
    private List<AvisoCobro> avisoCobroList;
    @JoinColumn(name = "numero_medidor", referencedColumnName = "numero_medidor")
    @ManyToOne(optional = false)
    private Medidor numeroMedidor;
    @JoinColumn(name = "rut", referencedColumnName = "rut")
    @ManyToOne(optional = false)
    private Socio rut;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCuenta")
    private List<RegistroCobro> registroCobroList;

    public Cuenta() {
    }

    public Cuenta(Integer idCuenta) {
        this.idCuenta = idCuenta;
    }

    public Cuenta(Integer idCuenta, String direccion, boolean activa) {
        this.idCuenta = idCuenta;
        this.direccion = direccion;
        this.activa = activa;
    }

    public Integer getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(Integer idCuenta) {
        this.idCuenta = idCuenta;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public BigInteger getGpsLatitud() {
        return gpsLatitud;
    }

    public void setGpsLatitud(BigInteger gpsLatitud) {
        this.gpsLatitud = gpsLatitud;
    }

    public BigInteger getGpsLongitud() {
        return gpsLongitud;
    }

    public void setGpsLongitud(BigInteger gpsLongitud) {
        this.gpsLongitud = gpsLongitud;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public boolean getActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    @XmlTransient
    public List<RegistroEstado> getRegistroEstadoList() {
        return registroEstadoList;
    }

    public void setRegistroEstadoList(List<RegistroEstado> registroEstadoList) {
        this.registroEstadoList = registroEstadoList;
    }

    @XmlTransient
    public List<SaldoCuenta> getSaldoCuentaList() {
        return saldoCuentaList;
    }

    public void setSaldoCuentaList(List<SaldoCuenta> saldoCuentaList) {
        this.saldoCuentaList = saldoCuentaList;
    }

    public CuentaSubsidio getCuentaSubsidio() {
        return cuentaSubsidio;
    }

    public void setCuentaSubsidio(CuentaSubsidio cuentaSubsidio) {
        this.cuentaSubsidio = cuentaSubsidio;
    }

    @XmlTransient
    public List<AvisoCobro> getAvisoCobroList() {
        return avisoCobroList;
    }

    public void setAvisoCobroList(List<AvisoCobro> avisoCobroList) {
        this.avisoCobroList = avisoCobroList;
    }

    public Medidor getNumeroMedidor() {
        return numeroMedidor;
    }

    public void setNumeroMedidor(Medidor numeroMedidor) {
        this.numeroMedidor = numeroMedidor;
    }

    public Socio getRut() {
        return rut;
    }

    public void setRut(Socio rut) {
        this.rut = rut;
    }

    @XmlTransient
    public List<RegistroCobro> getRegistroCobroList() {
        return registroCobroList;
    }

    public void setRegistroCobroList(List<RegistroCobro> registroCobroList) {
        this.registroCobroList = registroCobroList;
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
        if (!(object instanceof Cuenta)) {
            return false;
        }
        Cuenta other = (Cuenta) object;
        if ((this.idCuenta == null && other.idCuenta != null) || (this.idCuenta != null && !this.idCuenta.equals(other.idCuenta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.apr.entity.Cuenta[ idCuenta=" + idCuenta + " ]";
    }
    
}
