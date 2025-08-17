package com.l3xxd.cos_alpha.controllers;

import com.l3xxd.cos_alpha.controllers.layout.MenuSliderController;
import com.l3xxd.cos_alpha.controllers.layout.NavbarController;
import com.l3xxd.cos_alpha.models.OperatorModel;
import com.l3xxd.cos_alpha.utils.SessionManager;
import com.l3xxd.cos_alpha.utils.ThemeManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;

public class rootAppController {

    @FXML private BorderPane paneWorkflow;
    private boolean isDarkMode = false;
    private NavbarController navbarController;

    @FXML
    public void initialize() {
        try {
            // Obtener operador en sesión
            OperatorModel operador = SessionManager.getUser();
            if (operador == null) {
                System.err.println("[RootApp] ❌ No hay operador en sesión.");
                return;
            }

            String rol = operador.getRol();
            System.out.println("[RootApp] ✅ Rol del operador: " + rol);

            // Cargar menú lateral con rol
            cargarMenuSliderConRol(rol);

            // Cargar navbar
            FXMLLoader navbarLoader = new FXMLLoader(getClass().getResource("/com/l3xxd/cos_alpha/views/layout/navbar.fxml"));
            Node navbar = navbarLoader.load();
            navbarController = navbarLoader.getController();
            paneWorkflow.setTop(navbar);

            // Mostrar nombre del operador
            navbarController.showUserProfile();

        } catch (Exception e) {
            System.err.println("❌ Error al inicializar rootApp:");
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
                System.err.println("❌ ERROR: No se encontró el archivo FXML en " + fullPath);
                return;
            }

            Node contenido = FXMLLoader.load(location);
            paneWorkflow.setCenter(contenido);

        } catch (Exception ex) {
            System.err.println("❌ Error al cargar vista: " + fxmlPath);
            ex.printStackTrace();
        }
    }

    /**
     * Alterna entre tema claro y oscuro, aplicando estilos solo en LEFT y CENTER.
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

    /**
     * Carga el menú lateral y aplica el rol del operador para control de acceso visual.
     * @param rol rol del operador (admin, operador, etc.)
     */
    private void cargarMenuSliderConRol(String rol) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/l3xxd/cos_alpha/views/layout/menuSlider.fxml"));
            Node menuSlider = loader.load();

            MenuSliderController menuSliderController = loader.getController();
            menuSliderController.setRolOperador(rol);
            menuSliderController.setOnNavigate(this::cargarVista);
            menuSliderController.setOnToggleTheme(this::alternarTema);

            paneWorkflow.setLeft(menuSlider);

            System.out.println("[RootApp] ✅ menuSlider cargado con rol: " + rol);

        } catch (IOException e) {
            System.err.println("[RootApp] ❌ Error al cargar menuSlider: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
