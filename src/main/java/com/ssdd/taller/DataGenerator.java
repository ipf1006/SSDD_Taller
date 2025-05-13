package com.ssdd.taller;

import com.ssdd.taller.model.Gimnasio;
import com.ssdd.taller.model.Usuario;
import com.ssdd.taller.repository.GimnasioRepository;
import com.ssdd.taller.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataGenerator {

    @Bean
    public CommandLineRunner cargarUsuariosIniciales(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, GimnasioRepository gimnasioRepository) {
        return args -> {

            // —— Usuarios iniciales ——
            if (usuarioRepository.count() == 0) {
                usuarioRepository.save(new Usuario("Ignacio", "ipf1006@alu.ubu.es", passwordEncoder.encode("admin123"), "ADMIN"));
                usuarioRepository.save(new Usuario("Usuario1", "usuario1@alu.ubu.es", passwordEncoder.encode("user123"), "USER"));
                usuarioRepository.save(new Usuario("Usuario2", "usuario2@alu.ubu.es", passwordEncoder.encode("user123"), "USER"));
                System.out.println("Usuarios insertados correctamente.");
            } else {
                System.out.println("Los usuarios ya existen.");
            }

            // —— Gimnasios iniciales ——
            if (gimnasioRepository.count() == 0) {
                gimnasioRepository.save(new Gimnasio("SD Fitness Vallecas", "C. del Vizconde de Arlessón, 1", 40.384276, -3.665274));
                gimnasioRepository.save(new Gimnasio("SD Fitness Fuencarral", "C. de Badalona, 88", 40.494019, -3.693623));
                gimnasioRepository.save(new Gimnasio("SD Fitness Barcelona", "Carrer de l'Escorial", 41.410186, 2.158586));
                gimnasioRepository.save(new Gimnasio("SD Fitness Tenerife", "Av. de Anaga, 4", 28.479850, -16.241820));
                System.out.println("Gimnasios insertados correctamente.");
            } else {
                System.out.println("Los gimnasios ya existen.");
            }
        };
    }
}

