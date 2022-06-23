package com.deval.gestiondestock.repository;

import com.deval.gestiondestock.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Optional;

@EnableJpaRepositories()
public interface ClientRepository extends JpaRepository<Client, Integer> {
    Optional<Client> findClientByNom(String nom);

    Optional<Client> findClientByMail(String mail);

    Optional<Client> findClientByNumTel(String numTel);



}
