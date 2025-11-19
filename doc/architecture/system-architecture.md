# Arquitectura del Sistema

Este documento resume la arquitectura de la plataforma **Sazón**, una solución híbrida construida con Ionic + Vue que consume un backend de servicios REST para gestionar recetas, usuarios y contenido multimedia.

---

## Visión General

- **Usuarios finales** (creadores y comensales) interactúan con la aplicación móvil/web híbrida.
- La **App Móvil (Ionic/Vue)** maneja autenticación, navegación por pestañas, creación/edición de recetas, favoritos, perfiles y administración básica.
- La **API Backend** expone endpoints REST para autenticación, manejo de usuarios, recetas, estadísticas y archivos.
- El sistema utiliza una **Base de Datos relacional** para persistir usuarios y recetas, y un **Almacenamiento de archivos** para imágenes.

---

## C4 – Nivel 1 (Contexto)

```mermaid
graph LR
    subgraph Users
        U1[Usuario Creador]
        U2[Usuario Consumidor]
        U3[Administrador]
    end

    U1 -->|Crea/edita recetas| APP
    U2 -->|Explora recetas\nagrega favoritos| APP
    U3 -->|Gestiona usuarios\nrecetas globales| APP

    APP[App Móvil/Web<br>Ionic + Vue] --> API[API Backend REST]
    API --> DB[(Base de Datos)]
    API --> FS[(Almacenamiento de Imágenes)]
    API --> EXT[Servicios Externos\n(notificaciones, analytics)]
```

---

## C4 – Nivel 2 (Contenedores)

```mermaid
graph TB
    subgraph Frontend
        APP[Ionic/Vue SPA]
        SERVICE[Servicios locales\n(authService, recipeService,\nuserService)]
    end

    subgraph Backend
        API[REST API\n(Node/Java/.NET)]
        AUTH[Auth Module]
        REC[Recetas Module]
        USER[Usuarios Module]
        MEDIA[Módulo de archivos]
    end

    DB[(DB relacional\nUsers, Recipes, Favorites, Stats)]
    STORAGE[(Blob/S3\nImágenes de recetas)]

    APP --> SERVICE
    SERVICE -->|HTTPS JSON| API
    API --> DB
    MEDIA --> STORAGE
    AUTH --> DB
    REC --> DB
    USER --> DB
    APP -->|Assets| STORAGE
```

---

## Decisiones Técnicas Clave

| Decisión | Estado | Justificación |
| --- | --- | --- |
| **Arquitectura híbrida Ionic/Vue** | Aprobada | Permite compilar la misma base de código para Android/iOS/Web. |
| **Servicios front-end centralizados** (`authService`, `recipeService`, `userService`) | Aprobada | Facilita reutilización y manejo consistente de tokens/localStorage. |
| **REST API externa** | Aprobada | Se mantiene un backend desacoplado que puede escalar y versionarse de forma independiente. |
| **Persistencia relacional** | Aprobada | Las recetas y usuarios tienen relaciones naturales (favoritos, estadísticas). |
| **Uso de almacenamiento de archivos externo** | Requerida | Para soportar upload de imágenes (cámara, galería) sin sobrecargar la DB. |
| **Validaciones multiplataforma** | En progreso | Se ejecutan en frontend (pattern, MIME) y backend (MIME, tamaño, autenticación). |
| **Ciclo DevOps** | Pendiente | Se recomienda integrar CI/CD para ejecutar pruebas unitarias y despliegues de la app y la API. |

---

## Componentes Frontend Destacados

- **Páginas TabX** (`Tab1Page` – recetas, `Tab2Page` – creación, `Tab3Page` – edición, `Tab5Page` – perfil).
- **Servicios**: `authService`, `userService`, `recipeService` centralizan llamadas HTTP, manejo de tokens y almacenamiento en `localStorage`.
- **Config**: `src/config/api.js` define cómo resolver la URL base (Vite + proxy/túneles).

---

## Riesgos y Próximos Pasos

1. **Upload de imágenes** aún se realiza mediante URLs; se debe finalizar la transición a `multipart/form-data`.
2. **Manejo de errores**: se está mejorando la mensajería específica por código de estado (ya implementado para auth/usuarios/recetas).
3. **Monitoreo**: agregar telemetría (App + API) para detectar fallos en el flujo de recetas/favoritos.
# Arquitectura del Sistema

## Visión General

El proyecto **Sazón** se compone de dos grandes bloques:

1. **Frontend híbrido (`layoutBasica/`)**  
   - Construido con **Ionic + Vue 3 + Vite**.  
   - Consume la API REST exponiendo servicios en `src/services/*.js`.  
   - Se empaqueta para la web y para Android mediante **Capacitor** (ver `layoutBasica/android/`).

2. **Backend (`sazón/sazón/`)**  
   - Aplicación **Spring Boot 3** (Java 17) con Spring Web, Spring Security y Spring Data JPA.  
   - Persistencia con H2 en desarrollo y drivers listos para MySQL/PostgreSQL.  
   - Expone endpoints REST en `/api/auth`, `/api/recetas` y `/api/usuarios`.

Ambos componentes se comunican usando JSON sobre HTTP. La seguridad actual utiliza un token “simulado” (`token_<uuid>`), gestionado por `AuthController`.

## Estilo Arquitectónico

- **Capas**: Presentación (Ionic/Vue), Servicios (fetch wrappers), API REST (Spring Controllers), Dominio (entidades JPA), Persistencia (Repositories JPA) y Base de datos relacional.
- **Despliegue**: Arquitectura monolítica en el backend + cliente SPA. La app móvil y la web comparten la misma base de código (Ionic).
- **Comunicación**: HTTP/JSON. No existe comunicación directa entre frontend y BD.

## Flujo de Datos Simplificado

1. El usuario interactúa con vistas como `Tab1Page.vue` (lista de recetas) o `Tab2Page.vue` (creación).  
2. Las vistas invocan a `recipeService`, `authService` o `userService`.  
3. Los servicios llaman a `getApiUrl()` y hacen peticiones `fetch` autenticadas con Bearer `token_<uuid>`.  
4. El backend valida la solicitud (Spring Security + `UsuarioService`).  
5. Los datos pasan por los servicios (`RecetaService`, `UsuarioService`) y repositorios JPA.  
6. El resultado se devuelve como DTO (`RecetaDTO`, `UsuarioDTO`) al frontend.

## Despliegue recomendado

| Componente | Entorno | Comando principal |
|------------|---------|-------------------|
| Frontend web | Nodo con Node 18+ | `npm run dev` (Vite) |
| App Android | Android Studio / Gradle | `npx cap open android` y build estándar |
| Backend | JVM 17 | `./mvnw spring-boot:run` |
| Base de datos | H2 (dev) / MySQL/PostgreSQL (prod) | `schema.sql` + migraciones JPA |

La estructura modular permite reemplazar el motor de base de datos o añadir microservicios más adelante sin cambios drásticos en el frontend.*** End Patch

