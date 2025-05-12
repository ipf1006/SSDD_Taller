package com.ssdd.taller.repository;

import com.ssdd.taller.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Busca un Usuario por su email (username en el login).
    Optional<Usuario> findByUsername(String username);
}