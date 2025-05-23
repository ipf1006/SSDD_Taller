package com.ssdd.taller.service;

import com.ssdd.taller.model.Usuario;
import com.ssdd.taller.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Servicio que Spring Security utiliza para cargar los datos del usuario
 * (username y contraseña) desde la base de datos.
 */
@Service
public class AppUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepo;

    public AppUserDetailsService(UsuarioRepository usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Buscamos el usuario (por username) en la tabla usuarios
        Optional<Usuario> op = usuarioRepo.findByUsername(username);
        Usuario usuario = op.orElseThrow(
                () -> new UsernameNotFoundException("Usuario no encontrado: " + username)
        );

        // UserDetails que Spring Security necesita
        return User.builder()
                .username(usuario.getUsername())
                .password(usuario.getPassword()) // Hash guardado en BD
                .roles(usuario.getRole())
                .build();
    }
}