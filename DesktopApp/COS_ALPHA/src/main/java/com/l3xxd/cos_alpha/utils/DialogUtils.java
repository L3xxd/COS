package com.l3xxd.cos_alpha.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.Optional;

public class DialogUtils {

    public static boolean mostrarConfirmacion(String titulo, String encabezado, String contenido) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(encabezado);
        alerta.setContentText(contenido);

        // Botones personalizados
        ButtonType botonSi = new ButtonType("✔ Sí, cerrar sesión", ButtonBar.ButtonData.OK_DONE);
        ButtonType botonNo = new ButtonType("✖ Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        alerta.getButtonTypes().setAll(botonSi, botonNo);

        // Ícono visual claro

        // Estilo claro
        DialogPane dialogPane = alerta.getDialogPane();
        dialogPane.getStylesheets().add(DialogUtils.class.getResource("/com/l3xxd/cos_alpha/assets/css/dialogs/confirm-dialog.css").toExternalForm());
        dialogPane.getStyleClass().add("light-confirm-dialog");

        Optional<ButtonType> resultado = alerta.showAndWait();
        return resultado.isPresent() && resultado.get() == botonSi;
    }


}
