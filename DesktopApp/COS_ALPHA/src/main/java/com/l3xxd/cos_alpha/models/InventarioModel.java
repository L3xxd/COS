package com.l3xxd.cos_alpha.models;

import javafx.beans.property.*;

public class InventarioModel {

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty nombre = new SimpleStringProperty();
    private final StringProperty tipo = new SimpleStringProperty();
    private final DoubleProperty precioCompra = new SimpleDoubleProperty();
    private final DoubleProperty precioVenta = new SimpleDoubleProperty();
    private final IntegerProperty stock = new SimpleIntegerProperty();
    private final StringProperty estado = new SimpleStringProperty();
    private final StringProperty urlFoto = new SimpleStringProperty();

    // ✅ Constructor completo para compatibilidad con DAO
    public InventarioModel(int id, String nombre, String tipo, double precioCompra, double precioVenta, int stock, String estado, String urlFoto) {
        this.id.set(id);
        this.nombre.set(nombre);
        this.tipo.set(tipo);
        this.precioCompra.set(precioCompra);
        this.precioVenta.set(precioVenta);
        this.stock.set(stock);
        this.estado.set(estado);
        this.urlFoto.set(urlFoto);
    }

    // ✅ Constructor vacío para formularios o inicialización manual
    public InventarioModel() {}

    public int getId() { return id.get(); }
    public void setId(int value) { id.set(value); }
    public IntegerProperty idProperty() { return id; }

    public String getNombre() { return nombre.get(); }
    public void setNombre(String value) { nombre.set(value); }
    public StringProperty nombreProperty() { return nombre; }

    public String getTipo() { return tipo.get(); }
    public void setTipo(String value) { tipo.set(value); }
    public StringProperty tipoProperty() { return tipo; }

    public double getPrecioCompra() { return precioCompra.get(); }
    public void setPrecioCompra(double value) { precioCompra.set(value); }
    public DoubleProperty precioCompraProperty() { return precioCompra; }

    public double getPrecioVenta() { return precioVenta.get(); }
    public void setPrecioVenta(double value) { precioVenta.set(value); }
    public DoubleProperty precioVentaProperty() { return precioVenta; }

    public int getStock() { return stock.get(); }
    public void setStock(int value) { stock.set(value); }
    public IntegerProperty stockProperty() { return stock; }

    public String getEstado() { return estado.get(); }
    public void setEstado(String value) { estado.set(value); }
    public StringProperty estadoProperty() { return estado; }

    public String getUrlFoto() { return urlFoto.get(); }
    public void setUrlFoto(String value) { urlFoto.set(value); }
    public StringProperty urlFotoProperty() { return urlFoto; }
    // Alias para compatibilidad con PedidoDetalleModel
    public int getIdProducto() {
        return getId();
    }
    @Override
    public String toString() {
        return String.format("%s (%s) - Stock: %d", getNombre(), getTipo(), getStock());
    }


}
