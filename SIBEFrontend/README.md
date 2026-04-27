# SIBE (Frontend)

**SIBE (Sistema de Información de Bienestar Estudiantil)** — Aplicación SPA desarrollada con Angular para la Dirección de Bienestar y Evangelización de la Universidad Católica de Oriente. Provee la interfaz de usuario para gestionar actividades de bienestar, registro de asistencia (incluyendo lector RFID), carga masiva de participantes y visualización de indicadores.

## Especificaciones del Proyecto

- **Función**: Interfaz de usuario SPA (Single Page Application)
- **Framework**: Angular 16.2.0
- **Lenguaje**: TypeScript 5.1.3
- **Puerto de desarrollo**: 4200
- **Proxy al backend**: `/api` → `http://localhost:8080`

## Tecnologías Principales

| Categoría | Tecnología | Versión |
|-----------|-----------|---------|
| **Framework** | Angular | 16.2.0 |
| **Lenguaje** | TypeScript | 5.1.3 |
| **UI** | Bootstrap · Bootstrap Icons | 5.3.6 · 1.13.1 |
| **Gráficos** | Chart.js · chartjs-plugin-datalabels | 4.5.0 · 2.2.0 |
| **Auth** | jwt-decode | 4.0.0 |
| **Cookies** | ngx-cookie-service | 16.1.0 |
| **Paginación** | ngx-pagination | 6.0.3 |
| **Carrusel** | Swiper | 11.2.10 |
| **Excel** | SheetJS (xlsx) | 0.18.5 |
| **Testing** | Karma · Jasmine | 6.4.0 · 4.6.0 |

## Estructura del Proyecto

```
src/app/
├── core/                          ← Singleton: servicios base, guards, interceptores
│   ├── components/
│   │   ├── header/                ← Barra de navegación principal
│   │   └── footer/                ← Pie de página
│   ├── guard/
│   │   ├── security.guard.ts      ← Protege rutas autenticadas
│   │   └── public-route.guard.ts  ← Protege rutas públicas (login, recuperación)
│   ├── interceptor/
│   │   ├── auth-interceptor.ts    ← Inyección de credenciales y token JWT
│   │   ├── token-interceptor.ts   ← Validación de expiración del token
│   │   ├── manejador-error.ts     ← Gestión global de errores HTTP
│   │   └── http-codigo-error.ts   ← Catálogo de códigos de error
│   ├── model/
│   │   ├── usuario.model.ts       ← Modelo de usuario autenticado
│   │   ├── menu-item.model.ts     ← Modelo de ítems del menú
│   │   └── options.model.ts       ← Modelo de opciones de configuración
│   └── service/
│       ├── core.service.ts        ← Servicio de estado global (sesión, token)
│       └── http.service.ts        ← Servicio HTTP genérico (wrapper de HttpClient)
│
├── shared/                        ← Componentes y servicios reutilizables
│   ├── components/
│   │   ├── activities-table/      ← Tabla de actividades
│   │   ├── activity-info/         ← Detalle de actividad
│   │   ├── attendance-record/     ← Registro de asistencia (RFID / manual)
│   │   ├── data-visualization/    ← Gráficos con Chart.js
│   │   ├── upload-database/       ← Carga masiva desde Excel
│   │   ├── register-new-activity/ ← Formulario de nueva actividad
│   │   ├── edit-activity/         ← Edición de actividad existente
│   │   ├── change-password/       ← Cambio de contraseña
│   │   ├── external-participant/  ← Registro de participante externo
│   │   ├── filter-list/           ← Componente de filtros
│   │   ├── date-selector/         ← Selector de fechas
│   │   └── ...                    ← primary-button, separator, area-buttons, etc.
│   ├── model/                     ← 21 modelos de dominio (activity, area, student, employee, ...)
│   └── service/                   ← 11 servicios de negocio
│       ├── activity.service.ts
│       ├── area.service.ts
│       ├── department.service.ts
│       ├── excel-report.service.ts
│       ├── university-member.service.ts
│       ├── upload-database.service.ts
│       ├── user.service.ts
│       └── ...                    ← subarea, state, identification-type, user-type
│
├── feature/                       ← Módulos funcionales (lazy loaded)
│   ├── login/                     ← Autenticación de usuario
│   ├── home/                      ← Dashboard principal
│   │   ├── components/            ← Vistas del home (filtros, áreas, actividades)
│   │   └── modules/               ← Sub-módulos por área de bienestar
│   │       ├── bienestar-area/
│   │       ├── evangelizacion-area/
│   │       ├── hogar-area/
│   │       └── servicio-area/
│   ├── manage-department/         ← Gestión de dirección/áreas/subáreas
│   ├── manage-users/              ← Administración de usuarios y roles
│   ├── manage-indicators/         ← Gestión de indicadores (proyectos, acciones, PDI)
│   └── password-recovery/         ← Recuperación de contraseña (envío por email)
│
└── app-routing.module.ts          ← Rutas principales con lazy loading y guards
```

## Rutas de la Aplicación

| Ruta | Módulo | Guard | Descripción |
|------|--------|-------|-------------|
| `/login` | LoginModule | `publicRouteGuard` | Inicio de sesión |
| `/home` | HomeModule | `securityGuard` | Dashboard principal |
| `/gestionar-direccion` | ManageDepartmentModule | `securityGuard` | Gestión organizacional |
| `/gestionar-usuarios` | ManageUsersModule | `securityGuard` | Administración de usuarios |
| `/gestionar-indicadores` | ManageIndicatorsModule | `securityGuard` | Indicadores y métricas |
| `/recuperar-contrasena` | PasswordRecoveryModule | `publicRouteGuard` | Recuperación de contraseña |

## Scripts Disponibles

```bash
# Servidor de desarrollo con proxy al backend (abre navegador automáticamente)
npm start

# Build de producción
npm run build

# Ejecutar pruebas unitarias (headless, con cobertura)
npm test

# Build con estadísticas para análisis de bundles
npm run build:stats

# Analizar tamaño de bundles
npm run analyze
```

## Inicio Rápido

```bash
cd SIBEFrontend

# Instalar dependencias
npm install

# Iniciar servidor de desarrollo (proxy automático al backend en :8080)
npm start
```

La aplicación estará disponible en `http://localhost:4200`. Las peticiones a `/api/*` se redirigen al backend en el puerto 8080 mediante `proxy.conf.json`.

> **Prerrequisito**: El backend debe estar corriendo en `http://localhost:8080`.

## Pruebas

```bash
# Ejecutar pruebas unitarias (ChromeHeadless, sin watch, con cobertura)
npm test

# Reporte de cobertura → coverage/index.html
```

Las pruebas utilizan **Karma 6.4** como test runner y **Jasmine 4.6** como framework de aserciones.

## Configuración de Entornos

| Archivo | `production` | `endpoint` |
|---------|-------------|-----------|
| `environment.ts` | `false` | `http://localhost:8080/api` |
| `environment.prod.ts` | `true` | URL del servidor de producción |

## Contacto y Soporte

Para preguntas sobre el sistema SIBE:
- **Email**: sibeapplicationuco@gmail.com
