package com.ssdd.taller.controller;

import com.ssdd.taller.dto.GimnasioDto;
import com.ssdd.taller.service.GimnasioService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controlador REST encargado de exponer los datos de los gimnasios
 * en formato JSON para que el frontend (JavaScript con fetch)
 * los consuma.
 */
@RestController
@RequestMapping("/api/gimnasios")
public class GimnasioRestController {

    private final GimnasioService service;

    public GimnasioRestController(GimnasioService service) {
        this.service = service;
    }

    @GetMapping
    public List<GimnasioDto> listarGimnasios() {
        return service.listarGimnasiosDto();
    }
}