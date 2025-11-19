# Backlog inicial de historias de usuario

1. **Registro básico**  
   - *Como visitante quiero crear una cuenta proporcionando nombre, email, usuario y contraseña para comenzar a usar Sazón.*  
   - Criterios: Validar formato de correo, confirmar contraseña, recibir mensaje de éxito. (`HomePage.vue`, `AuthController#registrarUsuario`)

2. **Inicio de sesión**  
   - *Como usuario registrado quiero iniciar sesión para acceder a mis recetas y favoritos.*  
   - Criterios: Validación frontend, manejo de errores del backend, persistencia de token en `localStorage`.

3. **Ver feed de recetas**  
   - *Como usuario quiero ver un listado de recetas con título, fecha, imagen, resumen y contadores de pasos/ingredientes.*  
   - Criterios: Búsqueda por texto, paginación desde `/api/recetas`. (`Tab1Page.vue`)

4. **Ver detalle de receta**  
   - *Como usuario quiero abrir una receta concreta para revisar ingredientes y pasos completos.*  
   - Criterios: Mostrar imagen principal, autor, pasos ordenados y estado de favorito. (`RecipeDetailPage.vue`)

5. **Crear receta**  
   - *Como usuario autenticado quiero registrar una nueva receta con ingredientes y pasos para compartirla después.*  
   - Criterios: Validar mínimo título, permitir vista previa de imagen, enviar a `POST /api/recetas`. (`Tab2Page.vue`)

6. **Editar receta propia**  
   - *Como autor quiero editar mis recetas en borrador o publicadas para mantenerlas actualizadas.*  
   - Criterios: Cargar datos existentes, preservar orden de pasos, validar permisos en backend. (`Tab3Page.vue`, `PUT /api/recetas/{id}`)

7. **Publicar/Despublicar receta**  
   - *Como autor quiero publicar o devolver a borrador mis recetas para controlar su visibilidad.*  
   - Criterios: Acciones `/api/recetas/{id}/publicar` y `/despublicar`, solo para autor/admin.

8. **Gestionar perfil**  
   - *Como usuario quiero actualizar nombre, username, bio e imagen para mantener mi identidad en la comunidad.*  
   - Criterios: Validación de campos, actualización en `PUT /api/usuarios/perfil`. (`Tab5Page.vue`)

9. **Ver estadísticas personales**  
   - *Como usuario quiero consultar cuántas recetas publiqué y cuántos favoritos recibí.*  
   - Criterios: Llamar a `/api/usuarios/perfil/estadisticas`, mostrar tarjetas de métricas en Tab5.

10. **Administrar usuarios**  
   - *Como administrador quiero listar, buscar y promover/degradar usuarios para mantener el orden de la plataforma.*  
   - Criterios: Endpoints `/api/usuarios`, `/promover`, `/degradar`; interfaz en `Tab4Page.vue`/`Tab6Page.vue`.

11. **Favoritos (extra)**  
   - *Como usuario quiero guardar recetas en favoritos para consultarlas más tarde.*  
   - Criterios: Botones en detalle, endpoints `/api/recetas/{id}/favoritos` (POST/DELETE), indicador en DTO `favorita`.

