package com.l3xxd.cos_alpha;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/l3xxd/cos_alpha/fxml/lab.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1440, 900); // Usa el tamaño real del BorderPane

        stage.setTitle("COS_ALPHA v 2.1");
        stage.setScene(scene);
        stage.setResizable(false); // 👈 Esto bloquea el redimensionamiento
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
