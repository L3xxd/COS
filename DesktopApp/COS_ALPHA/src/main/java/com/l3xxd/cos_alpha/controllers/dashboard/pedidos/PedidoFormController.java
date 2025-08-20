package com.l3xxd.cos_alpha.controllers.dashboard.pedidos;

import com.l3xxd.cos_alpha.config.DBConnection;
import com.l3xxd.cos_alpha.dao.InventarioDAO;
import com.l3xxd.cos_alpha.dao.OperatorDAO;
import com.l3xxd.cos_alpha.dao.PedidoDAO;
import com.l3xxd.cos_alpha.models.InventarioModel;
import com.l3xxd.cos_alpha.models.OperatorModel;
import com.l3xxd.cos_alpha.models.PedidoModel;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.List;

public class PedidoFormController {

    @FXML private ComboBox<OperatorModel> cmbOperador;
    @FXML private DatePicker dpFechaSolicitud;
    @FXML private DatePicker dpFechaLlegada;
    @FXML private ComboBox<String> cmbEstado;
    @FXML private ComboBox<InventarioModel> cmbProducto;
    @FXML private TextField txtCantidad;
    @FXML private TableView<DetallePedido> tblDetalles;
    @FXML private TableColumn<DetallePedido, String> colProducto;
    @FXML private TableColumn<DetallePedido, Integer> colCantidad;
    @FXML private TableColumn<DetallePedido, Double> colSubtotal;
    @FXML private Label lblTotal;

    private InventarioDAO inventarioDAO;
    private OperatorDAO operadorDAO;
    private PedidoDAO pedidoDAO;
    private ObservableList<DetallePedido> detalles = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        inventarioDAO = new InventarioDAO(DBConnection.getConnection());
        operadorDAO = new OperatorDAO(DBConnection.getConnection());
        pedidoDAO = new PedidoDAO();

        cargarOperadores();
        cargarEstados();
        cargarProductos();
        configurarTabla();
        actualizarTotal();
    }

    private void cargarOperadores() {
        List<OperatorModel> operadores = operadorDAO.obtenerTodos();
        cmbOperador.setItems(FXCollections.observableArrayList(operadores));

        cmbOperador.setCellFactory(combo -> new ListCell<>() {
            @Override
            protected void updateItem(OperatorModel item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNombreCompleto());
            }
        });

        cmbOperador.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(OperatorModel item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNombreCompleto());
            }
        });
    }

    private void cargarEstados() {
        cmbEstado.setItems(FXCollections.observableArrayList("Pendiente", "En proceso", "Completado"));
    }

    private void cargarProductos() {
        cmbProducto.setItems(FXCollections.observableArrayList(inventarioDAO.obtenerTodos()));

        cmbProducto.setCellFactory(combo -> new ListCell<>() {
            @Override
            protected void updateItem(InventarioModel item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNombre() + " (" + item.getTipo() + ")");
            }
        });

        cmbProducto.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(InventarioModel item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNombre());
            }
        });
    }

    private void configurarTabla() {
        colProducto.setCellValueFactory(data -> {
            InventarioModel p = data.getValue().getProducto();
            return new SimpleStringProperty(p != null ? p.getNombre() : "");
        });

        colCantidad.setCellValueFactory(data -> data.getValue().cantidadProperty().asObject());

        colSubtotal.setCellValueFactory(data ->
                new SimpleDoubleProperty(data.getValue().getSubtotal()).asObject()
        );

        tblDetalles.setItems(detalles);
    }

    @FXML
    private void agregarProducto(ActionEvent event) {
        InventarioModel producto = cmbProducto.getValue();
        String cantidadStr = txtCantidad.getText();

        if (producto == null || cantidadStr.isEmpty()) {
            mostrarAlerta("Debe seleccionar un producto y especificar la cantidad.");
            return;
        }

        try {
            int cantidad = Integer.parseInt(cantidadStr);
            if (cantidad <= 0) throw new NumberFormatException();

            DetallePedido detalle = new DetallePedido(producto, cantidad);
            detalles.add(detalle);

            txtCantidad.clear();
            cmbProducto.getSelectionModel().clearSelection();
            actualizarTotal();
        } catch (NumberFormatException e) {
            mostrarAlerta("La cantidad debe ser un número entero positivo.");
        }
    }

    @FXML
    private void guardarPedido(ActionEvent event) {
        OperatorModel operador = cmbOperador.getValue();

        if (operador == null || dpFechaSolicitud.getValue() == null ||
                dpFechaLlegada.getValue() == null || cmbEstado.getValue() == null) {
            mostrarAlerta("Debe completar todos los campos del formulario.");
            return;
        }

        if (detalles.isEmpty()) {
            mostrarAlerta("Debe agregar al menos un producto al pedido.");
            return;
        }

        PedidoModel pedido = new PedidoModel(
                dpFechaSolicitud.getValue(),
                dpFechaLlegada.getValue(),
                cmbEstado.getValue(),
                operador.getId()
        );

        boolean exito = pedidoDAO.registrarConDetalles(pedido, detalles);

        if (exito) {
            mostrarAlerta("✅ Pedido guardado correctamente.");
            inicializarNuevo();
        } else {
            mostrarAlerta("❌ Error al guardar el pedido.");
        }
    }

    private void actualizarTotal() {
        double total = detalles.stream()
                .mapToDouble(DetallePedido::getSubtotal)
                .sum();
        lblTotal.setText(String.format("Total: $%.2f", total));
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Validación");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public void inicializarNuevo() {
        cmbOperador.getSelectionModel().clearSelection();
        dpFechaSolicitud.setValue(null);
        dpFechaLlegada.setValue(null);
        cmbEstado.getSelectionModel().clearSelection();
        cmbProducto.getSelectionModel().clearSelection();
        txtCantidad.clear();
        detalles.clear();
        actualizarTotal();
    }
}



