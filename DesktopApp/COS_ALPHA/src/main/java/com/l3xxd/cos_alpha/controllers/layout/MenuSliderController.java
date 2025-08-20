package com.l3xxd.cos_alpha.controllers.layout;

import com.l3xxd.cos_alpha.utils.RolAccessManager;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Controlador del menú lateral (MenuSlider).
 * Gestiona navegación entre vistas, selección visual de botones,
 * alternancia de tema y control de acceso por rol.
 */
public class MenuSliderController {

    // Botones del menú
    @FXML private Button btnVentas;
    @FXML private Button btnInventario;
    @FXML private Button btnEmpleados;
    @FXML private Button btnPedidos;
    @FXML private Button btnFinanzas;
    @FXML private Button btnCambiarTema;

    // Callbacks externos
    private Consumer<String> onNavigate;
    private Runnable onToggleTheme;

    // Pseudo-clase para resaltar botón activo
    private static final PseudoClass SELECTED = PseudoClass.getPseudoClass("selected");

    // Lista de botones navegables
    private List<Button> menuButtons;

    /**
     * Asigna el manejador de navegación externa.
     * @param handler función que recibe la ruta FXML
     */
    public void setOnNavigate(Consumer<String> handler) {
        this.onNavigate = handler;
    }

    /**
     * Asigna el manejador para alternar tema visual.
     * @param handler función sin parámetros
     */
    public void setOnToggleTheme(Runnable handler) {
        this.onToggleTheme = handler;
    }

    /**
     * Inicializa el menú: configura acciones de botones y selección visual.
     */
    @FXML
    public void initialize() {
        menuButtons = List.of(btnVentas, btnInventario, btnEmpleados, btnPedidos, btnFinanzas);

        btnVentas.setOnAction(e -> {
            navegar("dashboard/ventas/ventas.fxml");
            seleccionar(btnVentas);
        });

        btnInventario.setOnAction(e -> {
            navegar("dashboard/inventario/inventario.fxml");
            seleccionar(btnInventario);
        });

        btnEmpleados.setOnAction(e -> {
            navegar("dashboard/empleados/empleados.fxml");
            seleccionar(btnEmpleados);
        });

        btnPedidos.setOnAction(e -> {
            navegar("dashboard/pedidos/pedidos.fxml");
            seleccionar(btnPedidos);
        });

        btnFinanzas.setOnAction(e -> {
            navegar("dashboard/finanzas.fxml");
            seleccionar(btnFinanzas);
        });

        btnCambiarTema.setOnAction(e -> {
            if (onToggleTheme != null) onToggleTheme.run();
        });
    }

    /**
     * Navega a la vista indicada si el manejador está definido.
     * @param ruta ruta relativa al archivo FXML
     */
    private void navegar(String ruta) {
        if (onNavigate != null) onNavigate.accept(ruta);
    }

    /**
     * Aplica estilo visual al botón activo y desactiva los demás.
     * @param botonActivo botón que fue seleccionado
     */
    private void seleccionar(Button botonActivo) {
        menuButtons.forEach(b -> b.pseudoClassStateChanged(SELECTED, false));
        botonActivo.pseudoClassStateChanged(SELECTED, true);
    }

    /**
     * Aplica control de acceso visual según el rol del operador.
     * Oculta o muestra botones según permisos definidos en RolAccessManager.
     * @param rol rol del operador ("ADMINISTRADOR", "CAJERO", etc.)
     */
    public void setRolOperador(String rol) {
        System.out.println("[MenuSlider] Rol recibido: " + rol);

        Map<String, Node> nodos = new HashMap<>();
        nodos.put("btnVentas", btnVentas);
        nodos.put("btnInventario", btnInventario);
        nodos.put("btnEmpleados", btnEmpleados);
        nodos.put("btnPedidos", btnPedidos);
        nodos.put("btnFinanzas", btnFinanzas);

        RolAccessManager.aplicarPermisos(rol, nodos);
    }
}

