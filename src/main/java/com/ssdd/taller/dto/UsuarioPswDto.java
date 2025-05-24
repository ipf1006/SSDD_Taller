package com.ssdd.taller.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioPswDto {

    @Size(min = 5, max = 25, message = "El nombre de usuario debe tener entre 5 y 25 caracteres.")
    private String username;

    @NotBlank(message = "El email es obligatorio.")
    @Email(message = "El email introducido no es válido.")
    private String email;

    @NotBlank(message = "La contraseña no puede estar vacía.")
    @Size(min = 8, max = 20, message = "La contraseña debe tener entre 8 y 20 caracteres.")
    @Pattern(
            // (?=.*\\d)          : al menos un número
            // (?=.*[a-z])        : al menos una minúscula
            // (?=.*[A-Z])        : al menos una mayúscula
            // (?=.*[!@#$%^&*()_.-]) : al menos uno de estos caracteres especiales
            regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_.-]).+$",
            message = "La contraseña debe incluir al menos un número, una minúscula, una mayúscula y un carácter especial."
    )
    private String password;

    @NotBlank(message = "La confirmación de contraseña es obligatoria.")
    private String confirmPassword;

    private String role;
}