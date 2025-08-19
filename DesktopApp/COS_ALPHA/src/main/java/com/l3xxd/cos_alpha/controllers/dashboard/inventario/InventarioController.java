package com.l3xxd.cos_alpha.controllers.dashboard.inventario;

import com.l3xxd.cos_alpha.dao.InventarioDAO;
import com.l3xxd.cos_alpha.models.InventarioModel;
import com.l3xxd.cos_alpha.config.DBConnection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.io.IOException;
import java.util.Optional;

public class InventarioController {

    @FXML private TableView<InventarioModel> productosTable;
    @FXML private TableColumn<InventarioModel, Integer> colId;
    @FXML private TableColumn<InventarioModel, String> colNombre;
    @FXML private TableColumn<InventarioModel, String> colCategoria;
    @FXML private TableColumn<InventarioModel, Double> colPrecioCompra;
    @FXML private TableColumn<InventarioModel, Double> colPrecioVenta;
    @FXML private TableColumn<InventarioModel, Integer> colStock;
    @FXML private TableColumn<InventarioModel, String> colDescripcion;
    @FXML private TableColumn<InventarioModel, String> colFoto;

    @FXML private TextField findByNameTextField;
    @FXML private ComboBox<String> categoriaComboBox;
    @FXML private Button searchButton;
    @FXML private Button addButton;
    @FXML private Button editButton;
    @FXML private Button deleteButton;

    private final InventarioDAO inventarioDAO;
    private final ObservableList<InventarioModel> productosList = FXCollections.observableArrayList();

    public InventarioController() {
        Connection conn = DBConnection.getConnection();
        inventarioDAO = new InventarioDAO(conn);
    }

    @FXML
    public void initialize() {
        configurarColumnas();
        cargarProductos();
        configurarEventos();
    }

    private void configurarColumnas() {
        colId.setCellValueFactory(cell -> cell.getValue().idProperty().asObject());
        colNombre.setCellValueFactory(cell -> cell.getValue().nombreProperty());
        colCategoria.setCellValueFactory(cell -> cell.getValue().tipoProperty());
        colPrecioCompra.setCellValueFactory(cell -> cell.getValue().precioCompraProperty().asObject());
        colPrecioVenta.setCellValueFactory(cell -> cell.getValue().precioVentaProperty().asObject());
        colStock.setCellValueFactory(cell -> cell.getValue().stockProperty().asObject());
        colDescripcion.setCellValueFactory(cell -> cell.getValue().estadoProperty());
        colFoto.setCellValueFactory(cell -> cell.getValue().urlFotoProperty());
    }

    private void cargarProductos() {
        productosList.setAll(inventarioDAO.obtenerTodos());
        productosTable.setItems(productosList);
    }

    private void configurarEventos() {
        searchButton.setOnAction(e -> buscarProductos());
        addButton.setOnAction(e -> abrirFormulario("Agregar", null));
        editButton.setOnAction(e -> editarProducto());
        deleteButton.setOnAction(e -> eliminarProducto());
    }

    private void buscarProductos() {
        String nombre = findByNameTextField.getText().trim();
        String categoria = categoriaComboBox.getValue();
        productosList.setAll(inventarioDAO.buscar(nombre, categoria));
        productosTable.setItems(productosList);
    }

    private void editarProducto() {
        InventarioModel seleccionado = productosTable.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            abrirFormulario("Editar", seleccionado);
        } else {
            mostrarAlerta("Selecciona un producto para editar.");
        }
    }

    private void abrirFormulario(String modo, InventarioModel producto) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/l3xxd/cos_alpha/views/dashboard/inventario/formulario-inventario.fxml"));
            AnchorPane pane = loader.load();

            InventarioFormController controller = loader.getController();
            controller.setModo(modo);
            if (producto != null) controller.setProducto(producto);

            Stage stage = new Stage();
            stage.setTitle(modo + " Producto");
            stage.setScene(new Scene(pane));
            stage.showAndWait();

            cargarProductos();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void eliminarProducto() {
        InventarioModel seleccionado = productosTable.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar eliminación");
            confirmacion.setHeaderText("¿Eliminar producto seleccionado?");
            confirmacion.setContentText("Esta acción no se puede deshacer.");

            Optional<ButtonType> resultado = confirmacion.showAndWait();
            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                inventarioDAO.eliminar(seleccionado.getId());
                cargarProductos();
            }
        } else {
            mostrarAlerta("Selecciona un producto para eliminar.");
        }
    }

    private void mostrarAlerta(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Advertencia");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}

