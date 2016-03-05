/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.apr.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Matias
 */
@Entity
@Table(name = "datos_comite")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DatosComite.findAll", query = "SELECT d FROM DatosComite d"),
    @NamedQuery(name = "DatosComite.findByNombre", query = "SELECT d FROM DatosComite d WHERE d.nombre = :nombre"),
    @NamedQuery(name = "DatosComite.findByCodigo", query = "SELECT d FROM DatosComite d WHERE d.codigo = :codigo"),
    @NamedQuery(name = "DatosComite.findByDato", query = "SELECT d FROM DatosComite d WHERE d.dato = :dato")})
public class DatosComite implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "nombre")
    private String nombre;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "codigo")
    private String codigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "dato")
    private String dato;

    public DatosComite() {
    }

    public DatosComite(String codigo) {
        this.codigo = codigo;
    }

    public DatosComite(String codigo, String nombre, String dato) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.dato = dato;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDato() {
        return dato;
    }

    public void setDato(String dato) {
        this.dato = dato;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DatosComite)) {
            return false;
        }
        DatosComite other = (DatosComite) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.apr.entity.DatosComite[ codigo=" + codigo + " ]";
    }
    
}
