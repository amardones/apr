/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.apr.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author hmardones
 */
@Entity
@Table(name = "cobro_cuota")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CobroCuota.findAll", query = "SELECT c FROM CobroCuota c"),
    @NamedQuery(name = "CobroCuota.findByIdRegistroCobro", query = "SELECT c FROM CobroCuota c WHERE c.cobroCuotaPK.idRegistroCobro = :idRegistroCobro"),
    @NamedQuery(name = "CobroCuota.findByNumeroCuota", query = "SELECT c FROM CobroCuota c WHERE c.cobroCuotaPK.numeroCuota = :numeroCuota"),
    @NamedQuery(name = "CobroCuota.findByValorCuota", query = "SELECT c FROM CobroCuota c WHERE c.valorCuota = :valorCuota"),
    @NamedQuery(name = "CobroCuota.findByMes", query = "SELECT c FROM CobroCuota c WHERE c.mes = :mes"),
    @NamedQuery(name = "CobroCuota.findByAnio", query = "SELECT c FROM CobroCuota c WHERE c.anio = :anio"),
    @NamedQuery(name = "CobroCuota.findByPagado", query = "SELECT c FROM CobroCuota c WHERE c.pagado = :pagado")})
public class CobroCuota implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CobroCuotaPK cobroCuotaPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "valor_cuota")
    private int valorCuota;
    @Basic(optional = false)
    @NotNull
    @Column(name = "mes")
    private int mes;
    @Basic(optional = false)
    @NotNull
    @Column(name = "anio")
    private int anio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pagado")
    private boolean pagado;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "registro_cobro_cuota", joinColumns = {
        @JoinColumn(name = "numero_cuota", referencedColumnName = "numero_cuota"),
        @JoinColumn(name = "id_registro_cobro", referencedColumnName = "id_registro_cobro")}, inverseJoinColumns = {
        @JoinColumn(name = "id_detalle_aviso_cobro", referencedColumnName = "id_detalle_aviso_cobro")})
    
    private List<DetalleAvisoCobro> detalleAvisoCobroList;
    @JoinColumn(name = "id_registro_cobro", referencedColumnName = "id_registro_cobro", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private RegistroCobro registroCobro;

    public CobroCuota() {
    }

    public CobroCuota(CobroCuotaPK cobroCuotaPK) {
        this.cobroCuotaPK = cobroCuotaPK;
    }

    public CobroCuota(CobroCuotaPK cobroCuotaPK, int valorCuota, int mes, int anio, boolean pagado) {
        this.cobroCuotaPK = cobroCuotaPK;
        this.valorCuota = valorCuota;
        this.mes = mes;
        this.anio = anio;
        this.pagado = pagado;
    }

    public CobroCuota(int idRegistroCobro, int numeroCuota) {
        this.cobroCuotaPK = new CobroCuotaPK(idRegistroCobro, numeroCuota);
    }

    public CobroCuotaPK getCobroCuotaPK() {
        return cobroCuotaPK;
    }

    public void setCobroCuotaPK(CobroCuotaPK cobroCuotaPK) {
        this.cobroCuotaPK = cobroCuotaPK;
    }

    public int getValorCuota() {
        return valorCuota;
    }

    public void setValorCuota(int valorCuota) {
        this.valorCuota = valorCuota;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public boolean getPagado() {
        return pagado;
    }

    public void setPagado(boolean pagado) {
        this.pagado = pagado;
    }

    @XmlTransient
    public List<DetalleAvisoCobro> getDetalleAvisoCobroList() {
        return detalleAvisoCobroList;
    }

    public void setDetalleAvisoCobroList(List<DetalleAvisoCobro> detalleAvisoCobroList) {
        this.detalleAvisoCobroList = detalleAvisoCobroList;
    }

    public RegistroCobro getRegistroCobro() {
        return registroCobro;
    }

    public void setRegistroCobro(RegistroCobro registroCobro) {
        this.registroCobro = registroCobro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cobroCuotaPK != null ? cobroCuotaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CobroCuota)) {
            return false;
        }
        CobroCuota other = (CobroCuota) object;
        if ((this.cobroCuotaPK == null && other.cobroCuotaPK != null) || (this.cobroCuotaPK != null && !this.cobroCuotaPK.equals(other.cobroCuotaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.apr.entity.CobroCuota[ cobroCuotaPK=" + cobroCuotaPK + " ]";
    }
    
}
