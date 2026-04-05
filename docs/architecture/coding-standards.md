# SIBE - Estándares de Código 📝

## 📋 **Información General**

### Propósito del Documento

Este documento define los estándares de código obligatorios y recomendados para el desarrollo en SIBE (Sistema Integral de Bienestar Estudiantil). Estos estándares garantizan consistencia, legibilidad y mantenibilidad del código en todos los módulos del sistema.

**Audiencia**: Desarrolladores, Code Reviewers  
**Última Actualización**: 2026-03-15  
**Estado**: Activo

---

## 🚨 **Estándares Obligatorios**

### 1. Nomenclatura

#### 1.1 Backend (Java)

##### Variables y Métodos

```java
// ✅ CORRECTO - camelCase, nombres descriptivos en español
private UUID identificador;
private String correo;
private boolean estaActivo;

public ComandoRespuesta<UUID> ejecutar(UsuarioComando comando) { ... }
public Actividad consultarPorIdentificador(UUID identificador) { ... }
public static Usuario construir(UUID identificador, String correo, ...) { ... }

// ❌ INCORRECTO - abreviaciones, nombres en inglés mezclados sin razón
private UUID id;
private String mail;
private boolean active;

public ComandoRespuesta<UUID> exec(UsuarioComando cmd) { ... }
```

##### Clases por Capa

| Capa | Tipo | Sufijo/Patrón | Ejemplo |
|------|------|---------------|---------|
| **Dominio - Modelo** | Clase | Sin sufijo | `Usuario`, `Actividad`, `EjecucionActividad` |
| **Dominio - DTO** | Clase | `DTO` | `UsuarioDTO`, `ActividadDetalladaDTO` |
| **Dominio - Puerto Comando** | Interfaz | `RepositorioComando` | `AreaRepositorioComando` |
| **Dominio - Puerto Consulta** | Interfaz | `RepositorioConsulta` | `ActividadRepositorioConsulta` |
| **Dominio - Puerto Servicio** | Interfaz | `Servicio` / `Service` | `EncriptarClaveServicio` |
| **Dominio - Use Case Comando** | Clase | `AcciónNombreUseCase` | `GuardarUsuarioUseCase`, `ModificarActividadUseCase` |
| **Dominio - Use Case Consulta** | Clase | `ConsultarNombreUseCase` | `ConsultarUsuariosUseCase` |
| **Dominio - Regla** | Clase | `Regla` | `UsuarioRegla`, `ActividadRegla` |
| **Dominio - Motor Fábrica** | Clase | `MotorFabrica` | `UsuarioMotorFabrica` |
| **Dominio - Excepción** | Clase | `Excepcion` | `ValorObligatorioExcepcion`, `ValorDuplicadoExcepcion` |
| **Dominio - Constante** | Clase | `Constante` | `MensajesErrorConstante`, `ApiEndpointConstante` |
| **Dominio - Utilitario** | Clase | `Validador` / `Util` | `ValidadorTexto`, `UtilFecha`, `UtilUUID` |
| **Dominio - Enum** | Enum | `Tipo` (cuando aplica) | `TipoArea`, `TipoParticipante` |
| **Dominio - Service** | Clase | `Service` | `RegistrarParticipanteService` |
| **Aplicación - Comando** | Clase | `Comando` | `UsuarioComando`, `ActividadComando` |
| **Aplicación - Fábrica** | Clase | `Fabrica` | `UsuarioFabrica`, `ActividadFabrica` |
| **Aplicación - Manejador** | Clase | `Manejador` | `GuardarUsuarioManejador`, `ConsultarUsuariosManejador` |
| **Infraestructura - Entidad** | Clase | `Entidad` | `UsuarioEntidad`, `ActividadEntidad` |
| **Infraestructura - DAO** | Interfaz | `DAO` | `UsuarioDAO`, `ActividadDAO` |
| **Infraestructura - Mapeador** | Clase | `Mapeador` | `UsuarioMapeador`, `ActividadMapeador` |
| **Infraestructura - Impl. Comando** | Clase | `RepositorioComandoImplementacion` | `UsuarioRepositorioComandoImplementacion` (no abreviado) |
| **Infraestructura - Impl. Consulta** | Clase | `RepositorioConsultaImplementacion` | `ActividadRepositorioConsultaImplementacion` |
| **Infraestructura - Controlador Cmd** | Clase | `ComandoControlador` | `UsuarioComandoControlador` |
| **Infraestructura - Controlador Qry** | Clase | `ConsultaControlador` | `UsuarioConsultarControlador` |
| **Infraestructura - DataLoader** | Clase | `DataLoader` | `AreaDataLoader`, `TipoUsuarioDataLoader` |
| **Infraestructura - Bean Config** | Clase | `Bean` | `UseCaseBean`, `ServiceBean` |

##### Archivos y Paquetes

