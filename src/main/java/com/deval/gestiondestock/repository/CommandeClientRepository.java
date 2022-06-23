package com.deval.gestiondestock.repository;

import com.deval.gestiondestock.model.CommandeClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;
import java.util.Optional;

@EnableJpaRepositories()
public interface CommandeClientRepository extends JpaRepository<CommandeClient, Integer> {
        Optional<CommandeClient> findCommandeClientByCode(String code);

        List<CommandeClient> findAllByClientId(Integer id);
}
