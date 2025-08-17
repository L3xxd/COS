package com.l3xxd.cos_alpha.controllers;

import com.l3xxd.cos_alpha.controllers.layout.NavbarController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

import java.net.URL;

public class rootAppController {

    @FXML private BorderPane paneWorkflow;
    private boolean isDarkMode = false;
    private NavbarController navbarController;

    @FXML
    public void initialize() {
        try {
            // Cargar menú lateral
            FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("/com/l3xxd/cos_alpha/views/layout/menuSlider.fxml"));
            Node menu = menuLoader.load();
            MenuSliderController menuController = menuLoader.getController();
            menuController.setOnNavigate(this::cargarVista);
            menuController.setOnToggleTheme(this::alternarTema);
            paneWorkflow.setLeft(menu);

            // Cargar navbar manualmente
            FXMLLoader navbarLoader = new FXMLLoader(getClass().getResource("/com/l3xxd/cos_alpha/views/layout/navbar.fxml"));
            Node navbar = navbarLoader.load();
            navbarController = navbarLoader.getController(); // Guarda referencia
            paneWorkflow.setTop(navbar);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setUsername(String username) {
        if (navbarController != null) {
            navbarController.setUsername(username);
        }
    }


    private void cargarVista(String fxmlPath) {
        try {
            String fullPath = "/com/l3xxd/cos_alpha/views/" + fxmlPath;
            URL location = getClass().getResource(fullPath);
            if (location == null) {
                System.err.println("ERROR: No se encontró el archivo FXML en " + fullPath);
                return;
            }
            Node contenido = FXMLLoader.load(location);
            paneWorkflow.setCenter(contenido);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void alternarTema() {
        paneWorkflow.getStylesheets().clear();
        String css = isDarkMode ? "light-mode.css" : "dark-mode.css";
        paneWorkflow.getStylesheets().add(getClass().getResource("/com/l3xxd/cos_alpha/assets/css/login/" + css).toExternalForm());
        isDarkMode = !isDarkMode;
    }
}
