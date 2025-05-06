/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.unaplanilla2025.controller;

import cr.ac.una.unaplanilla2025.model.EmpleadoDto;
import cr.ac.una.unaplanilla2025.util.BindingUtils;
import cr.ac.una.unaplanilla2025.util.Formato;
import cr.ac.una.unaplanilla2025.util.Mensaje;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXRadioButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author josue_5njzopn
 */
public class EmpleadosController extends Controller implements Initializable {

    @FXML
    private MFXTextField txtId;
    @FXML
    private MFXTextField txtNombre;
    @FXML
    private MFXTextField txtPApellido;
    @FXML
    private MFXTextField txtSApellido;
    @FXML
    private MFXTextField txtCedula;
    @FXML
    private MFXRadioButton rdbMasculino;
    @FXML
    private ToggleGroup tggGenero;
    @FXML
    private MFXRadioButton rdbFemenino;
    @FXML
    private MFXCheckbox chkAdministrador;
    @FXML
    private MFXCheckbox chkActivo;
    @FXML
    private MFXDatePicker dtpFIngreso;
    @FXML
    private MFXDatePicker dtpFSalida;
    @FXML
    private MFXTextField txtCorreo;
    @FXML
    private MFXTextField txtUsuario;
    @FXML
    private MFXPasswordField txtClave;
    @FXML
    private MFXButton btnNuevo;
    @FXML
    private MFXButton btnBuscar;
    @FXML
    private MFXButton btnEliminar;
    @FXML
    private MFXButton btnGuardar;

    private EmpleadoDto empleado;
    private ObjectProperty<EmpleadoDto> empleadoProperty = new SimpleObjectProperty<>();
    private List<Node> requeridos = new ArrayList();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rdbFemenino.setUserData("F");
        rdbMasculino.setUserData("M");
        txtId.delegateSetTextFormatter(Formato.getInstance().integerFormat());
        txtNombre.delegateSetTextFormatter(Formato.getInstance().letrasFormat(30));
        txtPApellido.delegateSetTextFormatter(Formato.getInstance().letrasFormat(15));
        txtSApellido.delegateSetTextFormatter(Formato.getInstance().letrasFormat(15));
        txtCedula.delegateSetTextFormatter(Formato.getInstance().cedulaFormat(40));
        txtCorreo.delegateSetTextFormatter(Formato.getInstance().maxLengthFormat(80));
        txtUsuario.delegateSetTextFormatter(Formato.getInstance().letrasFormat(15));
        txtClave.delegateSetTextFormatter(Formato.getInstance().maxLengthFormat(8));        
        empleado = new EmpleadoDto();
        bindEmpleado();
        cargarValoresDefecto();
        indicarRequeridos();
    }

    @Override
    public void initialize() {
    }
    
private void bindEmpleado() {
        try {
            empleadoProperty.addListener((obs, oldVal, newVal) -> {
                if (oldVal != null) {
                    txtId.textProperty().unbind();
                    txtNombre.textProperty().unbindBidirectional(oldVal.getNombreProperty());
                    txtPApellido.textProperty().unbindBidirectional(oldVal.getPrimerApellidoProperty());
                    BindingUtils.unbindToggleGroupToProperty(tggGenero,oldVal.getGeneroProperty());
                }
                if (newVal != null) {
                    if(newVal.getIdProperty().get() != null &&
                            newVal.getIdProperty().get().isBlank()){
                        txtId.textProperty().bindBidirectional(newVal.getIdProperty());
                    }
                    txtNombre.textProperty().bindBidirectional(newVal.getNombreProperty());
                    txtPApellido.textProperty().bindBidirectional(newVal.getPrimerApellidoProperty());
                    BindingUtils.bindToggleGroupToProperty(tggGenero,newVal.getGeneroProperty());
                }
            });
        } catch (Exception ex) {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error al realizar el bindeo", getStage(), "Ocurrio un error al realizar el bindeo");
        }
    }

