package com.l3xxd.cos_alpha.dao;

import com.l3xxd.cos_alpha.models.OperatorModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OperatorDAO {

    private static final Logger LOGGER = Logger.getLogger(OperatorDAO.class.getName());

    private final Connection connection;

    public OperatorDAO(Connection connection) {
        this.connection = connection;
    }

    public Optional<OperatorModel> validate(String username, String password) {
        String sql = "SELECT username, email, first_name, last_name, rol FROM operadores WHERE username = ? AND password = ?";

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
                    return Optional.of(operator);
                }
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al validar operador", e);
        }

        return Optional.empty();
    }
}
