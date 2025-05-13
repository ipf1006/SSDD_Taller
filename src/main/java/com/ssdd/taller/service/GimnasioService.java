package com.ssdd.taller.service;

import com.ssdd.taller.dto.GimnasioDto;
import com.ssdd.taller.model.Gimnasio;
import com.ssdd.taller.repository.GimnasioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

@Service
public class GimnasioService {
    private final GimnasioRepository gimnasioRepository;
    private final ModelMapper modelMapper;

    public GimnasioService(GimnasioRepository gimnasioRepository, ModelMapper modelMapper) {
        this.gimnasioRepository = gimnasioRepository;
        this.modelMapper = modelMapper;
    }

    public List<GimnasioDto> listarGimnasiosDto() {
        // Lee todas las entidades
        List<Gimnasio> gimnasios = gimnasioRepository.findAll();

        // Prepara el iterador y la lista de DTOs vacía
        ListIterator<Gimnasio> it = gimnasios.listIterator();
        List<GimnasioDto> dtos = new ArrayList<>();

        // Recorre todas las entidades
        while (it.hasNext()) {
            Gimnasio gimnasio = it.next();

            // Para cada entidad, crea un DTO vacío y mapea
            GimnasioDto dto = new GimnasioDto();
            modelMapper.map(gimnasio, dto);

            dtos.add(dto);
        }
        return dtos;
    }
}
