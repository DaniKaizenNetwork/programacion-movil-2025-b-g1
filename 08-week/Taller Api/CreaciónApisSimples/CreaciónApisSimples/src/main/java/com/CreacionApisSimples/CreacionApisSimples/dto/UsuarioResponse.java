package com.CreacionApisSimples.CreacionApisSimples.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class UsuarioResponse {
    
    private UUID id;
    private String nombre;
    private String email;
    private LocalDateTime fechaCreacion;
    
    // Constructores
    public UsuarioResponse() {
    }
    
    public UsuarioResponse(UUID id, String nombre, String email, LocalDateTime fechaCreacion) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.fechaCreacion = fechaCreacion;
    }
    
    // Getters y Setters
    public UUID getId() {
        return id;
    }
    
    public void setId(UUID id) {
        this.id = id;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
    
    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
