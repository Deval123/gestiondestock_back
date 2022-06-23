package com.deval.gestiondestock.dto;

import com.deval.gestiondestock.model.Article;
import com.deval.gestiondestock.model.LigneVente;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class LigneVenteDto {

    private Integer id;

    private VentesDto vente;

    private BigDecimal quantite;

    private BigDecimal prixUnitaire;

    private  Integer identreprise;

    private Article article;

    public  static LigneVenteDto fromEntity(LigneVente ligneVente){
        if (ligneVente == null){
            return null;
        }
        return  LigneVenteDto.builder()
                .id(ligneVente.getId())
                .vente(VentesDto.fromEntity(ligneVente.getVente()))
                .quantite(ligneVente.getQuantite())
                .prixUnitaire(ligneVente.getPrixUnitaire())
                .article(ligneVente.getArticle())
                .identreprise(ligneVente.getIdentreprise())
                .build();
    }

    public static LigneVente toEntity(LigneVenteDto ligneVenteDto){
        if (ligneVenteDto == null){
            return null;
        }

        LigneVente ligneVente = new LigneVente();
        ligneVente.setId(ligneVenteDto.getId());
        ligneVente.setVente(VentesDto.toEntity(ligneVenteDto.getVente()));
        ligneVente.setQuantite(ligneVenteDto.getQuantite());
        ligneVente.setPrixUnitaire(ligneVenteDto.getPrixUnitaire());
        ligneVente.setArticle(ligneVenteDto.getArticle());
        ligneVente.setIdentreprise(ligneVenteDto.getIdentreprise());
        return ligneVente;
    }
}
