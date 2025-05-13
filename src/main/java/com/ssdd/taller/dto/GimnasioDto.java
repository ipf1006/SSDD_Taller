package com.ssdd.taller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class GimnasioDto {
    private Long id;
    private String nombre;
    private String direccion;
    private double latitud;
    private double longitud;
}
