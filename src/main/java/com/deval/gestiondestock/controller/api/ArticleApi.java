package com.deval.gestiondestock.controller.api;

import com.deval.gestiondestock.dto.ArticleDto;
import com.deval.gestiondestock.dto.LigneCommandeClientDto;
import com.deval.gestiondestock.dto.LigneCommandeFournisseurDto;
import com.deval.gestiondestock.dto.LigneVenteDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static com.deval.gestiondestock.utils.Constants.APP_ROOT;
import java.util.List;

@Api(APP_ROOT + "/articles")
public interface ArticleApi {
    @PostMapping(value = APP_ROOT + "/articles/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enregistrer un article", notes = "Cette méthode permet (Ajouter / Modifier) un article", response = ArticleDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "L'objet article cree / modifie"),
        @ApiResponse(code = 400, message = "L'objet article n'est pas valide")
    })
    ArticleDto save(@RequestBody ArticleDto dto);

    @GetMapping(value = APP_ROOT + "/articles/idArticles", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un article par ID", notes = "Cette méthode permet chercher un article par son ID", response = ArticleDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Article a été trouvé dans la Base de donnée"),
        @ApiResponse(code = 404, message = "Aucun article n'existe dans la base de donnée avec l'ID fourni"),

    })
    ArticleDto findById(@PathVariable("idArticles") Integer id);

    @GetMapping(value = APP_ROOT + "/articles/filtre/{codeArticle}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un article par CODE", notes = "Cette méthode permet chercher un article par son CODE", response = ArticleDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Article a été trouvé dans la Base de donnée"),
        @ApiResponse(code = 404, message = "Aucun article n'existe dans la base de donnée avec le CODE fourni"),

    })
    ArticleDto findByCodeArticle(@PathVariable("codeArticle") String codeArticle);

    @GetMapping(value = APP_ROOT + "/articles/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoie la liste des articles", notes = "Cette méthode permet chercher et renvoyer la liste des article qui existe dans la BDD", responseContainer = "List<ArticleDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des article / Une liste vide")
    })
    List<ArticleDto> findAll();



    @GetMapping(value = APP_ROOT + "/articles/historique/vente/{idArticle}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoie l'historique des ventes", notes = "Cette méthode permet de chercher l'historique des ventes", responseContainer = "List<LigneVenteDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'historique des ventes vide")
    })
    List<LigneVenteDto> findHistoriqueVentes(@PathVariable(value = "idArticle") Integer idArticle);


    @GetMapping(value = APP_ROOT + "/articles/historique/commandeclient/{idArticle}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoie l'istorique des commandes clients", notes = "Cette méthode permet chercher et renvoyer l'istorique des commandes clients qui existe dans la BDD", responseContainer = "List<LigneCommandeClientDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'istorique des commandes clients vide")
    })
    List<LigneCommandeClientDto> findHistoriqueCommandeClient(@PathVariable(value = "idArticle") Integer idArticle);


    @GetMapping(value = APP_ROOT + "/articles/historique/commandeFournisseur/{idArticle}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoie l'istorique des commandes fournisseur", notes = "Cette méthode permet chercher et renvoyer l'istorique des commandes fournisseur qui existe dans la BDD", responseContainer = "List<LigneCommandeFournisseurDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'istorique des commandes fournisseur vide")
    })
    List<LigneCommandeFournisseurDto> findHistoriqueCommandeFournisseur(@PathVariable(value = "idArticle") Integer idArticle);


    @GetMapping(value = APP_ROOT + "/articles/filtre/category/{idCategory}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Filtre les articles en fonction de la categorie", notes = "Cette méthode permet filtrer les articles en fonction de la categorie", responseContainer = "List<ArticleDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des article / Une liste vide")
    })
    List<ArticleDto> findArticleByIdCategory(@PathVariable(value = "idCategory") Integer idCategory);



    @DeleteMapping(value = APP_ROOT + "/articles/delete/{idArticle}")
    @ApiOperation(value = "Supprimer un article", notes = "Cette méthode permet de supprimer un article par son ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'article a été supprimé")
    })
    void delete(@PathVariable("idArticles") Integer id);
}
