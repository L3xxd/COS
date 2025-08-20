package com.l3xxd.cos_alpha.controllers.dashboard.ventas;

import com.l3xxd.cos_alpha.config.DBConnection;
import com.l3xxd.cos_alpha.models.DetalleVentaModel;
import com.l3xxd.cos_alpha.models.InventarioModel;
import com.l3xxd.cos_alpha.dao.InventarioDAO;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;




import java.io.IOException;
import java.util.List;

public class VentasController {

    @FXML private GridPane gridProductos;
    @FXML private TableView<DetalleVentaModel> tblCarrito;
    @FXML private TableColumn<DetalleVentaModel, String> colNombre;
    @FXML private TableColumn<DetalleVentaModel, Integer> colCantidad;
    @FXML private TableColumn<DetalleVentaModel, Double> colPrecio;
    @FXML private TextField txtDescuento;
    @FXML private Label lblTotal;
    InventarioDAO inventarioDAO;


    private final ObservableList<DetalleVentaModel> carrito = FXCollections.observableArrayList();

    public void initialize() {
        inventarioDAO = new InventarioDAO(DBConnection.getConnection());
        configurarTabla();
        cargarProductos();
        txtDescuento.textProperty().addListener((obs, oldVal, newVal) -> actualizarTotales());
    }

    private void configurarTabla() {
        tblCarrito.setItems(carrito);

        colNombre.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNombre()));
        colCantidad.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getCantidad()).asObject());
        colPrecio.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getTotal()).asObject());
    }

    private void cargarProductos() {
        List<InventarioModel> productos = inventarioDAO.obtenerDisponiblesParaVenta();
        // Reemplaza con tu DAO real
        int col = 0, row = 0;

        for (InventarioModel producto : productos) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/l3xxd/cos_alpha/views/components/ProductoCard.fxml"));
                Node card = loader.load();

                ProductoCardController controller = loader.getController();
                controller.setProducto(producto, this::agregarAlCarrito);

                gridProductos.add(card, col, row);
                col++;
                if (col == 3) {
                    col = 0;
                    row++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void agregarAlCarrito(DetalleVentaModel nuevoDetalle) {
        for (DetalleVentaModel existente : carrito) {
            if (existente.getProducto().getId() == nuevoDetalle.getProducto().getId()) {
                int nuevaCantidad = existente.getCantidad() + nuevoDetalle.getCantidad();
                carrito.remove(existente);
                carrito.add(new DetalleVentaModel(nuevoDetalle.getProducto(), nuevaCantidad));
                actualizarTotales();
                return;
            }
        }

        carrito.add(nuevoDetalle);
        actualizarTotales();
    }

    private void actualizarTotales() {
        double subtotal = carrito.stream().mapToDouble(DetalleVentaModel::getTotal).sum();
        double descuento = 0.0;

        try {
            descuento = Double.parseDouble(txtDescuento.getText());
        } catch (NumberFormatException ignored) {}

        double total = subtotal - (subtotal * descuento / 100.0);
        lblTotal.setText(String.format("$%.2f", total));
    }


}
