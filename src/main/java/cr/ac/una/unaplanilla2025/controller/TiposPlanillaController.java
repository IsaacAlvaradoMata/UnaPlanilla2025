package cr.ac.una.unaplanilla2025.controller;

import cr.ac.una.unaplanilla2025.model.TipoPlanillaDto;
import cr.ac.una.unaplanilla2025.service.TipoPlanillaService;
import cr.ac.una.unaplanilla2025.util.Formato;
import cr.ac.una.unaplanilla2025.util.Mensaje;
import cr.ac.una.unaplanilla2025.util.Respuesta;
import io.github.palexdev.materialfx.controls.*;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * FXML Controller class
 *
 * @author josue_5njzopn
 */
public class TiposPlanillaController extends Controller implements Initializable {

    @FXML
    private MFXTextField txtId;
    @FXML
    private MFXTextField txtCodigo;
    @FXML
    private MFXTextField txtDescripcion;
    @FXML
    private MFXTextField txtPlantillasXmes;
    @FXML
    private MFXCheckbox chkActivo;
    @FXML
    private MFXButton btnNuevo;
    @FXML
    private MFXButton btnBuscar;
    @FXML
    private MFXButton btnEliminar;
    @FXML
    private MFXButton btnGuardar;

    private TipoPlanillaDto tipoPlanilla;
    private ObjectProperty<TipoPlanillaDto> tipoPlanillaProperty = new SimpleObjectProperty<>();
    private List<Node> requeridos = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtId.delegateSetTextFormatter(Formato.getInstance().integerFormat());
        txtCodigo.delegateSetTextFormatter(Formato.getInstance().letrasFormat(4));
        txtDescripcion.delegateSetTextFormatter(Formato.getInstance().maxLengthFormat(40));
        txtPlantillasXmes.delegateSetTextFormatter(Formato.getInstance().integerFormat());

