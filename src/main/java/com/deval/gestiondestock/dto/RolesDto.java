package com.deval.gestiondestock.dto;

import com.deval.gestiondestock.model.Roles;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;


@Data
@Builder
@Jacksonized
public class RolesDto {

    private Integer id;

    private String roleName;

    private UtilisateurDto utilisateur;


    public static RolesDto fromEntity(Roles roles){
        if(roles == null){
            return null;
        }
        return  RolesDto.builder()
                .id(roles.getId())
                .roleName(roles.getRoleName())
                //.utilisateur(UtilisateurDto.fromEntity(roles.getUtilisateur()))
                .build();
    }

    public static Roles toEntity(RolesDto rolesDto){
        if(rolesDto == null){
            return null;
        }
        Roles roles = new Roles();
        roles.setId(rolesDto.getId());
        roles.setRoleName(rolesDto.getRoleName());
        roles.setUtilisateur(UtilisateurDto.toEntity(rolesDto.getUtilisateur()));
        return roles;
    }
}
