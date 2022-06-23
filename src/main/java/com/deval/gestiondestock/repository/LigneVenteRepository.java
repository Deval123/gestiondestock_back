package com.deval.gestiondestock.repository;

import com.deval.gestiondestock.dto.VentesDto;
import com.deval.gestiondestock.model.LigneVente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@EnableJpaRepositories()

public interface LigneVenteRepository extends JpaRepository<LigneVente, Integer> {
    List<LigneVente> findAllByArticleId(Integer idArticle);


    List<LigneVente> findAllByVenteId(Integer id);
}
