package com.udec.tareas.infrastructure.adapter.out;

import com.udec.tareas.domain.model.EstadoTarea;
import com.udec.tareas.domain.model.Tarea;
import com.udec.tareas.domain.port.TareaRepositoryPort;

import java.util.*;
import java.util.stream.Collectors;

/**
 * ADAPTADOR DE SALIDA - Repositorio en Memoria.
 * Implementa el puerto de salida TareaRepositoryPort.
 * Puede reemplazarse fácilmente por un adaptador con base de datos o archivo
 * sin tocar el dominio ni los casos de uso.
 */
public class InMemoryTareaRepository implements TareaRepositoryPort {

    private final Map<String, Tarea> almacenamiento = new LinkedHashMap<>();

    @Override
    public Tarea guardar(Tarea tarea) {
        almacenamiento.put(tarea.getId(), tarea);
        return tarea;
    }

    @Override
    public Optional<Tarea> buscarPorId(String id) {
        return Optional.ofNullable(almacenamiento.get(id));
    }

    @Override
    public List<Tarea> buscarTodas() {
        return new ArrayList<>(almacenamiento.values());
    }

    @Override
    public List<Tarea> buscarPendientes() {
        return almacenamiento.values().stream()
                .filter(Tarea::esPendiente)
                .collect(Collectors.toList());
    }

    @Override
    public void eliminar(String id) {
        almacenamiento.remove(id);
    }

    @Override
    public boolean existe(String id) {
        return almacenamiento.containsKey(id);
    }
}