```
co.edu.uco.sibe/
├── dominio/
│   ├── dto/                          # Objetos de transferencia de datos
│   ├── enums/                        # Enumeraciones del dominio
│   ├── modelo/                       # Modelos de negocio (entidades de dominio)
│   ├── puerto/
│   │   ├── comando/                  # Interfaces de repositorios de escritura
│   │   ├── consulta/                 # Interfaces de repositorios de lectura
│   │   └── servicio/                 # Interfaces de servicios externos
│   ├── regla/                        # Motor de reglas de negocio
│   │   ├── fabrica/implementacion/   # Fábricas de motores de reglas
│   │   ├── implementacion/           # Implementaciones de reglas
│   │   └── motor/                    # Motor de ejecución de reglas
│   ├── service/                      # Servicios de dominio
│   ├── transversal/
│   │   ├── constante/                # Constantes globales
│   │   ├── excepcion/                # Excepciones personalizadas
│   │   └── utilitarios/              # Utilidades de validación y transformación
│   └── usecase/
│       ├── comando/                  # Casos de uso de escritura
│       └── consulta/                 # Casos de uso de lectura
├── aplicacion/
│   ├── comando/                      # Objetos comando (request)
│   │   ├── fabrica/                  # Fábricas de modelos desde comandos
│   │   └── manejador/                # Manejadores de comandos (orquestadores)
│   ├── consulta/                     # Manejadores de consulta
│   ├── puerto/servicio/              # Interfaces de servicios de aplicación
│   └── transversal/                  # Interfaces genéricas de manejadores
│       └── manejador/
└── infraestructura/
    ├── adaptador/
    │   ├── dao/                      # Interfaces Spring Data JPA
    │   ├── entidad/                  # Entidades JPA (@Entity)
    │   ├── mapeador/                 # Mapeadores Entidad ↔ Modelo
    │   ├── repositorio/
    │   │   ├── comando/              # Implementaciones repositorios de escritura
    │   │   └── consulta/             # Implementaciones repositorios de lectura
    │   └── servicio/                 # Implementaciones de servicios externos
    ├── configuracion/
    │   ├── bean/                     # Configuración Spring @Bean
    │   └── dataloader/               # Carga de datos iniciales
    │       └── fabrica/              # Fábricas de datos para DataLoaders
    ├── controlador/
    │   ├── comando/                  # Controladores REST de escritura
    │   └── consulta/                 # Controladores REST de lectura
    ├── error/                        # Manejo global de errores
    └── seguridad/
        ├── configuration/            # Configuración Spring Security
        └── filter/                   # Filtros de seguridad JWT
```

#### 1.2 Frontend (Angular / TypeScript)

##### Archivos

```bash
# ✅ CORRECTO - kebab-case para archivos
activity.service.ts
manage-users.component.ts
activity-execution.model.ts
login-routing.module.ts
security.guard.ts
auth-interceptor.ts

# ❌ INCORRECTO
ActivityService.ts
manageUsers.component.ts
activityExecution.model.ts
```

##### Clases, Interfaces y Variables

```typescript
// ✅ CORRECTO
// Componentes: PascalCase + sufijo Component
export class HeaderComponent { ... }
export class RegisterNewActivityComponent { ... }

// Servicios: PascalCase + sufijo Service
export class ActivityService extends HttpService { ... }
export class StateService { ... }

// Interfaces/modelos: PascalCase + sufijo descriptivo
export interface ActivityRequest { ... }
export interface ActivityResponse { ... }
export interface MenuItem { ... }

// Guards: camelCase funcional (Angular 16+)
export const securityGuard: CanActivateFn = (route, state) => { ... }
export const publicRouteGuard: CanActivateFn = (route, state) => { ... }

// Variables: camelCase
private readonly ACTIVITY_ENDPOINT = '/actividades';
public menuItems: MenuItem[] = [...];
userSession$!: Observable<UserSession | undefined>;

// ❌ INCORRECTO
export class header_component { ... }
export class activitySVC { ... }
export interface activityReq { ... }
```

##### Selectores de Componentes

```typescript
// ✅ CORRECTO - prefijo 'app-' + kebab-case
@Component({ selector: 'app-header' })
@Component({ selector: 'app-attendance-record' })
@Component({ selector: 'app-register-new-activity' })

// ❌ INCORRECTO
@Component({ selector: 'header' })
@Component({ selector: 'appAttendanceRecord' })
```

##### Estructura de Archivos y Directorios

```
src/app/
├── core/                              # Singleton: guards, interceptors, servicios base
│   ├── core.module.ts
│   ├── components/                    # Componentes de layout (header, footer)
│   │   ├── header/
│   │   │   ├── header.component.ts
│   │   │   ├── header.component.html
│   │   │   ├── header.component.scss
│   │   │   └── header.component.spec.ts
│   │   └── footer/
│   ├── guard/                         # Guards de autenticación y rutas
│   ├── interceptor/                   # Interceptores HTTP (auth, token, errores)
│   ├── model/                         # Modelos exclusivos del core
│   └── service/                       # HttpService base y CoreService
├── shared/                            # Reutilizable: componentes, modelos, servicios de datos
│   ├── shared.module.ts
│   ├── components/                    # Componentes reutilizables entre features
│   ├── model/                         # Interfaces/modelos compartidos
│   └── service/                       # Servicios de datos HTTP compartidos
└── feature/                           # Módulos lazy-loaded por dominio funcional
    ├── login/
    │   ├── login.module.ts
    │   ├── login-routing.module.ts
    │   ├── components/
    │   ├── model/                     # Modelos específicos del feature
    │   └── service/                   # Servicios específicos del feature
    ├── home/
    │   ├── home.module.ts
    │   ├── home-routing.module.ts
    │   ├── components/
    │   ├── model/
    │   └── modules/                   # Sub-módulos de áreas
    ├── manage-users/
    ├── manage-department/
    ├── manage-indicators/
    └── password-recovery/
```

