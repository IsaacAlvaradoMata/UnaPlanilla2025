package cr.ac.una.unaplanilla2025.model;

import java.io.Serializable;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "PLAM_TIPOPLANILLAS", schema = "UNA")
@NamedQueries({
        @NamedQuery(name = "Tipoplanilla.findAll", query = "SELECT t FROM TipoPlanilla t"),
        @NamedQuery(name = "Tipoplanilla.findByTplaId", query = "SELECT t FROM TipoPlanilla t WHERE t.id = :id"),
        @NamedQuery(name = "Tipoplanilla.findByTplaCodigo", query = "SELECT t FROM TipoPlanilla t WHERE t.codigo = :codigo"),
        @NamedQuery(name = "Tipoplanilla.findByTplaDescripcion", query = "SELECT t FROM TipoPlanilla t WHERE t.descripcion = :descripcion"),
        @NamedQuery(name = "Tipoplanilla.findByTplaPlaxmes", query = "SELECT t FROM TipoPlanilla t WHERE t.plaxMes = :plaxMes"),
        @NamedQuery(name = "Tipoplanilla.findByTplaAnoultpla", query = "SELECT t FROM TipoPlanilla t WHERE t.anoUltPla = :anoUltPla"),
        @NamedQuery(name = "Tipoplanilla.findByTplaMesultpla", query = "SELECT t FROM TipoPlanilla t WHERE t.mesUltPla = :mesUltPla"),
        @NamedQuery(name = "Tipoplanilla.findByTplaNumultpla", query = "SELECT t FROM TipoPlanilla t WHERE t.numUltPla = :numUltPla"),
        @NamedQuery(name = "Tipoplanilla.findByTplaEstado", query = "SELECT t FROM TipoPlanilla t WHERE t.estado = :estado"),
        @NamedQuery(name = "Tipoplanilla.findByTplaVersion", query = "SELECT t FROM TipoPlanilla t WHERE t.version = :version")
})
public class TipoPlanilla implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PLAM_TIPOPLANILLAS_SEQ")
    @SequenceGenerator(name = "PLAM_TIPOPLANILLAS_SEQ", sequenceName = "UNA.PLAM_TIPOPLANILLAS_SEQ01", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "TPLA_ID")
    private Long id;
    @Basic(optional = false)
    @Column(name = "TPLA_CODIGO")
    private String codigo;
    @Basic(optional = false)
    @Column(name = "TPLA_DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "TPLA_PLAXMES")
    private Long plaxMes;
    @Column(name = "TPLA_ANOULTPLA")
    private Long anoUltPla;
    @Column(name = "TPLA_MESULTPLA")
    private Long mesUltPla;
    @Column(name = "TPLA_NUMULTPLA")
    private Long numUltPla;
    @Basic(optional = false)
    @Column(name = "TPLA_ESTADO")
    private String estado;
    @Version
    @Column(name = "TPLA_VERSION")
    private Long version;

    @JoinTable(
            name = "PLAM_EMPLEADOSPLANILLA",
            joinColumns = @JoinColumn(name = "EXP_IDTPLA", referencedColumnName = "TPLA_ID"),
            inverseJoinColumns = @JoinColumn(name = "EXP_IDEMP", referencedColumnName = "EMP_ID")
    )
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Empleado> empleados;

    public TipoPlanilla() {
    }

    public TipoPlanilla(Long id) {
        this.id = id;
    }

    public TipoPlanilla(Long id, String codigo, String descripcion, Long plaxMes, String estado, Long version) {
        this.id = id;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.plaxMes = plaxMes;
        this.estado = estado;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getPlaxMes() {
        return plaxMes;
    }

    public void setPlaxMes(Long plaxMes) {
        this.plaxMes = plaxMes;
    }

    public Long getAnoUltPla() {
        return anoUltPla;
    }

    public void setAnoUltPla(Long anoUltPla) {
        this.anoUltPla = anoUltPla;
    }

    public Long getMesUltPla() {
        return mesUltPla;
    }

    public void setMesUltPla(Long mesUltPla) {
        this.mesUltPla = mesUltPla;
    }

    public Long getNumUltPla() {
        return numUltPla;
    }

    public void setNumUltPla(Long numUltPla) {
        this.numUltPla = numUltPla;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public List<Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof TipoPlanilla)) {
            return false;
        }
        TipoPlanilla other = (TipoPlanilla) object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return "cr.ac.una.unaplanilla2025.model.Tipoplanilla[ id=" + id + " ]";
    }

    public TipoPlanilla(TipoPlanillaDto dto) {
        this.id = dto.getId();
        actualizar(dto);
    }

    public void actualizar(TipoPlanillaDto dto) {
        this.codigo = dto.getCodigo();
        this.descripcion = dto.getDescripcion();
        this.plaxMes = dto.getPlaxMes();
        this.anoUltPla = dto.getAnoUltPla();
        this.mesUltPla = dto.getMesUltPla();
        this.numUltPla = dto.getNumUltPla();
        this.estado = dto.getEstado();
        this.version = dto.getVersion();
    }

}