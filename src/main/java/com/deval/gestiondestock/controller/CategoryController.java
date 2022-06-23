package com.deval.gestiondestock.controller;


import com.deval.gestiondestock.controller.api.CategoryApi;
import com.deval.gestiondestock.dto.ArticleDto;
import com.deval.gestiondestock.dto.CategoryDto;
import com.deval.gestiondestock.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController implements CategoryApi {

    //Field Injection
    @Autowired
    private CategoryService categoryService;

    //Constructor Injection
    @Autowired
    public CategoryController(
            CategoryService categoryService
    ){
        this.categoryService = categoryService;
    }

    @Override
    public CategoryDto save(CategoryDto dto) {
        return categoryService.save(dto);
    }

    @Override
    @RequestMapping(value = "/category/id/{id}", method = RequestMethod.GET)
    public CategoryDto findById(@PathVariable Integer id) {
        return categoryService.findById(id);
    }

    @Override
    @RequestMapping(value = "/category/codecategory/{codecategory}", method = RequestMethod.GET)
    public CategoryDto findByCode(@PathVariable String codecategory) {
        return categoryService.findByCode(codecategory);
    }

    @Override
    public List<CategoryDto> findAll() {
        return categoryService.findAll();
    }

    @Override
    public void delete(Integer id) {
        categoryService.delete(id);
    }
}
