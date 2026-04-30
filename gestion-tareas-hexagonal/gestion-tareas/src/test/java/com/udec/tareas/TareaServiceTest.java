package com.udec.tareas;

import com.udec.tareas.application.usecase.TareaService;
import com.udec.tareas.domain.model.EstadoTarea;
import com.udec.tareas.domain.model.Tarea;
import com.udec.tareas.infrastructure.adapter.out.InMemoryTareaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitarios de los casos de uso.
 * Usan el repositorio en memoria directamente (sin mocks).
 */
public class TareaServiceTest {

    private TareaService servicio;

    @BeforeEach
    void setUp() {
        servicio = new TareaService(new InMemoryTareaRepository());
    }

    @Test
    void crearTarea_conTituloValido_debeRegistrarla() {
        Tarea tarea = servicio.crearTarea("Estudiar Arquitectura Hexagonal", "Capítulo 3");
        assertNotNull(tarea.getId());
        assertEquals("Estudiar Arquitectura Hexagonal", tarea.getTitulo());
        assertEquals(EstadoTarea.PENDIENTE, tarea.getEstado());
    }

    @Test
    void crearTarea_conTituloVacio_debeLanzarExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> servicio.crearTarea("", "Sin título"));
    }

    @Test
    void completarTarea_debeCambiarEstado() {
        Tarea tarea = servicio.crearTarea("Entregar informe", null);
        Tarea completada = servicio.completarTarea(tarea.getId());
        assertEquals(EstadoTarea.COMPLETADA, completada.getEstado());
    }

    @Test
    void listarPendientes_debeExcluirCompletadas() {
        servicio.crearTarea("Tarea 1", null);
        Tarea tarea2 = servicio.crearTarea("Tarea 2", null);
        servicio.completarTarea(tarea2.getId());

        List<Tarea> pendientes = servicio.listarTareasPendientes();
        assertEquals(1, pendientes.size());
        assertEquals("Tarea 1", pendientes.get(0).getTitulo());
    }

    @Test
    void eliminarTarea_debeRemoverlaDelRepositorio() {
        Tarea tarea = servicio.crearTarea("Tarea a eliminar", null);
        servicio.eliminarTarea(tarea.getId());
        assertThrows(IllegalArgumentException.class,
                () -> servicio.consultarTarea(tarea.getId()));
    }

    @Test
    void completarTarea_yaCompletada_debeLanzarExcepcion() {
        Tarea tarea = servicio.crearTarea("Tarea repetida", null);
        servicio.completarTarea(tarea.getId());
        assertThrows(IllegalStateException.class,
                () -> servicio.completarTarea(tarea.getId()));
    }
}
