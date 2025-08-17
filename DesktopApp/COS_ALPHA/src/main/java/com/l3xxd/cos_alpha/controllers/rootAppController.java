package com.l3xxd.cos_alpha.controllers;

import com.l3xxd.cos_alpha.controllers.layout.MenuSliderController;
import com.l3xxd.cos_alpha.controllers.layout.NavbarController;
import com.l3xxd.cos_alpha.utils.ThemeManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.Parent;

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

            // Mostrar nombre del operador
            navbarController.showUserProfile();

        } catch (Exception e) {
            System.err.println("Error al inicializar rootApp:");
            e.printStackTrace();
        }
    }

    /**
     * Carga dinámicamente una vista en el centro del layout.
     * @param fxmlPath ruta relativa al archivo FXML dentro de /views/
     */
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
            System.err.println("Error al cargar vista: " + fxmlPath);
            ex.printStackTrace();
        }
    }

    /**
     * Alterna entre tema claro y oscuro, aplicando estilos globales.
     */
    private void alternarTema() {
        isDarkMode = !isDarkMode;

        String cssFile = isDarkMode ? "dark-mode.css" : "light-mode.css";
        String cssPath = "/com/l3xxd/cos_alpha/assets/css/rootApp/" + cssFile;

        Parent left = (paneWorkflow.getLeft() instanceof Parent) ? (Parent) paneWorkflow.getLeft() : null;
        Parent center = (paneWorkflow.getCenter() instanceof Parent) ? (Parent) paneWorkflow.getCenter() : null;

        if (left == null || center == null) {
            System.err.println("❌ No se puede aplicar tema: LEFT o CENTER no son Parent");
            return;
        }

        ThemeManager.applyThemeWithFade(isDarkMode, cssPath, () -> {
            System.out.println("🌗 Tema cambiado a " + (isDarkMode ? "Oscuro" : "Claro"));
        }, left, center);
    }


}
