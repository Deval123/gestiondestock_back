package com.deval.gestiondestock.controller;

import com.deval.gestiondestock.controller.api.UtilisateurApi;
import com.deval.gestiondestock.dto.UtilisateurDto;
import com.deval.gestiondestock.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UtilisateurController implements UtilisateurApi {

    private UtilisateurService utilisateurService;

    @Autowired
    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @Override
    public UtilisateurDto save(UtilisateurDto dto) {
        return utilisateurService.save(dto);
    }

    @Override
    @RequestMapping(value = "/utilisateurs/id/{id}", method = RequestMethod.GET)
    public UtilisateurDto findById(@PathVariable Integer id) {
        return utilisateurService.findById(id);
    }

    @Override
    @RequestMapping(value = "/utilisateurs/nom/{nom}", method = RequestMethod.GET)
    public UtilisateurDto findByNom(@PathVariable String nom) {
        return utilisateurService.findByNom(nom);
    }

    @Override
    @RequestMapping(value = "/utilisateurs/numTel/{numTel}", method = RequestMethod.GET)
    public UtilisateurDto findByNumTel(@PathVariable String numTel) {
        return utilisateurService.findByNumTel(numTel);
    }

    @Override
    @RequestMapping(value = "/utilisateurs/email/{email}", method = RequestMethod.GET)
    public UtilisateurDto findByEmail(@PathVariable String email) {
        return utilisateurService.findByEmail(email);
    }

    @Override
    public List<UtilisateurDto> findAll() {
        return utilisateurService.findAll();
    }

    @Override
    public void delete(Integer id) {
        utilisateurService.delete(id);
    }
}
