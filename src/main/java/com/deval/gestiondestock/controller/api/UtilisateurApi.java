package com.deval.gestiondestock.controller.api;

import com.deval.gestiondestock.dto.ArticleDto;
import com.deval.gestiondestock.dto.UtilisateurDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.deval.gestiondestock.utils.Constants.*;


@Api(APP_ROOT + "/utilisateurs")
public interface UtilisateurApi {

    @PostMapping(value = CREATE_UTILISATEURS_ENDPOINT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    UtilisateurDto save(@RequestBody UtilisateurDto dto);

    @GetMapping(value = FIND_UTILISATEURS_BY_ID_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
    UtilisateurDto findById(@PathVariable("idUtilisateur") Integer id);

    @GetMapping(value = FIND_UTILISATEURS_BY_NOM_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
    UtilisateurDto findByNom(@PathVariable("nom") String nom);

    @GetMapping(value = FIND_UTILISATEURS_BY_NUM_TEL_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
    UtilisateurDto findByNumTel(@PathVariable("numTel")  String numTel);


    @GetMapping(value = FIND_UTILISATEURS_BY_EMAIL_TEL_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un utilisateur par EMAIL", notes = "Cette méthode permet chercher un utilisateur par son EMAIL", response = ArticleDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "utilisateur a été trouvé dans la Base de donnée"),
            @ApiResponse(code = 404, message = "Aucun utilisateur n'existe dans la base de donnée avec l'EMAIL fourni"),

    })
    UtilisateurDto findByEmail(@PathVariable("email") String email);

    @GetMapping(value = FIND_ALL_UTILISATEURS_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
    List<UtilisateurDto> findAll();

    @DeleteMapping(value = DELETE_UTILISATEURS_ENDPOINT)
    void delete(@PathVariable("idUtilisateur")  Integer id);
}
