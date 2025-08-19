package com.l3xxd.cos_alpha.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class DBConnection {

    private static final Logger LOGGER = Logger.getLogger(DBConnection.class.getName());

    private static final String URL = "jdbc:mysql://localhost:3306/cos_db?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private DBConnection() {
        throw new UnsupportedOperationException("DBConnection no debe instanciarse.");
    }

    /**
     * Crea y retorna una nueva conexión a la base de datos.
     * @return conexión activa o null si falla
     */
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            LOGGER.info("✅ Conexión establecida con la base de datos.");
            return conn;
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "❌ Driver JDBC no encontrado.", e);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "❌ Error al conectar a la base de datos.", e);
        }
        return null;
    }

    /**
     * Verifica si una conexión está viva.
     * @param conn conexión a verificar
     * @return true si está abierta, false si está cerrada o nula
     */
    public static boolean isAlive(Connection conn) {
        if (conn == null) return false;
        try {
            return !conn.isClosed();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "⚠️ Error al verificar estado de conexión.", e);
            return false;
        }
    }

    public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                LOGGER.info("🔒 Conexión cerrada correctamente.");
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "⚠️ Error al cerrar conexión.", e);
            }
        }
    }
}
