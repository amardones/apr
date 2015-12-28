/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.apr.entity;

import java.io.Serializable;
import java.math.BigInteger;
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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author hmardones
 */
@Entity
@Table(name = "subsidio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Subsidio.findAll", query = "SELECT s FROM Subsidio s"),
    @NamedQuery(name = "Subsidio.findByIdSubsidio", query = "SELECT s FROM Subsidio s WHERE s.idSubsidio = :idSubsidio"),
    @NamedQuery(name = "Subsidio.findByNombre", query = "SELECT s FROM Subsidio s WHERE s.nombre = :nombre"),
    @NamedQuery(name = "Subsidio.findByDescripcion", query = "SELECT s FROM Subsidio s WHERE s.descripcion = :descripcion"),
    @NamedQuery(name = "Subsidio.findByPorcentaje", query = "SELECT s FROM Subsidio s WHERE s.porcentaje = :porcentaje"),
    @NamedQuery(name = "Subsidio.findByMetrosCubicosTope", query = "SELECT s FROM Subsidio s WHERE s.metrosCubicosTope = :metrosCubicosTope")})
public class Subsidio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_subsidio")
    private Integer idSubsidio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 100)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Max(100)
    @Min(1)
    @Column(name = "porcentaje")
    private BigInteger porcentaje;
    @Basic(optional = false)
    @NotNull
    @Min(1)
    @Column(name = "metros_cubicos_tope")
    private int metrosCubicosTope;
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "idSubsidio")
    private List<CuentaSubsidio> cuentaSubsidioList;

    public Subsidio() {
    }

    public Subsidio(Integer idSubsidio) {
        this.idSubsidio = idSubsidio;
    }

    public Subsidio(Integer idSubsidio, String nombre, BigInteger porcentaje, int metrosCubicosTope) {
        this.idSubsidio = idSubsidio;
        this.nombre = nombre;
        this.porcentaje = porcentaje;
        this.metrosCubicosTope = metrosCubicosTope;
    }

    public Integer getIdSubsidio() {
        return idSubsidio;
    }

    public void setIdSubsidio(Integer idSubsidio) {
        this.idSubsidio = idSubsidio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigInteger getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(BigInteger porcentaje) {
        this.porcentaje = porcentaje;
    }

    public int getMetrosCubicosTope() {
        return metrosCubicosTope;
    }

    public void setMetrosCubicosTope(int metrosCubicosTope) {
        this.metrosCubicosTope = metrosCubicosTope;
    }

    @XmlTransient
    public List<CuentaSubsidio> getCuentaSubsidioList() {
        return cuentaSubsidioList;
    }

    public void setCuentaSubsidioList(List<CuentaSubsidio> cuentaSubsidioList) {
        this.cuentaSubsidioList = cuentaSubsidioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSubsidio != null ? idSubsidio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Subsidio)) {
            return false;
        }
        Subsidio other = (Subsidio) object;
        if ((this.idSubsidio == null && other.idSubsidio != null) || (this.idSubsidio != null && !this.idSubsidio.equals(other.idSubsidio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.apr.entity.Subsidio[ idSubsidio=" + idSubsidio + " ]";
    }
    
}
