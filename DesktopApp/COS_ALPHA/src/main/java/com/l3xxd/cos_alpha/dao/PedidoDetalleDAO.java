package com.l3xxd.cos_alpha.dao;

import com.l3xxd.cos_alpha.models.PedidoDetalleModel;
import com.l3xxd.cos_alpha.config.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDetalleDAO {
    private InventarioDAO inventarioDAO;


    public List<PedidoDetalleModel> listarPorPedido(int pedidoId) {
        List<PedidoDetalleModel> detalles = new ArrayList<>();
        String query = "SELECT * FROM pedido_detalle WHERE pedido_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, pedidoId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                PedidoDetalleModel detalle = new PedidoDetalleModel(
                        rs.getInt("id_detalle"),
                        rs.getInt("pedido_id"),
                        rs.getInt("producto_id"),
                        rs.getInt("cantidad")
                );
                detalles.add(detalle);
            }

        } catch (SQLException e) {
            System.err.println("Error al listar detalles del pedido " + pedidoId + ": " + e.getMessage());
        }

        return detalles;
    }

    public boolean registrar(PedidoDetalleModel detalle) {
        if (detalle.getCantidad() <= 0) {
            System.err.println("Cantidad inválida: " + detalle.getCantidad());
            return false;
        }

        String query = "INSERT INTO pedido_detalle (pedido_id, producto_id, cantidad) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, detalle.getPedidoId());
            stmt.setInt(2, detalle.getProductoId());
            stmt.setInt(3, detalle.getCantidad());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al registrar detalle: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarPorPedido(int pedidoId) {
        String query = "DELETE FROM pedido_detalle WHERE pedido_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, pedidoId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar detalles del pedido " + pedidoId + ": " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarPorId(int idDetalle) {
        String query = "DELETE FROM pedido_detalle WHERE id_detalle = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idDetalle);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar detalle " + idDetalle + ": " + e.getMessage());
            return false;
        }
    }
}
