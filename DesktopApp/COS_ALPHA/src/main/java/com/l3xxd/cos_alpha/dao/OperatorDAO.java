package com.l3xxd.cos_alpha.dao;

import com.l3xxd.cos_alpha.models.OperatorModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DAO para operaciones relacionadas con operadores.
 * Encapsula acceso a la tabla 'operadores'.
 */
public class OperatorDAO {

    private static final Logger LOGGER = Logger.getLogger(OperatorDAO.class.getName());
    private final Connection connection;

    public OperatorDAO(Connection connection) {
        this.connection = connection;
    }

    // 🔐 Validación de login (ya existente)
    public Optional<OperatorModel> validate(String username, String password) {
        String sql = """
            SELECT username, email, first_name, last_name, rol
            FROM operadores
            WHERE username = ? AND password = ?
        """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    OperatorModel operator = new OperatorModel(
                            rs.getString("username"),
                            rs.getString("email"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("rol")
                    );
                    LOGGER.info("✅ Operador validado: " + operator.getUsername());
                    return Optional.of(operator);
                } else {
                    LOGGER.warning("❌ Credenciales inválidas para usuario: " + username);
                }
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "❌ Error al validar operador", e);
        }

        return Optional.empty();
    }

    // 📥 Obtener todos los operadores
    public List<OperatorModel> obtenerTodos() {
        List<OperatorModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM operadores ORDER BY id DESC";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(mapear(rs));
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "❌ Error al obtener operadores", e);
        }

        return lista;
    }

    // 🔍 Buscar por username y teléfono
    public List<OperatorModel> buscar(String username, String phone) {
        List<OperatorModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM operadores WHERE username LIKE ? AND phone LIKE ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + username + "%");
            stmt.setString(2, "%" + phone + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "❌ Error en búsqueda de operadores", e);
        }

        return lista;
    }

    // 🗑️ Eliminar por ID
    public boolean eliminar(int id) {
        String sql = "DELETE FROM operadores WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int filas = stmt.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "❌ Error al eliminar operador", e);
            return false;
        }
    }

    // 🧠 Mapeo común
    private OperatorModel mapear(ResultSet rs) throws SQLException {
        return new OperatorModel(
                rs.getInt("id"),
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("rol"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("email"),
                rs.getString("phone")
        );
    }

    public boolean insertar(OperatorModel operador) {
        String sql = """
        INSERT INTO operadores (username, password, rol, first_name, last_name, email, phone)
        VALUES (?, ?, ?, ?, ?, ?, ?)
    """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, operador.getUsername());
            stmt.setString(2, operador.getPassword());
            stmt.setString(3, operador.getRol());
            stmt.setString(4, operador.getFirstName());
            stmt.setString(5, operador.getLastName());
            stmt.setString(6, operador.getEmail());
            stmt.setString(7, operador.getPhone());

            int filas = stmt.executeUpdate();
            LOGGER.info("✅ Operador insertado: " + operador.getUsername());
            return filas > 0;

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "❌ Error al insertar operador", e);
            return false;
        }
    }
    public boolean actualizar(OperatorModel operador) {
        String sql = """
        UPDATE operadores
        SET password = ?, rol = ?, first_name = ?, last_name = ?, email = ?, phone = ?
        WHERE id = ?
    """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, operador.getPassword());
            stmt.setString(2, operador.getRol());
            stmt.setString(3, operador.getFirstName());
            stmt.setString(4, operador.getLastName());
            stmt.setString(5, operador.getEmail());
            stmt.setString(6, operador.getPhone());
            stmt.setInt(7, operador.getId());

            int filas = stmt.executeUpdate();
            LOGGER.info("🔄 Operador actualizado: ID " + operador.getId());
            return filas > 0;

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "❌ Error al actualizar operador", e);
            return false;
        }
    }


}

