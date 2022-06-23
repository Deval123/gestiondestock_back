package com.deval.gestiondestock.repository;

import com.deval.gestiondestock.dto.AdressesDto;
import com.deval.gestiondestock.model.Adresses;
import com.deval.gestiondestock.model.Fournisseur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Optional;

@EnableJpaRepositories()

public interface FournisseurRepository extends JpaRepository<Fournisseur, Integer> {
    Optional<Fournisseur> findFournisseurByNom(String nom);

    Optional<Fournisseur> findFournisseurByAdresse(AdressesDto dto);

    Optional<Fournisseur> findFournisseurByMail(String mail);

    Optional<Fournisseur> findFournisseurByNumTel(String numTel);




}
