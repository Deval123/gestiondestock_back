package com.deval.gestiondestock.repository;

import com.deval.gestiondestock.model.LigneCommandeClient;
import com.deval.gestiondestock.model.LigneCommandeFournisseur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;
import java.util.Optional;

@EnableJpaRepositories()

public interface LigneCommandeFournisseurRepository extends JpaRepository<LigneCommandeFournisseur, Integer> {
    //Optional<LigneCommandeFournisseur> findAllLigneCommandesFournisseurByCommandeFournisseurId(Integer idCommande);

    List<LigneCommandeFournisseur> findAllByArticleId(Integer idCommande);


    List<LigneCommandeFournisseur> findAllLigneCommandesFournisseurByCommandeFournisseurId(Integer idCommande);
}
