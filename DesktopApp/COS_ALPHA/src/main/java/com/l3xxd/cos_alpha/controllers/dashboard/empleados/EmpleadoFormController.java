package com.l3xxd.cos_alpha.controllers.dashboard.empleados;

import com.l3xxd.cos_alpha.config.DBConnection;
import com.l3xxd.cos_alpha.dao.OperatorDAO;
import com.l3xxd.cos_alpha.models.OperatorModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.util.UUID;

public class EmpleadoFormController {

    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;
    @FXML private TextField passwordField;
    @FXML private ComboBox<String> rolComboBox;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;

    private String modo; // "Agregar" o "Editar"
    private OperatorModel operador;
    private final OperatorDAO operadorDAO;

    public EmpleadoFormController() {
        Connection conn = DBConnection.getConnection();
        operadorDAO = new OperatorDAO(conn);
    }

    public void setModo(String modo) {
        this.modo = modo;
        rolComboBox.getItems().addAll("ADMINISTRADOR", "CAJERO");
        saveButton.setText(modo.equals("Agregar") ? "Crear" : "Actualizar");
    }

    public void setOperador(OperatorModel operador) {
        this.operador = operador;
        cargarDatos();
    }

    private void cargarDatos() {
        firstNameField.setText(operador.getFirstName());
        lastNameField.setText(operador.getLastName());
        emailField.setText(operador.getEmail());
        phoneField.setText(operador.getPhone());
        passwordField.setText(operador.getPassword());
        rolComboBox.setValue(operador.getRol());
    }

    @FXML
    private void guardarOperador() {
        if (!validarCampos()) return;

        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String password = passwordField.getText().trim();
        String rol = rolComboBox.getValue();

        if (modo.equals("Agregar")) {
            String username = generarUsername(firstName, lastName);
            OperatorModel nuevo = new OperatorModel(0, username, password, rol, firstName, lastName, email, phone);
            operadorDAO.insertar(nuevo);
        } else {
            operador.setFirstName(firstName);
            operador.setLastName(lastName);
            operador.setEmail(email);
            operador.setPhone(phone);
            operador.setPassword(password);
            operador.setRol(rol);
            operadorDAO.actualizar(operador);
        }

        cerrarVentana();
    }

    private boolean validarCampos() {
        if (firstNameField.getText().isEmpty() ||
                lastNameField.getText().isEmpty() ||
                emailField.getText().isEmpty() ||
                phoneField.getText().isEmpty() ||
                passwordField.getText().isEmpty() ||
                rolComboBox.getValue() == null) {

            mostrarAlerta("Todos los campos son obligatorios.");
            return false;
        }
        return true;
    }

    private String generarUsername(String firstName, String lastName) {
        String base = firstName.toLowerCase() + "." + lastName.toLowerCase();
        String unique = UUID.randomUUID().toString().substring(0, 5);
        return base + "_" + unique;
    }

    @FXML
    private void cerrarVentana() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Validación");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}

