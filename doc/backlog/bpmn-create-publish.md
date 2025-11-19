# BPMN – Crear y Publicar Receta

```mermaid
flowchart TD
    A([Usuario Autenticado]) --> B[Inicia creación en Tab2]
    B --> C[Ingresa datos básicos\n(título, descripción)]
    C --> D[Captura ingredientes/pasos]
    D --> E[Selecciona imagen desde dispositivo]
    E --> F{Validaciones\n(frontend)}
    F -->|Error| F1[Mostrar mensajes y bloquear envío]
    F -->|OK| G[[Enviar receta a API]]
    G --> H{API valida token,\ncampos y archivo}
    H -->|Error| H1[Respuesta 4xx/5xx\nse muestra en UI]
    H -->|OK| I[Guardar en DB + Almacenar imagen]
    I --> J[API retorna receta normalizada]
    J --> K[Añadir a lista local y redirigir]
```

