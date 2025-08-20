package com.l3xxd.cos_alpha.controllers.dashboard.pedidos;

import com.l3xxd.cos_alpha.models.InventarioModel;
import javafx.beans.property.*;

public class DetallePedido {

    private final ObjectProperty<InventarioModel> producto = new SimpleObjectProperty<>();
    private final IntegerProperty cantidad = new SimpleIntegerProperty();

    public DetallePedido() {}

    public DetallePedido(InventarioModel producto, int cantidad) {
        this.producto.set(producto);
        this.cantidad.set(cantidad);
    }

    // Producto
    public InventarioModel getProducto() { return producto.get(); }
    public void setProducto(InventarioModel value) { producto.set(value); }
    public ObjectProperty<InventarioModel> productoProperty() { return producto; }

    // Cantidad
    public int getCantidad() { return cantidad.get(); }
    public void setCantidad(int value) { cantidad.set(value); }
    public IntegerProperty cantidadProperty() { return cantidad; }

    // Subtotal calculado (precioVenta × cantidad)
    public double getSubtotal() {
        return (getProducto() != null) ? getProducto().getPrecioVenta() * getCantidad() : 0;
    }

    public ReadOnlyDoubleProperty subtotalProperty() {
        return new SimpleDoubleProperty(getSubtotal());
    }

    @Override
    public String toString() {
        return String.format("%s x %d = $%.2f",
                getProducto() != null ? getProducto().getNombre() : "Producto",
                getCantidad(),
                getSubtotal());
    }
}
