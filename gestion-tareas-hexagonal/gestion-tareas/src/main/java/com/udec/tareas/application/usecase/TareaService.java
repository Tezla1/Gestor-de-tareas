package com.udec.tareas.application.usecase;

import com.udec.tareas.domain.model.Tarea;
import com.udec.tareas.domain.port.TareaRepositoryPort;
import com.udec.tareas.domain.port.TareaServicePort;

import java.util.List;
import java.util.UUID;

/**
 * CASOS DE USO - Capa de Aplicación.
 * Implementa el puerto de entrada TareaServicePort.
 * Orquesta la lógica de negocio usando el puerto de salida TareaRepositoryPort.
 * No conoce detalles de infraestructura (cómo se muestra ni cómo se guarda).
 */
public class TareaService implements TareaServicePort {

    private final TareaRepositoryPort repositorio;

    // Inyección de dependencia por constructor (el repositorio viene de fuera)
    public TareaService(TareaRepositoryPort repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public Tarea crearTarea(String titulo, String descripcion) {
        String id = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        Tarea nuevaTarea = new Tarea(id, titulo, descripcion);
        return repositorio.guardar(nuevaTarea);
    }

    @Override
    public List<Tarea> listarTareas() {
        return repositorio.buscarTodas();
    }

    @Override
    public List<Tarea> listarTareasPendientes() {
        return repositorio.buscarPendientes();
    }

    @Override
    public Tarea completarTarea(String id) {
        Tarea tarea = repositorio.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Tarea con id '" + id + "' no encontrada."));
        tarea.completar();
        return repositorio.guardar(tarea);
    }

    @Override
    public Tarea consultarTarea(String id) {
        return repositorio.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Tarea con id '" + id + "' no encontrada."));
    }

    @Override
    public void eliminarTarea(String id) {
        if (!repositorio.existe(id)) {
            throw new IllegalArgumentException("Tarea con id '" + id + "' no encontrada.");
        }
        repositorio.eliminar(id);
    }
}
