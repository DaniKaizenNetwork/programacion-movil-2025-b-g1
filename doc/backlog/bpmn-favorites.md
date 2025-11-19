# BPMN – Gestionar Favoritos

```mermaid
flowchart TD
    U([Usuario autenticado]) --> A[Explora recetas]
    A --> B{Decide acción}
    B -->|Agregar| C{¿Ya es favorita?}
    C -->|Sí| D[Mostrar aviso "ya agregada"]
    C -->|No| E[[POST /recetas/{id}/favoritos]]
    B -->|Quitar| F[[DELETE /recetas/{id}/favoritos]]

    E --> G{API valida token}
    F --> G
    G -->|Error| H[Mostrar error contextual]
    G -->|OK| I[Actualizar estado en DB]
    I --> J[API responde éxito]
    J --> K[Actualizar UI (iconos, contadores)]
```

