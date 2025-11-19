# Análisis de Coherencia: Documentación vs Código

**Fecha de análisis:** 2024  
**Objetivo:** Verificar que la documentación refleje fielmente el código implementado.

---

## Resumen Ejecutivo

Se identificaron **discrepancias importantes** entre la documentación y el código real, principalmente en:
- Estructura de base de datos (nombres de tablas, tipos de datos, relaciones)
- Configuración del servidor (puerto, nombre de base de datos)
- Entidades y campos faltantes o incorrectos

---

## 1. Base de Datos

### 1.1 Discrepancias en Nombres de Tablas

| Documentación | Código Real | Estado |
|--------------|-------------|--------|
| `favoritos` | `guardados` | ❌ **Incorrecto** |
| `estadisticas_receta` | No existe | ❌ **No implementado** |

**Acción requerida:** Actualizar `schema.md` y `erd.md` para reflejar `guardados` en lugar de `favoritos`.

### 1.2 Discrepancias en Tipos de Datos

| Entidad | Campo | Documentación | Código Real | Estado |
|---------|-------|---------------|-------------|--------|
| `ingredientes` | `id` | BIGINT | `Long` (IDENTITY) | ✅ Correcto |
| `pasos` | `id` | BIGINT | `Long` (IDENTITY) | ✅ Correcto |
| `imagenes` | `id` | UUID | `Long` (IDENTITY) | ❌ **Incorrecto** |
| `guardados` | `guardado_id` | UUID | `Long` (IDENTITY) | ❌ **Incorrecto** |

**Nota:** El ERD muestra `ingrediente_id`, `paso_id`, `imagen_id` como UUID, pero en el código son `Long` con `GenerationType.IDENTITY`.

### 1.3 Campos Faltantes o Incorrectos

#### Tabla `recetas`
- ❌ **Falta:** `imagen_principal_url` (documentado pero no existe en entidad)
- ✅ **Existe:** Las imágenes están en tabla separada `imagenes` con campo `es_principal`
- ✅ **Existe:** Campo `estado` con enum `BORRADOR`, `PUBLICADA`, `ELIMINADA`

#### Tabla `imagenes`
- ✅ **Existe:** `storage_key`, `ancho_px`, `alto_px`, `orden`, `es_principal` (no documentados completamente)
- ❌ **Falta en documentación:** Metadatos de imágenes

#### Tabla `guardados`
- ✅ **Existe:** `guardado_id` (Long, IDENTITY)
- ✅ **Existe:** `user_id`, `recipe_id` (UUID)
- ✅ **Existe:** `creado_en` (LocalDateTime)
- ✅ **Constraint único:** `(user_id, recipe_id)`

### 1.4 Tabla No Implementada

- ❌ **`estadisticas_receta`**: Documentada pero no existe en el código
  - Las estadísticas se calculan dinámicamente en el servicio

---

## 2. Configuración del Servidor

### 2.1 Puerto del Servidor

| Documentación | Código Real | Estado |
|--------------|-------------|--------|
| `8080` (en algunos docs) | `8086` | ❌ **Incorrecto** |

**Ubicación del error:**
- `CREDENCIALES_TESTING.md` menciona `localhost:8080`
- `application.properties` define `server.port=8086`
- `api.js` del frontend usa `localhost:8086`

**Acción requerida:** Actualizar todos los documentos que mencionen el puerto 8080.

### 2.2 Nombre de Base de Datos

| Documentación | Código Real | Estado |
|--------------|-------------|--------|
| `sazonDB` | `sanzonDB` (typo) | ⚠️ **Inconsistente** |

**Nota:** En `application.properties` aparece `sanzonDB` (posible typo de "sazonDB").

---

## 3. Endpoints de la API

### 3.1 Endpoints Documentados vs Implementados

✅ **Todos los endpoints documentados en `endpoints.md` están implementados correctamente.**

**Verificación:**
- ✅ `/api/auth/register` - Implementado
- ✅ `/api/auth/login` - Implementado
- ✅ `/api/auth/logout` - Implementado
- ✅ `/api/auth/verify` - Implementado
- ✅ `/api/recetas` (GET, POST) - Implementado
- ✅ `/api/recetas/{id}` (GET, PUT, DELETE) - Implementado
- ✅ `/api/recetas/{id}/favoritos` (POST, DELETE) - Implementado
- ✅ `/api/usuarios/perfil` (GET, PUT) - Implementado
- ✅ `/api/usuarios` (GET, PUT, DELETE) - Implementado (admin)

### 3.2 Endpoints Adicionales No Documentados

Los siguientes endpoints existen en el código pero no están completamente documentados:

- ✅ `/api/recetas/todas` - Lista todas las recetas (admin)
- ✅ `/api/recetas/{id}/publicar` - Publicar receta
- ✅ `/api/recetas/{id}/despublicar` - Despublicar receta
- ✅ `/api/recetas/buscar/ingrediente` - Búsqueda por ingrediente
- ✅ `/api/recetas/populares` - Recetas populares
- ✅ `/api/usuarios/perfil/password` - Cambiar contraseña
- ✅ `/api/usuarios/perfil/recetas` - Recetas del usuario
- ✅ `/api/usuarios/perfil/recetas/publicadas` - Solo publicadas
- ✅ `/api/usuarios/perfil/favoritos` - Favoritos del usuario
- ✅ `/api/usuarios/perfil/estadisticas` - Estadísticas
- ✅ `/api/usuarios/buscar` - Búsqueda de usuarios
- ✅ `/api/usuarios/{id}/promover` - Promover a admin
- ✅ `/api/usuarios/{id}/degradar` - Degradar a user

**Nota:** Estos endpoints están documentados en `endpoints.md`, pero algunos detalles pueden faltar.

---

## 4. Frontend

### 4.1 Servicios Implementados

✅ **Todos los servicios documentados están implementados:**
- ✅ `authService.js` - Login, registro, verificación, logout
- ✅ `recipeService.js` - CRUD de recetas, favoritos, búsqueda
- ✅ `userService.js` - Perfil, actualización, administración

### 4.2 Validaciones Implementadas

✅ **Validación de email:**
- Implementada en `src/utils/validators.js`
- Integrada en `HomePage.vue` y `Tab4Page.vue`

### 4.3 Manejo de Errores

✅ **Mensajes descriptivos:**
- Implementados en todos los servicios
- Funciones `getErrorMessage()` y `getConnectionErrorMessage()`

### 4.4 Rutas

✅ **Todas las rutas documentadas están implementadas:**
- `/home` - HomePage (login/registro)
- `/tabs/tab1` - Lista de recetas
- `/tabs/tab2` - Crear receta
- `/tabs/tab3` - Editar receta
- `/tabs/tab4` - Administración (registro usuarios)
- `/tabs/tab5` - Perfil
- `/tabs/tab6` - Administración (gestión usuarios)
- `/recipe/:id` - Detalle de receta

---

## 5. Arquitectura del Sistema

### 5.1 Stack Tecnológico

✅ **Correcto:**
- Frontend: Ionic + Vue 3 + Vite
- Backend: Spring Boot 3 (Java 17)
- Base de datos: PostgreSQL (desarrollo: H2 mencionado pero no configurado)

### 5.2 Estructura de Capas

✅ **Correcto:**
- Controllers → Services → Repositories → Entities
- DTOs para transferencia de datos
- Spring Security para autenticación

---

## 6. Recomendaciones de Corrección

### Prioridad Alta

1. **Actualizar `schema.md`:**
   - Cambiar `favoritos` → `guardados`
   - Corregir tipos de datos de IDs (Long vs UUID)
   - Eliminar referencia a `estadisticas_receta`
   - Agregar tabla `imagenes` con todos sus campos

2. **Actualizar `erd.md`:**
   - Corregir diagrama para mostrar `guardados` en lugar de `favoritos`
   - Actualizar tipos de datos de IDs
   - Agregar relación con tabla `imagenes`

3. **Actualizar puerto en documentación:**
   - Cambiar todas las referencias de `8080` → `8086`
   - Especialmente en `CREDENCIALES_TESTING.md`

### Prioridad Media

4. **Verificar nombre de base de datos:**
   - Confirmar si `sanzonDB` es intencional o debe ser `sazonDB`
   - Actualizar documentación según decisión

5. **Completar documentación de endpoints:**
   - Agregar ejemplos de request/response para endpoints menos documentados
   - Especificar códigos de estado HTTP para cada endpoint

### Prioridad Baja

6. **Documentar metadatos de imágenes:**
   - Explicar campos `storage_key`, `ancho_px`, `alto_px`, `orden`, `es_principal`

7. **Actualizar diagramas de arquitectura:**
   - Incluir tabla `imagenes` en diagramas
   - Actualizar flujos de datos si es necesario

---

## 7. Checklist de Verificación

- [ ] `schema.md` actualizado con estructura real
- [ ] `erd.md` corregido con relaciones correctas
- [ ] `endpoints.md` verificado con puerto 8086
- [ ] `CREDENCIALES_TESTING.md` actualizado con puerto 8086
- [ ] `system-architecture.md` verificado
- [ ] `developer-setup.md` verificado
- [ ] Todos los endpoints documentados correctamente

---

## 8. Notas Adicionales

### Funcionalidades Implementadas pero No Documentadas

1. **Validación de email en frontend:**
   - Archivo `src/utils/validators.js`
   - Integrado en formularios de login y registro

2. **Manejo de errores descriptivos:**
   - Funciones `getErrorMessage()` en todos los servicios
   - Mensajes específicos por código HTTP y operación

3. **Estados de receta:**
   - `BORRADOR`, `PUBLICADA`, `ELIMINADA`
   - Métodos de negocio en entidad `Receta`

### Funcionalidades Documentadas pero No Implementadas

1. **Upload de imágenes:**
   - Documentado como "pendiente" en `system-architecture.md`
   - Actualmente se usa URL de imagen (no file upload)

---

**Fin del análisis**

