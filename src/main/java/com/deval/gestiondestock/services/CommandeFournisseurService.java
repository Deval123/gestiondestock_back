package com.deval.gestiondestock.services;

import com.deval.gestiondestock.dto.CommandeClientDto;
import com.deval.gestiondestock.dto.CommandeFournisseurDto;
import com.deval.gestiondestock.dto.LigneCommandeClientDto;
import com.deval.gestiondestock.dto.LigneCommandeFournisseurDto;
import com.deval.gestiondestock.model.EtatCommande;

import java.math.BigDecimal;
import java.util.List;

public interface CommandeFournisseurService {
    CommandeFournisseurDto save(CommandeFournisseurDto dto);

    CommandeFournisseurDto updateEtatCommande(Integer idCommande, EtatCommande etatCommande);

    CommandeFournisseurDto updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite);

    CommandeFournisseurDto updateFournisseur(Integer idCommande, Integer idClient);

    CommandeFournisseurDto updateArticle(Integer idCommande, Integer idLigneCommande, Integer idArticle);

    // Delete article = delete LigneCommandeFournisseur
    CommandeFournisseurDto deleteArticle(Integer idCommande, Integer idLigneCommande);

    CommandeFournisseurDto findById(Integer id);

    CommandeFournisseurDto findByCode(String code);

    List<CommandeFournisseurDto> findAll();

    List<LigneCommandeFournisseurDto> findAllLigneCommandesClientByCommandeFournisseurId(Integer idCommande);

    void delete(Integer id);
}
