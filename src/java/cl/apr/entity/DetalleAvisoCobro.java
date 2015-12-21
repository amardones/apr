/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.apr.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "detalle_aviso_cobro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DetalleAvisoCobro.findAll", query = "SELECT d FROM DetalleAvisoCobro d"),
    @NamedQuery(name = "DetalleAvisoCobro.findByIdDetalleAvisoCobro", query = "SELECT d FROM DetalleAvisoCobro d WHERE d.idDetalleAvisoCobro = :idDetalleAvisoCobro"),
    @NamedQuery(name = "DetalleAvisoCobro.findByIdDetalleAvisoCobroAnt", query = "SELECT d FROM DetalleAvisoCobro d WHERE d.idDetalleAvisoCobroAnt = :idDetalleAvisoCobroAnt"),
    @NamedQuery(name = "DetalleAvisoCobro.findBySubTotal", query = "SELECT d FROM DetalleAvisoCobro d WHERE d.subTotal = :subTotal"),
    @NamedQuery(name = "DetalleAvisoCobro.findByDescuento", query = "SELECT d FROM DetalleAvisoCobro d WHERE d.descuento = :descuento"),
    @NamedQuery(name = "DetalleAvisoCobro.findByTotal", query = "SELECT d FROM DetalleAvisoCobro d WHERE d.total = :total"),
    @NamedQuery(name = "DetalleAvisoCobro.findByDescripcion", query = "SELECT d FROM DetalleAvisoCobro d WHERE d.descripcion = :descripcion"),
    @NamedQuery(name = "DetalleAvisoCobro.findByPagado", query = "SELECT d FROM DetalleAvisoCobro d WHERE d.pagado = :pagado")})
public class DetalleAvisoCobro implements Serializable {

    @ManyToMany(mappedBy = "detalleAvisoCobroList")
    private List<Pago> pagoList;
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_detalle_aviso_cobro")
    private Integer idDetalleAvisoCobro;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_detalle_aviso_cobro_ant")
    private int idDetalleAvisoCobroAnt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sub_total")
    private int subTotal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "descuento")
    private int descuento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "total")
    private int total;
    @Size(max = 500)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pagado")
    private boolean pagado;
    @ManyToMany(mappedBy = "detalleAvisoCobroList")
    private List<CobroCuota> cobroCuotaList;
    @JoinColumns({
        @JoinColumn(name = "id_periodo", referencedColumnName = "id_periodo"),
        @JoinColumn(name = "id_cuenta", referencedColumnName = "id_cuenta")})
    @ManyToOne(optional = false)
    private AvisoCobro avisoCobro;
    @JoinColumn(name = "id_tipo_cobro", referencedColumnName = "id_tipo_cobro")
    @ManyToOne(optional = false)
    private TipoCobro idTipoCobro;

    public DetalleAvisoCobro() {
    }

    public DetalleAvisoCobro(Integer idDetalleAvisoCobro) {
        this.idDetalleAvisoCobro = idDetalleAvisoCobro;
    }

    public DetalleAvisoCobro(Integer idDetalleAvisoCobro, int idDetalleAvisoCobroAnt, int subTotal, int descuento, int total, boolean pagado) {
        this.idDetalleAvisoCobro = idDetalleAvisoCobro;
        this.idDetalleAvisoCobroAnt = idDetalleAvisoCobroAnt;
        this.subTotal = subTotal;
        this.descuento = descuento;
        this.total = total;
        this.pagado = pagado;
    }

    public Integer getIdDetalleAvisoCobro() {
        return idDetalleAvisoCobro;
    }

    public void setIdDetalleAvisoCobro(Integer idDetalleAvisoCobro) {
        this.idDetalleAvisoCobro = idDetalleAvisoCobro;
    }

    public int getIdDetalleAvisoCobroAnt() {
        return idDetalleAvisoCobroAnt;
    }

    public void setIdDetalleAvisoCobroAnt(int idDetalleAvisoCobroAnt) {
        this.idDetalleAvisoCobroAnt = idDetalleAvisoCobroAnt;
    }

    public int getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(int subTotal) {
        this.subTotal = subTotal;
    }

    public int getDescuento() {
        return descuento;
    }

    public void setDescuento(int descuento) {
        this.descuento = descuento;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean getPagado() {
        return pagado;
    }

    public void setPagado(boolean pagado) {
        this.pagado = pagado;
    }

    @XmlTransient
    public List<CobroCuota> getCobroCuotaList() {
        return cobroCuotaList;
    }

    public void setCobroCuotaList(List<CobroCuota> cobroCuotaList) {
        this.cobroCuotaList = cobroCuotaList;
    }

    public AvisoCobro getAvisoCobro() {
        return avisoCobro;
    }

    public void setAvisoCobro(AvisoCobro avisoCobro) {
        this.avisoCobro = avisoCobro;
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
        hash += (idDetalleAvisoCobro != null ? idDetalleAvisoCobro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleAvisoCobro)) {
            return false;
        }
        DetalleAvisoCobro other = (DetalleAvisoCobro) object;
        if ((this.idDetalleAvisoCobro == null && other.idDetalleAvisoCobro != null) || (this.idDetalleAvisoCobro != null && !this.idDetalleAvisoCobro.equals(other.idDetalleAvisoCobro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.apr.entity.DetalleAvisoCobro[ idDetalleAvisoCobro=" + idDetalleAvisoCobro + " ]";
    }

    @XmlTransient
    public List<Pago> getPagoList() {
        return pagoList;
    }

    public void setPagoList(List<Pago> pagoList) {
        this.pagoList = pagoList;
    }
    
}
