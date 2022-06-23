package com.deval.gestiondestock.repository;

import com.deval.gestiondestock.model.LigneCommandeClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

@EnableJpaRepositories()

public interface LigneCommandeClientRepository extends JpaRepository<LigneCommandeClient, Integer> {
   List<LigneCommandeClient> findAllLigneCommandesClientByCommandeClientId(Integer idCommande);

   List<LigneCommandeClient> findAllByArticleId(Integer idCommande);

}
