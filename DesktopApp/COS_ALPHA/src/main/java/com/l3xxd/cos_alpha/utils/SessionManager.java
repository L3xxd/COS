package com.l3xxd.cos_alpha.utils;

import com.l3xxd.cos_alpha.models.OperatorModel;

/**
 * Gestor de sesión para el operador actual.
 * Permite establecer, obtener y limpiar el usuario en sesión.
 * Centraliza el acceso a datos del operador durante la ejecución.
 */
public class SessionManager {

    private static OperatorModel currentUser;

    /**
     * Establece el operador actual en sesión.
     * @param user instancia de OperatorModel
     */
    public static void setUser(OperatorModel user) {
        currentUser = user;
        System.out.println("[SessionManager] 👤 Sesión iniciada: " + user.getUsername());
    }

    /**
     * Obtiene el operador actual en sesión.
     * @return instancia de OperatorModel o null si no hay sesión activa
     */
    public static OperatorModel getUser() {
        return currentUser;
    }

    /**
     * Limpia la sesión actual.
     * Elimina referencia al operador.
     */
    public static void clear() {
        System.out.println("[SessionManager] 🔒 Sesión cerrada.");
        currentUser = null;
    }
}
