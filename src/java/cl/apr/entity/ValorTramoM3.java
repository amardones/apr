/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.apr.entity;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author hmardones
 */
@Entity
@Table(name = "valor_tramo_m3")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ValorTramoM3.findAll", query = "SELECT v FROM ValorTramoM3 v"),
    @NamedQuery(name = "ValorTramoM3.findByIdValorTramo", query = "SELECT v FROM ValorTramoM3 v WHERE v.idValorTramo = :idValorTramo"),
    @NamedQuery(name = "ValorTramoM3.findByNombreTramo", query = "SELECT v FROM ValorTramoM3 v WHERE v.nombreTramo = :nombreTramo"),
    @NamedQuery(name = "ValorTramoM3.findByM3Inicio", query = "SELECT v FROM ValorTramoM3 v WHERE v.m3Inicio = :m3Inicio"),
    @NamedQuery(name = "ValorTramoM3.findByM3Final", query = "SELECT v FROM ValorTramoM3 v WHERE v.m3Final = :m3Final"),
    @NamedQuery(name = "ValorTramoM3.findByPorcentajeRecargo", query = "SELECT v FROM ValorTramoM3 v WHERE v.porcentajeRecargo = :porcentajeRecargo")})
public class ValorTramoM3 implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_valor_tramo")
    private Integer idValorTramo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "nombre_tramo")
    private String nombreTramo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "m3_inicio")
    private int m3Inicio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "m3_final")
    private int m3Final;
    @Basic(optional = false)
    @NotNull
    @Column(name = "porcentaje_recargo")
    private BigInteger porcentajeRecargo;

    public ValorTramoM3() {
    }

    public ValorTramoM3(Integer idValorTramo) {
        this.idValorTramo = idValorTramo;
    }

    public ValorTramoM3(Integer idValorTramo, String nombreTramo, int m3Inicio, int m3Final, BigInteger porcentajeRecargo) {
        this.idValorTramo = idValorTramo;
        this.nombreTramo = nombreTramo;
        this.m3Inicio = m3Inicio;
        this.m3Final = m3Final;
        this.porcentajeRecargo = porcentajeRecargo;
    }

    public Integer getIdValorTramo() {
        return idValorTramo;
    }

    public void setIdValorTramo(Integer idValorTramo) {
        this.idValorTramo = idValorTramo;
    }

    public String getNombreTramo() {
        return nombreTramo;
    }

    public void setNombreTramo(String nombreTramo) {
        this.nombreTramo = nombreTramo;
    }

    public int getM3Inicio() {
        return m3Inicio;
    }

    public void setM3Inicio(int m3Inicio) {
        this.m3Inicio = m3Inicio;
    }

    public int getM3Final() {
        return m3Final;
    }

    public void setM3Final(int m3Final) {
        this.m3Final = m3Final;
    }

    public BigInteger getPorcentajeRecargo() {
        return porcentajeRecargo;
    }

    public void setPorcentajeRecargo(BigInteger porcentajeRecargo) {
        this.porcentajeRecargo = porcentajeRecargo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idValorTramo != null ? idValorTramo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ValorTramoM3)) {
            return false;
        }
        ValorTramoM3 other = (ValorTramoM3) object;
        if ((this.idValorTramo == null && other.idValorTramo != null) || (this.idValorTramo != null && !this.idValorTramo.equals(other.idValorTramo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.apr.entity.ValorTramoM3[ idValorTramo=" + idValorTramo + " ]";
    }
    
}
