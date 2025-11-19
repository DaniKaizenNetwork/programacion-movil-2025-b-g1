# Endpoints principales de la API REST

## Autenticación (`/api/auth`)

| Método | URI | Descripción | Request | Response |
|--------|-----|-------------|---------|----------|
| POST | `/register` | Crea usuario con rol USER. | `RegisterRequest { email, password, confirmPassword, nombre, username, bio? }` | 201 `{ success, message, user: UsuarioDTO, token }` |
| POST | `/login` | Autentica y retorna token simulado `token_<userId>`. | `LoginRequest { email, password }` | 200 `{ success, user, token }` |
| POST | `/logout` | Limpia el contexto de seguridad. | — | 200 `{ message }` |
| GET | `/verify` | Verifica sesión activa. | Header `Authorization: Bearer token_<uuid>` | 200 `{ authenticated: bool, user? }` |

## Recetas (`/api/recetas`)

| Método | URI | Descripción | Parámetros | Notas |
|--------|-----|-------------|------------|-------|
| GET | `/` | Lista recetas publicadas (opcional búsqueda). | `search`, paginación Spring (`page`, `size`, `sort`). | Retorna `Page<RecetaDTO>`. |
| GET | `/todas` | Lista TODAS (incluye borradores/eliminadas) – solo admin. | `search`, paginación. | Valida `usuario.isAdmin()`. |
| GET | `/{recipeId}` | Detalle de receta. | `recipeId (UUID)` | Valida permisos vía `ServicioAutorizacionReceta`. |
| POST | `/` | Crea receta del usuario autenticado. | Body `CreateRecetaRequest` (titulo, descripcion, ingredientes[], pasos[], imagenes[]). | Requiere token. |
| PUT | `/{recipeId}` | Actualiza receta existente del autor. | Body `CreateRecetaRequest`. | Retorna `RecetaDTO`. |
| DELETE | `/{recipeId}` | Eliminación lógica. | — | Cambia estado a `ELIMINADA`. |
| POST | `/{recipeId}/publicar` | Marca como publicada. | — | Solo autor/admin. |
| POST | `/{recipeId}/despublicar` | Vuelve a borrador. | — | Solo autor/admin. |
| POST | `/{recipeId}/favoritos` | Guarda receta como favorita. | — | Genera registro en `guardados`. |
| DELETE | `/{recipeId}/favoritos` | Quita de favoritos. | — | Retorna 204. |
| GET | `/buscar/ingrediente` | Filtra por ingrediente. | `ingrediente`, paginación. | Ideal para buscador avanzado. |
| GET | `/populares` | Recetas más guardadas. | paginación | Orden definido en `RecetaService.obtenerRecetasPopulares`. |

## Usuarios (`/api/usuarios`)

| Método | URI | Descripción | Notas |
|--------|-----|-------------|-------|
| GET | `/perfil` | Perfil del usuario autenticado. | Enriquecido con stats (`UsuarioService.UsuarioStats`). |
| PUT | `/perfil` | Actualiza nombre, username, bio, imagen. | Usa `UpdateUsuarioRequest`. |
| PUT | `/perfil/password` | Cambia contraseña. | Body `{ nuevaPassword }`. |
| GET | `/perfil/recetas` | Lista recetas propias (borradores y publicadas). | Acepta `search` y paginación. |
| GET | `/perfil/recetas/publicadas` | Solo publicadas del usuario. | Usa `@AuthenticationPrincipal`. |
| GET | `/perfil/favoritos` | Recetas guardadas. | Permite `search`. |
| GET | `/perfil/estadisticas` | KPIs (recetas publicadas, favoritos). | Devuelve objeto `UsuarioStats`. |
| GET | `/` | Listado completo de usuarios (admin). | Soporta `search`. |
| GET | `/{userId}` | Detalle de un usuario (admin). | — |
| GET | `/buscar?query=` | Búsqueda avanzada (admin). | — |
| PUT | `/{userId}` | Actualiza perfil de un usuario específico (admin). | `UpdateUsuarioRequest`. |
| POST | `/{userId}/promover` | Cambia rol a ADMIN. | Solo ADMIN. |
| POST | `/{userId}/degradar` | Revierte a USER. | Solo ADMIN. |
| DELETE | `/{userId}` | Elimina usuario. | Implementa borrado lógico/físico según `UsuarioService`. |

### Autenticación
- Todas las rutas (excepto `/api/auth/**`) esperan header `Authorization: Bearer token_<uuid>`.  
- `SecurityConfig` habilita CORS y usa `AuthenticationManager` para validar credenciales con `UserDetailsServiceImpl`.

### Formatos de DTO relevantes
- `RecetaDTO`: incluye `recipeId`, `autorNombre`, listas de `IngredienteDTO`, `PasoDTO`, `ImagenDTO`, `favorita`, `favoritosCount`.  
- `UsuarioDTO`: `userId`, `email`, `nombre`, `username`, `bio`, `profileImageUrl`, `role`, `recetasCount`, `favoritosCount`.

