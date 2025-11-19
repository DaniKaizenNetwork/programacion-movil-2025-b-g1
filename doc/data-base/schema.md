# Modelos de Datos (Esquema Relacional)

## Tabla `usuarios`
| Campo | Tipo | Descripción |
| --- | --- | --- |
| `id` | UUID / BIGINT | Identificador único. |
| `email` | VARCHAR(255) UNIQUE | Credencial principal. |
| `password_hash` | VARCHAR | Hash seguro (bcrypt/argon2). |
| `nombre` | VARCHAR(150) | Nombre completo. |
| `username` | VARCHAR(80) UNIQUE | Alias público. |
| `bio` | TEXT | Biografía corta. |
| `profile_image_url` | VARCHAR | URL en el storage. |
| `role` | ENUM('USER','ADMIN') | Permisos especiales. |
| `created_at` / `updated_at` | TIMESTAMP | Auditoría. |

## Tabla `recetas`
| Campo | Tipo | Descripción |
| --- | --- | --- |
| `id` | UUID / BIGINT | Identificador. |
| `titulo` | VARCHAR(150) | Nombre de la receta. |
| `descripcion` | TEXT | Resumen/Historia. |
| `autor_id` | FK → `usuarios.id` | Propietario. |
| `imagen_principal_url` | VARCHAR | URL de la imagen almacenada. |
| `estado` | ENUM('PUBLICADA','BORRADOR') | Flujo editorial. |
| `favoritos_count` | INT | Contador denormalizado. |
| `created_at` / `updated_at` | TIMESTAMP | Auditoría. |

## Tabla `ingredientes`
| Campo | Tipo | Descripción |
| --- | --- | --- |
| `id` | BIGINT | Identificador. |
| `receta_id` | FK → `recetas.id` | Receta asociada. |
| `orden` | INT | Orden de despliegue. |
| `cantidad` | VARCHAR(50) | Valor libre (ej. "2"). |
| `unidad` | VARCHAR(50) | Unidad (ej. "tazas"). |
| `nombre` | VARCHAR(150) | Ingrediente. |

## Tabla `pasos`
| Campo | Tipo |
| --- | --- |
| `id` | BIGINT |
| `receta_id` | FK → `recetas.id` |
| `orden` | INT |
| `descripcion` | TEXT |

## Tabla `favoritos`
| Campo | Tipo |
| --- | --- |
| `usuario_id` | FK → `usuarios.id` |
| `receta_id` | FK → `recetas.id` |
| `created_at` | TIMESTAMP |

## Tabla `estadisticas_receta`
| Campo | Tipo | Descripción |
| --- | --- | --- |
| `receta_id` | FK | Clave principal. |
| `views` | INT | Visitas. |
| `shares` | INT | Compartidos. |
| `last_viewed_at` | TIMESTAMP | Último acceso. |

