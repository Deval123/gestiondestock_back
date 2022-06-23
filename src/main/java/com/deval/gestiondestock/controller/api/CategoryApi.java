package com.deval.gestiondestock.controller.api;

import com.deval.gestiondestock.dto.ArticleDto;
import com.deval.gestiondestock.dto.CategoryDto;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.deval.gestiondestock.utils.Constants.APP_ROOT;
@Api(APP_ROOT + "/category")
public interface CategoryApi {

    @PostMapping(value = APP_ROOT + "/category/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enregistrer une categorie", notes = "Cette méthode permet (Ajouter / Modifier) une categorie", response = CategoryDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'objet categorie cree / modifie"),
            @ApiResponse(code = 400, message = "L'objet categorie n'est pas valide")
    })    CategoryDto save(@RequestBody CategoryDto dto);

    @GetMapping(value = APP_ROOT + "/category/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher une categorie par ID", notes = "Cette méthode permet chercher une categorie par son ID", response = ArticleDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La categorie a été trouvé dans la Base de donnée"),
            @ApiResponse(code = 404, message = "Aucune categorie n'existe dans la base de donnée avec l'ID fourni"),

    })
    CategoryDto findById(@PathVariable("id") Integer id);

/*    @GetMapping(value = APP_ROOT + "/category/{codeCategory}", produces = MediaType.APPLICATION_JSON_VALUE)
    CategoryDto findByCodeCategory(@PathVariable("codeCategory") String codeCategory);*/

    @GetMapping(value = APP_ROOT + "/category/{codecategory}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher une categorie par CODE", notes = "Cette méthode permet chercher un article par son CODE", response = CategoryDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Category a été trouvé dans la Base de donnée"),
            @ApiResponse(code = 404, message = "Aucune categorie n'existe dans la base de donnée avec le CODE fourni"),

    })
    // [CAT1 CAT2 CAT3 CAT4 ....]
    CategoryDto findByCode(@ApiParam(value = "Accepted values [CAT1 CAT2 CAT3 CAT4 ....]") @PathVariable("codecategory") String codecategory);


    @GetMapping(value = APP_ROOT + "/category/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoie la liste des categories", notes = "Cette méthode permet chercher et renvoyer la liste des article qui existe dans la BDD", responseContainer = "List<CategoryDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des categorie / Une liste vide")
    })
    List<CategoryDto> findAll();

    @DeleteMapping(value = APP_ROOT + "/category/delete/{idCategory}")
    @ApiOperation(value = "Supprimer une categorie", notes = "Cette méthode permet de supprimer un article par son ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La categorie a été supprimé")
    })
    void delete(@PathVariable("idCategory")  Integer id);
}
