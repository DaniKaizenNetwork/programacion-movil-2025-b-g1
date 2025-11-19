# Manual de Usuario – App Sazón

## Inicio de Sesión / Registro
1. Abre la app y elige *Iniciar sesión* o *Registrarse*.
2. Completa correo y contraseña (mín. 6 caracteres).  
3. Si eres nuevo, ingresa nombre completo, username y opcionalmente biografía/imagen.  
4. La app validará formato de correo y te mostrará mensajes claros si algo falla.

## Navegación Principal
- **Recetas (Tab1):** Explora tarjetas con resumen, foto y contadores de ingredientes/pasos. Toca una tarjeta para ver el detalle.
- **Nueva (Tab2):** Crea recetas ingresando texto, ingredientes por línea y pasos. (Próximamente input de archivo para imágenes locales).
- **Editar (Tab3):** Lista tus recetas; selecciona una para modificar título, descripción, ingredientes, pasos o imagen.
- **Perfil (Tab5):** Consulta tu información, estadísticas y actualiza nombre/username/bio/imagen. También puedes cambiar contraseña y cerrar sesión.
- **Admin Tabs:** Si tu usuario tiene rol `ADMIN`, verás opciones para registrar usuarios (Tab4) y gestionar cuentas (Tab6).

## Favoritos
1. Dentro del detalle de la receta, usa el botón *Agregar a favoritos* (icono de marcador).
2. Para quitarlo, repite la acción.
3. Los contadores se actualizan en las tarjetas y en tu perfil.

## Flujo de Creación de Recetas
1. Ve a **Nueva**.
2. Completa los campos obligatorios.
3. Añade ingredientes/pasos uno por línea.
4. Selecciona (temporalmente) una URL de imagen válida.
5. Publica; si hay errores del backend (403, 409, etc.) se mostrará el mensaje exacto.

## Edición y Eliminación
- Desde **Editar** puedes modificar datos o eliminar recetas (si tienes permisos).
- El formulario muestra la imagen actual y permite reemplazarla.

## Consejos
- La app recuerda tu sesión en `localStorage`. Usa *Cerrar sesión* en Perfil si compartes dispositivo.
- Si no ves datos, revisa tu conexión o que la API esté disponible en `http://localhost:8086/api`.
# Manual de Usuario Sazón (Frontend Ionic)

## Inicio de sesión y registro
1. **Abrir la app**: inicia en `HomePage.vue`.
2. **Iniciar sesión**  
   - Introduce correo y contraseña.  
   - El botón “Ingresar” se habilita cuando ambos campos cumplen validaciones (uso de `isValidEmail`).  
   - Errores típicos: “Completa correo y contraseña” o mensajes devueltos por `/api/auth/login`.
3. **Registrarse**  
   - Cambia al modo “Registrarse”.  
   - Completa Nombre completo, Nombre de usuario, Email, Contraseña y Confirmación.  
   - El sistema valida coincidencia de contraseñas y formato de email antes de enviar a `/api/auth/register`.  
   - Al terminar se limpia el formulario y se redirige a la pestaña principal (`/tabs/tab1`).

## Explorar recetas (`Tab1`)
1. Barra superior muestra el avatar del usuario y un botón para crear nueva receta.  
2. Busca recetas escribiendo en el campo “Buscar receta”; la consulta se envía con `debounce` de 500 ms.  
3. Cada tarjeta muestra título, fecha, imagen, resumen, número de ingredientes y pasos.  
4. Toca una tarjeta para abrir el **Detalle**.

## Detalle de receta
1. Encabezado con botón de volver y título adaptativo.  
2. Secciones: descripción, lista de ingredientes, lista ordenada de pasos.  
3. Desde aquí se podrían agregar favoritos (pendiente); actualmente solo lectura.

## Crear receta (`Tab2`)
1. Presiona “Nueva” en la barra de navegación inferior o el botón `+`.  
2. Completa título, descripción, ingredientes (uno por línea) y pasos.  
3. Adjunta una imagen (actualmente se usa URL, pendiente migrar a carga de archivo).  
4. Guarda la receta; se envía a `POST /api/recetas`. Tras éxito se limpia el formulario y se vuelve al listado.

## Editar receta (`Tab3`)
1. Lista todas tus recetas, con búsqueda opcional.  
2. Al seleccionar una, se carga en el formulario con los ingredientes/pasos existentes.  
3. Puedes actualizar campos y enviar a `PUT /api/recetas/{id}`.  
4. Incluye acciones adicionales (publicar, despublicar, eliminar) según rol y estado.

## Registro y gestión de usuarios (Admin)
- `Tab4` permite a administradores crear usuarios manualmente.  
- `Tab6` muestra la tabla completa de usuarios para búsqueda, edición, promoción y degradación.

## Perfil (`Tab5`)
1. Muestra avatar, conteos de recetas y favoritos.  
2. Permite editar nombre, username, bio e imagen de perfil.  
3. Incluye formulario para cambiar contraseña y botones de cerrar sesión.

> Las capturas pueden obtenerse ejecutando `npm run dev` y accediendo a `http://localhost:5173`.

