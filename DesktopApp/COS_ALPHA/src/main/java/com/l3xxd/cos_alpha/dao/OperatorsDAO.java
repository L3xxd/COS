package com.l3xxd.cos_alpha.dao;

import com.l3xxd.cos_alpha.config.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OperatorsDAO {

    private static final Logger LOGGER = Logger.getLogger(OperatorsDAO.class.getName());

    /**
     * Valida credenciales del operador.
     * @param username nombre de usuario
     * @param password contraseña
     * @return true si las credenciales son válidas
     */
    public static boolean validate(String username, String password) {
        String query = "SELECT * FROM OPERADORES WHERE username = ? AND password = ?";

        try (Connection conn = DBConnection.getConnection()) {

            if (!DBConnection.isAlive(conn)) {
                LOGGER.warning("Conexión a la base de datos no disponible");
                return false;
            }

            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, username);
                stmt.setString(2, password);

                try (ResultSet rs = stmt.executeQuery()) {
                    return rs.next(); // true si encontró coincidencia
                }
            }

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al validar operador", e);
            return false;
        }
    }
}
