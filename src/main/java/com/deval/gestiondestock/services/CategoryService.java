package com.deval.gestiondestock.services;

import com.deval.gestiondestock.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto save(CategoryDto dto);

    CategoryDto findById(Integer id);

    CategoryDto findByCode(String codecategory);

    List<CategoryDto> findAll();

    void delete(Integer id);

}
