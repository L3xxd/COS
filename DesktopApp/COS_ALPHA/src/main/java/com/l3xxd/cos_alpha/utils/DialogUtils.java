package com.l3xxd.cos_alpha.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;

import java.util.Optional;

/**
 * Utilidades para mostrar cuadros de diálogo personalizados.
 * Centraliza estilos, botones y comportamiento para confirmaciones.
 */
public class DialogUtils {

    /**
     * Muestra un cuadro de confirmación con estilo visual claro.
     * @param titulo título de la ventana
     * @param encabezado encabezado del diálogo
     * @param contenido mensaje principal
     * @return true si el usuario confirma, false si cancela
     */
    public static boolean mostrarConfirmacion(String titulo, String encabezado, String contenido) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(encabezado);
        alerta.setContentText(contenido);

        // Botones personalizados
        ButtonType botonSi = new ButtonType("✔ Sí, cerrar sesión", ButtonBar.ButtonData.OK_DONE);
        ButtonType botonNo = new ButtonType("✖ Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        alerta.getButtonTypes().setAll(botonSi, botonNo);

        // Aplicar estilos visuales
        aplicarEstiloClaro(alerta.getDialogPane());

        // Mostrar y retornar resultado
        Optional<ButtonType> resultado = alerta.showAndWait();
        return resultado.isPresent() && resultado.get() == botonSi;
    }

    /**
     * Aplica hoja de estilos clara al diálogo.
     * @param dialogPane panel del diálogo
     */
    private static void aplicarEstiloClaro(DialogPane dialogPane) {
        String cssPath = "/com/l3xxd/cos_alpha/assets/css/dialogs/confirm-dialog.css";
        dialogPane.getStylesheets().add(DialogUtils.class.getResource(cssPath).toExternalForm());
        dialogPane.getStyleClass().add("light-confirm-dialog");
    }
}

