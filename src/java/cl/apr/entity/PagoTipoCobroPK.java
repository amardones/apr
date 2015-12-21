/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.apr.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author hmardones
 */
@Embeddable
public class PagoTipoCobroPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_tipo_cobro")
    private int idTipoCobro;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_pago")
    private int idPago;

    public PagoTipoCobroPK() {
    }

    public PagoTipoCobroPK(int idTipoCobro, int idPago) {
        this.idTipoCobro = idTipoCobro;
        this.idPago = idPago;
    }

    public int getIdTipoCobro() {
        return idTipoCobro;
    }

    public void setIdTipoCobro(int idTipoCobro) {
        this.idTipoCobro = idTipoCobro;
    }

    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idTipoCobro;
        hash += (int) idPago;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PagoTipoCobroPK)) {
            return false;
        }
        PagoTipoCobroPK other = (PagoTipoCobroPK) object;
        if (this.idTipoCobro != other.idTipoCobro) {
            return false;
        }
        if (this.idPago != other.idPago) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.apr.entity.PagoTipoCobroPK[ idTipoCobro=" + idTipoCobro + ", idPago=" + idPago + " ]";
    }
    
}
