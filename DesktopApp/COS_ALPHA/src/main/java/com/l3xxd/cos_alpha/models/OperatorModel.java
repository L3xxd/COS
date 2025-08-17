package com.l3xxd.cos_alpha.models;

public final class OperatorModel {
    private final String username;
    private final String email;
    private final String firstName;
    private final String lastName;
    private final String rol;

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

    @Override
    public String toString() {
        return String.format("OperatorModel[%s %s, username=%s, rol=%s]", firstName, lastName, username, rol);
    }
}

