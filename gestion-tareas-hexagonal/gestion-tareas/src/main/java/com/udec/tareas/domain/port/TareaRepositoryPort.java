package com.udec.tareas.domain.port;

import com.udec.tareas.domain.model.Tarea;
import java.util.List;
import java.util.Optional;

/**
 * PUERTO DE SALIDA (Driven Port / Output Port)
 * Define el contrato de persistencia que el dominio necesita.
 * El dominio NO sabe cómo se implementa (memoria, archivo, BD, etc.).
 * Los adaptadores de salida implementan esta interfaz.
 */
public interface TareaRepositoryPort {

    Tarea guardar(Tarea tarea);

    Optional<Tarea> buscarPorId(String id);

    List<Tarea> buscarTodas();

    List<Tarea> buscarPendientes();

    void eliminar(String id);

    boolean existe(String id);
}
