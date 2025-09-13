package com.l3xxd.cos_alpha.utils;

import javafx.animation.FadeTransition;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.net.URL;

/**
 * Utilidades para aplicar temas visuales con transiciones suaves.
 * Soporta alternancia entre modo claro/oscuro y aplicación modular por región.
 */
public class ThemeManager {

    /**
     * Alterna el tema visual en una vista de login.
     * Aplica fade-out, cambia hoja de estilos y luego fade-in.
     *
     * @param pane             contenedor principal
     * @param toggleButton     botón que activa el cambio
     * @param isDarkMode       estado actual del tema
     * @param onToggleComplete acción adicional tras aplicar el tema
     */
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
                System.out.println("[Theme] Tema aplicado: " + cssPath);
            } else {
                System.err.println("[Theme] No se encontró CSS en " + cssPath);
            }

            if (onToggleComplete != null) onToggleComplete.run();

            FadeTransition fadeIn = new FadeTransition(Duration.millis(300), pane);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        });

        fadeOut.play();
    }

    /**
     * Aplica un tema visual con transición fade a múltiples regiones.
     * Preserva las hojas de estilo propias de cada vista (ventas.css, etc.)
     * y sustituye únicamente el tema global del rootApp.
     *
     * @param isDarkMode estado del tema
     * @param cssPath    ruta al archivo CSS del tema global
     * @param onComplete acción adicional tras aplicar el tema
     * @param targets    nodos visuales a estilizar
     */
    public static void applyThemeWithFade(boolean isDarkMode, String cssPath, Runnable onComplete, Parent... targets) {
        for (Parent node : targets) {
            FadeTransition fadeOut = new FadeTransition(Duration.millis(250), node);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);

            fadeOut.setOnFinished(e -> {
                // Elimina únicamente los estilos previos del tema global
                node.getStylesheets().removeIf(url -> url.contains("/assets/css/rootApp/"));

                URL cssUrl = ThemeManager.class.getResource(cssPath);
                if (cssUrl != null) {
                    String newUrl = cssUrl.toExternalForm();
                    if (!node.getStylesheets().contains(newUrl)) {
                        node.getStylesheets().add(newUrl);
                    }
                    System.out.println("[Theme] Estilo aplicado a " + node.getClass().getSimpleName());
                } else {
                    System.err.println("[Theme] No se encontró CSS en " + cssPath);
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

