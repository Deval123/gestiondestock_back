package com.deval.gestiondestock.services;

import com.deval.gestiondestock.dto.ArticleDto;
import com.deval.gestiondestock.dto.RolesDto;
import com.deval.gestiondestock.model.Utilisateur;

import java.util.List;

public interface RolesService {

    RolesDto save(RolesDto dto);

    RolesDto findById(Integer id);

    RolesDto findByRoleName(String roleName);

    RolesDto findByRoleName(Utilisateur utilisateur);

    List<RolesDto> findAll();
}
