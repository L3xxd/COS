package com.l3xxd.cos_alpha.controllers.dashboard.inventario;

import com.l3xxd.cos_alpha.models.InventarioModel;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;

public class InventarioFormController {

    private String modo; // "Agregar" o "Editar"
    private InventarioModel producto;

    @FXML private TextField nombreField;
    @FXML private ComboBox<String> categoriaCombo;

    public void setModo(String modo) {
        this.modo = modo;
    }

    public void setProducto(InventarioModel producto) {
        this.producto = producto;
        cargarDatosEnFormulario();
    }

    private void cargarDatosEnFormulario() {
        if (producto != null) {
            nombreField.setText(producto.getNombre());
            categoriaCombo.setValue(producto.getTipo());
            // ...otros campos que sí estén en la base de datos
        }
    }

    // Aquí iría la lógica para guardar o actualizar
}
