package com.l3xxd.cos_alpha.controllers.dashboard.pedidos;

import com.l3xxd.cos_alpha.models.PedidoModel;
import com.l3xxd.cos_alpha.dao.PedidoDAO;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class PedidoController implements Initializable {

    @FXML private TableView<PedidoModel> tblPedidos;

    @FXML private TableColumn<PedidoModel, Integer> colIdPedido;
    @FXML private TableColumn<PedidoModel, String> colOperador;
    @FXML private TableColumn<PedidoModel, LocalDate> colFechaSolicitud;
    @FXML private TableColumn<PedidoModel, LocalDate> colFechaLlegada;
    @FXML private TableColumn<PedidoModel, String> colEstado;

    @FXML private Button btnNuevo;

    private PedidoDAO pedidoDAO;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pedidoDAO = new PedidoDAO();
        configurarTabla();
        cargarPedidos();
    }

    private void configurarTabla() {
        colIdPedido.setCellValueFactory(new PropertyValueFactory<>("idPedido"));
        colOperador.setCellValueFactory(new PropertyValueFactory<>("nombreOperador"));
        colFechaSolicitud.setCellValueFactory(new PropertyValueFactory<>("fechaSolicitud"));
        colFechaLlegada.setCellValueFactory(new PropertyValueFactory<>("fechaLlegada"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

    }

    private void cargarPedidos() {
        List<PedidoModel> pedidos = pedidoDAO.listarPedidos();
        tblPedidos.setItems(FXCollections.observableArrayList(pedidos));
    }

    @FXML
    private void abrirFormularioNuevoPedido(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/l3xxd/cos_alpha/views/dashboard/pedidos/formulario-pedidos.fxml"));
            Parent root = loader.load();

            PedidoFormController controller = loader.getController();
            controller.inicializarNuevo();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Nuevo Pedido");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            cargarPedidos();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void editarPedido(ActionEvent event) {
        PedidoModel seleccionado = tblPedidos.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            System.out.println("Editar pedido: " + seleccionado.getIdPedido());
            // Aquí puedes abrir el formulario en modo edición
        }
    }

    @FXML
    private void eliminarPedido(ActionEvent event) {
        PedidoModel seleccionado = tblPedidos.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            pedidoDAO.eliminarPedido(seleccionado.getIdPedido());
            cargarPedidos(); // refrescar tabla
        }
    }

    @FXML
    private void verDetallePedido(ActionEvent event) {
        PedidoModel seleccionado = tblPedidos.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            System.out.println("Detalle pedido: " + seleccionado.getIdPedido());
            // Aquí puedes mostrar una ventana con los detalles
        }
    }


}

