package com.udec.tareas;

import com.udec.tareas.application.usecase.TareaService;
import com.udec.tareas.domain.port.TareaRepositoryPort;
import com.udec.tareas.domain.port.TareaServicePort;
import com.udec.tareas.infrastructure.adapter.in.CLIAdapter;
import com.udec.tareas.infrastructure.adapter.out.InMemoryTareaRepository;

/**
 * PUNTO DE ENTRADA DE LA APLICACIÓN
 *
 * Aquí se realiza la composición (wiring) de todos los componentes:
 *   - Se instancia el adaptador de salida (repositorio en memoria)
 *   - Se inyecta en el caso de uso (TareaService)
 *   - Se inyecta el caso de uso en el adaptador de entrada (CLI)
 *
 * Este es el único lugar donde todas las capas se conocen entre sí.
 */
public class Main {

    public static void main(String[] args) {
        // 1. Adaptador de salida: persistencia en memoria
        TareaRepositoryPort repositorio = new InMemoryTareaRepository();

        // 2. Casos de uso: lógica de negocio (recibe el repositorio por el puerto)
        TareaServicePort servicio = new TareaService(repositorio);

        // 3. Adaptador de entrada: CLI (recibe el servicio por el puerto)
        CLIAdapter cli = new CLIAdapter(servicio);

        // 4. Iniciar la aplicación
        cli.iniciar();
    }
}
