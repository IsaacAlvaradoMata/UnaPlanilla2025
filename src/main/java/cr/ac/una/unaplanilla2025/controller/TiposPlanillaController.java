package cr.ac.una.unaplanilla2025.controller;

import cr.ac.una.unaplanilla2025.model.EmpleadoDto;
import cr.ac.una.unaplanilla2025.model.TipoPlanillaDto;
import cr.ac.una.unaplanilla2025.service.EmpleadoService;
import cr.ac.una.unaplanilla2025.service.TipoPlanillaService;
import cr.ac.una.unaplanilla2025.util.BindingUtils;
import cr.ac.una.unaplanilla2025.util.Formato;
import cr.ac.una.unaplanilla2025.util.Mensaje;
import cr.ac.una.unaplanilla2025.util.Respuesta;
import io.github.palexdev.materialfx.controls.*;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.Event;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


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
    @FXML
    private Tab tabTipoPlanillas;
    @FXML
    private Tab tabInclusionEmpleados;
    @FXML
    private MFXTextField txtIdEmpleado;
    @FXML
    private MFXTextField txtNombre;
    @FXML
    private Button btnAgregarEmpleado;
    @FXML
    private TableView<EmpleadoDto> tbvEmpleados;
    @FXML
    private TableColumn<EmpleadoDto, String> tbcCodigo;
    @FXML
    private TableColumn<EmpleadoDto, String> tbcNombre;
    @FXML
    private TableColumn<EmpleadoDto, Boolean> tbcEliminar;

    private TipoPlanillaDto tipoPlanilla;
    private ObjectProperty<TipoPlanillaDto> tipoPlanillaProperty = new SimpleObjectProperty<>();
    private ObjectProperty<EmpleadoDto> empleadoProperty = new SimpleObjectProperty<>();

    private List<Node> requeridos = new ArrayList<>();

    EmpleadoDto empleadoDto;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtId.delegateSetTextFormatter(Formato.getInstance().integerFormat());
        txtCodigo.delegateSetTextFormatter(Formato.getInstance().letrasFormat(4));
        txtDescripcion.delegateSetTextFormatter(Formato.getInstance().maxLengthFormat(40));
        txtPlantillasXmes.delegateSetTextFormatter(Formato.getInstance().integerFormat());
        empleadoDto = new EmpleadoDto();
        tipoPlanilla = new TipoPlanillaDto();
        bindTipoPlanilla();
        bindEmpleado();
        cargarValoresDefecto();
        indicarRequeridos();



        tbcCodigo.setCellValueFactory((cd) -> cd.getValue().getIdProperty());
        tbcNombre.setCellValueFactory((cd) -> cd.getValue().getNombreProperty());
//        tbcEliminar.setCellFactory((cd) -> new SimpleBooleanProperty(cd.getValue() != null));
        tbcEliminar.setCellFactory((df) -> new ButtonCell());


        tbvEmpleados.getSelectionModel().selectedItemProperty().addListener((ov, oldValue, newValue) -> {
           if(newValue != null){
               this.empleadoDto = newValue;
               this.empleadoProperty.setValue(this.empleadoDto);

           }
        });

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
        limpiarEmpleado();
        cargarEmpleados();
    }

    private void limpiarEmpleado() {
        tbvEmpleados.getSelectionModel().select(null);
        this.empleadoDto = new EmpleadoDto();
        this.empleadoProperty.setValue(this.empleadoDto);
        txtIdEmpleado.clear();
        txtIdEmpleado.requestFocus();

    }

    private void cargarEmpleados() {
        tbvEmpleados.getItems().clear();
//        tbvEmpleados.setItems(this.tipoPlanillaDto.getEmpleados());
        tbvEmpleados.refresh();


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

    private void bindEmpleado() {
        try {
            empleadoProperty.addListener((obs, oldVal, newVal) -> {
                if (oldVal != null) {
                    txtIdEmpleado.textProperty().unbind();
                    txtIdEmpleado.textProperty().unbindBidirectional(oldVal.getNombreProperty());

                }
                if (newVal != null) {
                    if (newVal.getIdProperty().get() != null &&
                            newVal.getIdProperty().get().isBlank()) {
                        txtIdEmpleado.textProperty().bindBidirectional(newVal.getIdProperty());
                    }
                }
            });
        } catch (Exception ex) {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error al realizar el bindeo", getStage(), "Ocurrio un error al realizar el bindeo");
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
                validarRequeridos();
                cargarEmpleados();
            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Buscar Planilla", getStage(), respuesta.getMensaje());
            }
        } catch (Exception ex) {
            Logger.getLogger(TiposPlanillaController.class.getName()).log(Level.SEVERE, "Error cargando la planilla.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Buscar Planilla", getStage(), "Ocurrió un error al buscar la planilla.");
        }
    }


    private void cargarEmpleado(Long id){
        try {
            EmpleadoService empleadoService = new EmpleadoService();
            Respuesta respuesta = empleadoService.getEmpleado(id);
            if(respuesta.getEstado()){
                this.empleadoDto = (EmpleadoDto) respuesta.getResultado("Empleado");
                this.empleadoProperty.set(this.empleadoDto);
            }else{
                new Mensaje().showModal(Alert.AlertType.ERROR, "Buscar Empleado", getStage(), respuesta.getMensaje());

            }

        } catch (Exception ex) {
            Logger.getLogger(EmpleadosController.class.getName()).log(Level.SEVERE, "Error guardando el empleado.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar Empleado", getStage(), "Ocurrió un error guardando el empleado.");
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

    @FXML
    private void OnActionBtnAgregarEmpleado(ActionEvent event) {

        if(this.empleadoDto.getId() == null || this.empleadoDto.getNombre().isBlank()){
            new Mensaje().showModal(Alert.AlertType.INFORMATION, "Agregar Empleado", getStage(), "Empleado agregado con exito.");
        }else if(tbvEmpleados.getItems() == null || tbvEmpleados.getItems().stream().noneMatch(e -> e.equals(this.empleadoDto))) {

            this.empleadoDto.setModificado(Boolean.TRUE);
            tbvEmpleados.getItems().add(this.empleadoDto);
            tbvEmpleados.refresh();


        }
    }

    @FXML
    private void selectionChangeTabEmp(Event event) {

        if (tabInclusionEmpleados.isSelected()) {
//            if (this.tipoPlanillaDto.getId() == null) {
//
//                new Mensaje().showModal(Alert.AlertType.INFORMATION, "Incluir Empleados", getStage(), "Favor consultar la planilla a incluir empleados.");
//                tabTipoPlanillas.setSelected(true);
//            }
        }
    }

    private class ButtonCell extends TableCell<EmpleadoDto, Boolean> {
        final Button cellButton = new Button();

        public ButtonCell() {
            cellButton.setPrefWidth(500);
            cellButton.getStyleClass().add("jfx-btnimg-tbveliminar");
            cellButton.setOnAction(event -> {
               EmpleadoDto emp =  ButtonCell.this.getTableView().getItems().get(ButtonCell.this.getIndex());
//               tipoPlanillaDto.getEmpleadosEliminados().add(emp);
               tbvEmpleados.getItems().remove(emp);
               tbvEmpleados.refresh();


            });
        }

        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if(!empty){
                setGraphic(cellButton);



            }


        }

    }



}
