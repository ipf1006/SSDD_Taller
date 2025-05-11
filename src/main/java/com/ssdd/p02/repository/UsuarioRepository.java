package com.ssdd.p02.repository;

import com.ssdd.p02.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Busca un Usuario por su email (username en el login).
    Optional<Usuario> findByEmail(String username);
}