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

/**
 * Controlador principal de la aplicación (rootApp).
 * Carga el layout base, gestiona navegación, tema visual y control de acceso por rol.
 */
public class rootAppController {

    @FXML private BorderPane paneWorkflow;

    private boolean isDarkMode = false;
    private NavbarController navbarController;

    /**
     * Inicializa la aplicación: carga menú, navbar y aplica rol del operador.
     */
    @FXML
    public void initialize() {
        try {
            OperatorModel operador = SessionManager.getUser();
            if (operador == null) {
                System.err.println("[RootApp] ❌ No hay operador en sesión.");
                return;
            }

            String rol = operador.getRol();
            System.out.println("[RootApp] ✅ Rol del operador: " + rol);

            cargarMenuSliderConRol(rol);
            cargarNavbar();

        } catch (Exception e) {
            System.err.println("❌ Error al inicializar rootApp:");
            e.printStackTrace();
        }
    }

    /**
     * Carga el menú lateral y aplica el rol del operador para control de acceso visual.
     * @param rol rol del operador ("ADMINISTRADOR", "CAJERO", etc.)
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

    /**
     * Carga el navbar superior y muestra el nombre del operador.
     */
    private void cargarNavbar() {
        try {
            FXMLLoader navbarLoader = new FXMLLoader(getClass().getResource("/com/l3xxd/cos_alpha/views/layout/navbar.fxml"));
            Node navbar = navbarLoader.load();
            navbarController = navbarLoader.getController();
            paneWorkflow.setTop(navbar);

            navbarController.showUserProfile();

        } catch (IOException e) {
            System.err.println("[RootApp] ❌ Error al cargar navbar:");
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
                System.err.println("❌ No se encontró la vista: " + fxmlPath + " → " + fullPath);
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

        Parent left = safeCast(paneWorkflow.getLeft());
        Parent center = safeCast(paneWorkflow.getCenter());

        if (left == null || center == null) {
            System.err.println("❌ No se puede aplicar tema: LEFT o CENTER no son Parent");
            return;
        }

        ThemeManager.applyThemeWithFade(isDarkMode, cssPath, () -> {
            System.out.println("🌗 Tema cambiado a " + (isDarkMode ? "Oscuro" : "Claro"));
        }, left, center);
    }

    /**
     * Utilidad para castear nodos a Parent de forma segura.
     * @param node nodo a castear
     * @return instancia de Parent o null si no aplica
     */
    private Parent safeCast(Node node) {
        return (node instanceof Parent) ? (Parent) node : null;
    }
}
