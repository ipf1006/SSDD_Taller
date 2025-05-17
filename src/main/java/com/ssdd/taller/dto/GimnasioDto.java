package com.ssdd.taller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class GimnasioDto {

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 25, message = "El nombre no puede superar los 25 caracteres")
    private String nombre;

    @NotBlank(message = "La dirección no puede estar vacía")
    @Size(max = 50, message = "La dirección no puede superar los 50 caracteres")
    private String direccion;

    @NotNull(message = "La latitud es obligatoria")
    private double latitud;

    @NotNull(message = "La longitud es obligatoria")
    private double longitud;
}
