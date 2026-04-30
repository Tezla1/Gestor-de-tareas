# 📚 Gestión de Tareas Académicas — Arquitectura Hexagonal

> **Actividad R2-A1-S9** | Estructura y Arquitectura de Software  
> Universidad de Cundinamarca — REA 2

---

## 📌 Descripción del problema

Los estudiantes necesitan organizar y controlar sus tareas académicas pendientes de forma estructurada. Esta aplicación permite **registrar, consultar, completar, listar y eliminar tareas** académicas de manera básica, aplicando los principios de la arquitectura hexagonal para garantizar desacoplamiento, mantenibilidad y testabilidad.

---

## 🏗️ Arquitectura Hexagonal — Estructura del proyecto

```
gestion-tareas/
├── src/
│   ├── main/java/com/udec/tareas/
│   │   ├── Main.java                          ← Punto de entrada / composición
│   │   │
│   │   ├── domain/                            ← NÚCLEO DEL DOMINIO
│   │   │   ├── model/
│   │   │   │   ├── Tarea.java                 ← Entidad principal con reglas de negocio
│   │   │   │   └── EstadoTarea.java           ← Enumeración de estados
│   │   │   └── port/
│   │   │       ├── TareaServicePort.java      ← Puerto de ENTRADA (interfaz)
│   │   │       └── TareaRepositoryPort.java   ← Puerto de SALIDA (interfaz)
│   │   │
│   │   ├── application/                       ← CASOS DE USO
│   │   │   └── usecase/
│   │   │       └── TareaService.java          ← Implementa TareaServicePort
│   │   │
│   │   └── infrastructure/                    ← ADAPTADORES (infraestructura)
│   │       └── adapter/
│   │           ├── in/
│   │           │   └── CLIAdapter.java        ← Adaptador de ENTRADA: consola
│   │           └── out/
│   │               └── InMemoryTareaRepository.java  ← Adaptador de SALIDA: memoria
│   │
│   └── test/java/com/udec/tareas/
│       └── TareaServiceTest.java              ← Pruebas unitarias
│
├── pom.xml                                    ← Configuración Maven
└── README.md
```

---

## ⚙️ Tecnologías usadas

| Tecnología | Versión | Uso |
|---|---|---|
| Java | 17 | Lenguaje principal |
| Maven | 3.x | Gestión de dependencias y build |
| JUnit Jupiter | 5.10 | Pruebas unitarias |

---

## 🚀 Pasos para ejecutar

### Prerrequisitos
- Java 17 o superior instalado
- Maven 3.6+ instalado

Verificar instalación:
```bash
java -version
mvn -version
```

### Compilar el proyecto
```bash
cd gestion-tareas
mvn clean compile
```

### Ejecutar los tests
```bash
mvn test
```

### Ejecutar la aplicación
```bash
mvn exec:java -Dexec.mainClass="com.udec.tareas.Main"
```

### O bien, generar el JAR y ejecutarlo
```bash
mvn clean package
java -jar target/gestion-tareas.jar
```

---

## 🔌 Puertos y Adaptadores

### Puertos de Entrada (`TareaServicePort`)
Definen **qué puede hacer** la aplicación desde el exterior. Son interfaces que el adaptador de entrada invoca:
- `crearTarea(titulo, descripcion)`
- `listarTareas()`
- `listarTareasPendientes()`
- `completarTarea(id)`
- `consultarTarea(id)`
- `eliminarTarea(id)`

### Puertos de Salida (`TareaRepositoryPort`)
Definen **lo que necesita** el dominio para persistir datos. Son interfaces que el adaptador de salida implementa:
- `guardar(tarea)`
- `buscarPorId(id)`
- `buscarTodas()`
- `buscarPendientes()`
- `eliminar(id)`
- `existe(id)`

### Adaptadores
| Tipo | Clase | Descripción |
|---|---|---|
| Entrada | `CLIAdapter` | Interfaz de línea de comandos interactiva |
| Salida | `InMemoryTareaRepository` | Almacenamiento en memoria durante la ejecución |

> 💡 Para cambiar la persistencia a base de datos, solo se crea una nueva clase que implemente `TareaRepositoryPort` — sin tocar el dominio ni los casos de uso.

---

## ✅ Funcionalidades

1. **Registrar tarea** — título obligatorio, descripción opcional
2. **Listar todas las tareas** — muestra pendientes y completadas
3. **Ver tareas pendientes** — filtrado por estado
4. **Completar tarea** — cambia el estado a COMPLETADA
5. **Consultar tarea** — buscar por ID
6. **Eliminar tarea** — remover del sistema

### Reglas de negocio
- El título no puede estar vacío
- Una tarea completada no puede completarse de nuevo
- Cada tarea tiene un ID único generado automáticamente
- El listado distingue tareas pendientes de completadas

---

## 👥 Autores

Universidad de Cundinamarca — Programa de Ingeniería de Sistemas  
Asignatura: Estructura y Arquitectura de Software
