package com.l3xxd.cos_alpha.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
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

    public void setOnNavigate(Consumer<String> handler) {
        this.onNavigate = handler;
    }

    public void setOnToggleTheme(Runnable handler) {
        this.onToggleTheme = handler;
    }

    @FXML
    public void initialize() {
        btnVentas.setOnAction(e -> onNavigate.accept("dashboard/ventas.fxml"));
        btnInventario.setOnAction(e -> onNavigate.accept("dashboard/inventario.fxml"));
        btnEmpleados.setOnAction(e -> onNavigate.accept("dashboard/empleados.fxml"));
        btnPedidos.setOnAction(e -> onNavigate.accept("dashboard/pedidos.fxml"));
        btnFinanzas.setOnAction(e -> onNavigate.accept("dashboard/finanzas.fxml"));


        btnCambiarTema.setOnAction(e -> {
            if (onToggleTheme != null) onToggleTheme.run();
        });
    }
}
