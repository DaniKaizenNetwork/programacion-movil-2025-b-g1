# Backlog Inicial – Sazón

## Épica 1 – Experiencia de Recetas
| Historia | Descripción | Criterios de Aceptación | Tareas |
| --- | --- | --- | --- |
| REC-001 | Como usuario quiero explorar recetas con stats para decidir rápidamente cuáles probar. | Ver título, fecha, mini resumen, contador de ingredientes y pasos. | Actualizar `Tab1Page`, mejorar tarjetas, consumo de `recipeService`. |
| REC-002 | Como creador quiero subir imágenes desde mi dispositivo. | Input de archivo disponible en creación/edición; se muestra preview; errores amigables. | Actualizar formularios (Tab2/Tab3), manejar `FormData`, validar MIME. |
| REC-003 | Como creador quiero publicar recetas aunque esté offline. | Se pueden guardar borradores (local) y sincronizar. | Cache local, cola de sincronización, feedback UI. *(Pendiente)* |

## Épica 2 – Gestión de Usuarios
| Historia | Descripción | Criterios | Tareas |
| --- | --- | --- | --- |
| USR-001 | Como admin quiero registrar usuarios desde la app. | Formulario con validaciones, UI amigable, feedback de errores. | Tab4Page, `authService.register`, logs. |
| USR-002 | Como usuario quiero actualizar mi perfil y cargar foto. | Tab5 muestra preview, valida formatos, sincroniza con API. | `userService.updateProfile`, preview, manejo de tokens. |
| USR-003 | Como usuario quiero gestionar mis favoritos. | Puedo agregar/quitar favoritos, ver contadores. | Actualizar `recipeService.addToFavorites/removeFromFavorites`, UI. |

## Épica 3 – Plataforma Técnica
| Historia | Descripción | Criterios | Tareas |
| --- | --- | --- | --- |
| TEC-001 | Como equipo quiero una guía de instalación consistente. | Manual reproducible, incluye variables Vite/Capacitor, túneles. | Crear `documentation/manual/developer-setup.md`. |
| TEC-002 | Como equipo quiero entender la arquitectura completa. | Documentación C4, BPMN, ERD, secuencias. | Carpeta `documentation/architecture` y `data-base`. |
| TEC-003 | Como equipo necesito pipeline de validación. | Lint + pruebas antes de deploy. | Configurar CI, scripts npm, reporte. *(Pendiente)* |

