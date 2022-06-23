package com.deval.gestiondestock.services;

import com.deval.gestiondestock.dto.EntrepriseDto;
import com.deval.gestiondestock.model.Utilisateur;

import java.util.List;

public interface EntrepriseService {

    EntrepriseDto save(EntrepriseDto dto);

    EntrepriseDto findById(Integer id);

    EntrepriseDto findByNom(String nom);

    EntrepriseDto findByCodeFiscal(String codefiscal);

    EntrepriseDto findByNumTel(String tel);



    List<EntrepriseDto> findAll();

    void delete(Integer id);
}