---

### 2. Estructura de Código

#### 2.1 Backend - Modelos de Dominio

Los modelos de dominio deben seguir el patrón de constructor privado + fábrica estática `construir()`:

```java
// ✅ CORRECTO - Patrón obligatorio para modelos de dominio
@Getter
public class Usuario {
    private UUID identificador;
    private String correo;
    private String clave;
    private TipoUsuario tipoUsuario;
    private boolean estaActivo;

    // Constructor PRIVADO
    private Usuario(UUID identificador, String correo, String clave,
                    TipoUsuario tipoUsuario, boolean estaActivo) {
        this.identificador = identificador;
        this.correo = correo;
        this.clave = clave;
        this.tipoUsuario = tipoUsuario;
        this.estaActivo = estaActivo;
    }

    // Fábrica estática con validaciones
    public static Usuario construir(UUID identificador, String correo, String clave,
                                    TipoUsuario tipoUsuario, boolean estaActivo) {
        return new Usuario(
                identificador,
                obtenerTextoPorDefecto(correo),
                obtenerTextoPorDefecto(clave),
                obtenerObjetoPorDefecto(tipoUsuario, TipoUsuario.construir()),
                estaActivo
        );
    }

    // Fábrica de instancia vacía (valores por defecto)
    public static Usuario construir() {
        return new Usuario(
                UtilUUID.obtenerValorDefecto(),
                VACIO,
                VACIO,
                TipoUsuario.construir(),
                false
        );
    }
}

// ❌ INCORRECTO - Constructor público, sin validaciones
public class Usuario {
    public Usuario() { }
    public String correo;
}
```

#### 2.2 Backend - Manejadores (Handlers)

```java
// ✅ CORRECTO - Manejador implementa interfaz genérica, inyección por constructor
@Component
@AllArgsConstructor
public class GuardarUsuarioManejador
        implements ManejadorComandoRespuesta<UsuarioComando, ComandoRespuesta<UUID>> {

    private final UsuarioFabrica usuarioFabrica;
    private final PersonaFabrica personaFabrica;
    private final GuardarUsuarioUseCase guardarUsuarioUseCase;

    @Override
    public ComandoRespuesta<UUID> ejecutar(UsuarioComando comando) {
        var usuario = this.usuarioFabrica.construir(comando);
        var persona = this.personaFabrica.construir(comando);
        var area = UtilUUID.textoAUUID(comando.getArea().getArea());
        var tipoArea = TipoArea.valueOf(comando.getArea().getTipoArea());

        return new ComandoRespuesta<>(
                this.guardarUsuarioUseCase.ejecutar(usuario, persona, area, tipoArea)
        );
    }
}
```

#### 2.3 Backend - Controladores REST

```java
// ✅ CORRECTO - Controlador de Comando separado del de Consulta
@RestController
@AllArgsConstructor
@RequestMapping(USER_API)
public class UsuarioComandoControlador {
    private final GuardarUsuarioManejador guardarUsuarioManejador;
    private final ModificarUsuarioManejador modificarUsuarioManejador;

    @PreAuthorize(HAS_ADMIN_CREATE_AUTHORITY)
    @PostMapping
    public ComandoRespuesta<UUID> guardar(@RequestBody UsuarioComando usuario) {
        return this.guardarUsuarioManejador.ejecutar(usuario);
    }

    @PreAuthorize(HAS_USER_OR_ADMIN_UPDATE_AUTHORITY)
    @PutMapping(USUARIO_IDENTIFICADOR)
    public ComandoRespuesta<UUID> modificar(
            @RequestBody UsuarioModificacionComando usuario,
            @PathVariable(IDENTIFICADOR) UUID identificador) {
        return modificarUsuarioManejador.ejecutar(usuario, identificador);
    }
}

// ✅ CORRECTO - Controlador de Consulta separado
@RestController
@AllArgsConstructor
@RequestMapping(USER_API)
public class UsuarioConsultarControlador {
    private final ConsultarUsuariosManejador consultarUsuariosManejador;

    @PreAuthorize(HAS_USER_OR_AREA_ADMIN_OR_ADMIN_GET_AUTHORITY)
    @GetMapping()
    public List<UsuarioDTO> consultarTodos() {
        return consultarUsuariosManejador.ejecutar();
    }
}

// ❌ INCORRECTO - Mezclar comando y consulta en un solo controlador
@RestController
public class UsuarioControlador {
    @PostMapping
    public void guardar(...) { ... }
    @GetMapping
    public List<UsuarioDTO> consultarTodos() { ... }
}
```

#### 2.4 Backend - DTOs

