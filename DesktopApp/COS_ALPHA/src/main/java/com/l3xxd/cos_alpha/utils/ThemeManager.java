package com.l3xxd.cos_alpha.utils;

import javafx.animation.FadeTransition;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
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

    public static void applyThemeWithFade(boolean isDarkMode, String cssPath, Runnable onComplete, Parent... targets) {
        for (Parent node : targets) {
            FadeTransition fadeOut = new FadeTransition(Duration.millis(250), node);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);

            fadeOut.setOnFinished(e -> {
                node.getStylesheets().clear();
                URL cssUrl = ThemeManager.class.getResource(cssPath);
                if (cssUrl != null) {
                    node.getStylesheets().add(cssUrl.toExternalForm());
                    System.out.println("✔ Estilo aplicado a " + node.getClass().getSimpleName());
                } else {
                    System.err.println("❌ No se encontró CSS en " + cssPath);
                }

                if (onComplete != null) onComplete.run();

                FadeTransition fadeIn = new FadeTransition(Duration.millis(250), node);
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);
                fadeIn.play();
            });

            fadeOut.play();
        }
    }
}

