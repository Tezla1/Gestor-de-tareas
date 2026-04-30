package com.udec.tareas.infrastructure.adapter.in;

import com.udec.tareas.domain.model.Tarea;
import com.udec.tareas.domain.port.TareaServicePort;

import java.util.List;
import java.util.Scanner;

/**
 * ADAPTADOR DE ENTRADA - Interfaz de Línea de Comandos (CLI).
 * Depende del puerto de entrada TareaServicePort.
 * Traduce las interacciones del usuario hacia los casos de uso.
 * Podría reemplazarse por un controlador REST sin afectar el dominio.
 */
public class CLIAdapter {

    private final TareaServicePort servicio;
    private final Scanner scanner;

    public CLIAdapter(TareaServicePort servicio) {
        this.servicio = servicio;
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║   Gestor de Tareas Académicas - UdeC     ║");
        System.out.println("║   Arquitectura Hexagonal - Java           ║");
        System.out.println("╚══════════════════════════════════════════╝");

        boolean ejecutando = true;
        while (ejecutando) {
            mostrarMenu();
            String opcion = scanner.nextLine().trim();

            switch (opcion) {
                case "1" -> crearTarea();
                case "2" -> listarTodas();
                case "3" -> listarPendientes();
                case "4" -> completarTarea();
                case "5" -> consultarTarea();
                case "6" -> eliminarTarea();
                case "0" -> {
                    System.out.println("\n¡Hasta luego! Recuerda completar tus tareas pendientes. 📚");
                    ejecutando = false;
                }
                default -> System.out.println("⚠  Opción no válida. Intenta de nuevo.\n");
            }
        }
        scanner.close();
    }

    private void mostrarMenu() {
        System.out.println("\n──────────────────────────────────────────");
        System.out.println("  MENÚ PRINCIPAL");
        System.out.println("──────────────────────────────────────────");
        System.out.println("  1. Registrar nueva tarea");
        System.out.println("  2. Listar todas las tareas");
        System.out.println("  3. Ver tareas pendientes");
        System.out.println("  4. Completar una tarea");
        System.out.println("  5. Consultar tarea por ID");
        System.out.println("  6. Eliminar una tarea");
        System.out.println("  0. Salir");
        System.out.println("──────────────────────────────────────────");
        System.out.print("  Selecciona una opción: ");
    }

    private void crearTarea() {
        System.out.println("\n── REGISTRAR NUEVA TAREA ──");
        System.out.print("Título (*obligatorio): ");
        String titulo = scanner.nextLine().trim();

        System.out.print("Descripción (opcional, Enter para omitir): ");
        String descripcion = scanner.nextLine().trim();
        if (descripcion.isEmpty()) descripcion = null;

        try {
            Tarea tarea = servicio.crearTarea(titulo, descripcion);
            System.out.println("✔  Tarea registrada exitosamente:");
            System.out.println("   " + tarea);
        } catch (IllegalArgumentException e) {
            System.out.println("✘  Error: " + e.getMessage());
        }
    }

    private void listarTodas() {
        System.out.println("\n── TODAS LAS TAREAS ──");
        List<Tarea> tareas = servicio.listarTareas();
        if (tareas.isEmpty()) {
            System.out.println("  No hay tareas registradas.");
        } else {
            tareas.forEach(t -> System.out.println("  " + t));
        }
    }

    private void listarPendientes() {
        System.out.println("\n── TAREAS PENDIENTES ──");
        List<Tarea> pendientes = servicio.listarTareasPendientes();
        if (pendientes.isEmpty()) {
            System.out.println("  ¡No tienes tareas pendientes! 🎉");
        } else {
            System.out.println("  Tienes " + pendientes.size() + " tarea(s) pendiente(s):");
            pendientes.forEach(t -> System.out.println("  " + t));
        }
    }

    private void completarTarea() {
        System.out.println("\n── COMPLETAR TAREA ──");
        System.out.print("ID de la tarea a completar: ");
        String id = scanner.nextLine().trim().toUpperCase();
        try {
            Tarea tarea = servicio.completarTarea(id);
            System.out.println("✔  Tarea completada:");
            System.out.println("   " + tarea);
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("✘  Error: " + e.getMessage());
        }
    }

    private void consultarTarea() {
        System.out.println("\n── CONSULTAR TAREA ──");
        System.out.print("ID de la tarea: ");
        String id = scanner.nextLine().trim().toUpperCase();
        try {
            Tarea tarea = servicio.consultarTarea(id);
            System.out.println("  " + tarea);
        } catch (IllegalArgumentException e) {
            System.out.println("✘  Error: " + e.getMessage());
        }
    }

    private void eliminarTarea() {
        System.out.println("\n── ELIMINAR TAREA ──");
        System.out.print("ID de la tarea a eliminar: ");
        String id = scanner.nextLine().trim().toUpperCase();
        try {
            servicio.eliminarTarea(id);
            System.out.println("✔  Tarea eliminada correctamente.");
        } catch (IllegalArgumentException e) {
            System.out.println("✘  Error: " + e.getMessage());
        }
    }
}
