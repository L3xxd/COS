package com.l3xxd.cos_alpha.utils;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 * Utilidades para animaciones visuales en nodos JavaFX.
 * Incluye efectos como shake para retroalimentación visual.
 */
public class FXAnimations {

    /**
     * Aplica una animación de "shake" al nodo indicado.
     * Útil para indicar error o retroalimentación negativa.
     * @param node nodo a animar
     */
    public static void shake(Node node) {
        TranslateTransition shake = new TranslateTransition(Duration.millis(75), node);
        shake.setFromX(0);
        shake.setByX(10);
        shake.setCycleCount(6);
        shake.setAutoReverse(true);
        shake.play();
    }



    // Puedes agregar más animaciones aquí en el futuro:
    // fadeIn(Node node), bounce(Node node), slideIn(Node node), etc.
}


