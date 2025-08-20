package com.l3xxd.cos_alpha.controllers.dashboard.ventas;

import com.l3xxd.cos_alpha.models.InventarioModel;
import com.l3xxd.cos_alpha.models.DetalleVentaModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.function.Consumer;

public class ProductoCardController {

    @FXML private ImageView imgProducto;
    @FXML private Label lblNombre;
    @FXML private Label lblPrecio;
    @FXML private Spinner<Integer> spinnerCantidad;
    @FXML private Button btnAgregar;

    private InventarioModel producto;
    private Consumer<DetalleVentaModel> onAgregar;

    public void setProducto(InventarioModel producto, Consumer<DetalleVentaModel> onAgregar) {
        this.producto = producto;
        this.onAgregar = onAgregar;

        lblNombre.setText(producto.getNombre());
        lblPrecio.setText(String.format("$%.2f", producto.getPrecioVenta()));
        imgProducto.setImage(new Image(producto.getUrlFoto()));
        spinnerCantidad.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1));

        btnAgregar.setOnAction(e -> {
            int cantidad = spinnerCantidad.getValue();
            DetalleVentaModel detalle = new DetalleVentaModel(producto, cantidad);
            onAgregar.accept(detalle);
        });
    }
}
