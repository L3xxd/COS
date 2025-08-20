package com.l3xxd.cos_alpha.models;

import java.time.LocalDate;

/**
 * Modelo que representa un pedido en el sistema.
 * Incluye trazabilidad con operador, fechas y estado.
 */
public class PedidoModel {

    private int idPedido;
    private int operadorId;
    private String nombreOperador; // solo para visualización
    private LocalDate fechaSolicitud;
    private LocalDate fechaLlegada;
    private String estado; // Ej: PENDIENTE, EN PROCESO, COMPLETADO

    // 🧩 Constructor vacío (requerido por frameworks o setters)
    public PedidoModel() {}

    // 📥 Constructor para registro de nuevo pedido
    public PedidoModel(LocalDate fechaSolicitud, LocalDate fechaLlegada, String estado, int operadorId) {
        this.fechaSolicitud = fechaSolicitud;
        this.fechaLlegada = fechaLlegada;
        this.estado = estado;
        this.operadorId = operadorId;
    }

    // 👁️ Constructor para visualización con nombre del operador
    public PedidoModel(int idPedido, int operadorId, String nombreOperador,
                       LocalDate fechaSolicitud, LocalDate fechaLlegada, String estado) {
        this.idPedido = idPedido;
        this.operadorId = operadorId;
        this.nombreOperador = nombreOperador;
        this.fechaSolicitud = fechaSolicitud;
        this.fechaLlegada = fechaLlegada;
        this.estado = estado;
    }

    // 🛠️ Constructor para edición sin nombre del operador
    public PedidoModel(int idPedido, int operadorId,
                       LocalDate fechaSolicitud, LocalDate fechaLlegada, String estado) {
        this.idPedido = idPedido;
        this.operadorId = operadorId;
        this.fechaSolicitud = fechaSolicitud;
        this.fechaLlegada = fechaLlegada;
        this.estado = estado;
    }

    // 🔍 Getters
    public int getIdPedido() {
        return idPedido;
    }

    public int getOperadorId() {
        return operadorId;
    }

    public String getNombreOperador() {
        return nombreOperador;
    }

    public LocalDate getFechaSolicitud() {
        return fechaSolicitud;
    }

    public LocalDate getFechaLlegada() {
        return fechaLlegada;
    }

    public String getEstado() {
        return estado;
    }

    // ✏️ Setters
    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public void setOperadorId(int operadorId) {
        this.operadorId = operadorId;
    }

    public void setNombreOperador(String nombreOperador) {
        this.nombreOperador = nombreOperador;
    }

    public void setFechaSolicitud(LocalDate fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public void setFechaLlegada(LocalDate fechaLlegada) {
        this.fechaLlegada = fechaLlegada;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    // 📌 Representación textual para trazabilidad
    @Override
    public String toString() {
        return String.format("Pedido #%d | Operador: %s | Estado: %s | Solicitud: %s | Llegada: %s",
                idPedido,
                nombreOperador != null ? nombreOperador : "ID " + operadorId,
                estado,
                fechaSolicitud,
                fechaLlegada);
    }
}

