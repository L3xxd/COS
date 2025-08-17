package com.l3xxd.cos_alpha.models;

/**
 * Modelo que representa a un operador del sistema.
 * Contiene información básica como nombre, correo, rol y credenciales.
 * Es inmutable y seguro para uso en sesión.
 */
public final class OperatorModel {

    private final String username;
    private final String email;
    private final String firstName;
    private final String lastName;
    private final String rol;

    /**
     * Constructor completo del operador.
     * @param username nombre de usuario
     * @param email correo electrónico
     * @param firstName nombre
     * @param lastName apellido
     * @param rol rol del operador ("ADMINISTRADOR", "CAJERO", etc.)
     */
    public OperatorModel(String username, String email, String firstName, String lastName, String rol) {
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.rol = rol;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getRol() {
        return rol;
    }

    /**
     * Representación textual del operador para trazabilidad.
     */
    @Override
    public String toString() {
        return String.format("OperatorModel[%s %s, username=%s, rol=%s]", firstName, lastName, username, rol);
    }
}

