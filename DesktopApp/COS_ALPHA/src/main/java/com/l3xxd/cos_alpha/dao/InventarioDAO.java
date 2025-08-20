package com.l3xxd.cos_alpha.dao;

import com.l3xxd.cos_alpha.models.InventarioModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventarioDAO {

    private final Connection conn;

    public InventarioDAO(Connection conn) {
        this.conn = conn;
    }

    public List<InventarioModel> obtenerTodos() {
        List<InventarioModel> lista = new ArrayList<>();
        String sql = "SELECT id, name, type, price_purchase, price_sale, stock, status, url_photo FROM productos";

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public List<InventarioModel> buscar(String nombre, String categoria) {
        List<InventarioModel> lista = new ArrayList<>();
        String sql = "SELECT id, name, type, price_purchase, price_sale, stock, status, url_photo FROM productos " +
                "WHERE name LIKE ? AND type LIKE ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + nombre + "%");
            stmt.setString(2, (categoria == null || categoria.isEmpty()) ? "%" : "%" + categoria + "%");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public void insertar(InventarioModel producto) {
        String sql = "INSERT INTO productos (name, type, price_purchase, price_sale, stock, status, url_photo) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getTipo());
            stmt.setDouble(3, producto.getPrecioCompra());
            stmt.setDouble(4, producto.getPrecioVenta());
            stmt.setInt(5, producto.getStock());
            stmt.setString(6, producto.getEstado());
            stmt.setString(7, producto.getUrlFoto());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizar(InventarioModel producto) {
        String sql = "UPDATE productos SET name = ?, type = ?, price_purchase = ?, price_sale = ?, stock = ?, status = ?, url_photo = ? " +
                "WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getTipo());
            stmt.setDouble(3, producto.getPrecioCompra());
            stmt.setDouble(4, producto.getPrecioVenta());
            stmt.setInt(5, producto.getStock());
            stmt.setString(6, producto.getEstado());
            stmt.setString(7, producto.getUrlFoto());
            stmt.setInt(8, producto.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminar(int id) {
        String sql = "DELETE FROM productos WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private InventarioModel mapear(ResultSet rs) throws SQLException {
        return new InventarioModel(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("type"),
                rs.getDouble("price_purchase"),
                rs.getDouble("price_sale"),
                rs.getInt("stock"),
                rs.getString("status"),
                rs.getString("url_photo")
        );
    }
    public InventarioModel obtenerPorId(int id) {
        String sql = "SELECT id, name, type, price_purchase, price_sale, stock, status, url_photo FROM productos WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    public List<InventarioModel> obtenerDisponiblesParaVenta() {
        List<InventarioModel> lista = new ArrayList<>();
        String sql = """
        SELECT id, name, type, price_purchase, price_sale, stock, status, url_photo
        FROM productos
        WHERE status = 'Activo' AND stock > 0
    """;

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }


}

