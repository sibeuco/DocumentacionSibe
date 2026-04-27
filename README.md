# SIBE — Sistema de Información de Bienestar Estudiantil

**SIBE** es una aplicación web institucional desarrollada para la **Dirección de Bienestar y Evangelización** de la **Universidad Católica de Oriente (UCO)**. Centraliza la planificación, ejecución y seguimiento de las actividades de bienestar, vinculándolas con los indicadores de gestión del Plan de Desarrollo Institucional (PDI).

## Visión del Producto

> **Para** la Dirección de Bienestar y Evangelización, **que** enfrenta problemas con la gestión manual de asistencia a las actividades, **SIBE** es un aplicativo web **que** permitirá agendar, organizar y hacer seguimiento a estas actividades de forma eficiente, garantizando la precisión de la información, agilizando los procesos y automatizando la generación de reportes para facilitar auditorías y la toma de decisiones, eliminando procedimientos manuales y el uso de papel. **A diferencia de** Google Forms, CheckPoint y AccuClass, **nuestro producto** gestiona la asistencia mediante número de identificación o **lector RFID** con carnet universitario.

## Funcionalidades Principales

- Gestión de la jerarquía organizacional de Bienestar (Dirección → Área → Subárea)
- Planificación y ejecución de actividades con registro de asistencia granular
- Registro de participantes (estudiantes, empleados y externos) con perfiles completos
- Carga masiva de participantes desde archivos Excel
- Registro de asistencia por número de identificación o lector RFID
- Medición de impacto a través de indicadores vinculados a proyectos y acciones del PDI
- Generación de reportes y evidencia para Acreditación, Registro Calificado, ISO 9001 y MEN

## Stack Tecnológico

| Capa | Tecnología | Versión |
|------|-----------|---------|
| **Backend** | Java · Spring Boot · Spring Security (JWT) · Spring Data JPA | Java 17 · Spring Boot 3.5.0 |
| **Frontend** | Angular · TypeScript · Bootstrap · Chart.js | Angular 16.2.0 · TypeScript 5.1.3 |
| **Base de datos** | PostgreSQL | 15+ (producción) · H2 (tests) |
| **Construcción** | Gradle (backend) · Angular CLI (frontend) | Gradle 8.13 · Angular CLI 16.2.13 |
| **Arquitectura** | Hexagonal + CQRS | Dominio / Aplicación / Infraestructura |
| **Seguridad** | JWT Bearer · BCrypt · Roles CRUD con bits de permiso | JJWT 0.11.2 |

## Estructura del Repositorio

```
SIBE/
├── SIBEBackend/            ← API REST · Spring Boot 3.5.0 · Gradle
│   ├── build.gradle        ← Dependencias y configuración de build
│   ├── gradlew / gradlew.bat  ← Wrapper de Gradle (no requiere instalación global)
│   ├── settings.gradle
│   └── src/
│       ├── main/java/co/edu/uco/sibe/   ← Código fuente (Hexagonal + CQRS)
│       │   ├── dominio/                  ← Modelos, reglas de negocio, puertos
│       │   ├── aplicacion/               ← Comandos, consultas, manejadores
│       │   └── infraestructura/          ← Controladores REST, JPA, seguridad
│       ├── main/resources/               ← application.properties
│       └── test/                         ← Pruebas unitarias (JUnit 5)
│
├── SIBEFrontend/           ← SPA · Angular 16 · TypeScript
│   ├── package.json        ← Dependencias npm
│   ├── angular.json        ← Configuración Angular CLI
│   ├── proxy.conf.json     ← Proxy de desarrollo → localhost:8080
│   └── src/
│       ├── app/
│       │   ├── core/       ← Servicios singleton, guards, interceptores
│       │   ├── shared/     ← Componentes y pipes reutilizables
│       │   └── modules/    ← Feature modules (lazy loaded)
│       └── environments/   ← Variables de entorno
│
└── docs/                   ← Documentación del producto
    ├── artifacts/          ← 41 artefactos documentales
    └── architecture/       ← Documentación técnica y ADRs
```

## Prerrequisitos

| Herramienta | Versión mínima | Verificación |
|------------|---------------|--------------|
| **JDK** | Java 17 (LTS) | `java -version` |
| **Node.js** | 18 LTS (recomendado 20 LTS) | `node --version` |
| **npm** | 9.x | `npm --version` |
| **Angular CLI** | 16.2.13 | `ng version` |
| **PostgreSQL** | 12+ (recomendado 16) | `psql --version` |
| **Git** | 2.x | `git --version` |

