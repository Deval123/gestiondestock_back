package com.deval.gestiondestock.repository;

import com.deval.gestiondestock.model.CommandeClient;
import com.deval.gestiondestock.model.CommandeFournisseur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;
import java.util.Optional;

@EnableJpaRepositories()
public interface CommandeFournisseurRepository extends JpaRepository<CommandeFournisseur, Integer> {
    Optional<CommandeFournisseur> findCommandeFournisseurByCode(String code);

    List<CommandeFournisseur> findAllByFournisseurId(Integer id);

}
