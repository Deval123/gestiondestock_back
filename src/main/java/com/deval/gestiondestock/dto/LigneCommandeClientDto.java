package com.deval.gestiondestock.dto;


import com.deval.gestiondestock.model.LigneCommandeClient;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Builder
public class LigneCommandeClientDto {

    private Integer id;

    private  ArticleDto article;


    private  CommandeClientDto commandeclient;

    private BigDecimal quantite;

    private BigDecimal prixUnitaire;

    private  Integer identreprise;

    public static LigneCommandeClientDto fromEntity(LigneCommandeClient ligneCommandeClient){
        if(ligneCommandeClient == null){
            return null;
        }
        return LigneCommandeClientDto.builder()
                .id(ligneCommandeClient.getId())
                .article(ArticleDto.fromEntity(ligneCommandeClient.getArticle()))
                .commandeclient(CommandeClientDto.fromEntity(ligneCommandeClient.getCommandeClient()))
                .prixUnitaire(ligneCommandeClient.getPrixUnitaire())
                .quantite(ligneCommandeClient.getQuantite())
                .identreprise(ligneCommandeClient.getIdentreprise())
                .build();
    }

    public static LigneCommandeClient toEntity(LigneCommandeClientDto ligneCommandeClientDto){
        if(ligneCommandeClientDto == null){
            return null;
        }

        LigneCommandeClient ligneCommandeClient = new LigneCommandeClient();
        ligneCommandeClient.setId(ligneCommandeClientDto.getId());
        ligneCommandeClient.setArticle(ArticleDto.toEntity(ligneCommandeClientDto.getArticle()));
        ligneCommandeClient.setCommandeClient(CommandeClientDto.toEntity(ligneCommandeClientDto.getCommandeclient()));
        ligneCommandeClient.setQuantite(ligneCommandeClientDto.getQuantite());
        ligneCommandeClient.setPrixUnitaire(ligneCommandeClientDto.getPrixUnitaire());
        ligneCommandeClient.setIdentreprise(ligneCommandeClientDto.getIdentreprise());
        return ligneCommandeClient;
    }
}
