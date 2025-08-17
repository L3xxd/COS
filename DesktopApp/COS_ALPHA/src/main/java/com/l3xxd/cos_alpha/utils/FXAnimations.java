package com.l3xxd.cos_alpha.utils;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class FXAnimations {
    public static void shake(Node node) {
        TranslateTransition shake = new TranslateTransition(Duration.millis(75), node);
        shake.setFromX(0);
        shake.setByX(10);
        shake.setCycleCount(6);
        shake.setAutoReverse(true);
        shake.play();
    }
}

