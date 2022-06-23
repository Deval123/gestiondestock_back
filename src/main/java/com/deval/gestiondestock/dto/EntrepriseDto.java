package com.deval.gestiondestock.dto;

import com.deval.gestiondestock.model.Entreprise;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import javax.persistence.Table;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@Jacksonized
public class EntrepriseDto {

    private Integer id;

    private  String nom;

    private  String description;

    private AdressesDto adresse;

    private  String codeFiscal;

    private  String photo;

    private  String email;

    private  String numTel;

    private  String siteWeb;

    @JsonIgnore
    private List<UtilisateurDto> utilisateurs;

    public static EntrepriseDto fromEntity(Entreprise entreprise){
        if(entreprise == null){
            return null;
        }
        return EntrepriseDto.builder()
                .id(entreprise.getId())
                .nom(entreprise.getNom())
                .description(entreprise.getDescription())
                .adresse(AdressesDto.fromEntity(entreprise.getAdresse()))
                .codeFiscal(entreprise.getCodeFiscal())
                .photo(entreprise.getPhoto())
                .email(entreprise.getEmail())
                .numTel(entreprise.getNumTel())
                .siteWeb(entreprise.getSiteWeb())
/*                .utilisateurs(
                        entreprise.getUtilisateurs() != null ?
                                entreprise.getUtilisateurs().stream()
                                        .map(UtilisateurDto::fromEntity)
                                        .collect(Collectors.toList()) : null
                )*/
                .build();
    }
    public static Entreprise toEntity(EntrepriseDto entrepriseDto){
        if(entrepriseDto == null){
            return  null;
        }
        Entreprise entreprise = new Entreprise();
        entreprise.setId(entrepriseDto.getId());
        entreprise.setNom(entrepriseDto.getNom());
        entreprise.setDescription(entrepriseDto.getDescription());
        entreprise.setAdresse(AdressesDto.toEntity(entrepriseDto.getAdresse()));
        entreprise.setCodeFiscal(entrepriseDto.getCodeFiscal());
        entreprise.setPhoto(entrepriseDto.getPhoto());
        entreprise.setEmail(entrepriseDto.getEmail());
        entreprise.setNumTel(entrepriseDto.getNumTel());
        entreprise.setSiteWeb(entrepriseDto.getSiteWeb());
/*        entreprise.setUtilisateurs(entrepriseDto.getUtilisateurs() != null ?
                entrepriseDto.getUtilisateurs().stream()
                        .map(UtilisateurDto::toEntity)
                        .collect(Collectors.toList()) : null);*/
        return entreprise;
    }
}