> **Nota:** No es necesario instalar Gradle globalmente. El proyecto incluye `gradlew` / `gradlew.bat` que descarga Gradle 8.13 automáticamente.

## Inicio Rápido

### 1. Clonar el repositorio

```bash
git clone <URL_DEL_REPOSITORIO> SIBE
cd SIBE
```

### 2. Base de datos

Crear la base de datos PostgreSQL:

```sql
CREATE DATABASE sibe_db2;
```

### 3. Backend

```bash
cd SIBEBackend

# Configurar conexión a BD en src/main/resources/application.properties:
#   spring.datasource.url=jdbc:postgresql://localhost:5432/sibe_db2
#   spring.datasource.username=<tu_usuario>
#   spring.datasource.password=<tu_contraseña>

# Windows
.\gradlew.bat bootRun

# Linux / macOS
./gradlew bootRun
```

El backend estará disponible en `http://localhost:8080`.

### 4. Frontend

```bash
cd SIBEFrontend

# Instalar dependencias
npm install

# Iniciar servidor de desarrollo con proxy al backend
ng serve --proxy-config proxy.conf.json
```

La aplicación estará disponible en `http://localhost:4200`. Las peticiones a `/api/*` se redirigen automáticamente al backend en el puerto 8080.

## Pruebas

### Backend (JUnit 5 + JaCoCo)

```bash
cd SIBEBackend

# Windows
.\gradlew.bat test

# Linux / macOS
./gradlew test

# Reporte de cobertura
.\gradlew.bat jacocoTestReport
# → build/reports/jacoco/test/html/index.html
```

### Frontend (Karma + Jasmine)

```bash
cd SIBEFrontend

# Ejecutar pruebas
ng test

# Ejecutar con cobertura
ng test --code-coverage
# → coverage/index.html
```

## Arquitectura

El sistema sigue una **arquitectura Hexagonal (Puertos y Adaptadores)** con **CQRS** en el backend:

```
┌──────────────────────────────────────────────────────┐
│  SIBEFrontend (Angular SPA)                          │
│  http://localhost:4200                               │
└──────────────┬───────────────────────────────────────┘
               │ HTTP/JSON + JWT Bearer
┌──────────────▼───────────────────────────────────────┐
│  SIBEBackend (Spring Boot REST API)                  │
│  http://localhost:8080                               │
│  ┌─────────────┬──────────────┬────────────────────┐ │
│  │ Controladores│  Aplicación  │    Dominio         │ │
│  │ REST (API)  │  Cmd/Query   │  Modelos + Puertos │ │
│  │             │  CQRS        │  Reglas de negocio │ │
│  └──────┬──────┴──────────────┴────────────────────┘ │
│         │ Infraestructura: JPA, Seguridad, Mail      │
└─────────┼────────────────────────────────────────────┘
          │ JDBC
┌─────────▼────────────────────────────────────────────┐
│  PostgreSQL (sibe_db2:5432)                          │
└──────────────────────────────────────────────────────┘
```

## Documentación

La documentación completa del producto está organizada en 7 fases con 41 artefactos. El índice completo se encuentra en [`docs/artifacts/0.index.md`](docs/artifacts/0.index.md).

| Fase | Nombre | Artefactos |
|------|--------|-----------|
| 1 | Descubrimiento del Producto | 1 → 9 |
| 2 | Gestión del Producto | 10 → 16 |
| 3 | Análisis y Diseño Funcional | 17 → 24 |
| 4 | Arquitectura de Solución | 25 → 30 |
| 5 | Construcción y Línea Base | 31 → 35 |
| 6 | Calidad y Certificación | 36 → 38 |
| 7 | Operación y Referencia Técnica | 39 → 41 |

### Lectura sugerida por perfil

| Perfil | Artefactos clave |
|--------|-----------------|
| Product Owner / Stakeholder | 1, 2, 3, 10, 13, 14, 16 |
| Arquitecto de Software | 19, 20, 21, 22, 23, 24, 25 → 30, 41 |
| Desarrollador Backend | 17, 18, 22, 24, 25, 26, 27, 28, 33, 34, 35 |
| Desarrollador Frontend | 13, 16, 17, 22, 26, 28, 36 |
| QA / Tester | 15, 16, 33, 35, 36, 37 |

## Institución

**Universidad Católica de Oriente (UCO)** — Dirección de Bienestar y Evangelización

---

*SIBE v1.0.0*
