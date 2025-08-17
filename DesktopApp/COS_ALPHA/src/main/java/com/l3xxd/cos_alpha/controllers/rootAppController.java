package com.l3xxd.cos_alpha.controllers;

import com.l3xxd.cos_alpha.controllers.layout.MenuSliderController;
import com.l3xxd.cos_alpha.controllers.layout.NavbarController;
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
        URL cssUrl = getClass().getResource(cssPath);

        if (cssUrl == null) {
            System.err.println("ERROR: No se encontró el archivo CSS en " + cssPath);
            return;
        }

        // Aplicar al LEFT si es Parent
        Node leftNode = paneWorkflow.getLeft();
        if (leftNode instanceof Parent leftParent) {
            leftParent.getStylesheets().clear();
            leftParent.getStylesheets().add(cssUrl.toExternalForm());
            System.out.println("✔ Tema aplicado al LEFT");
        } else {
            System.err.println("❌ LEFT no es instancia de Parent");
        }

        // Aplicar al CENTER si es Parent
        Node centerNode = paneWorkflow.getCenter();
        if (centerNode instanceof Parent centerParent) {
            centerParent.getStylesheets().clear();
            centerParent.getStylesheets().add(cssUrl.toExternalForm());
            System.out.println("✔ Tema aplicado al CENTER");
        } else {
            System.err.println("❌ CENTER no es instancia de Parent");
        }

        System.out.println("🌗 Tema cambiado a " + (isDarkMode ? "Oscuro" : "Claro"));
    }

}
