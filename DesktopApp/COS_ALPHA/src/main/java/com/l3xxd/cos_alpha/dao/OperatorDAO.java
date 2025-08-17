package com.l3xxd.cos_alpha.dao;

import com.l3xxd.cos_alpha.models.OperatorModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    /**
     * Constructor que recibe una conexión activa.
     * @param connection conexión a la base de datos
     */
    public OperatorDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Valida credenciales del operador.
     * @param username nombre de usuario
     * @param password contraseña en texto plano (⚠️ se recomienda encriptar)
     * @return Optional con el modelo si es válido, vacío si no
     */
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
}
