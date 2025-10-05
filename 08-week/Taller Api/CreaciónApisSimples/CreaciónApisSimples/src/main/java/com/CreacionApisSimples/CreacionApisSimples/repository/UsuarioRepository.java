package com.CreacionApisSimples.CreacionApisSimples.repository;

import com.CreacionApisSimples.CreacionApisSimples.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    
    /**
     * Busca un usuario por su email
     * @param email el email del usuario
     * @return Optional<Usuario> el usuario encontrado o vacío
     */
    Optional<Usuario> findByEmail(String email);
    
    /**
     * Verifica si existe un usuario con el email dado
     * @param email el email a verificar
     * @return true si existe, false en caso contrario
     */
    boolean existsByEmail(String email);
}
