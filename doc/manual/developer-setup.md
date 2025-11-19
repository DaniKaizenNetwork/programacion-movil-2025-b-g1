# Guía de Instalación / Setup para Desarrolladores

## Requisitos
- Node.js 18+ y npm 9+.
- Ionic CLI (`npm i -g @ionic/cli`).
- Capacitor CLI (incluido con Ionic).
- Android Studio (para builds nativos) y Xcode (macOS) si corresponde.
- Backend/API corriendo en `http://localhost:8086` o tunnel equivalente.

## Pasos
1. **Clonar el repo**  
   ```bash
   git clone https://example.com/sazon.git
   cd sazon/layoutBasica
   ```

2. **Instalar dependencias**  
   ```bash
   npm install
   ```

3. **Variables de entorno**  
   - Crear `layoutBasica/.env` con:  
     ```
     VITE_API_BASE_URL=http://localhost:8086/api
     ```
   - Si usas Dev Tunnels u otro proxy, actualiza el valor.

4. **Ejecutar en navegador**  
   ```bash
   npm run dev
   # Vite sirve en http://localhost:5173
   ```

5. **Ejecutar en dispositivo/emulador**  
   ```bash
   ionic cap sync
   ionic cap open android   # o ios
   ```
   Luego compila desde Android Studio/Xcode.

6. **Lint/format**  
   ```bash
   npm run lint
   npm run build
   ```

7. **Backend**  
   - Asegúrate de iniciar el servicio REST (no forma parte de este repo).  
   - Endpoints esperados: `/auth/*`, `/recetas`, `/usuarios`, `/recetas/{id}/favoritos`, etc.

8. **Subida de imágenes (futuro)**  
   - Se migrará a `multipart/form-data`. Mientras tanto se aceptan URLs.  
   - Coordinar con el equipo backend para las rutas definitivas.

## Estructura Clave
- `src/views/TabXPage.vue`: UI principal.
- `src/services/*`: llamadas HTTP y manejo de tokens/localStorage.
- `src/config/api.js`: define cómo resolver `API_BASE_URL`.
- `documentation/`: arquitectura, backlog, manuales.
- `data-base/`, `backend/`, `app-movil/`: documentación técnica complementaria.
# Guía de instalación y configuración para desarrolladores

## Requisitos previos

- **Node.js 18+** y npm.
- **Java 17** (JDK).
- **Maven 3.9+** (o usar `./mvnw` incluido).
- **Android Studio / SDK** si se compilará la app móvil.
- Git, cURL y Postman (opcional) para probar la API.

## Clonación del repositorio

```bash
git clone <repo-url> sazondemo
cd sazondemo
```

La estructura relevante tras la clonación:

```
/layoutBasica        → frontend Ionic/Vue + Capacitor
/sazón/sazón         → backend Spring Boot
/documentation       → documentación generada
/data-base           → esquemas y ERD
```

## Frontend (`layoutBasica`)

1. Instalar dependencias:
   ```bash
   cd layoutBasica
   npm install
   ```
2. Configurar variables (opcional): copiar `.env.example` → `.env` y definir `VITE_API_BASE_URL`.
3. Ejecutar en desarrollo:
   ```bash
   npm run dev
   ```
   Abre `http://localhost:5173`. Para usar Capacitor:
   ```bash
   npx cap sync android
   npx cap open android
   ```

## Backend (`sazón/sazón`)

1. Instalar dependencias y compilar:
   ```bash
   cd ../sazón/sazón
   ./mvnw clean install
   ```
2. Configurar `src/main/resources/application.properties` (puerto, credenciales BD). Por defecto usa H2.
3. Ejecutar:
   ```bash
   ./mvnw spring-boot:run
   ```
   La API queda en `http://localhost:8080` (ajusta según propiedades). Endpoints principales documentados en `backend/endpoints.md`.

## Base de datos

- Script inicial disponible en `data-base/schema.sql`.  
- Para PostgreSQL: ejecutar `CREATE EXTENSION "uuid-ossp";` y luego las definiciones de tablas.  
- Para MySQL/H2: adaptar tipos UUID según motor.

## Troubleshooting rápido

| Problema | Solución |
|----------|----------|
| `npm run dev` falla por puerto ocupado | Cambiar `server.port` en `vite.config.ts` o cerrar procesos en 5173. |
| El frontend no puede iniciar sesión | Verificar que el backend esté corriendo en `http://localhost:8086` (según `api.js`) y que el token simulado se guarde en `localStorage`. |
| Error `No qualifying bean of type AuthenticationManager` | Asegurarse de que `SecurityConfig` exponga un `AuthenticationManager` bean (ya definido). Ejecutar `mvn clean`. |
| Android build falla por SDK | Abre `layoutBasica/android` en Android Studio y acepta la instalación de SDKs faltantes. |

> Consejo: ejecuta backend y frontend simultáneamente para probar flujos reales. Usa la colección Postman incluida en `backend/endpoints.md` o crea una nueva a partir de esa referencia.