```java
// ✅ CORRECTO - DTO con Lombok para getters/setters y constructores
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
    private String identificador;
    private String nombres;
    private String apellidos;
    private String correo;
    private IdentificacionDTO identificacion;
    private TipoUsuarioDTO tipoUsuario;
    private UsuarioAreaDTO area;
    private Boolean estaActivo;
}

// ❌ INCORRECTO - DTO sin Lombok, con lógica de negocio
public class UsuarioDTO {
    private String correo;
    public String getCorreo() { return correo; }
    public void setCorreo(String c) { this.correo = c; }
    public boolean esValido() { ... } // Lógica NO pertenece al DTO
}
```

#### 2.5 Backend - Implementaciones de Repositorio

```java
// ✅ CORRECTO - Implementación que usa DAO y Mapeador
@Repository
@AllArgsConstructor
public class AccionRepositorioComandoImplementacion implements AccionRepositorioComando {
    private final AccionDAO accionDAO;
    private final AccionMapeador accionMapeador;

    @Override
    public UUID guardar(Accion accion) {
        var entidad = accionMapeador.construirEntidad(accion);
        return this.accionDAO.save(entidad).getIdentificador();
    }

    @Override
    public UUID modificar(Accion accion, UUID identificador) {
        var entidad = accionDAO.findById(identificador).orElse(null);
        assert !esNulo(entidad);
        accionMapeador.actualizarEntidad(entidad, accion);
        return this.accionDAO.save(entidad).getIdentificador();
    }
}
```

#### 2.6 Backend - Configuración de Use Cases como Beans

```java
// ✅ CORRECTO - Use Cases son POJOs sin anotaciones Spring
// Se registran explícitamente como @Bean en la configuración
@Configuration
public class UseCaseBean {
    @Bean
    public GuardarUsuarioUseCase agregarNuevoUsuarioUseCase(
            PersonaRepositorioComando personaRepositorioComando,
            PersonaRepositorioConsulta personaRepositorioConsulta,
            EncriptarClaveServicio encriptarClaveServicio,
            VincularUsuarioConAreaService vincularUsuarioConAreaService) {
        return new GuardarUsuarioUseCase(
                personaRepositorioComando,
                personaRepositorioConsulta,
                encriptarClaveServicio,
                vincularUsuarioConAreaService
        );
    }
}

// ❌ INCORRECTO - Use Case con @Service (acopla dominio a Spring)
@Service
public class GuardarUsuarioUseCase { ... }
```

#### 2.7 Frontend - Servicios HTTP

```typescript
// ✅ CORRECTO - Servicio extiende HttpService, endpoints como constantes readonly
@Injectable({
  providedIn: 'root'
})
export class ActivityService extends HttpService {
  private readonly ACTIVITY_ENDPOINT = '/actividades';
  private readonly ACTIVITY_AREA_ENDPOINT = '/actividades/area';

  constructor(http: HttpClient) {
    super(http);
  }

  agregarNuevaActividad(actividad: ActivityRequest): Observable<Response<string>> {
    const opts = this.createDefaultOptions();
    const url = `${environment.endpoint}${this.ACTIVITY_ENDPOINT}`;
    return this.doPost<ActivityRequest, Response<string>>(url, actividad, opts);
  }

  consultarPorArea(identificador: string): Observable<ActivityResponse[]> {
    const opts = this.createDefaultOptions();
    const url = `${environment.endpoint}${this.ACTIVITY_AREA_ENDPOINT}/${identificador}`;
    return this.doGet<ActivityResponse[]>(url, opts);
  }
}

// ❌ INCORRECTO - URLs hardcodeadas, sin HttpService base
@Injectable({ providedIn: 'root' })
export class ActivityService {
  getActivities() {
    return this.http.get('http://localhost:8080/api/actividades');
  }
}
```

#### 2.8 Frontend - Modelos e Interfaces

```typescript
// ✅ CORRECTO - Interfaces con sufijo Request/Response según uso
export interface ActivityRequest {
  nombre: string;
  objetivo: string;
  semestre: string;
  indicador: string;
  area: AreaRequest;
  fechasProgramada: FechaProgramadaRequest[];
}

export interface ActivityResponse {
  identificador: string;
  nombre: string;
  objetivo: string;
  semestre: string;
  indicador: IndicatorResponse;
  colaborador: string;
}

// ✅ CORRECTO - Modelo simple
export interface MenuItem {
  url: string;
  nombre: string;
}

// ❌ INCORRECTO - Sin tipado, propiedades any
export interface Activity {
  data: any;
  info: any;
}
```

#### 2.9 Frontend - Módulos Feature (Lazy Loading)

```typescript
// ✅ CORRECTO - Módulos lazy-loaded con routing separado
// login.module.ts
@NgModule({
  declarations: [LoginComponent],
  imports: [CommonModule, LoginRoutingModule, SharedModule, FormsModule]
})
export class LoginModule { }

// login-routing.module.ts
const routes: Routes = [
  { path: '', component: LoginComponent }
];
@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class LoginRoutingModule { }

// app-routing.module.ts (carga lazy)
const routes: Routes = [
  {
    path: 'login',
    loadChildren: () => import('./feature/login/login.module').then(m => m.LoginModule),
    canActivate: [publicRouteGuard]
  }
];
```

#### 2.10 Frontend - Gestión de Estado

