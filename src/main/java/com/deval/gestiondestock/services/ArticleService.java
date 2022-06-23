package com.deval.gestiondestock.services;

import com.deval.gestiondestock.dto.ArticleDto;
import com.deval.gestiondestock.dto.LigneCommandeClientDto;
import com.deval.gestiondestock.dto.LigneCommandeFournisseurDto;
import com.deval.gestiondestock.dto.LigneVenteDto;

import java.util.List;

public interface ArticleService {

    ArticleDto save(ArticleDto dto);

    ArticleDto findById(Integer id);

    ArticleDto findByCodeArticle(String codeArticle);

    List<ArticleDto> findAll();

    List<LigneVenteDto> findHistoriqueVentes(Integer idArticle);

    List<LigneCommandeClientDto> findHistoriqueCommandeClient(Integer idArticle);

    List<LigneCommandeFournisseurDto> findHistoriqueCommandeFournisseur(Integer idArticle);

    List<ArticleDto> findArticleByIdCategory(Integer idCategory);

    void delete(Integer id);
}
