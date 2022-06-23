package com.deval.gestiondestock.dto;


import com.deval.gestiondestock.model.Roles;
import com.deval.gestiondestock.model.Utilisateur;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Column;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@Jacksonized
public class UtilisateurDto {

    private Integer id;

    private String nom;

    private String prenom;

    private String email;

    private Instant dateDeNaissance;

    private String motDePasse;

    private AdressesDto adresse;

    private String photo;

    private  String numTel;

    private EntrepriseDto entreprise;

    @JsonIgnore
    private List<RolesDto> roles;

    public static UtilisateurDto fromEntity(Utilisateur utilisateur){
        if(utilisateur == null){
            return null;
        }
        return UtilisateurDto.builder()
                .id(utilisateur.getId())
                .nom(utilisateur.getNom())
                .prenom(utilisateur.getPrenom())
                .email(utilisateur.getEmail())
                .dateDeNaissance(utilisateur.getDateDeNaissance())
                .motDePasse(utilisateur.getMotDePasse())
                .adresse(AdressesDto.fromEntity(utilisateur.getAdresse()))
                .photo(utilisateur.getPhoto())
                .numTel(utilisateur.getNumTel())
                .entreprise(EntrepriseDto.fromEntity(utilisateur.getEntreprise()))
                .roles(
                        utilisateur.getRoles() != null ?
                                utilisateur.getRoles().stream()
                                        .map(RolesDto::fromEntity)
                                        .collect(Collectors.toList()) : null
                )
                .build();
    }

    public static Utilisateur toEntity(UtilisateurDto utilisateurDto){
        if (utilisateurDto == null){
            return null;
        }
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(utilisateurDto.getId());
        utilisateur.setNom(utilisateur.getNom());
        utilisateur.setPrenom(utilisateurDto.getPrenom());
        utilisateur.setEmail(utilisateurDto.getEmail());
        utilisateur.setDateDeNaissance(utilisateurDto.getDateDeNaissance());
        utilisateur.setMotDePasse(passwordEncoder().encode(utilisateurDto.getMotDePasse()));
        utilisateur.setAdresse(AdressesDto.toEntity(utilisateurDto.getAdresse()));
        utilisateur.setPhoto(utilisateurDto.getPhoto());
        utilisateur.setNumTel(utilisateurDto.getNumTel());
        utilisateur.setEntreprise(EntrepriseDto.toEntity(utilisateurDto.getEntreprise()));
        utilisateur.setRoles((List<Roles>) RolesDto.toEntity((RolesDto) utilisateurDto.getRoles()));
        return utilisateur;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private String generateRandomPassword(){
        return "deval123@$$luna";
    }

/*    @Bean
    public static PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }*/

}