```typescript
// ✅ CORRECTO - StateService centralizado con BehaviorSubject
@Injectable({ providedIn: 'root' })
export class StateService {
  private stateProps: Map<StateProps, any> = new Map();
  private state = new BehaviorSubject<Map<StateProps, any>>(new Map());

  updateState(prop: StateProps, value: any): void {
    this.stateProps.set(prop, value);
    this.state.next(this.stateProps);
  }

  select<T>(prop: StateProps): Observable<T | undefined> {
    return this.state.asObservable().pipe(
      map(stateMap => stateMap.get(prop) as T | undefined),
      distinctUntilChanged()
    );
  }
}

// Uso en componentes
this.userSession$ = this.stateService.select<UserSession>(StateProps.USER_SESSION);
this.isLogged$ = this.userSession$.pipe(map(session => !!session && session.logged));
```

---

### 3. Organización de Imports

#### 3.1 Backend (Java) - Orden de Imports

```java
// ✅ CORRECTO - Orden de imports
// 1. Imports del mismo proyecto (co.edu.uco.sibe.*)
import co.edu.uco.sibe.dominio.modelo.Usuario;
import co.edu.uco.sibe.dominio.puerto.comando.UsuarioRepositorioComando;
import co.edu.uco.sibe.aplicacion.transversal.ComandoRespuesta;

// 2. Imports de Spring Framework
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

// 3. Imports de Lombok
import lombok.AllArgsConstructor;
import lombok.Getter;

// 4. Imports de Java estándar
import java.util.UUID;
import java.util.List;

// 5. Imports static (al final o agrupados)
import static co.edu.uco.sibe.dominio.transversal.constante.TextoConstante.VACIO;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorTexto.obtenerTextoPorDefecto;
```

#### 3.2 Frontend (TypeScript) - Orden de Imports

```typescript
// ✅ CORRECTO - Orden de imports
// 1. Angular core y módulos
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

// 2. Librerías externas (RxJS, Bootstrap, etc.)
import { Observable, BehaviorSubject } from 'rxjs';
import { map, distinctUntilChanged } from 'rxjs/operators';

// 3. Core (guards, interceptors, servicios base)
import { HttpService } from 'src/app/core/service/http.service';

// 4. Shared (modelos, servicios compartidos)
import { StateService } from 'src/app/shared/service/state.service';
import { ActivityResponse } from 'src/app/shared/model/activity.model';

// 5. Feature (modelos y servicios del mismo módulo)
import { LoginService } from '../service/login.service';

// 6. Environments
import { environment } from 'src/environments/environment';
```

---

### 4. Restricciones Arquitectónicas de Codificación

#### 4.1 Backend - Arquitectura Hexagonal + CQRS

##### Dirección de Dependencias (OBLIGATORIO)

```
infraestructura → aplicación → dominio
         ↓              ↓           ↑
    (implementa)   (orquesta)   (NUNCA depende de nadie)
```

```java
// ✅ CORRECTO - Dominio NO importa nada de Spring ni de infraestructura
package co.edu.uco.sibe.dominio.modelo;

import lombok.Getter;  // Solo Lombok permitido como excepción
import java.util.UUID; // Solo Java estándar

@Getter
public class Usuario {
    // Sin @Entity, sin @Repository, sin @Service
}

// ✅ CORRECTO - Los puertos son interfaces en el dominio
package co.edu.uco.sibe.dominio.puerto.comando;

public interface AreaRepositorioComando {
    UUID guardar(Area area);
}

// ✅ CORRECTO - Los Use Cases son POJOs sin anotaciones Spring
package co.edu.uco.sibe.dominio.usecase.comando;

public class GuardarUsuarioUseCase {
    private final PersonaRepositorioComando personaRepositorioComando;
    // ... inyección por constructor manual (no Spring)
}

// ❌ INCORRECTO - Dominio importando Spring
package co.edu.uco.sibe.dominio.modelo;
import org.springframework.stereotype.Service;  // PROHIBIDO
import jakarta.persistence.Entity;                // PROHIBIDO
```

##### Separación CQRS (OBLIGATORIO)

| Operación | Puerto | Manejador | Controlador | Use Case |
|-----------|--------|-----------|-------------|----------|
| **Escritura** | `XRepositorioComando` | `XManejador` en `aplicacion/comando/manejador/` | `XComandoControlador` | `XUseCase` en `dominio/usecase/comando/` |
| **Lectura** | `XRepositorioConsulta` | `XManejador` en `aplicacion/consulta/` | `XConsultaControlador` | `XUseCase` en `dominio/usecase/consulta/` |

```java
// ❌ INCORRECTO - Mezclar lectura y escritura en un mismo repositorio
public interface UsuarioRepositorio {
    UUID guardar(Usuario usuario);               // comando
    List<UsuarioDTO> consultarTodos();            // consulta
}

// ✅ CORRECTO - Separar en dos interfaces
public interface UsuarioRepositorioComando {
    UUID guardar(Usuario usuario);
}

public interface UsuarioRepositorioConsulta {
    List<UsuarioDTO> consultarTodos();
}
```

##### Registro de Use Cases y Services (OBLIGATORIO)

