package com.l3xxd.cos_alpha.utils;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.css.PseudoClass;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class ErrorFeedback {

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

    public static void hide(Label label) {
        label.setVisible(false);
        label.setOpacity(0);
    }

    public static void applyErrorStyles(TextField... fields) {
        PseudoClass errorClass = PseudoClass.getPseudoClass("error");
        for (TextField field : fields) {
            field.pseudoClassStateChanged(errorClass, true);
        }
    }

    public static void clearErrorStyles(TextField... fields) {
        PseudoClass errorClass = PseudoClass.getPseudoClass("error");
        for (TextField field : fields) {
            field.pseudoClassStateChanged(errorClass, false);
        }
    }
}

