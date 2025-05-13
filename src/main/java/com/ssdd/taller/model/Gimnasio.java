package com.ssdd.taller.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "gimnasios")
public class Gimnasio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del gimnasio no puede estar vacío")
    @Size(max = 50, message = "El nombre no puede exceder 50 caracteres")
    @Column(nullable = false)
    private String nombre;

    @NotBlank(message = "La dirección no puede estar vacía")
    @Size(max = 100, message = "La dirección no puede exceder 100 caracteres")
    @Column(nullable = false)
    private String direccion;

    @Column(nullable = false)
    private double latitud;

    @Column(nullable = false)
    private double longitud;

    public Gimnasio(String nombre, String direccion, double latitud, double longitud) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.latitud = latitud;
        this.longitud = longitud;
    }
}