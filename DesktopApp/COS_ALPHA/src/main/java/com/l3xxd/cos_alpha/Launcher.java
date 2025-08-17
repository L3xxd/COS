package com.l3xxd.cos_alpha;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Launcher extends Application {
    @Override
    public void start(Stage stage) {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/l3xxd/cos_alpha/views/login/login.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1920, 1030);

            // 👇 Cargar el ícono desde recursos
            Image iconoApp = new Image(getClass().getResourceAsStream("/com/l3xxd/cos_alpha/assets/img/logo/logoImage.png"));
            stage.getIcons().add(iconoApp);
            stage.setScene(scene);


            stage.setTitle("COS_ALPHA v 5.1.1");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setMaximized(true);
            stage.show();
        } catch (Exception e) {
            System.err.println("❌ Error al cargar login.fxml:");
            e.printStackTrace();
        }


    }


    public static void main(String[] args) {
        launch();
    }
}
