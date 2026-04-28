# SIBE - Guía de Despliegue con Docker

## Prerrequisitos

- **Docker** >= 20.10
- **Docker Compose** >= 2.0
- **Git**

No se requiere instalar Java, Node.js, PostgreSQL ni ninguna otra dependencia localmente. Docker se encarga de todo.

---

## 1. Clonar el repositorio

```bash
git clone <URL_DEL_REPOSITORIO> SIBE
cd SIBE
```

**Rama recomendada:** `main` (o la que el equipo defina como estable).

---

## 2. Configurar variables de entorno (opcional)

El sistema funciona con valores por defecto sin necesidad de configuración adicional. Si se desea personalizar:

```bash
cp .env.example .env
# Editar .env con los valores deseados
```

### Variables disponibles

| Variable | Descripción | Valor por defecto |
|---|---|---|
| `POSTGRES_DB` | Nombre de la base de datos | `sibe_db2` |
| `DB_USERNAME` | Usuario de PostgreSQL | `postgres` |
| `DB_PASSWORD` | Contraseña de PostgreSQL | `admin1234` |
| `DB_PORT` | Puerto externo de PostgreSQL | `5432` |
| `BACKEND_PORT` | Puerto externo del backend | `8080` |
| `DDL_AUTO` | Estrategia Hibernate DDL | `update` |
| `CORS_ALLOWED_ORIGINS` | Orígenes CORS permitidos (separados por coma) | `http://localhost:4200,http://localhost` |
| `FRONTEND_PORT` | Puerto externo del frontend | `80` |
| `MAIL_HOST` | Servidor SMTP | `smtp.gmail.com` |
| `MAIL_PORT` | Puerto SMTP | `587` |
| `MAIL_USERNAME` | Usuario SMTP | `sibeapplicationuco@gmail.com` |
| `MAIL_PASSWORD` | Contraseña SMTP | *(configurada)* |

---

## 3. Construir y levantar los servicios

```bash
docker compose up --build -d
```

Esto levanta 3 contenedores:
- **sibe-db** — PostgreSQL 16
- **sibe-backend** — Spring Boot 3 (Java 17)
- **sibe-frontend** — Angular 16 servido por Nginx

### Primera ejecución

Al iniciar por primera vez contra una base de datos vacía:
1. **Hibernate** crea automáticamente todas las tablas (`ddl-auto=update`).
2. Los **DataLoaders** insertan automáticamente los datos base del sistema (tipos de usuario, tipos de identificación, direcciones, áreas, subareas, usuario administrador, etc.).

No se requiere ejecutar scripts SQL manualmente.

---

## 4. Verificar el despliegue

```bash
# Ver estado de los contenedores
docker compose ps

# Ver logs del backend (útil para verificar DataLoaders en primera ejecución)
docker compose logs -f backend

# Ver logs de todos los servicios
docker compose logs -f
```

### URLs de acceso

| Servicio | URL |
|---|---|
| **Aplicación (Frontend)** | `http://localhost` (o el puerto configurado en `FRONTEND_PORT`) |
| **API Backend (directo)** | `http://localhost:8080/api` |
| **PostgreSQL** | `localhost:5432` |

---

## 5. Ubicación de la configuración de base de datos

**Archivo:** `SIBEBackend/src/main/resources/application.properties`

Las propiedades de conexión relevantes son:
```properties
spring.datasource.url=${DB_URL:jdbc:postgresql://localhost:5432/sibe_db2}
spring.datasource.username=${DB_USERNAME:postgres}
spring.datasource.password=${DB_PASSWORD:admin1234}
```

En el despliegue Docker, estas propiedades se sobreescriben automáticamente a través de las variables de entorno definidas en `docker-compose.yml`.

---

## 6. Comandos útiles

```bash
# Detener los servicios
docker compose down

# Detener y eliminar volúmenes (BORRA LA BASE DE DATOS)
docker compose down -v

# Reconstruir solo el backend
docker compose up --build -d backend

# Reconstruir solo el frontend
docker compose up --build -d frontend

# Acceder a la consola de PostgreSQL
docker compose exec db psql -U postgres -d sibe_db2
```

---

## 7. Arquitectura del despliegue

```
┌─────────────────┐     ┌──────────────────┐     ┌──────────────┐
│   Browser        │────▶│  Nginx (Frontend)│────▶│  Spring Boot │
│                  │     │  Puerto: 80      │     │  Puerto: 8080│
│                  │     │  Angular 16 SPA  │     │  API REST    │
└─────────────────┘     └──────────────────┘     └──────┬───────┘
                          Sirve archivos                  │
                          estáticos +                     │
                          proxy /api → backend            │
                                                   ┌─────▼──────┐
                                                   │ PostgreSQL  │
                                                   │ Puerto: 5432│
                                                   └─────────────┘
```

- **Nginx** sirve los archivos estáticos de Angular y actúa como proxy reverso, redirigiendo las peticiones `/api/*` al contenedor backend.
- **Spring Boot** expone la API REST en `/api` y se conecta a PostgreSQL.
- **PostgreSQL** almacena todos los datos. Los datos persisten en un volumen Docker (`sibe-pgdata`).

---

## 8. Consideraciones para producción

Cuando el equipo de infraestructura desee llevar el sistema a un ambiente productivo, se recomienda:

1. **Cambiar todas las credenciales** en el archivo `.env` (base de datos, SMTP, etc.).
2. **Configurar `DDL_AUTO=validate`** para evitar modificaciones automáticas al esquema en producción.
3. **Ajustar `CORS_ALLOWED_ORIGINS`** al dominio real de producción.
4. **Configurar HTTPS** mediante un reverse proxy externo (Traefik, Nginx, etc.) o directamente en el Nginx del frontend.
5. **Respaldar el volumen de PostgreSQL** (`sibe-pgdata`) periódicamente.
