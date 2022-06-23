package com.deval.gestiondestock.dto;

import com.deval.gestiondestock.model.Article;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class ArticleDto {

    private Integer id;

    private  String codeArticle;

    private  String designation;

    private  BigDecimal prixUnitaireHt;

    private BigDecimal tauxTVA;

    private  BigDecimal prixUnitaireTTC;

    private  String photo;

    private CategoryDto category;

    private  Integer identreprise;

    @JsonCreator
    public static ArticleDto fromEntity(Article article){
        if (article == null){
            return null;
        }
        return ArticleDto.builder()
                .id(article.getId())
                .codeArticle(article.getCodeArticle())
                .designation(article.getDesignation())
                .prixUnitaireHt(article.getPrixUnitaireHt())
                .prixUnitaireTTC(article.getPrixUnitaireTTC())
                .photo(article.getPhoto())
                .tauxTVA(article.getTauxTVA())
                .identreprise(article.getIdentreprise())
                .category(CategoryDto.fromEntity(article.getCategory()))
                .build();
    }

    public static Article toEntity(ArticleDto articleDto){
        if (articleDto == null){
            return null;
        }
        Article article = new Article();
        article.setId(articleDto.getId());
        article.setCodeArticle(articleDto.getCodeArticle());
        article.setDesignation(articleDto.getDesignation());
        article.setPrixUnitaireHt(articleDto.getPrixUnitaireHt());
        article.setPrixUnitaireTTC(articleDto.getPrixUnitaireTTC());
        article.setTauxTVA(articleDto.getTauxTVA());
        article.setPhoto(articleDto.getPhoto());
        article.setIdentreprise(articleDto.getIdentreprise());
        article.setCategory(CategoryDto.toEntity(articleDto.getCategory()));
        return article;

    }
}
