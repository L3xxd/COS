package com.l3xxd.cos_alpha;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * Clase principal que lanza la aplicación COS_ALPHA.
 * Carga la vista de login y configura la ventana inicial.
 */
public class Launcher extends Application {

    /**
     * Punto de entrada de JavaFX.
     * @param stage ventana principal
     */
    @Override
    public void start(Stage stage) {
        try {
            // Cargar vista de login
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/l3xxd/cos_alpha/views/login/login.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1920, 1030);

            // Cargar ícono de la aplicación
            Image iconoApp = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/l3xxd/cos_alpha/assets/img/logo/logoImage.png")));
            stage.getIcons().add(iconoApp);

            // Configurar ventana
            stage.setScene(scene);
            stage.setTitle("COS_ALPHA v 2.1");
            stage.setResizable(false);
            stage.setMaximized(true);
            stage.show();

            System.out.println("🚀 COS_ALPHA iniciado correctamente.");

        } catch (Exception e) {
            System.err.println("❌ Error al cargar login.fxml:");
            e.printStackTrace();
        }
    }

    /**
     * Método principal que lanza la aplicación.
     * @param args argumentos de línea de comandos
     */
    public static void main(String[] args) {
        launch();
    }
}
