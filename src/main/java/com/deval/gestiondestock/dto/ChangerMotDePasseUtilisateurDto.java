package com.deval.gestiondestock.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChangerMotDePasseUtilisateurDto {

    // l'id ou l'email car ces deux attricbuts sont unique
    private Integer id;

    //private String email;

    private String motDePasse;

    private String confirmMotDePasse;
}
