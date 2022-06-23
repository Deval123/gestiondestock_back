package com.deval.gestiondestock.repository;

import com.deval.gestiondestock.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories()

public interface RolesRepository extends JpaRepository<Roles, Integer> {
}
