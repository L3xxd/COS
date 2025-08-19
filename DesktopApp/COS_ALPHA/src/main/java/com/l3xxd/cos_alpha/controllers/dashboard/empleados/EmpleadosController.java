package com.l3xxd.cos_alpha.controllers.dashboard.empleados;

import com.l3xxd.cos_alpha.config.DBConnection;
import com.l3xxd.cos_alpha.dao.OperatorDAO;
import com.l3xxd.cos_alpha.models.OperatorModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.util.Optional;

public class EmpleadosController {

    @FXML private TableView<OperatorModel> operadoresTable;
    @FXML private TableColumn<OperatorModel, Integer> colId;
    @FXML private TableColumn<OperatorModel, String> colUsername;
    @FXML private TableColumn<OperatorModel, String> colRol;
    @FXML private TableColumn<OperatorModel, String> colNombre;
    @FXML private TableColumn<OperatorModel, String> colApellido;
    @FXML private TableColumn<OperatorModel, String> colEmail;
    @FXML private TableColumn<OperatorModel, String> colTelefono;

    @FXML private TextField findByUserTextField;
    @FXML private TextField findByPhoneTextField;
    @FXML private Button searchButton;
    @FXML private Button addButton;
    @FXML private Button editButton;
    @FXML private Button deleteButton;

    private final OperatorDAO operadorDAO;
    private final ObservableList<OperatorModel> operadoresList = FXCollections.observableArrayList();

    public EmpleadosController() {
        Connection conn = DBConnection.getConnection();
        operadorDAO = new OperatorDAO(conn);
    }

    @FXML
    public void initialize() {
        configurarColumnas();
        cargarOperadores();
    }

    private void configurarColumnas() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colRol.setCellValueFactory(new PropertyValueFactory<>("rol"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colApellido.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("phone"));
    }
    @FXML
    private void cargarOperadores() {
        operadoresList.setAll(operadorDAO.obtenerTodos());
        operadoresTable.setItems(operadoresList);
    }

    @FXML
    private void buscarOperadores() {
        String username = findByUserTextField.getText().trim();
        String telefono = findByPhoneTextField.getText().trim();
        operadoresList.setAll(operadorDAO.buscar(username, telefono));
        operadoresTable.setItems(operadoresList);
    }

    @FXML
    private void abrirFormularioAgregar() {
        abrirFormulario("Agregar", null);
    }

    @FXML
    private void abrirFormularioEditar() {
        OperatorModel seleccionado = operadoresTable.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            abrirFormulario("Editar", seleccionado);
        } else {
            mostrarAlerta("Selecciona un operador para editar.");
        }
    }

    private void abrirFormulario(String modo, OperatorModel operador) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/l3xxd/cos_alpha/views/dashboard/empleados/formulario-empleados.fxml"));
            AnchorPane pane = loader.load();

            EmpleadoFormController controller = loader.getController();
            controller.setModo(modo);
            if (operador != null) {
                controller.setOperador(operador);
            }

            Stage stage = new Stage();
            stage.setTitle(modo + " Operador");
            stage.setScene(new Scene(pane));
            stage.showAndWait();

            cargarOperadores(); // Refrescar tabla después de agregar/editar
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void eliminarOperador() {
        OperatorModel seleccionado = operadoresTable.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar eliminación");
            confirmacion.setHeaderText("¿Estás seguro de eliminar este operador?");
            confirmacion.setContentText("Esta acción no se puede deshacer.");

            Optional<ButtonType> resultado = confirmacion.showAndWait();
            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                operadorDAO.eliminar(seleccionado.getId());
                cargarOperadores();
            }
        } else {
            mostrarAlerta("Selecciona un operador para eliminar.");
        }
    }

    private void mostrarAlerta(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Advertencia");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}

