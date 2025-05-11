package com.ssdd.p02;

import com.ssdd.p02.model.Usuario;
import com.ssdd.p02.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataGenerator {

    @Bean
    public CommandLineRunner cargarUsuariosIniciales(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (usuarioRepository.count() == 0) {
                usuarioRepository.save(new Usuario("Ignacio", "ipf1006@alu.ubu.es", passwordEncoder.encode("admin123"), "ADMIN"));
                usuarioRepository.save(new Usuario("Alumno2", "alumno2@alu.ubu.es", passwordEncoder.encode("user123"), "USER"));
                usuarioRepository.save(new Usuario("Alumno3", "alumno3@alu.ubu.es", passwordEncoder.encode("user123"), "USER"));
                System.out.println("Usuarios insertados correctamente.");
            } else {
                System.out.println("Los usuarios ya existen.");
            }
        };
    }
}

