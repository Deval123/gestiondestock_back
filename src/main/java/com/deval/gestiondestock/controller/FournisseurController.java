package com.deval.gestiondestock.controller;

import com.deval.gestiondestock.controller.api.FournisseurApi;
import com.deval.gestiondestock.dto.FournisseurDto;
import com.deval.gestiondestock.services.FournisseurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FournisseurController implements FournisseurApi {

    @Autowired
    private FournisseurService fournisseurService;

    @Autowired
    public FournisseurController(FournisseurService fournisseurService){
        this.fournisseurService = fournisseurService;
    }

    @Override
    public FournisseurDto save(FournisseurDto dto) {
        return fournisseurService.save(dto);
    }

    @Override
    public FournisseurDto findById(Integer id) {
        return fournisseurService.findById(id);
    }

    @Override
    public FournisseurDto findByNom(String nom) {
        return fournisseurService.findByNom(nom);
    }


    @Override
    public List<FournisseurDto> findAll() {
        return fournisseurService.findAll();
    }

    @Override
    public void delete(Integer id) {
        fournisseurService.delete(id);

    }
}