```java
// ✅ CORRECTO - Use Cases y Domain Services se registran como @Bean
// en infraestructura/configuracion/bean/
@Configuration
public class UseCaseBean {
    @Bean
    public GuardarAccionUseCase agregarNuevaAccionUseCase(
            AccionRepositorioComando accionRepositorioComando,
            AccionRepositorioConsulta accionRepositorioConsulta) {
        return new GuardarAccionUseCase(accionRepositorioComando, accionRepositorioConsulta);
    }
}

@Configuration
public class ServiceBean {
    @Bean
    public VincularUsuarioConAreaService vincularUsuarioConAreaService(
            UsuarioOrganizacionComando usuarioOrganizacionComando) {
        return new VincularUsuarioConAreaService(usuarioOrganizacionComando);
    }
}
```

#### 4.2 Frontend - Arquitectura Core-Feature-Shared

##### Reglas de Dependencia (OBLIGATORIO)

| Módulo | Puede importar | NO puede importar |
|--------|----------------|-------------------|
| **Core** | Shared | Feature modules |
| **Shared** | Nada (autónomo) | Core, Feature modules |
| **Feature** | Core, Shared | Otros Feature modules |

```typescript
// ✅ CORRECTO - Feature importa Shared y Core
@NgModule({
  imports: [CommonModule, LoginRoutingModule, SharedModule, FormsModule]
})
export class LoginModule { }

// ❌ INCORRECTO - Feature importa otro Feature
@NgModule({
  imports: [ManageUsersModule] // PROHIBIDO: Feature → Feature
})
export class HomeModule { }
```

##### Core Module - Reglas

- Se importa **una sola vez** en `AppModule`
- Contiene: guards, interceptors, `HttpService` base, componentes de layout
- Los interceptores HTTP se registran aquí con `HTTP_INTERCEPTORS`
- El `ErrorHandler` global se registra aquí

##### Shared Module - Reglas

- Se importa en **cada Feature Module** que lo necesite
- Contiene: componentes reutilizables, modelos/interfaces compartidos, servicios de datos
- Los servicios usan `providedIn: 'root'` (singleton automático)
- NO debe tener lógica de negocio específica de un feature

##### Feature Modules - Reglas

- Cada módulo de feature usa **lazy loading** via `loadChildren`
- Cada feature tiene su propio routing module (`*-routing.module.ts`)
- Modelos y servicios específicos del feature se colocan dentro del feature
- Sub-módulos permitidos para features complejos (ej: `home/modules/bienestar-area/`)

---

### 5. Pruebas Unitarias Obligatorias

> **Estado actual**: No hay pruebas unitarias implementadas. La infraestructura de testing está configurada:
> - **Backend**: JUnit Platform (Spring Boot Starter Test)
> - **Frontend**: Jasmine + Karma
>
> Los siguientes estándares son **obligatorios** para toda nueva prueba unitaria que se implemente.

#### 5.1 Uso obligatorio de Test Data Builder y patrón 3A (Arrange-Act-Assert)

##### Backend (Java - JUnit)

```java
// ✅ OBLIGATORIO - Test Data Builder + Patrón 3A
class GuardarUsuarioUseCaseTest {

    @Test
    void deberiaGuardarUsuarioCorrectamente() {
        // Arrange
        var usuario = new UsuarioTestDataBuilder()
                .conCorreo("test@universidad.edu.co")
                .conTipoUsuario(TipoUsuario.construir(uuid, "ADMIN", "Administrador"))
                .conEstaActivo(true)
                .construir();

        when(personaRepositorioConsulta.consultarPorCorreo(anyString())).thenReturn(null);
        when(personaRepositorioComando.guardar(any())).thenReturn(uuid);

        // Act
        var resultado = guardarUsuarioUseCase.ejecutar(usuario, persona, area, tipoArea);

        // Assert
        assertNotNull(resultado);
        verify(personaRepositorioComando).guardar(any());
    }
}

// Ejemplo de Test Data Builder
public class UsuarioTestDataBuilder {
    private UUID identificador = UtilUUID.obtenerValorDefecto();
    private String correo = "default@test.com";
    private String clave = "clave123";
    private TipoUsuario tipoUsuario = TipoUsuario.construir();
    private boolean estaActivo = true;

    public UsuarioTestDataBuilder conCorreo(String correo) {
        this.correo = correo;
        return this;
    }

    public UsuarioTestDataBuilder conTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
        return this;
    }

    public UsuarioTestDataBuilder conEstaActivo(boolean estaActivo) {
        this.estaActivo = estaActivo;
        return this;
    }

    public Usuario construir() {
        return Usuario.construir(identificador, correo, clave, tipoUsuario, estaActivo);
    }
}

// ❌ INCORRECTO - Sin Test Data Builder, sin patrón 3A claro
@Test
void testGuardar() {
    var u = Usuario.construir(UUID.randomUUID(), "a@b.com", "123", TipoUsuario.construir(), true);
    assertNotNull(useCase.ejecutar(u, p, a, t));
}
```

##### Frontend (TypeScript - Jasmine)

