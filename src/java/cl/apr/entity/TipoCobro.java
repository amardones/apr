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
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "tipo_cobro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoCobro.findAll", query = "SELECT t FROM TipoCobro t"),
    @NamedQuery(name = "TipoCobro.findByIdTipoCobro", query = "SELECT t FROM TipoCobro t WHERE t.idTipoCobro = :idTipoCobro"),
    @NamedQuery(name = "TipoCobro.findByCodigoTipoCobro", query = "SELECT t FROM TipoCobro t WHERE t.codigoTipoCobro = :codigoTipoCobro"),
    @NamedQuery(name = "TipoCobro.findByNombre", query = "SELECT t FROM TipoCobro t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "TipoCobro.findByAceptaPagoCuotas", query = "SELECT t FROM TipoCobro t WHERE t.aceptaPagoCuotas = :aceptaPagoCuotas"),
    @NamedQuery(name = "TipoCobro.findByAceptaRegistroCobro", query = "SELECT t FROM TipoCobro t WHERE t.aceptaRegistroCobro = :aceptaRegistroCobro")})
public class TipoCobro implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoCobro")
    private List<PagoTipoCobro> pagoTipoCobroList;
    @Column(name = "valor")
    private Integer valor;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_tipo_cobro")
    private Integer idTipoCobro;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "codigo_tipo_cobro")
    private String codigoTipoCobro;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "acepta_pago_cuotas")
    private boolean aceptaPagoCuotas;
    @Basic(optional = false)
    @NotNull
    @Column(name = "acepta_registro_cobro")
    private boolean aceptaRegistroCobro;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTipoCobro")
    private List<DetalleAvisoCobro> detalleAvisoCobroList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTipoCobro")
    private List<RegistroCobro> registroCobroList;

    public TipoCobro() {
    }

    public TipoCobro(Integer idTipoCobro) {
        this.idTipoCobro = idTipoCobro;
    }

    public TipoCobro(Integer idTipoCobro, String codigoTipoCobro, String nombre, boolean aceptaPagoCuotas, boolean aceptaRegistroCobro) {
        this.idTipoCobro = idTipoCobro;
        this.codigoTipoCobro = codigoTipoCobro;
        this.nombre = nombre;
        this.aceptaPagoCuotas = aceptaPagoCuotas;
        this.aceptaRegistroCobro = aceptaRegistroCobro;
    }

    public Integer getIdTipoCobro() {
        return idTipoCobro;
    }

    public void setIdTipoCobro(Integer idTipoCobro) {
        this.idTipoCobro = idTipoCobro;
    }

    public String getCodigoTipoCobro() {
        return codigoTipoCobro;
    }

    public void setCodigoTipoCobro(String codigoTipoCobro) {
        this.codigoTipoCobro = codigoTipoCobro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean getAceptaPagoCuotas() {
        return aceptaPagoCuotas;
    }

    public void setAceptaPagoCuotas(boolean aceptaPagoCuotas) {
        this.aceptaPagoCuotas = aceptaPagoCuotas;
    }

    public boolean getAceptaRegistroCobro() {
        return aceptaRegistroCobro;
    }

    public void setAceptaRegistroCobro(boolean aceptaRegistroCobro) {
        this.aceptaRegistroCobro = aceptaRegistroCobro;
    }

    @XmlTransient
    public List<DetalleAvisoCobro> getDetalleAvisoCobroList() {
        return detalleAvisoCobroList;
    }

    public void setDetalleAvisoCobroList(List<DetalleAvisoCobro> detalleAvisoCobroList) {
        this.detalleAvisoCobroList = detalleAvisoCobroList;
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
        hash += (idTipoCobro != null ? idTipoCobro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoCobro)) {
            return false;
        }
        TipoCobro other = (TipoCobro) object;
        if ((this.idTipoCobro == null && other.idTipoCobro != null) || (this.idTipoCobro != null && !this.idTipoCobro.equals(other.idTipoCobro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.apr.entity.TipoCobro[ idTipoCobro=" + idTipoCobro + " ]";
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    @XmlTransient
    public List<PagoTipoCobro> getPagoTipoCobroList() {
        return pagoTipoCobroList;
    }

    public void setPagoTipoCobroList(List<PagoTipoCobro> pagoTipoCobroList) {
        this.pagoTipoCobroList = pagoTipoCobroList;
    }
    
}
