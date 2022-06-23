package com.deval.gestiondestock.repository;

import com.deval.gestiondestock.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;
import java.util.Optional;

@EnableJpaRepositories()
public interface ArticleRepository extends JpaRepository<Article, Integer> {

    //Avec le JpaRepository nous avons les methodes predefini
    Optional<Article> findArticleByCodeArticle(String codeArticle);

    List<Article> findAllByCategoryId(Integer idCategory);


}