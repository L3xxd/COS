package com.l3xxd.cos_alpha.utils;

import javafx.scene.control.TextField;

public class PlaceholderManager {

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

    public static void refresh(TextField field, String placeholder, boolean isDarkMode) {
        if (field.getText().equals(placeholder)) {
            field.setStyle("-fx-text-fill: " + getHintColor(isDarkMode) + ";");
        } else {
            field.setStyle("-fx-text-fill: " + getTextColor(isDarkMode) + ";");
        }
    }

    private static String getHintColor(boolean isDarkMode) {
        return isDarkMode ? "#AAAAAA" : "#888888";
    }

    private static String getTextColor(boolean isDarkMode) {
        return isDarkMode ? "#FFFFFF" : "#000000";
    }
}
