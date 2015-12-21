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
@Table(name = "valores_parametricos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ValoresParametricos.findAll", query = "SELECT v FROM ValoresParametricos v"),
    @NamedQuery(name = "ValoresParametricos.findByIdValoresParametricos", query = "SELECT v FROM ValoresParametricos v WHERE v.idValoresParametricos = :idValoresParametricos"),
    @NamedQuery(name = "ValoresParametricos.findByValorCargoFijo", query = "SELECT v FROM ValoresParametricos v WHERE v.valorCargoFijo = :valorCargoFijo"),
    @NamedQuery(name = "ValoresParametricos.findByValorM3", query = "SELECT v FROM ValoresParametricos v WHERE v.valorM3 = :valorM3"),
    @NamedQuery(name = "ValoresParametricos.findByM3Fijos", query = "SELECT v FROM ValoresParametricos v WHERE v.m3Fijos = :m3Fijos"),
    @NamedQuery(name = "ValoresParametricos.findByM3LimiteDctoInterno", query = "SELECT v FROM ValoresParametricos v WHERE v.m3LimiteDctoInterno = :m3LimiteDctoInterno"),
    @NamedQuery(name = "ValoresParametricos.findByPorcentajeDctoInterno", query = "SELECT v FROM ValoresParametricos v WHERE v.porcentajeDctoInterno = :porcentajeDctoInterno"),
    @NamedQuery(name = "ValoresParametricos.findByFechaActualizacion", query = "SELECT v FROM ValoresParametricos v WHERE v.fechaActualizacion = :fechaActualizacion"),
    @NamedQuery(name = "ValoresParametricos.findByDescripcionCambios", query = "SELECT v FROM ValoresParametricos v WHERE v.descripcionCambios = :descripcionCambios"),
    @NamedQuery(name = "ValoresParametricos.findByDiaVencimiento", query = "SELECT v FROM ValoresParametricos v WHERE v.diaVencimiento = :diaVencimiento"),
    @NamedQuery(name = "ValoresParametricos.findByDiaLecturaMedidor", query = "SELECT v FROM ValoresParametricos v WHERE v.diaLecturaMedidor = :diaLecturaMedidor"),
    @NamedQuery(name = "ValoresParametricos.findByDiaEmision", query = "SELECT v FROM ValoresParametricos v WHERE v.diaEmision = :diaEmision")})
public class ValoresParametricos implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idValoresParametricos")
    private List<Periodo> periodoList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_valores_parametricos")
    private Integer idValoresParametricos;
    @Basic(optional = false)
    @NotNull
    @Column(name = "valor_cargo_fijo")
    private int valorCargoFijo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "valor_m3")
    private int valorM3;
    @Basic(optional = false)
    @NotNull
    @Column(name = "m3_fijos")
    private int m3Fijos;
    @Basic(optional = false)
    @NotNull
    @Column(name = "m3_limite_dcto_interno")
    private int m3LimiteDctoInterno;
    @Basic(optional = false)
    @NotNull
    @Column(name = "porcentaje_dcto_interno")
    private BigInteger porcentajeDctoInterno;
    @Column(name = "fecha_actualizacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaActualizacion;
    @Size(max = 50)
    @Column(name = "descripcion_cambios")
    private String descripcionCambios;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dia_vencimiento")
    private int diaVencimiento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dia_lectura_medidor")
    private int diaLecturaMedidor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dia_emision")
    private int diaEmision;

    public ValoresParametricos() {
    }

    public ValoresParametricos(Integer idValoresParametricos) {
        this.idValoresParametricos = idValoresParametricos;
    }

    public ValoresParametricos(Integer idValoresParametricos, int valorCargoFijo, int valorM3, int m3Fijos, int m3LimiteDctoInterno, BigInteger porcentajeDctoInterno, int diaVencimiento, int diaLecturaMedidor, int diaEmision) {
        this.idValoresParametricos = idValoresParametricos;
        this.valorCargoFijo = valorCargoFijo;
        this.valorM3 = valorM3;
        this.m3Fijos = m3Fijos;
        this.m3LimiteDctoInterno = m3LimiteDctoInterno;
        this.porcentajeDctoInterno = porcentajeDctoInterno;
        this.diaVencimiento = diaVencimiento;
        this.diaLecturaMedidor = diaLecturaMedidor;
        this.diaEmision = diaEmision;
    }

    public Integer getIdValoresParametricos() {
        return idValoresParametricos;
    }

    public void setIdValoresParametricos(Integer idValoresParametricos) {
        this.idValoresParametricos = idValoresParametricos;
    }

    public int getValorCargoFijo() {
        return valorCargoFijo;
    }

    public void setValorCargoFijo(int valorCargoFijo) {
        this.valorCargoFijo = valorCargoFijo;
    }

    public int getValorM3() {
        return valorM3;
    }

    public void setValorM3(int valorM3) {
        this.valorM3 = valorM3;
    }

    public int getM3Fijos() {
        return m3Fijos;
    }

    public void setM3Fijos(int m3Fijos) {
        this.m3Fijos = m3Fijos;
    }

    public int getM3LimiteDctoInterno() {
        return m3LimiteDctoInterno;
    }

    public void setM3LimiteDctoInterno(int m3LimiteDctoInterno) {
        this.m3LimiteDctoInterno = m3LimiteDctoInterno;
    }

    public BigInteger getPorcentajeDctoInterno() {
        return porcentajeDctoInterno;
    }

    public void setPorcentajeDctoInterno(BigInteger porcentajeDctoInterno) {
        this.porcentajeDctoInterno = porcentajeDctoInterno;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public String getDescripcionCambios() {
        return descripcionCambios;
    }

    public void setDescripcionCambios(String descripcionCambios) {
        this.descripcionCambios = descripcionCambios;
    }

    public int getDiaVencimiento() {
        return diaVencimiento;
    }

    public void setDiaVencimiento(int diaVencimiento) {
        this.diaVencimiento = diaVencimiento;
    }

    public int getDiaLecturaMedidor() {
        return diaLecturaMedidor;
    }

    public void setDiaLecturaMedidor(int diaLecturaMedidor) {
        this.diaLecturaMedidor = diaLecturaMedidor;
    }

    public int getDiaEmision() {
        return diaEmision;
    }

    public void setDiaEmision(int diaEmision) {
        this.diaEmision = diaEmision;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idValoresParametricos != null ? idValoresParametricos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ValoresParametricos)) {
            return false;
        }
        ValoresParametricos other = (ValoresParametricos) object;
        if ((this.idValoresParametricos == null && other.idValoresParametricos != null) || (this.idValoresParametricos != null && !this.idValoresParametricos.equals(other.idValoresParametricos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.apr.entity.ValoresParametricos[ idValoresParametricos=" + idValoresParametricos + " ]";
    }

    @XmlTransient
    public List<Periodo> getPeriodoList() {
        return periodoList;
    }

    public void setPeriodoList(List<Periodo> periodoList) {
        this.periodoList = periodoList;
    }
    
}
