```mermaid
flowchart LR
    subgraph Client["layoutBasica (Ionic + Vue)"]
        Views["Views (Home, Tabs, RecipeDetail, Profile)"]
        Services["Servicios JS (authService, recipeService, userService)"]
        Storage["LocalStorage (token, usuario)"]
        Views --> Services
        Services --> Storage
    end

    subgraph API["sazón/sazón (Spring Boot)"]
        Controllers["REST Controllers\n(Auth, Recetas, Usuarios)"]
        ServicesB["Servicios de dominio\nRecetaService, UsuarioService"]
        Repos["Repositorios JPA"]
        Controllers --> ServicesB --> Repos
    end

    subgraph Data["Base de datos relacional"]
        Tables["usuarios, recetas,\ningredientes, pasos,\nimagenes, guardados"]
    end

    Services -- HTTP/JSON --> Controllers
    Repos --> Tables
```

### Descripción

- **Views**: Componentes Ionic (por ejemplo `Tab1Page.vue`, `Tab3Page.vue`) que orquestan formularios y navegación.  
- **Servicios JS**: Encapsulan `fetch`, construyen URLs con `getApiUrl()` y adjuntan Bearer tokens almacenados.  
- **Controladores Spring**: Validan peticiones, convierten entidades a DTO y delegan en los servicios de dominio.  
- **Servicios de dominio**: Contienen las reglas (validación de autoría, publicación/borrador, estadísticas).  
- **Repositorios JPA**: Mapas a las tablas físicas definidas en `schema.sql`.  
- **Base de datos**: Modelo relacional con UUID como claves primarias, relaciones 1:N (usuarios-recetas, recetas-ingredientes/pasos/imagenes) y tabla puente `guardados`.

