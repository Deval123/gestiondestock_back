package com.deval.gestiondestock.services;

import com.deval.gestiondestock.dto.AdressesDto;
import com.deval.gestiondestock.dto.ChangerMotDePasseUtilisateurDto;
import com.deval.gestiondestock.dto.UtilisateurDto;

import java.util.List;

public interface UtilisateurService {

    UtilisateurDto save(UtilisateurDto dto);

    UtilisateurDto findById(Integer id);

    UtilisateurDto findByNom(String nom);

    UtilisateurDto findByNumTel(String numTel);

    UtilisateurDto findByEmail(String email);

    List<UtilisateurDto> findAll();

    void delete(Integer id);

    UtilisateurDto changerMotDePasse(ChangerMotDePasseUtilisateurDto dto);
}
