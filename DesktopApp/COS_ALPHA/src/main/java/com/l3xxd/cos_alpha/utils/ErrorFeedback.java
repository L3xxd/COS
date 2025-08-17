package com.l3xxd.cos_alpha.utils;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.css.PseudoClass;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;

/**
 * Utilidades para mostrar mensajes de error y aplicar estilos visuales.
 * Incluye animaciones, pseudo-clases y limpieza automática.
 */
public class ErrorFeedback {

    private static final PseudoClass ERROR_CLASS = PseudoClass.getPseudoClass("error");

    /**
     * Muestra un mensaje de error con animación fade-in y ocultamiento automático.
     * @param label etiqueta donde se muestra el mensaje
     * @param message texto del error
     */
    public static void show(Label label, String message) {
        label.setText(message);
        label.setVisible(true);
        label.setOpacity(0);

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), label);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();

        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(e -> hide(label));
        pause.play();
    }

    /**
     * Oculta el mensaje de error inmediatamente.
     * @param label etiqueta a ocultar
     */
    public static void hide(Label label) {
        label.setVisible(false);
        label.setOpacity(0);
    }

    /**
     * Aplica estilo de error a los campos indicados.
     * @param fields campos de texto afectados
     */
    public static void applyErrorStyles(TextField... fields) {
        for (TextField field : fields) {
            field.pseudoClassStateChanged(ERROR_CLASS, true);
        }
    }

    /**
     * Limpia el estilo de error de los campos indicados.
     * @param fields campos de texto afectados
     */
    public static void clearErrorStyles(TextField... fields) {
        for (TextField field : fields) {
            field.pseudoClassStateChanged(ERROR_CLASS, false);
        }
    }
}

