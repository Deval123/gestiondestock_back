package com.deval.gestiondestock.controller;

import com.deval.gestiondestock.controller.api.EntrepriseApi;
import com.deval.gestiondestock.dto.EntrepriseDto;
import com.deval.gestiondestock.model.Utilisateur;
import com.deval.gestiondestock.services.ArticleService;
import com.deval.gestiondestock.services.EntrepriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EntrepriseController implements EntrepriseApi {

    @Autowired
    private EntrepriseService entrepriseService;

/*    //Setter Injection
    @Autowired
    public ArticleService getArticleService(
    ){
        return articleService;
    }*/

    //Constructor Injection
    @Autowired
    public EntrepriseController(
            EntrepriseService entrepriseService
    ){
        this.entrepriseService = entrepriseService;
    }


    @Override
    public EntrepriseDto save(EntrepriseDto dto) {
        return entrepriseService.save(dto);
    }

    @Override
    public EntrepriseDto findById(Integer id) {
        return entrepriseService.findById(id);
    }

    @Override
    public EntrepriseDto findByNom(String nom) {
        return entrepriseService.findByNom(nom);
    }

    @Override
    public EntrepriseDto findByCodeFiscal(String codefiscal) {
        return entrepriseService.findByCodeFiscal(codefiscal);
    }

    @Override
    public EntrepriseDto findByNumTel(String tel) {
        return entrepriseService.findByNumTel(tel);
    }

    @Override
    public List<EntrepriseDto> findAll() {
        return entrepriseService.findAll();
    }

    @Override
    public void delete(Integer id) {
        entrepriseService.delete(id);
    }
}
