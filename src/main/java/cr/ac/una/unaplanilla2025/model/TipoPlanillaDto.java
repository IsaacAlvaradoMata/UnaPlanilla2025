package cr.ac.una.unaplanilla2025.model;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.util.Objects;

/**
 * FXML Controller class
 *
 * @author josue_5njzopn
 */
public class TipoPlanillaDto {

    private StringProperty id;
    private StringProperty codigo;
    private StringProperty descripcion;
    private LongProperty plaxMes;
    private LongProperty anoUltPla;
    private LongProperty mesUltPla;
    private LongProperty numUltPla;
    private StringProperty estado;
    private Long version;
    private Boolean modificado;

    public TipoPlanillaDto() {
        this.id = new SimpleStringProperty("");
        this.codigo = new SimpleStringProperty("");
        this.descripcion = new SimpleStringProperty("");
        this.plaxMes = new SimpleLongProperty();
        this.anoUltPla = new SimpleLongProperty();
        this.mesUltPla = new SimpleLongProperty();
        this.numUltPla = new SimpleLongProperty();
        this.estado = new SimpleStringProperty("");
        this.modificado = false;
    }

    public TipoPlanillaDto(TipoPlanilla tipoPlanilla) {
        this();
        this.id.set(tipoPlanilla.getId() != null ? tipoPlanilla.getId().toString() : "");
        this.codigo.set(tipoPlanilla.getCodigo());
        this.descripcion.set(tipoPlanilla.getDescripcion());
        if (tipoPlanilla.getPlaxMes() != null) {
            this.plaxMes.set(tipoPlanilla.getPlaxMes());
        }
        if (tipoPlanilla.getAnoUltPla() != null) {
            this.anoUltPla.set(tipoPlanilla.getAnoUltPla());
        }
        if (tipoPlanilla.getMesUltPla() != null) {
            this.mesUltPla.set(tipoPlanilla.getMesUltPla());
        }
        if (tipoPlanilla.getNumUltPla() != null) {
            this.numUltPla.set(tipoPlanilla.getNumUltPla());
        }
        this.estado.set(tipoPlanilla.getEstado());
        this.version = tipoPlanilla.getVersion();
    }

    public Long getId() {
        try {
            return (this.id.get() != null && !this.id.get().isBlank()) ? Long.valueOf(this.id.get()) : null;
        } catch (NumberFormatException ex) {
            return null;
        }
    }


    public void setId(Long id) {
        this.id.set(id.toString());
    }

    public String getCodigo() {
        return codigo.get();
    }

    public void setCodigo(String codigo) {
        this.codigo.set(codigo);
    }

    public String getDescripcion() {
        return descripcion.get();
    }

    public void setDescripcion(String descripcion) {
        this.descripcion.set(descripcion);
    }

    public Long getPlaxMes() {
        return plaxMes.get();
    }

    public void setPlaxMes(Long plaxMes) {
        this.plaxMes.set(plaxMes);
    }

    public Long getAnoUltPla() {
        return anoUltPla.get();
    }

    public void setAnoUltPla(Long anoUltPla) {
        this.anoUltPla.set(anoUltPla);
    }

    public Long getMesUltPla() {
        return mesUltPla.get();
    }

    public void setMesUltPla(Long mesUltPla) {
        this.mesUltPla.set(mesUltPla);
    }

    public Long getNumUltPla() {
        return numUltPla.get();
    }

    public void setNumUltPla(Long numUltPla) {
        this.numUltPla.set(numUltPla);
    }

    public String getEstado() {
        return estado.get();
    }

    public void setEstado(String estado) {
        this.estado.set(estado);
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Boolean getModificado() {
        return modificado;
    }

    public void setModificado(Boolean modificado) {
        this.modificado = modificado;
    }

    public StringProperty getIdProperty() {
        return id;
    }

    public StringProperty getCodigoProperty() {
        return codigo;
    }

    public StringProperty getDescripcionProperty() {
        return descripcion;
    }

    public LongProperty getPlaxMesProperty() {
        return plaxMes;
    }

    public LongProperty getAnoUltPlaProperty() {
        return anoUltPla;
    }

    public LongProperty getMesUltPlaProperty() {
        return mesUltPla;
    }

    public LongProperty getNumUltPlaProperty() {
        return numUltPla;
    }

    public StringProperty getEstadoProperty() {
        return estado;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final TipoPlanillaDto other = (TipoPlanillaDto) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "TipoPlanillaDto{" + "id=" + id + ", codigo=" + codigo + ", descripcion=" + descripcion + '}';
    }
}
