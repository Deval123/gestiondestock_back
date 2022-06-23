package com.deval.gestiondestock.services.impl;

import com.deval.gestiondestock.dto.CategoryDto;
import com.deval.gestiondestock.execption.EntityNotFoundException;
import com.deval.gestiondestock.execption.ErrorCodes;
import com.deval.gestiondestock.execption.InvalidEntityException;
import com.deval.gestiondestock.services.CategoryService;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public  class CategoryServiceImplTest {


    @Autowired
    private CategoryService service;

    @Test
    public void shouldSaveCategoryWithSuccess(){
        CategoryDto expectedCategory = CategoryDto.builder()
                .codecategory("CAT test")
                .designation("Designation test")
                .identreprise(1)
                .build();

        CategoryDto savedCategory = service.save(expectedCategory);

        assertNotNull(savedCategory);
        assertNotNull(savedCategory.getId());
        assertEquals(expectedCategory.getCodecategory(), savedCategory.getCodecategory());
        assertEquals(expectedCategory.getDesignation(), savedCategory.getDesignation());
        assertEquals(expectedCategory.getIdentreprise(), savedCategory.getIdentreprise());
    }

    @Test
    public void shouldUpdateCategoryWithSuccess(){
        CategoryDto expectedCategory = CategoryDto.builder()
                .codecategory("CAT test")
                .designation("Designation test")
                .identreprise(1)
                .build();

        CategoryDto savedCategory = service.save(expectedCategory);

        CategoryDto categoryToUpdate = savedCategory;

        categoryToUpdate.setCodecategory("CAT UPDATE");
        savedCategory = service.save(categoryToUpdate);

        assertNotNull(categoryToUpdate);
        assertNotNull(categoryToUpdate.getId());
        assertEquals(categoryToUpdate.getCodecategory(), savedCategory.getCodecategory());
        assertEquals(categoryToUpdate.getDesignation(), savedCategory.getDesignation());
        assertEquals(categoryToUpdate.getIdentreprise(), savedCategory.getIdentreprise());
    }

    @Test
    public void shouldThrowInvalideEntityException(){
        CategoryDto expectedCategory = CategoryDto.builder().build();
       InvalidEntityException expectedException =  assertThrows(InvalidEntityException.class, () -> service.save(expectedCategory));
       assertEquals(ErrorCodes.CATEGORY_NOT_VALID, expectedException.getErrorCodes());
       assertEquals(1, expectedException.getErrors().size());
       assertEquals("Veuillez renseigner le code de la categorie", expectedException.getErrors().get(0));

    }

/*    @Test
    public void shouldThrowEntityNotFoundException(){
        EntityNotFoundException expectedException =  assertThrows(EntityNotFoundException.class, () -> service.findById(0));
        assertEquals(ErrorCodes.CATEGORY_NOT_FOUND, expectedException.getErrorCodes());
        assertEquals("Aucune Categorie avec l'ID = 0 n'a été trouvé dans la BD", expectedException.getMessage());

    }*/

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowEntityNotFoundException2() {
        service.findById(0);

    }

}