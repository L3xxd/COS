package com.l3xxd.cos_alpha.utils;

import javafx.scene.control.TextField;

/**
 * Utilidades para simular placeholders en campos de texto.
 * Aplica estilos dinámicos según el tema visual (claro/oscuro).
 */
public class PlaceholderManager {

    /**
     * Configura el comportamiento de placeholder en un TextField.
     * Aplica color de texto según el tema y gestiona foco.
     * @param field campo de texto a configurar
     * @param placeholder texto que simula el placeholder
     * @param isDarkMode indica si el tema actual es oscuro
     */
    public static void setup(TextField field, String placeholder, boolean isDarkMode) {
        field.setText(placeholder);
        field.setStyle("-fx-text-fill: " + getHintColor(isDarkMode) + ";");

        field.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal && field.getText().equals(placeholder)) {
                field.clear();
                field.setStyle("-fx-text-fill: " + getTextColor(isDarkMode) + ";");
            } else if (!newVal && field.getText().isEmpty()) {
                field.setText(placeholder);
                field.setStyle("-fx-text-fill: " + getHintColor(isDarkMode) + ";");
            }
        });
    }

    /**
     * Refresca el color del texto según si el campo contiene el placeholder.
     * Útil al cambiar de tema dinámicamente.
     * @param field campo de texto a actualizar
     * @param placeholder texto que simula el placeholder
     * @param isDarkMode indica si el tema actual es oscuro
     */
    public static void refresh(TextField field, String placeholder, boolean isDarkMode) {
        boolean esPlaceholder = field.getText().equals(placeholder);
        String color = esPlaceholder ? getHintColor(isDarkMode) : getTextColor(isDarkMode);
        field.setStyle("-fx-text-fill: " + color + ";");
    }

    /**
     * Color para texto de placeholder según el tema.
     */
    private static String getHintColor(boolean isDarkMode) {
        return isDarkMode ? "#AAAAAA" : "#888888";
    }

    /**
     * Color para texto real según el tema.
     */
    private static String getTextColor(boolean isDarkMode) {
        return isDarkMode ? "#FFFFFF" : "#000000";
    }
}