        tipoPlanilla = new TipoPlanillaDto();
        bindTipoPlanilla();
        cargarValoresDefecto();
        indicarRequeridos();
    }

    @Override
    public void initialize() {}

    private void bindTipoPlanilla() {
        tipoPlanillaProperty.addListener((obs, oldVal, newVal) -> {
            if (oldVal != null) {
                txtId.textProperty().unbind();
                txtCodigo.textProperty().unbindBidirectional(oldVal.getCodigoProperty());
                txtDescripcion.textProperty().unbindBidirectional(oldVal.getDescripcionProperty());
            }
            if (newVal != null) {
                txtId.textProperty().bindBidirectional(newVal.getIdProperty());
                txtCodigo.textProperty().bindBidirectional(newVal.getCodigoProperty());
                txtDescripcion.textProperty().bindBidirectional(newVal.getDescripcionProperty());

                txtPlantillasXmes.setText(String.valueOf(newVal.getPlaxMes() != null ? newVal.getPlaxMes() : 0));
                chkActivo.setSelected("A".equalsIgnoreCase(newVal.getEstado()));
            }
        });
    }


    private void cargarValoresDefecto() {
        tipoPlanilla = new TipoPlanillaDto();
        tipoPlanilla.setEstado("A");
        tipoPlanillaProperty.set(tipoPlanilla);
        txtId.clear();
        txtId.requestFocus();
    }

    @FXML
    private void onActionBtnNuevo(ActionEvent event) {
        if(new Mensaje().showConfirmation("Limpiar informacion", getStage(), "Esta seguro que desea limpiar el registro?")){
            cargarValoresDefecto();
        }
    }

    @FXML
    private void onActionBtnBuscar(ActionEvent event) {

    }

    @FXML
    private void onActionBtnEliminar(ActionEvent event) {
        try {
            if (tipoPlanilla.getId() == null) {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar Tipo Planilla", getStage(), "Favor consultar la planilla a eliminar.");
            } else {
                TipoPlanillaService service = new TipoPlanillaService();
                Respuesta respuesta = service.eliminarTipoPlanilla(tipoPlanilla.getId());
                if (respuesta.getEstado()) {
                    cargarValoresDefecto();
                    new Mensaje().showModal(Alert.AlertType.INFORMATION, "Eliminar Tipo Planilla", getStage(), "La planilla se eliminó correctamente.");
                } else {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar Tipo Planilla", getStage(), respuesta.getMensaje());
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(TiposPlanillaController.class.getName()).log(Level.SEVERE, "Error eliminando la planilla.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar Tipo Planilla", getStage(), "Ocurrió un error eliminando la planilla.");
        }
    }

    @FXML
    private void onActionBtnGuardar(ActionEvent event) {
        try {
            String invalidos = validarRequeridos();
            if (!invalidos.isBlank()) {
                new Mensaje().showModal(Alert.AlertType.WARNING, "Guardar Tipo Planilla", getStage(), invalidos);
            } else {
                tipoPlanilla.setPlaxMes(Long.valueOf(txtPlantillasXmes.getText()));
                tipoPlanilla.setEstado(chkActivo.isSelected() ? "A" : "I");
                TipoPlanillaService service = new TipoPlanillaService();
                Respuesta respuesta = service.guardarTipoPlanilla(tipoPlanilla);
                if (respuesta.getEstado()) {
                    tipoPlanilla = (TipoPlanillaDto) respuesta.getResultado("TipoPlanilla");
                    tipoPlanillaProperty.set(tipoPlanilla);
                    new Mensaje().showModal(Alert.AlertType.INFORMATION, "Guardar Tipo Planilla", getStage(), "La planilla se guardó correctamente.");
                } else {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar Tipo Planilla", getStage(), respuesta.getMensaje());
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(TiposPlanillaController.class.getName()).log(Level.SEVERE, "Error guardando la planilla.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar Tipo Planilla", getStage(), "Ocurrió un error guardando la planilla.");
        }
    }

    private void indicarRequeridos() {
        requeridos.clear();
        requeridos.addAll(Arrays.asList(txtCodigo, txtDescripcion, txtPlantillasXmes));
    }

    private String validarRequeridos() {
        Boolean validos = true;
        String invalidos = "";
        for (Node node : requeridos) {
            if (node instanceof MFXTextField && (((MFXTextField) node).getText() == null || ((MFXTextField) node).getText().isBlank())) {
                if (validos) {
                    invalidos += ((MFXTextField) node).getFloatingText();
                } else {
                    invalidos += ", " + ((MFXTextField) node).getFloatingText();
                }
                validos = false;
            }
        }
        return validos ? "" : "Campos requeridos o con formato incorrecto: [" + invalidos + "].";
    }

    @FXML
    private void onKeyPressedTxtId(javafx.scene.input.KeyEvent event) {
        if (event.getCode() == javafx.scene.input.KeyCode.ENTER && !txtId.getText().isBlank()) {
            try {
                cargarTipoPlanilla(Long.valueOf(txtId.getText()));
            } catch (NumberFormatException e) {
                new Mensaje().showModal(Alert.AlertType.ERROR, "ID inválido", getStage(), "Debe ingresar un número válido como ID.");
            }
        }
    }

    private void cargarTipoPlanilla(Long id) {
        try {
            TipoPlanillaService service = new TipoPlanillaService();
            Respuesta respuesta = service.getTipoPlanilla(id);
            if (respuesta.getEstado()) {
                this.tipoPlanilla = (TipoPlanillaDto) respuesta.getResultado("TipoPlanilla");
                this.tipoPlanillaProperty.set(this.tipoPlanilla);
                txtPlantillasXmes.setText(String.valueOf(tipoPlanilla.getPlaxMes()));
                chkActivo.setSelected("A".equalsIgnoreCase(tipoPlanilla.getEstado()));
            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Buscar Planilla", getStage(), respuesta.getMensaje());
            }
        } catch (Exception ex) {
            Logger.getLogger(TiposPlanillaController.class.getName()).log(Level.SEVERE, "Error cargando la planilla.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Buscar Planilla", getStage(), "Ocurrió un error al buscar la planilla.");
        }
    }

    @FXML
    private void onKeyPressedTxtCodigo(javafx.scene.input.KeyEvent event) {
        if (event.getCode() == javafx.scene.input.KeyCode.ENTER && !txtCodigo.getText().isBlank()) {
            cargarTipoPlanillaPorCodigo(txtCodigo.getText().trim());
        }
    }

    private void cargarTipoPlanillaPorCodigo(String codigo) {
        try {
            TipoPlanillaService service = new TipoPlanillaService();
            Respuesta respuesta = service.getTipoPlanillaPorCodigo(codigo);
            if (respuesta.getEstado()) {
                this.tipoPlanilla = (TipoPlanillaDto) respuesta.getResultado("TipoPlanilla");
                this.tipoPlanillaProperty.set(this.tipoPlanilla);
                txtPlantillasXmes.setText(String.valueOf(tipoPlanilla.getPlaxMes()));
                chkActivo.setSelected("A".equalsIgnoreCase(tipoPlanilla.getEstado()));
            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Buscar Planilla", getStage(), respuesta.getMensaje());
            }
        } catch (Exception ex) {
            Logger.getLogger(TiposPlanillaController.class.getName()).log(Level.SEVERE, "Error cargando la planilla por código.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Buscar Planilla", getStage(), "Ocurrió un error al buscar la planilla.");
        }
    }



}
