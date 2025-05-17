package com.ssdd.taller.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioPswDto {

    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(min = 5, max = 25, message = "El nombre de usuario debe tener entre 5 y 25 caracteres")
    private String username;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email introducido no es válido")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;

    @NotBlank(message = "La confirmación de contraseña es obligatoria")
    private String confirmPassword;
}