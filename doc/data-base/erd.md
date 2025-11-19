# Diagrama Entidad-Relación

```mermaid
erDiagram
    USUARIOS ||--o{ RECETAS : "crea"
    USUARIOS ||--o{ GUARDADOS : "guarda"
    RECETAS ||--o{ INGREDIENTES : "contiene"
    RECETAS ||--o{ PASOS : "define"
    RECETAS ||--o{ IMAGENES : "tiene"

    USUARIOS {
      uuid user_id PK
      string email UK
      string nombre
      string username UK
      string role
      text bio
      string profile_image_url
      timestamp creado_en
      timestamp actualizado_en
    }

    RECETAS {
      uuid recipe_id PK
      uuid autor_id FK
      string titulo
      text descripcion
      enum estado
      timestamp creado_en
      timestamp actualizado_en
    }

    INGREDIENTES {
      bigint ingrediente_id PK
      uuid recipe_id FK
      string nombre
      string cantidad
      string unidad
      int orden
    }

    PASOS {
      bigint paso_id PK
      uuid recipe_id FK
      int orden
      text descripcion
    }

    IMAGENES {
      bigint imagen_id PK
      uuid recipe_id FK
      string url
      string storage_key
      int ancho_px
      int alto_px
      int orden
      boolean es_principal
    }

    GUARDADOS {
      bigint guardado_id PK
      uuid user_id FK
      uuid recipe_id FK
      timestamp creado_en
    }
```
```mermaid
erDiagram
    USUARIOS ||--o{ RECETAS : "autor_id"
    USUARIOS ||--o{ GUARDADOS : "user_id"
    RECETAS ||--o{ INGREDIENTES : "receta_id"
    RECETAS ||--o{ PASOS : "receta_id"
    RECETAS ||--o{ IMAGENES : "receta_id"
    RECETAS ||--o{ GUARDADOS : "recipe_id"

    USUARIOS {
        UUID user_id
        string email
        string nombre
        string username
        string role
    }

    RECETAS {
        UUID recipe_id
        UUID autor_id
        string titulo
        string estado
        text descripcion
    }

    INGREDIENTES {
        bigint ingrediente_id PK
        uuid recipe_id FK
        string nombre
        string cantidad
        string unidad
        int orden
    }

    PASOS {
        bigint paso_id PK
        uuid recipe_id FK
        int orden
        text descripcion
    }

    IMAGENES {
        bigint imagen_id PK
        uuid recipe_id FK
        string url
        string storage_key
        int ancho_px
        int alto_px
        int orden
        boolean es_principal
    }

    GUARDADOS {
        bigint guardado_id PK
        uuid user_id FK
        uuid recipe_id FK
        timestamp creado_en
    }
```

### Notas
- **Claves primarias:**
  - `usuarios.user_id` y `recetas.recipe_id` usan UUID (ver `@GeneratedValue(strategy = GenerationType.UUID)`).
  - `ingredientes.ingrediente_id`, `pasos.paso_id`, `imagenes.imagen_id` y `guardados.guardado_id` usan BIGINT con auto-incremento (ver `@GeneratedValue(strategy = GenerationType.IDENTITY)`).
- **Tabla `guardados`:** Funciona como tabla puente para representar favoritos (muchos a muchos entre usuarios y recetas). Tiene constraint único en `(user_id, recipe_id)`.
- **Orden:** Columnas `orden` en ingredientes/pasos/imágenes preservan el orden definido por el autor (ver `@OrderBy("orden ASC")` en `Receta`).
- **Imágenes:** Se almacenan en tabla separada `imagenes` con metadatos (`storage_key`, `ancho_px`, `alto_px`, `orden`, `es_principal`) para permitir múltiples imágenes por receta y migrar a almacenamiento externo.
- **Estados de receta:** `BORRADOR`, `PUBLICADA`, `ELIMINADA` (eliminación lógica).
- **Estadísticas:** No existe tabla `estadisticas_receta`. Las estadísticas se calculan dinámicamente en el servicio.

