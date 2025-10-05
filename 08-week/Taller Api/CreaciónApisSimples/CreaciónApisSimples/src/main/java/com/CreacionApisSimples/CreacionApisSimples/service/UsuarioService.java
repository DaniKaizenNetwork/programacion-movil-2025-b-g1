package com.CreacionApisSimples.CreacionApisSimples.service;

import com.CreacionApisSimples.CreacionApisSimples.dto.UsuarioRequest;
import com.CreacionApisSimples.CreacionApisSimples.dto.UsuarioResponse;
import com.CreacionApisSimples.CreacionApisSimples.exception.EmailAlreadyExistsException;
import com.CreacionApisSimples.CreacionApisSimples.exception.ResourceNotFoundException;
import com.CreacionApisSimples.CreacionApisSimples.model.Usuario;
import com.CreacionApisSimples.CreacionApisSimples.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class UsuarioService {
    
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    
    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    /**
     * Crea un nuevo usuario
     */
    public UsuarioResponse crearUsuario(UsuarioRequest request) {
        // Verificar si el email ya existe
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("El email " + request.getEmail() + " ya está registrado");
        }
        
        // Crear nuevo usuario
        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setEmail(request.getEmail());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        
        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        return convertirAUsuarioResponse(usuarioGuardado);
    }
    
    /**
     * Obtiene todos los usuarios
     */
    @Transactional(readOnly = true)
    public List<UsuarioResponse> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::convertirAUsuarioResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * Obtiene un usuario por su ID
     */
    @Transactional(readOnly = true)
    public UsuarioResponse obtenerUsuarioPorId(UUID id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));
        return convertirAUsuarioResponse(usuario);
    }
    
    /**
     * Actualiza un usuario existente
     */
    public UsuarioResponse actualizarUsuario(UUID id, UsuarioRequest request) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));
        
        // Verificar si el email ya existe en otro usuario
        if (!usuario.getEmail().equals(request.getEmail()) && 
            usuarioRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("El email " + request.getEmail() + " ya está registrado");
        }
        
        // Actualizar datos
        usuario.setNombre(request.getNombre());
        usuario.setEmail(request.getEmail());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        
        Usuario usuarioActualizado = usuarioRepository.save(usuario);
        return convertirAUsuarioResponse(usuarioActualizado);
    }
    
    /**
     * Elimina un usuario
     */
    public void eliminarUsuario(UUID id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuario no encontrado con ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }
    
    /**
     * Busca un usuario por email (para autenticación)
     */
    @Transactional(readOnly = true)
    public Usuario obtenerUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con email: " + email));
    }
    
    /**
     * Convierte una entidad Usuario a UsuarioResponse
     */
    private UsuarioResponse convertirAUsuarioResponse(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getFechaCreacion()
        );
    }
}
