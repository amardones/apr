/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.apr.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author hmardones
 */
@Entity
@Table(name = "pago_tipo_cobro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PagoTipoCobro.findAll", query = "SELECT p FROM PagoTipoCobro p"),
    @NamedQuery(name = "PagoTipoCobro.findByIdTipoCobro", query = "SELECT p FROM PagoTipoCobro p WHERE p.pagoTipoCobroPK.idTipoCobro = :idTipoCobro"),
    @NamedQuery(name = "PagoTipoCobro.findByIdPago", query = "SELECT p FROM PagoTipoCobro p WHERE p.pagoTipoCobroPK.idPago = :idPago"),
    @NamedQuery(name = "PagoTipoCobro.findByTotal", query = "SELECT p FROM PagoTipoCobro p WHERE p.total = :total")})
public class PagoTipoCobro implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PagoTipoCobroPK pagoTipoCobroPK;
    @Column(name = "total")
    private Integer total;
    @JoinColumn(name = "id_pago", referencedColumnName = "id_pago", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Pago pago;
    @JoinColumn(name = "id_tipo_cobro", referencedColumnName = "id_tipo_cobro", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private TipoCobro tipoCobro;

    public PagoTipoCobro() {
    }

    public PagoTipoCobro(PagoTipoCobroPK pagoTipoCobroPK) {
        this.pagoTipoCobroPK = pagoTipoCobroPK;
    }

    public PagoTipoCobro(int idTipoCobro, int idPago) {
        this.pagoTipoCobroPK = new PagoTipoCobroPK(idTipoCobro, idPago);
    }

    public PagoTipoCobroPK getPagoTipoCobroPK() {
        return pagoTipoCobroPK;
    }

    public void setPagoTipoCobroPK(PagoTipoCobroPK pagoTipoCobroPK) {
        this.pagoTipoCobroPK = pagoTipoCobroPK;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }

    public TipoCobro getTipoCobro() {
        return tipoCobro;
    }

    public void setTipoCobro(TipoCobro tipoCobro) {
        this.tipoCobro = tipoCobro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pagoTipoCobroPK != null ? pagoTipoCobroPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PagoTipoCobro)) {
            return false;
        }
        PagoTipoCobro other = (PagoTipoCobro) object;
        if ((this.pagoTipoCobroPK == null && other.pagoTipoCobroPK != null) || (this.pagoTipoCobroPK != null && !this.pagoTipoCobroPK.equals(other.pagoTipoCobroPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.apr.entity.PagoTipoCobro[ pagoTipoCobroPK=" + pagoTipoCobroPK + " ]";
    }
    
}
