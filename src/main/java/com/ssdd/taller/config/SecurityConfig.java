package com.ssdd.taller.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuración de seguridad de la aplicación.
 * Define qué rutas son públicas, la página de login y logout,
 * y el cifrado de contraseñas.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Definimos permisos de acceso
                .authorizeHttpRequests(auth -> auth
                        // Permitimos todos a página de inicio, login y recursos estáticos
                        .requestMatchers("/", "/login", "/registro","/gimnasios","/api/gimnasios", "/css/**", "/js/**", "/images/**").permitAll()
                        // Restringir esta ruta solo a usuarios con rol ADMIN
                        .requestMatchers("/api/externa/archivo/restringido").hasRole("ADMIN")
                        // El resto requiere autenticación
                        .anyRequest().authenticated()
                )
                // Configuramos el formulario de login
                .formLogin(form -> form
                        .loginPage("/login")           // URL de la vista de login
                        .defaultSuccessUrl("/?login", true)  // A dónde ir tras login correcto
                        .permitAll()
                )
                // Configuramos logout
                .logout(logout -> logout
                        .logoutUrl("/logout")              // URL para hacer logout
                        .logoutSuccessUrl("/login?logout") // Redirigir tras logout
                        .permitAll()
                )
                .exceptionHandling(e -> e
                        .accessDeniedPage("/acceso-denegado")
                )
        ;
        return http.build();
    }

    /**
     * Bean que Spring usará para encriptar y comprobar las contraseñas con BCrypt.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}