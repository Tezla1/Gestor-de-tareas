package com.udec.tareas.domain.model;

import java.time.LocalDateTime;

/**
 * DOMINIO - Entidad principal del negocio.
 * Esta clase no depende de ningún framework externo.
 * Contiene las reglas de negocio básicas.
 */
public class Tarea {

    private final String id;
    private String titulo;
    private String descripcion;
    private EstadoTarea estado;
    private final LocalDateTime fechaCreacion;

    public Tarea(String id, String titulo, String descripcion) {
        validarTitulo(titulo);
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.estado = EstadoTarea.PENDIENTE;
        this.fechaCreacion = LocalDateTime.now();
    }

    // Regla de negocio: no se permite título vacío
    private void validarTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("El título de la tarea no puede estar vacío.");
        }
    }

    // Regla de negocio: marcar como completada cambia el estado
    public void completar() {
        if (this.estado == EstadoTarea.COMPLETADA) {
            throw new IllegalStateException("La tarea ya está completada.");
        }
        this.estado = EstadoTarea.COMPLETADA;
    }

    public boolean esPendiente() {
        return this.estado == EstadoTarea.PENDIENTE;
    }

    // Getters
    public String getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getDescripcion() { return descripcion; }
    public EstadoTarea getEstado() { return estado; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }

    @Override
    public String toString() {
        return String.format("[%s] %s - %s | %s | Creada: %s",
                id, titulo,
                descripcion != null ? descripcion : "Sin descripción",
                estado,
                fechaCreacion.toLocalDate());
    }
}
