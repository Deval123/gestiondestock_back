package com.deval.gestiondestock.dto;

import com.deval.gestiondestock.model.EtatCommande;
import com.deval.gestiondestock.model.Fournisseur;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class FournisseurDto {

    private Integer id;

    private  String nom;

    private  String prenom;

    private AdressesDto adresse;

    private  String photo;

    private  String mail;

    private  String numTel;

    private  Integer identreprise;

    private List<CommandeFournisseurDto> commandeFournisseurs;

    public static FournisseurDto fromEntity(Fournisseur fournisseur){
        if(fournisseur == null){
            return null;
        }

        return FournisseurDto.builder()
                .id(fournisseur.getId())
                .nom(fournisseur.getNom())
                .prenom(fournisseur.getPrenom())
                .adresse(AdressesDto.fromEntity(fournisseur.getAdresse()))
                .photo(fournisseur.getPhoto())
                .mail(fournisseur.getMail())
                .numTel(fournisseur.getNumTel())
                .identreprise(fournisseur.getIdentreprise())
/*                .commandeFournisseurs(
                        fournisseur.getCommandeFournisseurs() != null ?
                                fournisseur.getCommandeFournisseurs().stream()
                                        .map(CommandeFournisseurDto::fromEntity)
                                        .collect(Collectors.toList()) : null
                )*/
                .build();
    }
    public static Fournisseur toEntity(FournisseurDto fournisseurDto){
        if(fournisseurDto == null){
            return null;
        }
        Fournisseur fournisseur = new Fournisseur();
        fournisseur.setId(fournisseurDto.getId());
        fournisseur.setNom(fournisseurDto.getNom());
        fournisseur.setPrenom(fournisseurDto.getPrenom());
        fournisseur.setAdresse(AdressesDto.toEntity(fournisseurDto.getAdresse()));
        fournisseur.setPhoto(fournisseurDto.getPhoto());
        fournisseur.setNumTel(fournisseurDto.getNumTel());
        fournisseur.setMail(fournisseurDto.getMail());
        fournisseur.setIdentreprise(fournisseurDto.getIdentreprise());
        //fournisseur.setCommandeFournisseurs(fournisseurDto.getCommandeFournisseurs());
        return fournisseur;
    }
}
