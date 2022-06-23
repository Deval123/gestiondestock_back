package com.deval.gestiondestock.repository;

import com.deval.gestiondestock.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Optional;

@EnableJpaRepositories()
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findCategoryByCodecategory(String codecategory);
/*
    Optional<Category> findByIdAndIdEntreprise(Integer id, Integer IdEntreprise);
*/
}
