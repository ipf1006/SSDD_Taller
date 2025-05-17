package com.ssdd.taller.repository;

import com.ssdd.taller.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Busca un usuario por su nombre de usuario.
    Optional<Usuario> findByUsername(String username);

    // Busca un usuario por su email.
    Optional<Usuario> findByEmail(String email);

    // Comprueba si existe un usuario con el nombre de usuario dado.
    boolean existsByUsername(String username);

    // Comprueba si existe un usuario con el email dado.
    boolean existsByEmail(String email);
}