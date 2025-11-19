# Backend / API – Resumen Arquitectónico

> El código fuente del backend no está en este repositorio, pero la app Ionic depende de los siguientes servicios REST (URL base configurada en `VITE_API_BASE_URL`, por defecto `http://localhost:8086/api`).

## Endpoints Principales
| Recurso | Método | Descripción |
| --- | --- | --- |
| `/auth/login` | POST | Devuelve token JWT + usuario. |
| `/auth/register` | POST | Registra usuario y retorna token. |
| `/auth/logout` | POST | Invalida sesión actual. |
| `/auth/verify` | GET | Valida token en localStorage. |
| `/recetas` | GET/POST | Listado paginado / crear receta. |
| `/recetas/{id}` | GET/PUT/DELETE | Detalle, actualizar, eliminar. |
| `/recetas/{id}/favoritos` | POST/DELETE | Agregar/quitar favorito. |
| `/usuarios/perfil` | GET/PUT | Perfil actual y actualización. |
| `/usuarios` | GET | Listado para admins (paginado + search). |

## Módulos Lógicos
1. **Auth Module** – Manejo de credenciales, creación de tokens, verificación.
2. **Recipes Module** – CRUD, búsqueda, estadísticas, favoritos.
3. **Users Module** – Perfil, estadísticas, administración (roles, permisos).
4. **Media Module** – Recepción de archivos, almacenamiento, generación de URLs.

## Tecnologías Recomendadas
- Node.js/Express o NestJS, aunque cualquier framework REST (Spring Boot, .NET) es válido.
- ORM sugerido: Prisma, TypeORM o Sequelize.
- Almacenamiento de archivos: S3 compatible, Azure Blob o FileSystem con CDN.

Consulte `backend/sequence-diagram.md` para entender el flujo de “Carga local de imágenes y creación de receta”.
# Backend Sazón (Spring Boot)

- **Ubicación del código**: `sazón/sazón/`  
- **Stack**: Spring Boot 3.5, Spring Security, Spring Data JPA, H2/MySQL/PostgreSQL, Java 17.  
- **Estructura**:
  ```
  com.sazon
  ├── config/            → Seguridad, filtros y carga de datos
  ├── controller/        → AuthController, RecetaController, UsuarioController
  ├── domain/            → Entidades JPA (Usuario, Receta, Ingrediente, Paso, Imagen, Guardado)
  ├── dto/               → Contratos API
  ├── repository/        → Interfaces JPA
  └── service/           → Lógica de negocio
  ```
- **Ejecutar**:
  ```bash
  cd sazón/sazón
  ./mvnw spring-boot:run
  ```
- **Configuración**: editar `src/main/resources/application.properties` para puertos y credenciales de BD.  
- **Script SQL**: ver `data-base/schema.sql` (derivado de `schema.sql` original).  
- **Endpoints**: documentados en `backend/endpoints.md`.

