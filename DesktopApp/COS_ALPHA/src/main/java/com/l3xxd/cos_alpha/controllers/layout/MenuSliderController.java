package com.l3xxd.cos_alpha.controllers.layout;

import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.util.List;
import java.util.function.Consumer;

public class MenuSliderController {

    @FXML private Button btnVentas;
    @FXML private Button btnInventario;
    @FXML private Button btnEmpleados;
    @FXML private Button btnPedidos;
    @FXML private Button btnFinanzas;
    @FXML private Button btnCambiarTema;

    private Consumer<String> onNavigate;
    private Runnable onToggleTheme;

    private static final PseudoClass SELECTED = PseudoClass.getPseudoClass("selected");
    private List<Button> menuButtons;

    public void setOnNavigate(Consumer<String> handler) {
        this.onNavigate = handler;
    }

    public void setOnToggleTheme(Runnable handler) {
        this.onToggleTheme = handler;
    }

    @FXML
    public void initialize() {
        menuButtons = List.of(btnVentas, btnInventario, btnEmpleados, btnPedidos, btnFinanzas);

        btnVentas.setOnAction(e -> {
            navegar("dashboard/ventas.fxml");
            seleccionar(btnVentas);
        });

        btnInventario.setOnAction(e -> {
            navegar("dashboard/inventario.fxml");
            seleccionar(btnInventario);
        });

        btnEmpleados.setOnAction(e -> {
            navegar("dashboard/empleados.fxml");
            seleccionar(btnEmpleados);
        });

        btnPedidos.setOnAction(e -> {
            navegar("dashboard/pedidos.fxml");
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

    private void navegar(String ruta) {
        if (onNavigate != null) onNavigate.accept(ruta);
    }

    private void seleccionar(Button botonActivo) {
        menuButtons.forEach(b -> b.pseudoClassStateChanged(SELECTED, false));
        botonActivo.pseudoClassStateChanged(SELECTED, true);
    }
}
