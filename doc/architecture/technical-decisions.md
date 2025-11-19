# Decisiones Técnicas Clave

| Decisión | Justificación | Evidencia en código |
|----------|---------------|---------------------|
| **Ionic + Vue 3** para el frontend | Permite compartir componentes entre web y móvil. Ionic ofrece UI móvil lista y compatibilidad con Capacitor/Android. | `layoutBasica/package.json` (`@ionic/vue`, `vue@3`, `vite`). Vistas en `src/views/*.vue`. |
| **Vite** como bundler | Arranque rápido, HMR y configuración mínima para proyectos modernos. | `layoutBasica/vite.config.ts`. |
| **Capacitor** para despliegue móvil | Integra el bundle web con Android/iOS sin duplicar código. | `layoutBasica/android/` y `capacitor.config.ts`. |
| **Spring Boot 3** en backend | Proporciona stack integral: REST, seguridad, JPA, validaciones y actuator. Facilita despliegue monolítico. | `sazón/pom.xml` (dependencias `spring-boot-starter-*`). |
| **JWT “simulado”** (`token_<uuid>`) | Simplifica autenticación durante el desarrollo mientras se define una solución real de tokens. | `AuthController` y `RecetaController.obtenerUsuarioDesdeToken`. |
| **UUID como claves primarias** | Mejora unicidad en ambientes distribuidos y evita colisiones en sincronización offline. | Anotaciones `@GeneratedValue(strategy = GenerationType.UUID)` en `Receta`, `Usuario`, etc. |
| **H2 para desarrollo, drivers MySQL/PostgreSQL listos** | Permite levantar el backend sin infraestructura adicional y facilita migrar a producción. | `schema.sql`, dependencias `com.h2database`, `mysql-connector-java`, `org.postgresql`. |
| **DTOs explícitos** | Aíslan la API pública de las entidades JPA, controlando qué campos se exponen. | `com.sazon.dto.*` y uso en controladores. |
| **Servicios JS con `fetch` en lugar de Axios** | Reduce dependencias y aprovecha el estándar nativo (disponible en navegadores y Capacitor). | `src/services/*Service.js`. |
| **Validación en frontend y backend** | Mejora UX (mensajes inmediatos) y garantiza integridad del lado del servidor. | Front: `validators.js`, `HomePage.vue`; Back: anotaciones de validación y checks manuales en controladores. |

> Próximos pasos sugeridos: reemplazar el token simulado por JWT real, introducir migraciones (Flyway/Liquibase) y manejar carga de imágenes con almacenamiento dedicado.*** End Patch

