package com.deval.gestiondestock.services.impl;

import com.deval.gestiondestock.dto.CategoryDto;
import com.deval.gestiondestock.execption.EntityNotFoundException;
import com.deval.gestiondestock.execption.ErrorCodes;
import com.deval.gestiondestock.execption.InvalidEntityException;
import com.deval.gestiondestock.execption.InvalidOperationException;
import com.deval.gestiondestock.model.Article;
import com.deval.gestiondestock.model.Category;
import com.deval.gestiondestock.repository.ArticleRepository;
import com.deval.gestiondestock.repository.CategoryRepository;
import com.deval.gestiondestock.services.CategoryService;
import com.deval.gestiondestock.validator.CategoryValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    private ArticleRepository articleRepository;


    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ArticleRepository articleRepository) {
        this.categoryRepository = categoryRepository;
        this.articleRepository = articleRepository;
    }

    @Override
    public CategoryDto save(CategoryDto dto){
        List<String> errors = CategoryValidator.validate(dto);
        if(!errors.isEmpty()){
            //test si la category est valid avant l'insertion dans la bd
            log.error(" Category is not valid{}", dto);
            throw new InvalidEntityException("la  Categorie n'est pas valide", ErrorCodes.CATEGORY_NOT_VALID, errors);
        }
        Category savedCategory = categoryRepository.save(CategoryDto.toEntity(dto));
        return CategoryDto.fromEntity(savedCategory);

        //ou encore return CategoryDto.fromEntity(categoryRepository.save(CategoryDto.toEntity(dto)));
    }

    @Override
    public CategoryDto findById(Integer id){
        if(id == null){
            log.error("Category ID is null");
            return null;
        }
        Optional<Category> category = categoryRepository.findById(id);
        return Optional.of(CategoryDto.fromEntity(category.get())).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucune Categorie avec l'ID =" + id + "n'a été trouvé dans la BD",
                        ErrorCodes.CATEGORY_NOT_FOUND)
        );
    }

    @Override
    public CategoryDto findByCode(String codecategory){

        if(!StringUtils.hasLength(codecategory)) {
            log.error("Category CODE is null");
            return null;
        }
        Optional<Category>  category = categoryRepository.findCategoryByCodecategory(codecategory);
        return Optional.of(CategoryDto.fromEntity(category.get())).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucune categorie avec le code =" + codecategory + "n'a été trouvé dans la BD",
                        ErrorCodes.CATEGORY_NOT_FOUND)
        );
    }


    @Override
   public List<CategoryDto> findAll(){
        return categoryRepository.findAll().stream()
                .map(CategoryDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if(id == null){
            log.error("Category ID is null");
            return ;
        }
        List<Article> articles = articleRepository.findAllByCategoryId(id);
        if (!articles.isEmpty()){
            throw new InvalidOperationException("Impossible de supprimer cette Categorie qui est déjà utilisé", ErrorCodes.CATEGORY_ALREADY_IN_USE);
        }
        categoryRepository.deleteById(id);
    }}
