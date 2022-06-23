package com.deval.gestiondestock.dto;

import com.deval.gestiondestock.model.Category;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class CategoryDto {
    private Integer id;

    private  String codecategory;

    private  String designation;

    private  Integer identreprise;

    @JsonIgnore
    private List<ArticleDto> article;

    @JsonCreator
    public static CategoryDto fromEntity(Category category){
     if (category == null) {
         // TOTO throw on exception
         return null;
     }
        // Category -->  CategoryDto

        return CategoryDto.builder()
             .id(category.getId())
             .codecategory(category.getCodecategory())
             .designation(category.getDesignation())
             .identreprise(category.getIdentreprise())
             .build();
    }

    public static Category toEntity(CategoryDto categoryDto){
        if(categoryDto == null){
            // TOTO throw on exception
            return null;
        }
            // CategoryDto  -->  Category
        Category category = new Category();
        category.setId(categoryDto.getId());
        category.setCodecategory(categoryDto.getCodecategory());
        category.setDesignation(categoryDto.getDesignation());
        category.setIdentreprise(categoryDto.getIdentreprise());
        return category;

    }
}
