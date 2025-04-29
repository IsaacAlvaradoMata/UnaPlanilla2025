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
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXRadioButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ToggleGroup;
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
        
    }    

    @Override
    public void initialize() {
    }
    
    private void bindEmpleado(){
    try {
        empleadoProperty.addListener((obs, oldVal, newVal) -> {
                if(oldVal != null ){
                    txtId.textFillProperty().unbind();
                txtNombre.textProperty().unbindBidirectional(oldVal.getNombreProperty());
                txtPApellido.textProperty().unbindBidirectional(oldVal.getPrimerApellidoProperty());
                BindingUtils.unbindToggleGroupToProperty(tggGenero, oldVal.getGeneroProperty());

                }
                
                if(newVal != null){
                             if(newVal.getIdProperty().get() != null && 
                                     !newVal.getIdProperty().get().isBlank()) {
                             txtId.textProperty().bindBidirectional(newVal.getNombreProperty());
                             
                             }
                              txtNombre.textProperty().bindBidirectional(newVal.getNombreProperty());
                              txtPApellido.textProperty().unbindBidirectional(newVal.getPrimerApellidoProperty());
                               BindingUtils.bindToggleGroupToProperty(tggGenero, newVal.getGeneroProperty());


                }
});
    } catch(Exception ex){
            
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error al realizar rl bindeo", getStage(), "Ocurrio un error al realizar el bideneo");
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
//            requeridos.addAll(Arrays.asList(txtUsuario, txtClave));
            txtUsuario.setDisable(false);
            txtClave.setDisable(false);
        } else {
//            requeridos.removeAll(Arrays.asList(txtUsuario, txtClave));
            txtUsuario.clear();
            txtUsuario.setDisable(true);
            txtClave.clear();
            txtClave.setDisable(true);
        }
    }

    @FXML
    private void onKeyPressedTxtId(KeyEvent event) {
    }

    @FXML
    private void onActionCheckAdministrador(ActionEvent event) {
                validarAdministrador();

    }

    @FXML
    private void onActionBtnNuevo(ActionEvent event) {
    }

    @FXML
    private void onActionBtnBuscar(ActionEvent event) {
    }

    @FXML
    private void onActionBtnEliminar(ActionEvent event) {
    }

    @FXML
    private void onActionBtnGuardar(ActionEvent event) {
    }
    
}
