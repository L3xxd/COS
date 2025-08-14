package com.l3xxd.cos_alpha.controllers.login;

import java.net.URL;

import java.util.ResourceBundle;

import javafx.animation.FadeTransition;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.scene.Node;

public class LoginController implements Initializable {



    @FXML
    private Button themeToggleButton;



    @FXML
    private Pane paneFloating;

    @FXML
    private TextField userTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Button enterButton;

    private boolean isDarkMode = false;

    private final String USER_PLACEHOLDER = "Usuario";
    private final String PASSWORD_PLACEHOLDER = "Contraseña";

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
        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), paneFloating);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        fadeOut.setOnFinished(e -> {
            ObservableList<String> stylesheets = paneFloating.getStylesheets();
            stylesheets.clear();

            String cssPath = isDarkMode
                    ? "/com/l3xxd/cos_alpha/assets/css/login/light-mode.css"
                    : "/com/l3xxd/cos_alpha/assets/css/login/dark-mode.css";

            themeToggleButton.setText(isDarkMode ? "Cambiar Oscuro" : "Cambiar Claro");

            URL cssUrl = getClass().getResource(cssPath);
            if (cssUrl != null) {
                stylesheets.add(cssUrl.toExternalForm());
            }

            isDarkMode = !isDarkMode;
            refreshFieldColors();

            FadeTransition fadeIn = new FadeTransition(Duration.millis(300), paneFloating);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        });

        fadeOut.play();
    }

    private void setupPlaceholders() {
        setupPlaceholder(userTextField, USER_PLACEHOLDER);
        setupPlaceholder(passwordTextField, PASSWORD_PLACEHOLDER);
    }

    private void setupPlaceholder(TextField field, String placeholder) {
        field.setText(placeholder);
        field.setStyle("-fx-text-fill: " + getHintColor() + ";");

        field.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal && field.getText().equals(placeholder)) {
                field.clear();
                field.setStyle("-fx-text-fill: " + getTextColor() + ";");
            } else if (!newVal && field.getText().isEmpty()) {
                field.setText(placeholder);
                field.setStyle("-fx-text-fill: " + getHintColor() + ";");
            }
        });
    }

    private void refreshFieldColors() {
        updateFieldColor(userTextField, USER_PLACEHOLDER);
        updateFieldColor(passwordTextField, PASSWORD_PLACEHOLDER);
    }

    private void updateFieldColor(TextField field, String placeholder) {
        if (field.getText().equals(placeholder)) {
            field.setStyle("-fx-text-fill: " + getHintColor() + ";");
        } else {
            field.setStyle("-fx-text-fill: " + getTextColor() + ";");
        }
    }

    private String getHintColor() {
        return isDarkMode ? "#AAAAAA" : "#888888";
    }

    private String getTextColor() {
        return isDarkMode ? "#FFFFFF" : "#000000";
    }

    private void handleLogin(ActionEvent event) {
        // Obtiene el nodo raíz actual (login.fxml)
        Node sourceNode = (Node) event.getSource();
        Scene currentScene = sourceNode.getScene();
        Stage stage = (Stage) currentScene.getWindow();

        // Fade out de la escena actual
        FadeTransition fadeOut = new FadeTransition(Duration.millis(500), currentScene.getRoot());
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        fadeOut.setOnFinished(e -> {
            try {
                // Carga rootApp.fxml
                Parent rootAppView = FXMLLoader.load(getClass().getResource("/com/l3xxd/cos_alpha/views/rootApp.fxml"));

                // Crea nueva escena con dimensiones fijas
                Scene newScene = new Scene(rootAppView, 1920, 1080);
                stage.setScene(newScene);
                stage.setResizable(false);
                stage.setTitle("COS_ALPHA v 2.1");
                stage.setMaximized(true);


                // Fade in de la nueva escena
                FadeTransition fadeIn = new FadeTransition(Duration.millis(500), newScene.getRoot());
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);
                fadeIn.play();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        fadeOut.play();
    }
}
