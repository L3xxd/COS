package com.l3xxd.cos_alpha.models;

public class PedidoDetalleModel {
    private int idDetalle;
    private int pedidoId;
    private int productoId;
    private int cantidad;

    public PedidoDetalleModel() {}

    public PedidoDetalleModel(int idDetalle, int pedidoId, int productoId, int cantidad) {
        this.idDetalle = idDetalle;
        this.pedidoId = pedidoId;
        this.productoId = productoId;
        this.cantidad = cantidad;
    }

    // Getters
    public int getIdDetalle() { return idDetalle; }
    public int getPedidoId() { return pedidoId; }
    public int getProductoId() { return productoId; }
    public int getCantidad() { return cantidad; }

    // Setters
    public void setIdDetalle(int idDetalle) { this.idDetalle = idDetalle; }
    public void setPedidoId(int pedidoId) { this.pedidoId = pedidoId; }
    public void setProductoId(int productoId) { this.productoId = productoId; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    // Referencia visual al producto (no persistente, solo para mostrar en la vista)
    private InventarioModel producto;

    public InventarioModel getProducto() {
        return producto;
    }

    public void setProducto(InventarioModel producto) {
        this.producto = producto;
    }

}
