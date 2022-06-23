package com.deval.gestiondestock.dto;

import com.deval.gestiondestock.model.CommandeFournisseur;
import com.deval.gestiondestock.model.EtatCommande;
import lombok.Builder;
import lombok.Data;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class CommandeFournisseurDto {

    private Integer id;

    private  String code;

    private Instant dateCommande;

    private FournisseurDto fournisseur;

    private EtatCommande etatCommande;

    private List<LigneCommandeFournisseurDto> lignecommandefournisseur;

    private  Integer identreprise;

    public static CommandeFournisseurDto fromEntity(CommandeFournisseur commandeFournisseur){
        if(commandeFournisseur == null){
            return null;
        }
        return CommandeFournisseurDto.builder()
                .id(commandeFournisseur.getId())
                .code(commandeFournisseur.getCode())
                .dateCommande(commandeFournisseur.getDateCommande())
                .fournisseur(FournisseurDto.fromEntity(commandeFournisseur.getFournisseur()))
                .identreprise(commandeFournisseur.getIdentreprise())
                .etatCommande(commandeFournisseur.getEtatCommande())
/*                .lignecommandefournisseur(
                        commandeFournisseur.getLignecommandefournisseur() != null ?
                                commandeFournisseur.getLignecommandefournisseur().stream()
                                        .map(LigneCommandeFournisseurDto::fromEntity)
                                        .collect(Collectors.toList()) : null
                )*/
                .build();
    }

    public static CommandeFournisseur toEntity(CommandeFournisseurDto commandeFournisseurDto){
        if(commandeFournisseurDto == null){
            return null;
        }
        CommandeFournisseur commandeFournisseur = new CommandeFournisseur();
        commandeFournisseur.setId(commandeFournisseurDto.getId());
        commandeFournisseur.setCode(commandeFournisseurDto.getCode());
        commandeFournisseur.setFournisseur(FournisseurDto.toEntity(commandeFournisseurDto.getFournisseur()));
        commandeFournisseur.setIdentreprise(commandeFournisseurDto.getIdentreprise());
        commandeFournisseur.setEtatCommande(commandeFournisseurDto.getEtatCommande());
        //commandeFournisseur.setLignecommandefournisseur(commandeFournisseurDto.getLignecommandefournisseur());
        return commandeFournisseur;
    }

    public boolean isCommandeLivree(){
        return EtatCommande.LIVREE.equals(this.etatCommande);
    }

}
