package com.deval.gestiondestock.repository;

import com.deval.gestiondestock.model.Utilisateur;
import com.deval.gestiondestock.model.Ventes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.time.Instant;
import java.util.Optional;

@EnableJpaRepositories()

public interface VentesRepository extends JpaRepository<Ventes, Integer> {

    Optional<Ventes> findVentesByCode(String code);

    Optional<Ventes> findVentesByDateVente(Instant dateVente);
}
