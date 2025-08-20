package com.l3xxd.cos_alpha.models;

public class DetalleVentaModel {
    private final InventarioModel producto;
    private final int cantidad;

    public DetalleVentaModel(InventarioModel producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return producto.getNombre();
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getTotal() {
        return cantidad * producto.getPrecioVenta();
    }

    public InventarioModel getProducto() {
        return producto;
    }
}

