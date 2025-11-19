# Tareas pendientes identificadas

1. **Implementar subida real de imágenes**  
   - Reemplazar input de URL por `input[type=file]` en Tab2/Tab3 y soportar `multipart/form-data` en `RecetaController`.

2. **Autenticación con JWT**  
   - Sustituir el token simulado `token_<uuid>` por JWT firmado y con expiración configurable.

3. **Pruebas automatizadas**  
   - Aumentar cobertura en `tests/unit` y crear suites E2E en Cypress para los flujos de recetas/favoritos.

4. **Migraciones de base de datos**  
   - Introducir Flyway o Liquibase para versionar el esquema (actualmente solo `schema.sql`).

5. **Optimización de búsqueda**  
   - Indexar campos `titulo`, `descripcion`, `ingredientes.nombre` para mejorar `/api/recetas?search=`.

6. **Manejo de errores consistente**  
   - Estandarizar la respuesta de errores (códigos + mensajes traducidos) en todos los controladores.

7. **Internacionalización del frontend**  
   - Preparar archivos de traducción (es/en) para los textos en vistas y mensajes de error.

8. **Modo offline / caché**  
   - Analizar uso de `Ionic Storage`/`IndexedDB` para cachear las últimas recetas en móviles.

9. **Notificaciones push**  
   - Integrar Capacitor Push notifications para avisar de nuevas recetas o comentarios futuros.

10. **Monitoreo y métricas**  
   - Configurar Actuator + Prometheus/Grafana para métricas del backend y Lighthouse para la web.*** End Patch

