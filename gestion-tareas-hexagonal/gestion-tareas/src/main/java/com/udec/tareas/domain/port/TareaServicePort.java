package com.udec.tareas.domain.port;

import com.udec.tareas.domain.model.Tarea;
import java.util.List;

/**
 * PUERTO DE ENTRADA (Driving Port / Input Port)
 * Define el contrato que expone la aplicación hacia el exterior.
 * Los adaptadores de entrada (CLI, REST) dependen de esta interfaz.
 */
public interface TareaServicePort {

    Tarea crearTarea(String titulo, String descripcion);

    List<Tarea> listarTareas();

    List<Tarea> listarTareasPendientes();

    Tarea completarTarea(String id);

    Tarea consultarTarea(String id);

    void eliminarTarea(String id);
}
