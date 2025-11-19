-- Esquema basado en las entidades JPA del backend (sazón/sazón)
-- Adaptado para PostgreSQL (usar uuid-ossp) o MySQL (reemplazar UUID por CHAR(36))

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS usuarios (
    user_id            UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    email              VARCHAR(120) UNIQUE NOT NULL,
    password_hash      VARCHAR(255) NOT NULL,
    nombre             VARCHAR(120) NOT NULL,
    username           VARCHAR(80) UNIQUE NOT NULL,
    bio                TEXT,
    profile_image_url  TEXT,
    role               VARCHAR(10) NOT NULL DEFAULT 'USER',
    creado_en          TIMESTAMP NOT NULL DEFAULT NOW(),
    actualizado_en     TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS recetas (
    recipe_id      UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    autor_id       UUID NOT NULL REFERENCES usuarios(user_id),
    titulo         VARCHAR(255) NOT NULL,
    estado         VARCHAR(15) NOT NULL DEFAULT 'BORRADOR',
    descripcion    TEXT,
    creado_en      TIMESTAMP NOT NULL DEFAULT NOW(),
    actualizado_en TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS ingredientes (
    ingrediente_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    receta_id      UUID NOT NULL REFERENCES recetas(recipe_id) ON DELETE CASCADE,
    nombre         VARCHAR(255) NOT NULL,
    cantidad       VARCHAR(64),
    unidad         VARCHAR(32),
    orden          INTEGER NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS pasos (
    paso_id   UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    receta_id UUID NOT NULL REFERENCES recetas(recipe_id) ON DELETE CASCADE,
    orden     INTEGER NOT NULL DEFAULT 0,
    descripcion TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS imagenes (
    imagen_id   UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    receta_id   UUID NOT NULL REFERENCES recetas(recipe_id) ON DELETE CASCADE,
    url         TEXT NOT NULL,
    storage_key TEXT,
    ancho_px    INTEGER,
    alto_px     INTEGER,
    orden       INTEGER DEFAULT 0,
    es_principal BOOLEAN DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS guardados (
    guardado_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id     UUID NOT NULL REFERENCES usuarios(user_id) ON DELETE CASCADE,
    recipe_id   UUID NOT NULL REFERENCES recetas(recipe_id) ON DELETE CASCADE,
    creado_en   TIMESTAMP NOT NULL DEFAULT NOW(),
    UNIQUE (user_id, recipe_id)
);

CREATE INDEX IF NOT EXISTS idx_recetas_estado ON recetas (estado);
CREATE INDEX IF NOT EXISTS idx_recetas_titulo ON recetas (titulo);
CREATE INDEX IF NOT EXISTS idx_ingredientes_nombre ON ingredientes (nombre);

