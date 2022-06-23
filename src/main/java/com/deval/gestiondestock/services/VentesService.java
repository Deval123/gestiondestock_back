package com.deval.gestiondestock.services;

import com.deval.gestiondestock.dto.VentesDto;

import java.time.Instant;
import java.util.List;

public interface VentesService {

    VentesDto save(VentesDto dto);

    VentesDto findById(Integer id);

    VentesDto findByDateVente(Instant dateVente);

    VentesDto findByCode(String code);

    List<VentesDto> findAll();

    void delete(Integer id);

}
