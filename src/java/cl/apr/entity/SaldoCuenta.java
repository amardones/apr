/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.apr.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author hmardones
 */
@Entity
@Table(name = "saldo_cuenta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SaldoCuenta.findAll", query = "SELECT s FROM SaldoCuenta s"),
    @NamedQuery(name = "SaldoCuenta.findByIdSaldoCuenta", query = "SELECT s FROM SaldoCuenta s WHERE s.idSaldoCuenta = :idSaldoCuenta"),
    @NamedQuery(name = "SaldoCuenta.findByDescripcion", query = "SELECT s FROM SaldoCuenta s WHERE s.descripcion = :descripcion"),
    @NamedQuery(name = "SaldoCuenta.findByFecha", query = "SELECT s FROM SaldoCuenta s WHERE s.fecha = :fecha"),
    @NamedQuery(name = "SaldoCuenta.findByMonto", query = "SELECT s FROM SaldoCuenta s WHERE s.monto = :monto")})
public class SaldoCuenta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_saldo_cuenta")
    private Integer idSaldoCuenta;
    @Size(max = 50)
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Basic(optional = false)
    @NotNull
    @Column(name = "monto")
    private int monto;
    @JoinColumn(name = "id_cuenta", referencedColumnName = "id_cuenta")
    @ManyToOne(optional = false)
    private Cuenta idCuenta;
    @JoinColumn(name = "tipo_movimiento", referencedColumnName = "tipo_movimiento")
    @ManyToOne(optional = false)
    private TipoMovimiento tipoMovimiento;

    public SaldoCuenta() {
    }

    public SaldoCuenta(Integer idSaldoCuenta) {
        this.idSaldoCuenta = idSaldoCuenta;
    }

    public SaldoCuenta(Integer idSaldoCuenta, int monto) {
        this.idSaldoCuenta = idSaldoCuenta;
        this.monto = monto;
    }

    public Integer getIdSaldoCuenta() {
        return idSaldoCuenta;
    }

    public void setIdSaldoCuenta(Integer idSaldoCuenta) {
        this.idSaldoCuenta = idSaldoCuenta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    public Cuenta getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(Cuenta idCuenta) {
        this.idCuenta = idCuenta;
    }

    public TipoMovimiento getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(TipoMovimiento tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSaldoCuenta != null ? idSaldoCuenta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SaldoCuenta)) {
            return false;
        }
        SaldoCuenta other = (SaldoCuenta) object;
        if ((this.idSaldoCuenta == null && other.idSaldoCuenta != null) || (this.idSaldoCuenta != null && !this.idSaldoCuenta.equals(other.idSaldoCuenta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.apr.entity.SaldoCuenta[ idSaldoCuenta=" + idSaldoCuenta + " ]";
    }
    
}