```typescript
// ✅ OBLIGATORIO - Test Data Builder + Patrón 3A
describe('ActivityService', () => {
  it('debería agregar nueva actividad correctamente', () => {
    // Arrange
    const actividad = new ActivityRequestTestDataBuilder()
      .conNombre('Taller de Bienestar')
      .conSemestre('2026-1')
      .conArea({ area: 'uuid-area', tipoArea: 'SUBAREA' })
      .construir();

    // Act
    service.agregarNuevaActividad(actividad).subscribe(response => {
      // Assert
      expect(response).toBeTruthy();
      expect(response.valor).toBeDefined();
    });

    const req = httpMock.expectOne(`${environment.endpoint}/actividades`);
    expect(req.request.method).toBe('POST');
    req.flush({ valor: 'uuid-generado' });
  });
});

// Ejemplo de Test Data Builder
class ActivityRequestTestDataBuilder {
  private nombre = 'Actividad Default';
  private objetivo = 'Objetivo Default';
  private semestre = '2026-1';
  private indicador = 'uuid-indicador';
  private area: AreaRequest = { area: 'uuid-area', tipoArea: 'AREA' };
  private fechasProgramada: FechaProgramadaRequest[] = [];

  conNombre(nombre: string): this {
    this.nombre = nombre;
    return this;
  }

  conSemestre(semestre: string): this {
    this.semestre = semestre;
    return this;
  }

  conArea(area: AreaRequest): this {
    this.area = area;
    return this;
  }

  construir(): ActivityRequest {
    return {
      nombre: this.nombre,
      objetivo: this.objetivo,
      semestre: this.semestre,
      indicador: this.indicador,
      area: this.area,
      fechasProgramada: this.fechasProgramada
    };
  }
}

// ❌ INCORRECTO - Datos inline, sin estructura 3A
it('should work', () => {
  service.agregarNuevaActividad({ nombre: 'x', objetivo: 'y' } as any).subscribe(r => expect(r).toBeTruthy());
});
```

#### 5.2 Convenciones de Naming para Tests

```java
// Backend - Nombres de test en español, descriptivos
@Test
void deberiaGuardarUsuarioCorrectamente() { ... }

@Test
void deberiaLanzarExcepcionCuandoCorreoEsDuplicado() { ... }

@Test
void deberiaConsultarActividadesPorAreaExitosamente() { ... }
```

```typescript
// Frontend - describe + it en español, descriptivos
describe('LoginComponent', () => {
  it('debería iniciar sesión con credenciales válidas', () => { ... });
  it('debería mostrar error cuando el correo es inválido', () => { ... });
  it('debería redirigir a home después del login exitoso', () => { ... });
});
```

---

## 💡 **Convenciones Recomendadas**

### 1. Backend - Uso de Lombok

| Anotación | Uso Apropiado | Ejemplo |
|-----------|---------------|---------|
| `@Getter` | Modelos de dominio, entidades, DTOs | Casi todas las clases |
| `@Setter` | Solo DTOs y Entidades JPA | `@Setter` en `UsuarioDTO` |
| `@AllArgsConstructor` | Inyección de dependencias en componentes | Manejadores, controladores, repositorios |
| `@NoArgsConstructor` | Solo DTOs y Entidades JPA | `@NoArgsConstructor` en `UsuarioDTO` |

```java
// ✅ RECOMENDADO - Modelo: solo @Getter
@Getter
public class Usuario { ... }

// ✅ RECOMENDADO - DTO: @Getter + @Setter + constructores
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class UsuarioDTO { ... }

// ✅ RECOMENDADO - Componente: @AllArgsConstructor para inyección
@Component
@AllArgsConstructor
public class GuardarUsuarioManejador { ... }
```

### 2. Backend - Uso de `var` (Type Inference)

```java
// ✅ RECOMENDADO - var cuando el tipo es evidente por contexto
var entidad = actividadMapeador.construirEntidad(actividad);
var usuario = this.usuarioFabrica.construir(comando);

// 👌 ACEPTABLE - Tipo explícito para tipos complejos o cuando mejora legibilidad
List<ActividadDTO> actividades = actividadMapeador.construirDTOs(entidades);
```

### 3. Backend - Constantes Centralizadas

```java
// ✅ RECOMENDADO - Constantes en clases dedicadas por dominio
public class ApiEndpointConstante {
    public static final String USER_API = "/usuarios";
    public static final String LOGIN_API = "/login";
}

public class MensajesErrorConstante {
    public static final String VALOR_OBLIGATORIO = "El valor es obligatorio";
}

public class SeguridadConstante {
    public static final String HAS_ADMIN_CREATE_AUTHORITY = "hasAuthority('ADMIN_CREATE')";
}
```

### 4. Frontend - Observables y Async Pipe

```typescript
// ✅ RECOMENDADO - Declarar observables con $ y usar async pipe en templates
userSession$!: Observable<UserSession | undefined>;
isLogged$!: Observable<boolean>;

// En template
<ng-container *ngIf="isLogged$ | async as isLogged">
  <span *ngIf="userSession$ | async as userSession">{{ userSession.correo }}</span>
</ng-container>
```

### 5. Frontend - Estilos SCSS

