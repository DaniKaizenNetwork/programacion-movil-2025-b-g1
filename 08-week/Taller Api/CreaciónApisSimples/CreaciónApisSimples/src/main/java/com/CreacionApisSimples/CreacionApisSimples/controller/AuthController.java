package com.CreacionApisSimples.CreacionApisSimples.controller;

import com.CreacionApisSimples.CreacionApisSimples.dto.LoginRequest;
import com.CreacionApisSimples.CreacionApisSimples.dto.LoginResponse;
import com.CreacionApisSimples.CreacionApisSimples.dto.UsuarioRequest;
import com.CreacionApisSimples.CreacionApisSimples.dto.UsuarioResponse;
import com.CreacionApisSimples.CreacionApisSimples.model.Usuario;
import com.CreacionApisSimples.CreacionApisSimples.service.JwtService;
import com.CreacionApisSimples.CreacionApisSimples.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticación", description = "Endpoints para autenticación y registro de usuarios")
public class AuthController {
    
    private final UsuarioService usuarioService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    
    public AuthController(UsuarioService usuarioService, JwtService jwtService, 
                         PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
        this.usuarioService = usuarioService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }
    
    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Autentica un usuario y devuelve un token JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login exitoso"),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        try {
            // Buscar usuario por email
            Usuario usuario = usuarioService.obtenerUsuarioPorEmail(request.getEmail());
            
            // Verificar contraseña
                if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            
            // Generar token JWT
            String token = jwtService.generateToken(userDetailsService.loadUserByUsername(usuario.getEmail()));
            
            // Crear respuesta
            UsuarioResponse usuarioResponse = new UsuarioResponse(
                    usuario.getId(),
                    usuario.getNombre(),
                    usuario.getEmail(),
                    usuario.getFechaCreacion()
            );
            
            LoginResponse loginResponse = new LoginResponse(token, "Bearer", usuarioResponse);
            
            return ResponseEntity.ok(loginResponse);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    
    @PostMapping("/register")
    @Operation(summary = "Registrar usuario", description = "Crea un nuevo usuario en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "409", description = "Email ya registrado")
    })
    public ResponseEntity<UsuarioResponse> register(@Valid @RequestBody UsuarioRequest request) {
        UsuarioResponse usuarioCreado = usuarioService.crearUsuario(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCreado);
    }
}
