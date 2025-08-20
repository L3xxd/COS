package com.l3xxd.cos_alpha.dao;

import com.l3xxd.cos_alpha.config.DBConnection;
import com.l3xxd.cos_alpha.controllers.dashboard.pedidos.DetallePedido;
import com.l3xxd.cos_alpha.models.PedidoModel;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    private final Connection conn;

    public PedidoDAO() {
        this.conn = DBConnection.getConnection();
    }

    public boolean registrarConDetalles(PedidoModel pedido, List<DetallePedido> detalles) {
        String sqlPedido = """
        INSERT INTO pedidos (operador_id, fecha_solicitud, fecha_llegada, estado)
        VALUES (?, ?, ?, ?)
    """;

        String sqlDetalle = """
        INSERT INTO pedido_detalle (pedido_id, producto_id, cantidad, subtotal)
        VALUES (?, ?, ?, ?)
    """;

        try (
                PreparedStatement stmtPedido = conn.prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS);
                PreparedStatement stmtDetalle = conn.prepareStatement(sqlDetalle)
        ) {
            // Insertar pedido
            stmtPedido.setInt(1, pedido.getOperadorId());
            stmtPedido.setObject(2, pedido.getFechaSolicitud());
            stmtPedido.setObject(3, pedido.getFechaLlegada());
            stmtPedido.setString(4, pedido.getEstado());
            stmtPedido.executeUpdate();

            // Obtener ID generado
            ResultSet rs = stmtPedido.getGeneratedKeys();
            if (!rs.next()) {
                throw new SQLException("No se pudo obtener el ID del pedido.");
            }
            int idPedido = rs.getInt(1);

            // Insertar detalles
            for (DetallePedido detalle : detalles) {
                stmtDetalle.setInt(1, idPedido);
                stmtDetalle.setInt(2, detalle.getProducto().getIdProducto());
                stmtDetalle.setInt(3, detalle.getCantidad());
                stmtDetalle.setDouble(4, detalle.getSubtotal());

                stmtDetalle.addBatch();
            }

            stmtDetalle.executeBatch();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public int obtenerUltimoId() {
        String sql = "SELECT MAX(id_pedido) AS ultimo_id FROM pedidos";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("ultimo_id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public List<PedidoModel> listarPedidos() {
        List<PedidoModel> pedidos = new ArrayList<>();
        String sql = """
    SELECT p.*, CONCAT(o.first_name, ' ', o.last_name) AS nombre_operador
    FROM pedidos p
    JOIN operadores o ON p.operador_id = o.id
""";


        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                PedidoModel pedido = new PedidoModel(
                        rs.getInt("id_pedido"),
                        rs.getInt("operador_id"),
                        rs.getString("nombre_operador"),
                        rs.getObject("fecha_solicitud", LocalDate.class),
                        rs.getObject("fecha_llegada", LocalDate.class),
                        rs.getString("estado")
                );
                pedidos.add(pedido);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pedidos;
    }

    public PedidoModel buscarPorId(int idPedido) {
        String sql = """
    SELECT p.id_pedido, p.operador_id,
           CONCAT(o.first_name, ' ', o.last_name) AS nombre_operador,
           p.fecha_solicitud, p.fecha_llegada, p.estado
    FROM pedidos p
    JOIN operadores o ON p.operador_id = o.id
    WHERE p.id_pedido = ?
""";


        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idPedido);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new PedidoModel(
                        rs.getInt("id_pedido"),
                        rs.getInt("operador_id"),
                        rs.getString("nombre_operador"),
                        rs.getObject("fecha_solicitud", LocalDate.class),
                        rs.getObject("fecha_llegada", LocalDate.class),
                        rs.getString("estado")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void actualizarPedido(PedidoModel pedido) {
        String sql = """
            UPDATE pedidos
            SET operador_id = ?, fecha_solicitud = ?, fecha_llegada = ?, estado = ?
            WHERE id_pedido = ?
        """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, pedido.getOperadorId());
            stmt.setObject(2, pedido.getFechaSolicitud());
            stmt.setObject(3, pedido.getFechaLlegada());
            stmt.setString(4, pedido.getEstado());
            stmt.setInt(5, pedido.getIdPedido());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarPedido(int idPedido) {
        String sql = "DELETE FROM pedidos WHERE id_pedido = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idPedido);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


