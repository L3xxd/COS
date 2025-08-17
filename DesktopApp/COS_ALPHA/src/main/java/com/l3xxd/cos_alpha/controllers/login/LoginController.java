package com.l3xxd.cos_alpha.controllers.login;

import com.l3xxd.cos_alpha.config.DBConnection;
import com.l3xxd.cos_alpha.dao.OperatorDAO;
import com.l3xxd.cos_alpha.models.OperatorModel;
import com.l3xxd.cos_alpha.utils.*;
import javafx.animation.FadeTransition;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.sql.Connection;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controlador de la pantalla de login.
 * Valida credenciales, gestiona tema visual y transiciones hacia la aplicación principal.
 */
public class LoginController implements Initializable {

    @FXML private Button themeToggleButton;
    @FXML private Pane paneFloating;
    @FXML private Pane contenedor;;
    @FXML private TextField userTextField;
    @FXML private PasswordField passwordTextField;
    @FXML private Button enterButton;
    @FXML private Label errorLabel;

    private boolean isDarkMode = false;

    private static final String USER_PLACEHOLDER = "Usuario";
    private static final String PASSWORD_PLACEHOLDER = "Contraseña";
    private static final PseudoClass ERROR_CLASS = PseudoClass.getPseudoClass("error");

    /**
     * Inicializa la pantalla de login: configura tema, placeholders y eventos.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupThemeToggle();
        setupPlaceholders();
        enterButton.setOnAction(this::handleLogin);
    }

    /**
     * Configura el botón para alternar entre tema claro/oscuro.
     */
    private void setupThemeToggle() {
        themeToggleButton.setOnAction(e -> toggleTheme());
    }

    /**
     * Alterna el tema visual y actualiza los campos.
     */
    private void toggleTheme() {
        ThemeManager.toggle(paneFloating, themeToggleButton, isDarkMode, () -> {
            isDarkMode = !isDarkMode;
            refreshFieldColors();
        });
    }

    /**
     * Configura los placeholders iniciales en los campos de texto.
     */
    private void setupPlaceholders() {
        PlaceholderManager.setup(userTextField, USER_PLACEHOLDER, isDarkMode);
        PlaceholderManager.setup(passwordTextField, PASSWORD_PLACEHOLDER, isDarkMode);
    }

    /**
     * Refresca los colores de los campos según el tema actual.
     */
    private void refreshFieldColors() {
        PlaceholderManager.refresh(userTextField, USER_PLACEHOLDER, isDarkMode);
        PlaceholderManager.refresh(passwordTextField, PASSWORD_PLACEHOLDER, isDarkMode);
    }

    /**
     * Maneja el intento de login: valida credenciales y redirige si son correctas.
     * @param event evento del botón de ingreso
     */
    private void handleLogin(ActionEvent event) {
        String username = userTextField.getText().trim();
        String password = passwordTextField.getText().trim();

        ErrorFeedback.hide(errorLabel);
        ErrorFeedback.clearErrorStyles(userTextField, passwordTextField);

        Connection conn = DBConnection.getConnection();
        if (!DBConnection.isAlive(conn)) {
            mostrarError("Error de conexión con la base de datos.");
            return;
        }

        OperatorDAO dao = new OperatorDAO(conn);
        Optional<OperatorModel> operatorOpt = dao.validate(username, password);

        if (operatorOpt.isEmpty()) {
            mostrarError("Usuario y/o Contraseña incorrectos, ingresar nuevamente los datos.");
            return;
        }

        SessionManager.setUser(operatorOpt.get());
        transitionToRootApp(event);
    }

    /**
     * Muestra mensaje de error con animación y estilos.
     * @param mensaje texto a mostrar
     */
    private void mostrarError(String mensaje) {
        FXAnimations.shake(contenedor);
        ErrorFeedback.applyErrorStyles(userTextField, passwordTextField);
        ErrorFeedback.show(errorLabel, mensaje);
    }

    /**
     * Transición visual hacia la vista principal (rootApp).
     * @param event evento del botón de ingreso
     */
    private void transitionToRootApp(ActionEvent event) {
        Node sourceNode = (Node) event.getSource();
        Scene currentScene = sourceNode.getScene();
        Stage stage = (Stage) currentScene.getWindow();

        FadeTransition fadeOut = new FadeTransition(Duration.millis(500), currentScene.getRoot());
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        fadeOut.setOnFinished(e -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/l3xxd/cos_alpha/views/rootApp.fxml"));
                Parent rootAppView = loader.load();

                Scene newScene = new Scene(rootAppView, 1920, 1080);
                stage.setScene(newScene);
                stage.setTitle("COS_ALPHA v 2.1");
                stage.setResizable(false);
                stage.setMaximized(true);

                FadeTransition fadeIn = new FadeTransition(Duration.millis(500), newScene.getRoot());
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);
                fadeIn.play();

            } catch (Exception ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, "❌ Error al cargar rootApp.fxml", ex);
            }
        });

        fadeOut.play();
    }
}
