/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.unaplanilla2025.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author josue_5njzopn
 */
@Entity
@Table(name = "PLA_HISSALARIOS")
@NamedQueries({
    @NamedQuery(name = "Salario.findAll", query = "SELECT s FROM Salario s"),
    @NamedQuery(name = "Salario.findById", query = "SELECT s FROM Salario s WHERE s.id = :id"),
    @NamedQuery(name = "Salario.findBySalAno", query = "SELECT s FROM Salario s WHERE s.salAno = :salAno"),
    @NamedQuery(name = "Salario.findBySalMes", query = "SELECT s FROM Salario s WHERE s.salMes = :salMes"),
    @NamedQuery(name = "Salario.findBySalMonto", query = "SELECT s FROM Salario s WHERE s.salMonto = :salMonto"),
    @NamedQuery(name = "Salario.findBySalVersion", query = "SELECT s FROM Salario s WHERE s.salVersion = :salVersion")})
public class Salario implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Basic(optional = false)
    @Column(name = "SAL_ANO")
    private short salAno;
    @Basic(optional = false)
    @Column(name = "SAL_MES")
    private short salMes;
    @Basic(optional = false)
    @Column(name = "SAL_MONTO")
    private BigDecimal salMonto;
    @Basic(optional = false)
    @Column(name = "SAL_VERSION")
    private BigInteger salVersion;
    @JoinColumn(name = "SAL_EMPID", referencedColumnName = "ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Empleado salEmpid;

    public Salario() {
    }

    public Salario(BigDecimal id) {
        this.id = id;
    }

    public Salario(BigDecimal id, short salAno, short salMes, BigDecimal salMonto, BigInteger salVersion) {
        this.id = id;
        this.salAno = salAno;
        this.salMes = salMes;
        this.salMonto = salMonto;
        this.salVersion = salVersion;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public short getSalAno() {
        return salAno;
    }

    public void setSalAno(short salAno) {
        this.salAno = salAno;
    }

    public short getSalMes() {
        return salMes;
    }

    public void setSalMes(short salMes) {
        this.salMes = salMes;
    }

    public BigDecimal getSalMonto() {
        return salMonto;
    }

    public void setSalMonto(BigDecimal salMonto) {
        this.salMonto = salMonto;
    }

    public BigInteger getSalVersion() {
        return salVersion;
    }

    public void setSalVersion(BigInteger salVersion) {
        this.salVersion = salVersion;
    }

    public Empleado getSalEmpid() {
        return salEmpid;
    }

    public void setSalEmpid(Empleado salEmpid) {
        this.salEmpid = salEmpid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Salario)) {
            return false;
        }
        Salario other = (Salario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.unaplanilla2025.model.Salario[ id=" + id + " ]";
    }
    
}