private void cargarValoresDefecto() {
        empleado = new EmpleadoDto();
        empleado.setActivo(Boolean.TRUE);
        empleado.setAdministrador(Boolean.FALSE);
        empleado.setFechaIngreso(LocalDate.now());
        empleado.setGenero("M");
        empleadoProperty.setValue(empleado);
        validarAdministrador();
        txtId.clear();
        txtId.requestFocus();
    }
    private void validarAdministrador() {
        if (chkAdministrador.isSelected()) {
            //requeridos.addAll(Arrays.asList(txtUsuario, txtClave));
            txtUsuario.setDisable(false);
            txtClave.setDisable(false);
        } else {
            //requeridos.removeAll(Arrays.asList(txtUsuario, txtClave));
            txtUsuario.clear();
            txtUsuario.setDisable(true);
            txtClave.clear();
            txtClave.setDisable(true);
        }
    }

    @FXML
    private void onKeyPressedTxtId(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER && txtId.getText().isBlank()){
            cargarEmpleado(Long.valueOf(txtId.getText()));
        }
    }

    @FXML
    private void onActionCheckAdministrador(ActionEvent event) {
        validarAdministrador();
    }

    @FXML
    private void onActionBtnNuevo(ActionEvent event) {

        try {
            String invalidos = validarRequeridos();
            if(!invalidos.isBlank()){
                new Mensaje().showModal(Alert.AlertType.WARNING, "Nuevos Empleados", getStage(), invalidos);
            }else{
                new Mensaje().showModal(Alert.AlertType.INFORMATION, "Nuevos Empleados", getStage(), "El empleado se creo correctamente.");
            }
        } catch (Exception ex) {
            Logger.getLogger(EmpleadosController.class.getName()).log(Level.SEVERE, "Error creando el empleado.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar Empleado", getStage(), "Ocurrió un error creando el empleado.");
        }
    }
    

    @FXML
    private void onActionBtnBuscar(ActionEvent event) {

        
            if(new Mensaje().showConfirmation("Limpiar Empleado", getStage(), "Esta seguro que desea limpiar el registro?")){
                cargarValoresDefecto();
            }
            
    }

    @FXML
    private void onActionBtnEliminar(ActionEvent event) {
        
        try {
            String invalidos = validarRequeridos();
            if(!invalidos.isBlank()){
                new Mensaje().showModal(Alert.AlertType.WARNING, "Eliminar Empleados", getStage(), invalidos);
            }else{
                new Mensaje().showModal(Alert.AlertType.INFORMATION, "Eliminar Empleados", getStage(), "El empleado se guardo correctamente.");
            }
        } catch (Exception ex) {
            Logger.getLogger(EmpleadosController.class.getName()).log(Level.SEVERE, "Error eliminando el empleado.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar Empleados", getStage(), "Ocurrió un error eliminando el empleado.");
        }
    }
    

    @FXML
    private void onActionBtnGuardar(ActionEvent event) {
        try {
            String invalidos = validarRequeridos();
            if(!invalidos.isBlank()){
                new Mensaje().showModal(Alert.AlertType.WARNING, "Guardar Empleados", getStage(), invalidos);
            }else{
                new Mensaje().showModal(Alert.AlertType.INFORMATION, "Guardar Empleados", getStage(), "El empleado se guardo correctamente.");
            }
        } catch (Exception ex) {
            Logger.getLogger(EmpleadosController.class.getName()).log(Level.SEVERE, "Error guardando el empleado.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar Empleado", getStage(), "Ocurrió un error guardando el empleado.");
        }
    }
    
    private void indicarRequeridos(){
        requeridos.clear();
        requeridos.addAll(Arrays.asList(txtCedula, txtNombre, txtPApellido, dtpFIngreso));
    }
    public String validarRequeridos() {
        Boolean validos = true;
        String invalidos = "";
        for (Node node : requeridos) {
            if (node instanceof MFXTextField && (((MFXTextField) node).getText() == null || ((MFXTextField) node).getText().isBlank())) {
                if (validos) {
                    invalidos += ((MFXTextField) node).getFloatingText();
                } else {
                    invalidos += "," + ((MFXTextField) node).getFloatingText();
                }
                validos = false;
            } else if (node instanceof MFXPasswordField && (((MFXPasswordField) node).getText() == null || ((MFXPasswordField) node).getText().isBlank())) {
                if (validos) {
                    invalidos += ((MFXPasswordField) node).getFloatingText();
                } else {
                    invalidos += "," + ((MFXPasswordField) node).getFloatingText();
                }
                validos = false;
            } else if (node instanceof MFXDatePicker && ((MFXDatePicker) node).getValue() == null) {
                if (validos) {
                    invalidos += ((MFXDatePicker) node).getFloatingText();
                } else {
                    invalidos += "," + ((MFXDatePicker) node).getFloatingText();
                }
                validos = false;
            } else if (node instanceof MFXComboBox && ((MFXComboBox) node).getSelectionModel().getSelectedIndex() < 0) {
                if (validos) {
                    invalidos += ((MFXComboBox) node).getFloatingText();
                } else {
                    invalidos += "," + ((MFXComboBox) node).getFloatingText();
                }
                validos = false;
            }
        }
        if (validos) {
            return "";
        } else {
            return "Campos requeridos o con problemas de formato [" + invalidos + "].";
        }
    }
    private void cargarEmpleado(Long id){
         try {
            String invalidos = validarRequeridos();
            if(!invalidos.isBlank()){
                new Mensaje().showModal(Alert.AlertType.WARNING, "Guardar Empleados", getStage(), invalidos);
            }else{
                new Mensaje().showModal(Alert.AlertType.INFORMATION, "Guardar Empleados", getStage(), "El empleado se guardo correctamente.");
            }
        } catch (Exception ex) {
            Logger.getLogger(EmpleadosController.class.getName()).log(Level.SEVERE, "Error guardando el empleado.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar Empleado", getStage(), "Ocurrió un error guardando el empleado.");
        }
    }
}