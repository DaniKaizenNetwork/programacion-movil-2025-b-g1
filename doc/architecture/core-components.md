# Componentes Principales del Sistema

## Frontend (`layoutBasica/`)

| Componente | Descripción | Archivos clave |
|------------|-------------|----------------|
| **Vistas (Tabs)** | Organización de la app móvil/web en pestañas (recetas, nuevas, editar, perfil, admin). | `src/views/Tab1Page.vue` a `Tab6Page.vue`, `TabsPage.vue`. |
| **HomePage** | Pantalla de login/registro con validaciones de email y contraseña. | `src/views/HomePage.vue`. |
| **Detalle de receta** | Muestra ingredientes/pasos/favoritos, permite regresar al listado. | `src/views/RecipeDetailPage.vue`. |
| **Servicios de datos** | `authService`, `recipeService`, `userService` encapsulan las llamadas HTTP y persistencia en `localStorage`. | `src/services/*.js`. |
| **Configuración API** | Resuelve el `baseURL` desde variables de entorno y evita errores de doble slash. | `src/config/api.js`. |
| **Validadores** | Reglas reutilizables (p. ej. formato de correo). | `src/utils/validators.js`. |
| **Tema y assets** | Paleta y recursos compartidos (imagen `food.jpg`). | `src/theme/variables.css`, `src/assets`. |

## Backend (`sazón/sazón/`)

| Capa | Responsabilidad | Archivos clave |
|------|-----------------|----------------|
| **Controladores REST** | Exponer endpoints para autenticación, usuarios y recetas. | `controller/AuthController.java`, `RecetaController.java`, `UsuarioController.java`. |
| **Servicios de dominio** | Reglas de negocio: creación de recetas, estadísticas, autorizaciones. | `service/RecetaService.java`, `UsuarioService.java`, `ServicioAutorizacionReceta.java`. |
| **Repositorios JPA** | Consultas a la base de datos con Spring Data. | `repository/*Repository.java`. |
| **Dominio** | Entidades y objetos de valor (Receta, Usuario, Ingrediente, Paso, Imagen, Guardado, Titulo). | `domain/*.java`. |
| **Configuración** | Seguridad, carga inicial de datos, filtros. | `config/SecurityConfig.java`, `DataInitializer.java`, `OptionsFilter.java`. |
| **DTOs** | Contratos que exponen los controladores. | `dto/*.java`. |

## Base de Datos

- Tablas principales: `usuarios`, `recetas`, `ingredientes`, `pasos`, `imagenes`, `guardados`.  
- Relaciones: un usuario tiene muchas recetas; una receta tiene muchos ingredientes/pasos/imagenes; `guardados` hace de tabla puente entre usuario y receta para favoritos.

## Android / Capacitor

- `layoutBasica/android/` contiene el proyecto generado por Capacitor.  
- Configuraciones importantes: `android/app/src/main/AndroidManifest.xml`, `gradle.properties`, `capacitor.config.ts`.

## Testing

- **Unit tests**: ejemplos en `layoutBasica/tests/unit/example.spec.ts`.  
- **E2E (Cypress)**: `layoutBasica/tests/e2e/specs/test.cy.ts`.  
- **Backend tests**: `sazón/sazón/src/test/java/...`.

