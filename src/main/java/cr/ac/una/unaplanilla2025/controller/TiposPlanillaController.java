/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.unaplanilla2025.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author josue_5njzopn
 */
public class TiposPlanillaController extends Controller implements Initializable {

    @FXML
    private MFXTextField txtId;
    @FXML
    private MFXCheckbox chkActivo;
    @FXML
    private MFXTextField txtCodigo;
    @FXML
    private MFXTextField txtDescripcion;
    @FXML
    private MFXTextField txtPlantillasXmes;
    @FXML
    private MFXTextField txtEmpleado;
    @FXML
    private MFXTextField txtNombre;
    @FXML
    private TableView<?> tblvListaEmpleados;
    @FXML
    private MFXButton btnNuevo;
    @FXML
    private MFXButton btnBuscar;
    @FXML
    private MFXButton btnEliminar;
    @FXML
    private MFXButton btnGuardar;
    @FXML
    private ImageView imgAdd;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @Override
    public void initialize() {
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
