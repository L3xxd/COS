package com.l3xxd.cos_alpha;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Launcher extends Application {
    @Override
    public void start(Stage stage) {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/l3xxd/cos_alpha/views/rootApp.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1440, 900);

            stage.setTitle("COS_ALPHA v 2.1");
            stage.setScene(scene);
            stage.setResizable(false);
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
