package com.l3xxd.cos_alpha;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class LoginController implements Initializable {

    @FXML
    private Text clockText;

    @FXML
    private Text dateText;

    @FXML
    private Button themeToggleButton;

    @FXML
    private AnchorPane paneWorkflow;

    @FXML
    private Pane paneFloating;


    @FXML
    private TextField userTextField;

    @FXML
    private PasswordField passwordTextField;

    private boolean isDarkMode = false;

    private final String USER_PLACEHOLDER = "Usuario";
    private final String PASSWORD_PLACEHOLDER = "Contraseña";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 🕒 Reloj en tiempo real
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        Timeline clock = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            LocalDateTime now = LocalDateTime.now();
            clockText.setText(timeFormat.format(now));
            dateText.setText(dateFormat.format(now));
        }));

        clock.setCycleCount(Timeline.INDEFINITE);
        clock.play();

        // 🌗 Botón de cambio de tema
        themeToggleButton.setOnAction(e -> toggleTheme());

        // 🧑 Placeholders interactivos
        setupPlaceholder(userTextField, USER_PLACEHOLDER);
        setupPlaceholder(passwordTextField, PASSWORD_PLACEHOLDER);
    }

    private void toggleTheme() {
        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), paneFloating);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        fadeOut.setOnFinished(e -> {
            ObservableList<String> stylesheets = paneFloating.getStylesheets();
            stylesheets.clear();

            String cssPath;
            if (isDarkMode) {
                cssPath = "/com/l3xxd/cos_alpha/assets/css/light-mode.css";
                themeToggleButton.setText("Tema Oscuro");
            } else {
                cssPath = "/com/l3xxd/cos_alpha/assets/css/dark-mode.css";
                themeToggleButton.setText("Tema Claro");
            }

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
}
