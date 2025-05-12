package com.ssdd.taller.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "usuarios", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 3, max = 25)
    @Column(unique = true, nullable = false)
    private String username;

    @NotBlank(message = "El email no puede estar vacío")
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 6, max = 25)
    private String password;

    @NotBlank(message = "Debe asignarse un rol")
    private String rol;

    // Constructores
    public Usuario() {
    }

    public Usuario(String username, String email, String password, String rol) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.rol = rol;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}