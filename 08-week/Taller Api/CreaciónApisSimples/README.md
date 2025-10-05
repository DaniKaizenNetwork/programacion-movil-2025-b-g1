# API REST de Gestión de Usuarios

Una API REST completa desarrollada con Spring Boot para la gestión de usuarios, implementando autenticación JWT, cifrado de contraseñas con BCrypt y documentación interactiva con Swagger/OpenAPI.

## Características

- **CRUD completo** para la gestión de usuarios
- **Autenticación JWT** con tokens seguros
- **Cifrado de contraseñas** con BCrypt
- **Validación de datos** con Bean Validation
- **Manejo de excepciones** global
- **Documentación interactiva** con Swagger/OpenAPI
- **Arquitectura en capas** (Controller, Service, Repository, Model)
- **Base de datos PostgreSQL** con JPA/Hibernate

## Arquitectura del Proyecto

```
src/main/java/com/CreacionApisSimples/CreacionApisSimples/
├── config/                    # Configuraciones
│   ├── OpenApiConfig.java     # Configuración de Swagger
│   └── SecurityConfig.java    # Configuración de seguridad
├── controller/                # Controladores REST
│   ├── AuthController.java    # Endpoints de autenticación
│   └── UsuarioController.java # Endpoints CRUD de usuarios
├── dto/                       # Data Transfer Objects
│   ├── LoginRequest.java      # DTO para login
│   ├── LoginResponse.java     # DTO para respuesta de login
│   ├── UsuarioRequest.java    # DTO para crear/actualizar usuario
│   └── UsuarioResponse.java   # DTO para respuesta de usuario
├── exception/                 # Manejo de excepciones
│   ├── EmailAlreadyExistsException.java
│   ├── GlobalExceptionHandler.java
│   └── ResourceNotFoundException.java
├── model/                     # Entidades JPA
│   └── Usuario.java           # Entidad Usuario
├── repository/                # Repositorios de datos
│   └── UsuarioRepository.java # Repositorio de usuarios
├── security/                  # Configuración de seguridad
│   ├── CustomUserDetailsService.java
│   └── JwtAuthenticationFilter.java
├── service/                   # Lógica de negocio
│   ├── JwtService.java        # Servicio para JWT
│   └── UsuarioService.java    # Servicio de usuarios
└── CreacionApisSimplesApplication.java
```

## Requisitos Previos

- **Java 17** o superior
- **Maven 3.6+**
- **PostgreSQL** (configurado y ejecutándose)
- **IDE** (IntelliJ IDEA, Eclipse, VS Code)

## Instalación y Configuración

### 1. Clonar el proyecto
```bash
git clone <url-del-repositorio>
cd CreacionApisSimples
```

### 2. Configurar la base de datos
Asegúrate de que PostgreSQL esté ejecutándose y crear la base de datos:
```sql
CREATE DATABASE APIsSimples;
```

### 3. Configurar las propiedades
Edita el archivo `src/main/resources/application.properties` si es necesario:
```properties
# Base de datos
spring.datasource.url=jdbc:postgresql://localhost:5432/APIsSimples
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña

# Puerto del servidor
server.port=8087
```

### 4. Compilar y ejecutar
```bash
# Compilar el proyecto
mvn clean compile

# Ejecutar la aplicación
mvn spring-boot:run
```

La aplicación estará disponible en: `http://localhost:8087`

##  Documentación de la API

### Swagger UI
Una vez que la aplicación esté ejecutándose, puedes acceder a la documentación interactiva en:
- **Swagger UI**: `http://localhost:8087/swagger-ui.html`
- **OpenAPI JSON**: `http://localhost:8087/api-docs`

##  Endpoints de la API

### Autenticación

#### 1. Registrar Usuario
```http
POST /api/auth/register
Content-Type: application/json

{
    "nombre": "Juan Pérez",
    "email": "juan@ejemplo.com",
    "password": "123456"
}
```

#### 2. Iniciar Sesión
```http
POST /api/auth/login
Content-Type: application/json

{
    "email": "juan@ejemplo.com",
    "password": "123456"
}
```

**Respuesta:**
```json
{
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "tipo": "Bearer",
    "usuario": {
        "id": "123e4567-e89b-12d3-a456-426614174000",
        "nombre": "Juan Pérez",
        "email": "juan@ejemplo.com",
        "fechaCreacion": "2024-01-15T10:30:00"
    }
}
```

### Gestión de Usuarios

