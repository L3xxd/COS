package com.l3xxd.cos_alpha.models;

/**
 * Modelo que representa a un operador del sistema.
 * Compatible con login y operaciones CRUD.
 */
public class OperatorModel {

    private int id;
    private String username;
    private String password;
    private String rol;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    // 🔐 Constructor para login (sin ID ni password)
    public OperatorModel(String username, String email, String firstName, String lastName, String rol) {
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.rol = rol;
    }

    // 🧩 Constructor completo para CRUD
    public OperatorModel(int id, String username, String password, String rol,
                         String firstName, String lastName, String email, String phone) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.rol = rol;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }

    // Getters
    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRol() { return rol; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setRol(String rol) { this.rol = rol; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }

    /**
     * Representación textual del operador para trazabilidad.
     */
    @Override
    public String toString() {
        return String.format("OperatorModel[%s %s, username=%s, rol=%s]", firstName, lastName, username, rol);
    }
    public String getNombreCompleto() {
        return firstName + " " + lastName;
    }

}

