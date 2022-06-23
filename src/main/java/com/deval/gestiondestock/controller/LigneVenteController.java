package com.deval.gestiondestock.controller;

import com.deval.gestiondestock.controller.api.LigneVenteApi;
import com.deval.gestiondestock.dto.LigneVenteDto;
import com.deval.gestiondestock.dto.VentesDto;
import com.deval.gestiondestock.services.LigneVenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LigneVenteController implements LigneVenteApi {

    @Autowired
    private LigneVenteService ligneVenteService;

    @Autowired
    public LigneVenteController(LigneVenteService ligneVenteService){
        this.ligneVenteService = ligneVenteService;
    }

    @Override
    public LigneVenteDto save(LigneVenteDto dto) {
        return ligneVenteService.save(dto);
    }

    @Override
    public LigneVenteDto findById(Integer id) {
        return ligneVenteService.findById(id);
    }

    @Override
    public List<LigneVenteDto> findAll() {
        return ligneVenteService.findAll();
    }

    @Override
    public void delete(Integer id) {
        ligneVenteService.delete(id);

    }
}
