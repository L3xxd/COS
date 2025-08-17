package com.l3xxd.cos_alpha.utils;

import javafx.animation.FadeTransition;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.net.URL;

public class ThemeManager {

    public static void toggle(Pane pane, Button toggleButton, boolean isDarkMode, Runnable onToggleComplete) {
        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), pane);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        fadeOut.setOnFinished(e -> {
            ObservableList<String> stylesheets = pane.getStylesheets();
            stylesheets.clear();

            String cssPath = isDarkMode
                    ? "/com/l3xxd/cos_alpha/assets/css/login/light-mode.css"
                    : "/com/l3xxd/cos_alpha/assets/css/login/dark-mode.css";

            toggleButton.setText(isDarkMode ? "Cambiar Oscuro" : "Cambiar Claro");

            URL cssUrl = ThemeManager.class.getResource(cssPath);
            if (cssUrl != null) {
                stylesheets.add(cssUrl.toExternalForm());
            }

            onToggleComplete.run(); // Para refrescar colores u otras acciones

            FadeTransition fadeIn = new FadeTransition(Duration.millis(300), pane);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        });

        fadeOut.play();
    }
}
