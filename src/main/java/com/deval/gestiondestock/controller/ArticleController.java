package com.deval.gestiondestock.controller;

import com.deval.gestiondestock.controller.api.ArticleApi;
import com.deval.gestiondestock.dto.ArticleDto;
import com.deval.gestiondestock.dto.LigneCommandeClientDto;
import com.deval.gestiondestock.dto.LigneCommandeFournisseurDto;
import com.deval.gestiondestock.dto.LigneVenteDto;
import com.deval.gestiondestock.services.ArticleService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ArticleController implements ArticleApi {

    //Field Injection
    @Autowired
    private ArticleService articleService;

/*    //Setter Injection
    @Autowired
    public ArticleService getArticleService(
    ){
        return articleService;
    }*/

    //Constructor Injection
    @Autowired
    public ArticleController(
            ArticleService articleService
    ){
        this.articleService = articleService;
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "L'objet article cree / modifie")
    })
    @Override
    public ArticleDto save(ArticleDto dto) {
        return articleService.save(dto);
    }

    @Override
    public ArticleDto findById(Integer id) {
        return articleService.findById(id);
    }

    @Override
    public ArticleDto findByCodeArticle(String codeArticle) {
        return articleService.findByCodeArticle(codeArticle);
    }

    @Override
    public List<ArticleDto> findAll() {
        return articleService.findAll();
    }

    @Override
    public List<LigneVenteDto> findHistoriqueVentes(Integer idArticle) {
        return articleService.findHistoriqueVentes(idArticle);
    }

    @Override
    public List<LigneCommandeClientDto> findHistoriqueCommandeClient(Integer idArticle) {
        return articleService.findHistoriqueCommandeClient(idArticle);
    }

    @Override
    public List<LigneCommandeFournisseurDto> findHistoriqueCommandeFournisseur(Integer idArticle) {
        return articleService.findHistoriqueCommandeFournisseur(idArticle);
    }

    @Override
    public List<ArticleDto> findArticleByIdCategory(Integer idCategory) {
        return articleService.findArticleByIdCategory(idCategory);
    }

    @Override
    public void delete(Integer id) {
        articleService.delete(id);
    }
}