```scss
// ✅ RECOMENDADO - Usar variables centralizadas
@use 'src/assets/scss/modules/_var.scss' as *;

.header {
  background-color: $primary-color;
}

.header-text {
  color: $white-color;
  font-size: $font-general-size;
  transition: color 0.3s ease;

  &:hover {
    color: $secondary-color;
  }
}
```

### 6. Frontend - Endpoints como Constantes Readonly

```typescript
// ✅ RECOMENDADO - Endpoints como propiedades private readonly
@Injectable({ providedIn: 'root' })
export class AreaService extends HttpService {
  private readonly AREA_ENDPOINT = '/areas';
  private readonly AREA_NAME_ENDPOINT = '/areas/nombre';
  private readonly AREA_DETAIL_ENDPOINT = '/areas/detalle';
}
```

---

## 🔧 **Configuración de Herramientas**

### EditorConfig (Frontend)

Configuración actual en `.editorconfig`:

```ini
root = true

[*]
charset = utf-8
indent_style = space
indent_size = 2
insert_final_newline = true
trim_trailing_whitespace = true

[*.ts]
quote_type = single

[*.md]
max_line_length = off
trim_trailing_whitespace = false
```

### TypeScript Strict Mode (Frontend)

Configuración actual de `tsconfig.json` con modo estricto habilitado:

```json
{
  "compilerOptions": {
    "strict": true,
    "noImplicitOverride": true,
    "noPropertyAccessFromIndexSignature": true,
    "noImplicitReturns": true,
    "noFallthroughCasesInSwitch": true,
    "target": "ES2022",
    "module": "ES2022"
  },
  "angularCompilerOptions": {
    "strictInjectionParameters": true,
    "strictInputAccessModifiers": true,
    "strictTemplates": true
  }
}
```

### Gradle (Backend)

```groovy
plugins {
    id 'java'
    id 'org.springframework.boot' version '3.5.0'
    id 'io.spring.dependency-management' version '1.1.7'
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

tasks.named('test') {
    useJUnitPlatform()
}
```

### Scripts de Desarrollo (Frontend)

```json
{
  "scripts": {
    "start": "ng serve --proxy-config proxy.conf.json -o",
    "build": "ng build",
    "test": "ng test --browsers=ChromeHeadless --watch=false --code-coverage",
    "build:stats": "ng build --configuration production --stats-json",
    "analyze": "webpack-bundle-analyzer dist/app-base/stats.json"
  }
}
```

### Herramientas Pendientes de Configurar

> **Nota**: Actualmente el proyecto NO tiene configuradas herramientas de análisis estático. Se recomienda agregar:
>
> **Backend**:
> - Checkstyle o SpotBugs para análisis estático de Java
> - Spotless para formateo automático
>
> **Frontend**:
> - ESLint con `@angular-eslint` para linting de TypeScript y templates
> - Prettier para formateo consistente

---

## 🚀 **Mejores Prácticas Específicas**

### Java 17 + Spring Boot 3.5.0

1. **Usar `var`** para variables locales cuando el tipo es evidente
2. **Records** para DTOs inmutables (considerar migración futura)
3. **`@Transactional(readOnly = true)`** en repositorios de consulta
4. **Endpoints con constantes** en lugar de strings hardcodeados
5. **`@PreAuthorize`** en controladores para seguridad declarativa
6. **Puertos como interfaces** en el dominio, implementaciones en infraestructura
7. **Nunca exponer entidades JPA** fuera de la capa de infraestructura
8. **Use Cases como POJOs** registrados como `@Bean`, no como `@Service`

### Angular 16 + TypeScript 5.1

1. **Lazy loading** para todos los Feature Modules
2. **Guards funcionales** (Angular 16+) en lugar de guards basados en clases
3. **`providedIn: 'root'`** para servicios singleton
4. **Async pipe** en templates en lugar de `subscribe()` manual
5. **Observables con sufijo `$`** para identificación visual
6. **SCSS con variables** centralizadas, no colores hardcodeados
7. **HttpService base** como capa de abstracción HTTP
8. **Interfaces** para modelos request/response con tipado fuerte

---

## 📚 **Referencias y Recursos**

### Documentación Oficial

- [Spring Boot Reference](https://docs.spring.io/spring-boot/docs/3.5.0/reference/html/)
- [Angular 16 Documentation](https://angular.io/docs)
- [TypeScript Handbook](https://www.typescriptlang.org/docs/)
- [Lombok Documentation](https://projectlombok.org/features/)

### Herramientas Configuradas

- **Build Backend**: Gradle 8.13
- **Build Frontend**: Angular CLI 16.2.13
- **Testing Backend**: JUnit Platform (Spring Boot Starter Test)
- **Testing Frontend**: Jasmine 4.6 + Karma 6.4
- **Estilos**: SCSS con Bootstrap 5.3.6 + Bootstrap Icons
- **Proxy Dev**: `proxy.conf.json` → `http://localhost:8080`

---

**NOTA IMPORTANTE**: Estos estándares fueron generados analizando el código base existente del proyecto SIBE y las prácticas actuales del equipo. Deben mantenerse alineados con las convenciones reales de codificación del proyecto y revisarse periódicamente.

---

_Documento generado con Método Ceiba - Arquitecto_  
_Última actualización: 2026-03-15_  
_Versión: 1.0_
