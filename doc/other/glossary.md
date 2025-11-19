# Glosario y notas adicionales

| Término | Definición / Uso en el proyecto |
|---------|----------------------------------|
| **Capacitor** | Herramienta de Ionic para compilar la app web dentro de contenedores nativos (Android/iOS). Código en `layoutBasica/android`. |
| **DTO (Data Transfer Object)** | Objetos planos que se envían entre backend y frontend (ej. `RecetaDTO`, `UsuarioDTO`). Permiten controlar campos expuestos. |
| **Guardado** | Entidad que representa un “favorito”. Relaciona a un `Usuario` con una `Receta`. |
| **Estado de receta** | Enum `BORRADOR`, `PUBLICADA`, `ELIMINADA` usado para controlar visibilidad. |
| **Tabs** | Navegación típica de Ionic. Cada pestaña (Tab1–Tab6) agrupa funciones: feed, crear, editar, administrar, perfil. |
| **Token simulado** | Cadena `token_<uuid>` que identifica al usuario autenticado. Se almacena en `localStorage` hasta migrar a JWT real. |
| **SampleDataInitializer** | Clase Spring que inserta registros de ejemplo para pruebas locales. Útil cuando no hay datos en la BD. |
| **Service Worker (pendiente)** | Actualmente no se usa uno; sería recomendable para cachear recursos y soportar PWA offline. |

