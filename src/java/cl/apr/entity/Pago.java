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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "pago")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pago.findAll", query = "SELECT p FROM Pago p"),
    @NamedQuery(name = "Pago.findByIdPago", query = "SELECT p FROM Pago p WHERE p.idPago = :idPago"),
    @NamedQuery(name = "Pago.findByNumeroDocumento", query = "SELECT p FROM Pago p WHERE p.numeroDocumento = :numeroDocumento"),
    @NamedQuery(name = "Pago.findByFechaCreacion", query = "SELECT p FROM Pago p WHERE p.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "Pago.findBySubtotal", query = "SELECT p FROM Pago p WHERE p.subtotal = :subtotal"),
    @NamedQuery(name = "Pago.findByInteres", query = "SELECT p FROM Pago p WHERE p.interes = :interes"),
    @NamedQuery(name = "Pago.findByTotal", query = "SELECT p FROM Pago p WHERE p.total = :total")})
public class Pago implements Serializable {
    @JoinTable(name = "pago_detalle_aviso", joinColumns = {
        @JoinColumn(name = "id_pago", referencedColumnName = "id_pago")}, inverseJoinColumns = {
        @JoinColumn(name = "id_detalle_aviso_cobro", referencedColumnName = "id_detalle_aviso_cobro")})
    @ManyToMany
    private List<DetalleAvisoCobro> detalleAvisoCobroList;
  
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pago")
    private List<PagoTipoCobro> pagoTipoCobroList;
    
    private static final long serialVersionUID = 1L;
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pago")
    private Integer idPago;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "numero_documento")
    private String numeroDocumento;
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "subtotal")
    private int subtotal;
    @Column(name = "interes")
    private Integer interes;
    @Column(name = "total")
    private Integer total;
    @JoinColumn(name = "id_cuenta", referencedColumnName = "id_cuenta")
    @ManyToOne
    private Cuenta idCuenta;

    public Pago() {
    }

    public Pago(Integer idPago) {
        this.idPago = idPago;
    }

    public Pago(Integer idPago, String numeroDocumento, int subtotal) {
        this.idPago = idPago;
        this.numeroDocumento = numeroDocumento;
        this.subtotal = subtotal;
    }

    public Integer getIdPago() {
        return idPago;
    }

    public void setIdPago(Integer idPago) {
        this.idPago = idPago;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public int getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(int subtotal) {
        this.subtotal = subtotal;
    }

    public Integer getInteres() {
        return interes;
    }

    public void setInteres(Integer interes) {
        this.interes = interes;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Cuenta getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(Cuenta idCuenta) {
        this.idCuenta = idCuenta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPago != null ? idPago.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pago)) {
            return false;
        }
        Pago other = (Pago) object;
        if ((this.idPago == null && other.idPago != null) || (this.idPago != null && !this.idPago.equals(other.idPago))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.apr.entity.Pago[ idPago=" + idPago + " ]";
    }

    @XmlTransient
    public List<DetalleAvisoCobro> getDetalleAvisoCobroList() {
        return detalleAvisoCobroList;
    }

    public void setDetalleAvisoCobroList(List<DetalleAvisoCobro> detalleAvisoCobroList) {
        this.detalleAvisoCobroList = detalleAvisoCobroList;
    }

    @XmlTransient
    public List<PagoTipoCobro> getPagoTipoCobroList() {
        return pagoTipoCobroList;
    }

    public void setPagoTipoCobroList(List<PagoTipoCobro> pagoTipoCobroList) {
        this.pagoTipoCobroList = pagoTipoCobroList;
    }
    
}
