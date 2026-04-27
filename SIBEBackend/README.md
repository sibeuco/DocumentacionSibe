# SIBE (Backend)

**SIBE (Sistema Integral de Bienestar Estudiantil)** es un aplicativo web desarrollado para la Vicerrectoría de Evangelización y Bienestar de la Universidad Católica de Oriente, que permite:

- **Gestionar actividades**: Agendar, organizar y hacer seguimiento a actividades de bienestar estudiantil
- **Registro de asistencia**: Mediante número de identificación o lector RFID usando carnet universitario
- **Carga masiva de participantes**: Importación eficiente de estudiantes y empleados
- **Generación automática de reportes**: Para auditorías y toma de decisiones
- **Eliminación de procesos manuales**: Automatización completa eliminando uso de papel

**Ventaja competitiva vs. Google Forms, CheckPoint y AccuClass**: Proceso rápido y seguro de registro de asistencia con carnet universitario RFID.
 
## Especificaciones del Proyecto

- **Función**: API REST, lógica de negocio, persistencia y seguridad
- **Patrón arquitectónico**: Hexagonal (Puertos y Adaptadores) + CQRS

**Estructura hexagonal:**
- `dominio/` - Modelos de negocio, reglas, casos de uso
- `aplicacion/` - Comandos (write), Consultas (read), manejadores
- `infraestructura/` - Controladores REST, adaptadores JPA, configuración, seguridad

**Dominios de negocio identificados:**
- Usuarios y Autenticación
- Participantes (Estudiantes, Empleados, Internos, Externos)
- Actividades y Ejecución de Actividades
- Proyectos, Acciones e Indicadores
- Organización (Áreas, Subareas, Direcciones)
- Registro de Asistencia
- Carga Masiva (Excel)

### Tecnologías Principales Identificadas

**Backend:**
- **Lenguaje**: Java 17
- **Framework**: Spring Boot 3.5.0
- **Build Tool**: Gradle
- **Persistencia**: Spring Data JPA, Hibernate
- **Base de datos**: PostgreSQL (producción), H2 (desarrollo/testing)
- **Seguridad**: Spring Security, JWT (io.jsonwebtoken 0.11.2)
- **Email**: Spring Mail (SMTP Gmail)
- **Utilidades**: Lombok, Apache POI (Excel 5.4.0)
- **Testing**: JUnit Platform, Spring Security Test

### Patrones Arquitectónicos

1. **Arquitectura Hexagonal**
   - **Ubicación**: `SIBEBackend/src/main/java/co/edu/uco/sibe/`
   - **Descripción**: Separación clara entre dominio (core), aplicación (use cases) e infraestructura (adaptadores)
   - **Puertos**: Interfaces en `dominio/puerto/` y `aplicacion/puerto/`
   - **Adaptadores**: Implementaciones en `infraestructura/adaptador/`

2. **CQRS (Command Query Responsibility Segregation)**
   - **Ubicación**: `SIBEBackend/src/main/java/co/edu/uco/sibe/aplicacion/`
   - **Descripción**: Separación entre comandos (escritura) y consultas (lectura)
   - **Comandos**: `aplicacion/comando/` - Operaciones que modifican estado
   - **Consultas**: `aplicacion/consulta/` - Operaciones de solo lectura

4. **Repository Pattern**
   - **Ubicación**: `SIBEBackend/src/main/java/co/edu/uco/sibe/infraestructura/adaptador/repositorio/`
   - **Descripción**: Abstracción de acceso a datos con Spring Data JPA

5. **DTO Pattern**
   - **Ubicación**: `SIBEBackend/src/main/java/co/edu/uco/sibe/dominio/dto/`
   - **Descripción**: Objetos de transferencia de datos para comunicación entre capas


### Base de Datos

- **PostgreSQL**: `jdbc:postgresql://localhost:5432/sibe_db2`
  - **Usuario**: postgres
  - **Esquema**: `sibe_db2`
  - **DDL**: Auto-generado (`spring.jpa.hibernate.ddl-auto = update`)
  - **Repositorios que la usan**: SIBEBackend

## 📞 **Contacto y Soporte**

Para preguntas sobre esta arquitectura o el sistema SIBE:
- **Email**: sibeapplicationuco@gmail.com