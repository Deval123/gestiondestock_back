package com.deval.gestiondestock.services.impl;

import com.deval.gestiondestock.dto.RolesDto;
import com.deval.gestiondestock.model.Utilisateur;
import com.deval.gestiondestock.repository.RolesRepository;
import com.deval.gestiondestock.services.RolesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RolesServiceImpl implements RolesService {


    private RolesRepository rolesRepository;

    @Autowired
    public RolesServiceImpl(RolesRepository rolesRepository){
        this.rolesRepository = rolesRepository;
    }

    @Override
    public RolesDto save(RolesDto dto) {
        return null;
    }

    @Override
    public RolesDto findById(Integer id) {
        return null;
    }

    @Override
    public RolesDto findByRoleName(String roleName) {
        return null;
    }

    @Override
    public RolesDto findByRoleName(Utilisateur utilisateur) {
        return null;
    }

    @Override
    public List<RolesDto> findAll() {
        return null;
    }
}
