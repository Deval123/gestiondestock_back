package com.deval.gestiondestock.repository;

import com.deval.gestiondestock.dto.MvtStockDto;
import com.deval.gestiondestock.model.Article;
import com.deval.gestiondestock.model.Entreprise;
import com.deval.gestiondestock.model.MvtStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface MvtStockRepository extends JpaRepository<MvtStock, Integer> {

    Optional<MvtStock> findMvtStockByDateMvt(Instant dateMvt);

    Optional<MvtStock> findMvtStockByArticle(Article article);


    //Optional<MvtStock> findMvtStockByArticle(String typeMvt);
    @Query("select sum(m.quantite) from MvtStock m where m.article.id = :idArticle")
    BigDecimal stockReelArticle(@Param("idArticle") Integer idArticle);

    List<MvtStock> findAllByArticleId(Integer IdArticle);

}