**Nota:** Todos los endpoints de usuarios requieren autenticación JWT. Incluye el header:
```
Authorization: Bearer <tu_token_jwt>
```

#### 1. Crear Usuario
```http
POST /api/usuarios
Authorization: Bearer <token>
Content-Type: application/json

{
    "nombre": "María García",
    "email": "maria@ejemplo.com",
    "password": "123456"
}
```

#### 2. Listar Todos los Usuarios
```http
GET /api/usuarios
Authorization: Bearer <token>
```

#### 3. Obtener Usuario por ID
```http
GET /api/usuarios/{id}
Authorization: Bearer <token>
```

#### 4. Actualizar Usuario
```http
PUT /api/usuarios/{id}
Authorization: Bearer <token>
Content-Type: application/json

{
    "nombre": "María García Actualizada",
    "email": "maria.nueva@ejemplo.com",
    "password": "nueva123456"
}
```

#### 5. Eliminar Usuario
```http
DELETE /api/usuarios/{id}
Authorization: Bearer <token>
```

## Códigos de Respuesta HTTP

| Código | Descripción |
|--------|-------------|
| 200 | OK - Operación exitosa |
| 201 | Created - Recurso creado exitosamente |
| 204 | No Content - Recurso eliminado exitosamente |
| 400 | Bad Request - Datos de entrada inválidos |
| 401 | Unauthorized - No autorizado (token inválido o faltante) |
| 403 | Forbidden - Acceso denegado |
| 404 | Not Found - Recurso no encontrado |
| 409 | Conflict - Email ya registrado |
| 500 | Internal Server Error - Error interno del servidor |

## Seguridad

### Autenticación JWT
- Los tokens JWT tienen una duración de 24 horas por defecto
- Se incluyen en el header `Authorization: Bearer <token>`
- Los endpoints de autenticación (`/api/auth/**`) son públicos
- Todos los demás endpoints requieren autenticación

### Cifrado de Contraseñas
- Las contraseñas se cifran usando BCrypt
- Nunca se almacenan en texto plano
- Se requiere mínimo 6 caracteres

## Pruebas con Postman

Se incluye una colección de Postman (`postman_collection.json`) con todas las peticiones necesarias para probar la API. Importa este archivo en Postman para tener acceso a todos los endpoints preconfigurados.

### Flujo de Pruebas Recomendado:
1. **Registrar un usuario** usando `/api/auth/register`
2. **Iniciar sesión** con `/api/auth/login` para obtener el token
3. **Copiar el token** de la respuesta
4. **Configurar la autenticación** en Postman (Bearer Token)
5. **Probar los endpoints CRUD** de usuarios

## 🛠️ Tecnologías Utilizadas

- **Spring Boot 3.5.6** - Framework principal
- **Spring Security** - Seguridad y autenticación
- **Spring Data JPA** - Persistencia de datos
- **PostgreSQL** - Base de datos
- **JWT (jjwt)** - Tokens de autenticación
- **BCrypt** - Cifrado de contraseñas
- **Lombok** - Reducción de código boilerplate
- **Swagger/OpenAPI** - Documentación de API
- **Bean Validation** - Validación de datos
- **Maven** - Gestión de dependencias

## 📝 Estructura de la Base de Datos

### Tabla: usuarios
| Campo | Tipo | Descripción |
|-------|------|-------------|
| id | UUID | Identificador único (clave primaria) |
| nombre | VARCHAR(50) | Nombre del usuario |
| email | VARCHAR(100) | Email único del usuario |
| password | VARCHAR | Contraseña cifrada con BCrypt |
| fecha_creacion | TIMESTAMP | Fecha de creación (autogenerada) |

## Despliegue

### Variables de Entorno Recomendadas:
```bash
# Base de datos
DB_URL=jdbc:postgresql://localhost:5432/APIsSimples
DB_USERNAME=tu_usuario
DB_PASSWORD=tu_contraseña

# JWT
JWT_SECRET=tu_clave_secreta_muy_larga_y_segura
JWT_EXPIRATION=86400000

# Servidor
SERVER_PORT=8087
```

### Docker (Opcional)
```dockerfile
FROM openjdk:17-jdk-slim
COPY target/CreacionApisSimples-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8087
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

## Contribución

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo `LICENSE` para más detalles.

## Soporte

Si tienes preguntas o necesitas ayuda, puedes:
- Abrir un issue en el repositorio
- Contactar al equipo de desarrollo
- Revisar la documentación de Swagger en `/swagger-ui.html`

