package com.l3xxd.cos_alpha.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/cos_db?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private DBConnection() {
        // Previene instanciación
    }

    /**
     * Crea y retorna una nueva conexión a la base de datos.
     * @return conexión activa o null si falla
     */
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, "Error al conectar a la base de datos", e);
            return null;
        }
    }

    /**
     * Verifica si una conexión está viva.
     * @param conn conexión a verificar
     * @return true si está abierta, false si está cerrada o nula
     */
    public static boolean isAlive(Connection conn) {
        try {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
}
