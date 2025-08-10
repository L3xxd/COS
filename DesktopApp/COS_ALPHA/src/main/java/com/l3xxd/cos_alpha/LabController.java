package com.l3xxd.cos_alpha;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LabController {

    @FXML
    private Text clockText;

    @FXML
    private Text dateText;

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
}
