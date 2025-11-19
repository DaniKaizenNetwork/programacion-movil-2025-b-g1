# Modelos de Datos (Esquema Relacional)

## Tabla `usuarios`
| Campo | Tipo | Descripción |
| --- | --- | --- |
| `user_id` | UUID | Identificador único (clave primaria). |
| `email` | VARCHAR(255) UNIQUE | Credencial principal. |
| `password_hash` | VARCHAR | Hash seguro (bcrypt/argon2). |
| `nombre` | VARCHAR(150) | Nombre completo. |
| `username` | VARCHAR(80) UNIQUE | Alias público. |
| `bio` | TEXT | Biografía corta (opcional). |
| `profile_image_url` | VARCHAR | URL en el storage (opcional). |
| `role` | ENUM('USER','ADMIN') | Permisos especiales. |
| `creado_en` | TIMESTAMP | Fecha de creación (no actualizable). |
| `actualizado_en` | TIMESTAMP | Fecha de última actualización. |

## Tabla `recetas`
| Campo | Tipo | Descripción |
| --- | --- | --- |
| `recipe_id` | UUID | Identificador único (clave primaria). |
| `autor_id` | UUID | FK → `usuarios.user_id` (propietario). |
| `titulo` | VARCHAR(150) | Nombre de la receta (embebido en `Titulo`). |
| `descripcion` | TEXT | Resumen/Historia. |
| `estado` | ENUM('BORRADOR','PUBLICADA','ELIMINADA') | Estado de la receta. |
| `creado_en` | TIMESTAMP | Fecha de creación (no actualizable). |
| `actualizado_en` | TIMESTAMP | Fecha de última actualización. |

**Nota:** Las imágenes de las recetas se almacenan en la tabla `imagenes` (ver más abajo). No existe el campo `imagen_principal_url` en esta tabla.

## Tabla `ingredientes`
| Campo | Tipo | Descripción |
| --- | --- | --- |
| `ingrediente_id` | BIGINT | Identificador único (clave primaria, auto-incremental). |
| `recipe_id` | UUID | FK → `recetas.recipe_id` (receta asociada). |
| `nombre` | VARCHAR(100) | Nombre del ingrediente. |
| `cantidad` | VARCHAR(50) | Cantidad (ej. "2", "1/2") (opcional). |
| `unidad` | VARCHAR(20) | Unidad de medida (ej. "tazas", "gramos") (opcional). |
| `orden` | INT | Orden de despliegue en la lista. |

## Tabla `pasos`
| Campo | Tipo | Descripción |
| --- | --- | --- |
| `paso_id` | BIGINT | Identificador único (clave primaria, auto-incremental). |
| `recipe_id` | UUID | FK → `recetas.recipe_id` (receta asociada). |
| `orden` | INT | Orden del paso en la receta. |
| `descripcion` | TEXT | Descripción del paso. |

## Tabla `imagenes`
| Campo | Tipo | Descripción |
| --- | --- | --- |
| `imagen_id` | BIGINT | Identificador único (clave primaria, auto-incremental). |
| `recipe_id` | UUID | FK → `recetas.recipe_id` (receta asociada). |
| `url` | VARCHAR(500) | URL de la imagen almacenada. |
| `storage_key` | VARCHAR(255) | Clave en el sistema de almacenamiento (opcional). |
| `ancho_px` | INT | Ancho de la imagen en píxeles (opcional). |
| `alto_px` | INT | Alto de la imagen en píxeles (opcional). |
| `orden` | INT | Orden de despliegue (por defecto 0). |
| `es_principal` | BOOLEAN | Indica si es la imagen principal de la receta (por defecto false). |

## Tabla `guardados`
| Campo | Tipo | Descripción |
| --- | --- | --- |
| `guardado_id` | BIGINT | Identificador único (clave primaria, auto-incremental). |
| `user_id` | UUID | FK → `usuarios.user_id` (usuario que guardó). |
| `recipe_id` | UUID | FK → `recetas.recipe_id` (receta guardada). |
| `creado_en` | TIMESTAMP | Fecha en que se guardó (no actualizable). |

**Constraint único:** `(user_id, recipe_id)` - Un usuario no puede guardar la misma receta dos veces.

**Nota:** Esta tabla representa los "favoritos" o recetas guardadas por los usuarios. El nombre de la tabla es `guardados`, no `favoritos`.

