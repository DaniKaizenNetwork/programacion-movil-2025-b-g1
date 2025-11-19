# App Móvil (Ionic + Vue) – Resumen

## Componentes/Estados Principales
- **HomePage.vue** – Pantalla de autenticación con modos login/registro.
- **TabsPage.vue** – Contenedor principal con IonTabs.
- **Tab1Page.vue (Recetas)** – Lista de recetas con contadores e integración con `recipeService.getAllRecipes`.
- **Tab2Page.vue (Nueva)** – Formulario de creación (título, descripción, ingredientes/pasos, imagen URL → se migrará a input file).
- **Tab3Page.vue (Editar)** – Panel dual: lista de recetas + formulario de edición (cuando se selecciona una).
- **Tab4Page.vue (Registro Admin)** – Solo visible para admins, usa `authService.register`.
- **Tab5Page.vue (Perfil)** – Perfil, estadísticas y cambio de contraseña; integra `userService`.
- **Tab6Page.vue (Gestión de usuarios)** – Administración avanzada (promover/degradar, búsquedas).

## Servicios Front-end
- `authService.js`: login, registro, verificación, logout, headers con token.
- `userService.js`: perfiles, stats, CRUD de usuarios, cambio de contraseña.
- `recipeService.js`: listados (paginados, admin, favoritos), crear/editar/eliminar recetas y convertir formatos backend/frontend.

## Estado y Almacenamiento
- Tokens y usuario actual se guardan en `localStorage` (`sazon_auth_token`, `sazon_user`).
- Los servicios reutilizan `getApiUrl()` para resolver endpoints.

## Estilos/UX
- Cabeceras translúcidas, tarjetas con blur y contadores.
- Manejo intensivo de IonComponents (IonPage, IonContent, IonList, IonItem, IonInput, IonButton, IonIcon, IonTabBar).

## Próximos Pasos
1. Migrar campos de imagen a `input type="file"` con preview y `FormData`.
2. Integrar manejo offline/drafts.
3. Añadir pruebas end-to-end (Cypress) para flujos críticos (login, crear receta, favoritos).
# App móvil (Ionic + Capacitor)

## Código fuente
- Ubicación principal: `layoutBasica/`
- Entrypoint: `src/main.js`
- Componentes clave: `src/views/*` (Tab1–Tab6, Home, RecipeDetail)
- Servicios compartidos: `src/services/*.js`

## Construcción web
```bash
cd layoutBasica
npm install
npm run build
```
El resultado queda en `layoutBasica/dist/`.

## Integración con Android
1. Sincronizar:
   ```bash
   npx cap sync android
   ```
2. Abrir en Android Studio:
   ```bash
   npx cap open android
   ```
3. Ejecutar/compilar desde Android Studio (Gradle Wrapper incluido).

### Configuración relevante

| Archivo | Descripción |
|---------|-------------|
| `capacitor.config.ts` | Define `appId`, `appName`, `webDir` y `server`. |
| `android/app/build.gradle` | Configura SDK compile/target, dependencias de Capacitor. |
| `android/variables.gradle` | Versiones de librerías y plugin de Capacitor. |
| `android/app/src/main/AndroidManifest.xml` | Permisos (INTERNET, acceso cámara/archivos cuando se integre subida de imágenes). |

### Recursos gráficos
- Iconos y splash en `android/app/src/main/res/mipmap-*` y `drawable`.  
- Imagen de fondo reutilizada en el frontend: `layoutBasica/src/assets/food.jpg`.

## Tips de desarrollo móvil
- Usa `ionic serve --external` para probar en dispositivos físicos apuntando al host local.  
- Para acceder al backend desde emulador, expón el puerto (por ejemplo `http://10.0.2.2:8080`).  
- Verifica permisos en tiempo de ejecución cuando se implemente carga de imágenes desde la cámara o galería.*** End Patch

