# Diagrama Entidad-Relación

```mermaid
erDiagram
    USUARIOS ||--o{ RECETAS : "crea"
    USUARIOS ||--o{ FAVORITOS : "guarda"
    RECETAS ||--o{ INGREDIENTES : "contiene"
    RECETAS ||--o{ PASOS : "define"
    RECETAS ||--|| ESTADISTICAS_RECETA : "tiene"

    USUARIOS {
      uuid id
      string email
      string username
      string role
    }

    RECETAS {
      uuid id
      string titulo
      text descripcion
      string imagen_principal_url
      uuid autor_id
    }

    INGREDIENTES {
      int id
      uuid receta_id
      int orden
      string nombre
      string cantidad
      string unidad
    }

    PASOS {
      int id
      uuid receta_id
      int orden
      text descripcion
    }

    FAVORITOS {
      uuid usuario_id
      uuid receta_id
      datetime created_at
    }

    ESTADISTICAS_RECETA {
      uuid receta_id
      int views
      int shares
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
        UUID ingrediente_id
        UUID receta_id
        string nombre
        string cantidad
        string unidad
        int orden
    }

    PASOS {
        UUID paso_id
        UUID receta_id
        int orden
        text descripcion
    }

    IMAGENES {
        UUID imagen_id
        UUID receta_id
        text url
        boolean es_principal
    }

    GUARDADOS {
        UUID guardado_id
        UUID user_id
        UUID recipe_id
    }
```

### Notas
- Todas las claves primarias usan UUID (ver anotaciones `@GeneratedValue(strategy = GenerationType.UUID)` en las entidades).  
- `Guardados` funciona como tabla puente para representar favoritos (muchos a muchos entre usuarios y recetas).  
- Columnas `orden` en ingredientes/pasos preservan el orden definido por el autor (ver `@OrderBy("orden ASC")` en `Receta`).  
- Las imágenes se almacenan como URL + metadatos (`storageKey`, dimensiones) para permitir migrar a almacenamiento externo.*** End Patch

