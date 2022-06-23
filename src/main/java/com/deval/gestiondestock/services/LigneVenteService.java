package com.deval.gestiondestock.services;

import com.deval.gestiondestock.dto.LigneVenteDto;
import com.deval.gestiondestock.dto.VentesDto;

import java.math.BigDecimal;
import java.util.List;

public interface LigneVenteService {


    LigneVenteDto save(LigneVenteDto dto);

    LigneVenteDto findById(Integer id);

    List<LigneVenteDto> findAll();

    void delete(Integer id);

}
