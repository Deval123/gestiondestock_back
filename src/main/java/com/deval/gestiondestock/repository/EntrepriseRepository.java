package com.deval.gestiondestock.repository;

import com.deval.gestiondestock.model.Entreprise;
import com.deval.gestiondestock.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Optional;

@EnableJpaRepositories()

public interface EntrepriseRepository extends JpaRepository<Entreprise, Integer> {
    Optional<Entreprise> findEntrepriseByNom(String nom);
    Optional<Entreprise> findEntrepriseByCodeFiscal(String codefiscal);

    Optional<Entreprise> findEntrepriseByNumTel(String numTel);
}
