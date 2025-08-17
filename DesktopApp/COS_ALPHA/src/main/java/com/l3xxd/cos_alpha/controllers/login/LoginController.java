package com.l3xxd.cos_alpha.controllers.login;

import com.l3xxd.cos_alpha.config.DBConnection;
import com.l3xxd.cos_alpha.dao.OperatorDAO;
import com.l3xxd.cos_alpha.models.OperatorModel;
import com.l3xxd.cos_alpha.utils.*;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
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

public class LoginController implements Initializable {

    @FXML private Button themeToggleButton;
    @FXML private Pane paneFloating;
    @FXML private TextField userTextField;
    @FXML private PasswordField passwordTextField;
    @FXML private Button enterButton;
    @FXML private Label errorLabel;

    private boolean isDarkMode = false;
    private final String USER_PLACEHOLDER = "Usuario";
    private final String PASSWORD_PLACEHOLDER = "Contraseña";
    private final PseudoClass errorClass = PseudoClass.getPseudoClass("error");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupThemeToggle();
        setupPlaceholders();
        enterButton.setOnAction(this::handleLogin);
    }

    private void setupThemeToggle() {
        themeToggleButton.setOnAction(e -> toggleTheme());
    }

    private void toggleTheme() {
        ThemeManager.toggle(paneFloating, themeToggleButton, isDarkMode, () -> {
            isDarkMode = !isDarkMode;
            refreshFieldColors();
        });
    }

    private void setupPlaceholders() {
        PlaceholderManager.setup(userTextField, USER_PLACEHOLDER, isDarkMode);
        PlaceholderManager.setup(passwordTextField, PASSWORD_PLACEHOLDER, isDarkMode);
    }

    private void refreshFieldColors() {
        PlaceholderManager.refresh(userTextField, USER_PLACEHOLDER, isDarkMode);
        PlaceholderManager.refresh(passwordTextField, PASSWORD_PLACEHOLDER, isDarkMode);
    }

    private void handleLogin(ActionEvent event) {
        String username = userTextField.getText().trim();
        String password = passwordTextField.getText().trim();

        ErrorFeedback.hide(errorLabel);
        ErrorFeedback.clearErrorStyles(userTextField, passwordTextField);

        Connection conn = DBConnection.getConnection();
        if (!DBConnection.isAlive(conn)) {
            ErrorFeedback.show(errorLabel, "Error de conexión con la base de datos.");
            FXAnimations.shake(paneFloating);
            return;
        }

        OperatorDAO dao = new OperatorDAO(conn);
        Optional<OperatorModel> operatorOpt = dao.validate(username, password);

        if (operatorOpt.isEmpty()) {
            FXAnimations.shake(paneFloating);
            ErrorFeedback.applyErrorStyles(userTextField, passwordTextField);
            ErrorFeedback.show(errorLabel, "Usuario y/o Contraseña incorrectos, ingresar nuevamente los datos.");
            return;
        }

        SessionManager.setUser(operatorOpt.get());
        transitionToRootApp(event);
    }



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

                // Ya no necesitas pasar username, el rootAppController puede usar SessionManager.getUser()

                Scene newScene = new Scene(rootAppView, 1920, 1080);
                stage.setScene(newScene);
                stage.setResizable(false);
                stage.setTitle("COS_ALPHA v 2.1");
                stage.setMaximized(true);

                FadeTransition fadeIn = new FadeTransition(Duration.millis(500), newScene.getRoot());
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);
                fadeIn.play();

            } catch (Exception ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, "Error al cargar rootApp.fxml", ex);
            }
        });

        fadeOut.play();
    }


    private void showErrorMessage(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
        errorLabel.setOpacity(0);

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), errorLabel);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();

        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(e -> hideErrorMessage());
        pause.play();
    }

    private void hideErrorMessage() {
        errorLabel.setVisible(false);
        errorLabel.setOpacity(0);
    }

    private void applyErrorStyles() {
        userTextField.pseudoClassStateChanged(errorClass, true);
        passwordTextField.pseudoClassStateChanged(errorClass, true);
    }

    private void clearErrorStyles() {
        userTextField.pseudoClassStateChanged(errorClass, false);
        passwordTextField.pseudoClassStateChanged(errorClass, false);
    }

}
