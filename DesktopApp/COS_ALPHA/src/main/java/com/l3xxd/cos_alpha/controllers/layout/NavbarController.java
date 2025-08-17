package com.l3xxd.cos_alpha.controllers.layout;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class NavbarController {
    @FXML
    private Text clockText;

    @FXML
    private Text dateText;

    @FXML private Text userprofileText;

    public void initialize() {
        // Formatos personalizados
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("hh:mma"); // 12 horas con am/pm
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy"); // Día-Mes-Año con guiones

        Timeline clockTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            LocalDateTime now = LocalDateTime.now();
            String formattedTime = timeFormat.format(now).toLowerCase(); // Convertimos AM/PM a am/pm
            clockText.setText(formattedTime);
            dateText.setText(dateFormat.format(now));
        }));

        clockTimeline.setCycleCount(Timeline.INDEFINITE);
        clockTimeline.play();
    }
    public void setUsername(String username) {
        userprofileText.setText("Bienvenido, " + username);

        userprofileText.setOpacity(0);
        javafx.animation.FadeTransition fadeIn = new javafx.animation.FadeTransition(Duration.millis(600), userprofileText);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
    }

}
