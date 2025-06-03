/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.unaplanilla2025.controller;

import cr.ac.una.unaplanilla2025.model.EmpleadoDto;
import cr.ac.una.unaplanilla2025.service.EmpleadoService;
import cr.ac.una.unaplanilla2025.util.Mensaje;
import cr.ac.una.unaplanilla2025.util.Respuesta;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


public class BuscarController extends Controller implements Initializable {

    @FXML
    private MFXTextField txtCedula;
    @FXML
    private MFXTextField txtNombre;
    @FXML
    private MFXButton btnFiltrar;
    @FXML
    private TableView<EmpleadoDto> tbvInfo;
    @FXML
    private TableColumn<EmpleadoDto, String> tbcCedula;
    @FXML
    private TableColumn<EmpleadoDto, String> tbcNombre;

    private ObservableList<EmpleadoDto> empleadosList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tbcCedula.setCellValueFactory(emp -> emp.getValue().getCedulaProperty());
        tbcNombre.setCellValueFactory(emp -> emp.getValue().getNombreProperty());
        tbvInfo.setItems(empleadosList);
    }

    @Override
    public void initialize() {

    }

    @FXML
    private void onActionBtnFiltrar(ActionEvent event) {
        EmpleadoService service = new EmpleadoService();
        String cedula = txtCedula.getText();
        String nombre = txtNombre.getText();

        Respuesta respuesta = service.filtrarEmpleados(cedula, nombre);
        if (respuesta.getEstado()) {
            empleadosList.setAll((List<EmpleadoDto>) respuesta.getResultado("Empleados"));
        } else {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Filtrar Empleados", getStage(), respuesta.getMensaje());
        }
    }

}
