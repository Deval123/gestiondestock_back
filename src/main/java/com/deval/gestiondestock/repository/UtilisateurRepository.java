package com.deval.gestiondestock.repository;

import com.deval.gestiondestock.dto.AdressesDto;
import com.deval.gestiondestock.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import javax.persistence.Column;
import java.util.Optional;

@EnableJpaRepositories()

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {

    Optional<Utilisateur> findUtilisateurByNom(String nom);

    Optional<Utilisateur> findUtilisateurByNumTel(String numTel);

    //JPQL query
    @Query(value = "select u from Utilisateur u where u.email = :email")
    Optional<Utilisateur> findUtilisateurByEmail(@Param("email") String email);


}
