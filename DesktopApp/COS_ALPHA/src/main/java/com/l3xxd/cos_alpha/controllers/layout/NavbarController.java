package com.l3xxd.cos_alpha.controllers.layout;

import com.l3xxd.cos_alpha.models.OperatorModel;
import com.l3xxd.cos_alpha.utils.SessionManager;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class NavbarController implements Initializable {

    @FXML private Text clockText;
    @FXML private Text dateText;
    @FXML private Text userprofileText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupClock();
        showUserProfile();
    }

    private void setupClock() {
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("hh:mma");
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        Timeline clockTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            LocalDateTime now = LocalDateTime.now();
            clockText.setText(timeFormat.format(now).toLowerCase());
            dateText.setText(dateFormat.format(now));
        }));

        clockTimeline.setCycleCount(Timeline.INDEFINITE);
        clockTimeline.play();
    }

    public void showUserProfile() {
        OperatorModel operador = SessionManager.getUser();

        String saludo = "Bienvenido";
        if (operador != null) {
            saludo += ", " + operador.getFirstName();
        }

        userprofileText.setText(saludo);
        userprofileText.setOpacity(0);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(600), userprofileText);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
    }

    @FXML private HBox paneNavbar;

    public HBox getRoot() {
        return paneNavbar;
    }

}
