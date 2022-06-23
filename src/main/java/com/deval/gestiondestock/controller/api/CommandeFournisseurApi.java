package com.deval.gestiondestock.controller.api;

import com.deval.gestiondestock.dto.CommandeClientDto;
import com.deval.gestiondestock.dto.CommandeFournisseurDto;
import com.deval.gestiondestock.dto.LigneCommandeClientDto;
import com.deval.gestiondestock.dto.LigneCommandeFournisseurDto;
import com.deval.gestiondestock.model.EtatCommande;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static com.deval.gestiondestock.utils.Constants.*;

@Api(COMMANDE_FOURNISSEURS_ENDPOINT)
public interface CommandeFournisseurApi {

    @PostMapping(CREATE_COMMANDE_FOURNISSEURS_ENDPOINT)
    CommandeFournisseurDto save(@RequestBody CommandeFournisseurDto dto);



    @PatchMapping(APP_ROOT + "/CommandeFournisseurs/update/etat/{idCommande}/{etatCommande}")
    ResponseEntity<CommandeFournisseurDto> updateEtatCommande(@PathVariable(value = "idCommande") Integer idCommande, @PathVariable(value = "etatCommande") EtatCommande etatCommande);


    @PatchMapping(APP_ROOT + "/CommandeFournisseurs/update/quantite/{idCommande}/{idLigneCommande}/{quantite}")
    ResponseEntity<CommandeFournisseurDto> updateQuantiteCommande(@PathVariable(value = "idCommande")  Integer idCommande,
                                                             @PathVariable(value = "idLigneCommande") Integer idLigneCommande, @PathVariable(value = "quantite") BigDecimal quantite);

    @PatchMapping(APP_ROOT + "/CommandeFournisseurs/update/fournisseur/{idCommande}/{idClient}")
    ResponseEntity<CommandeFournisseurDto> updateFournisseur(@PathVariable(value = "idCommande") Integer idCommande, @PathVariable(value = "idFournisseur") Integer idFournisseur);

    @PatchMapping(APP_ROOT + "/CommandeFournisseurs/update/article/{idCommande}/{idLigneCommande}/{idArticle}")
    ResponseEntity<CommandeFournisseurDto> updateArticle(@PathVariable(value = "idCommande") Integer idCommande,
                                                    @PathVariable(value = "idLigneCommande") Integer idLigneCommande, @PathVariable(value = "idArticle") Integer idArticle);


    @GetMapping(APP_ROOT + "/CommandeFournisseurs/ligneCommandes/{idCommande}")
    ResponseEntity<List<LigneCommandeFournisseurDto>> findAllLigneCommandesFournisseurByCommandeFournisseurId(@PathVariable(value = "idCommande") Integer idCommande);

    @DeleteMapping(APP_ROOT + "/CommandeFournisseurs/delete/article/{idCommande}/{idLigneCommande}}")
    ResponseEntity<CommandeFournisseurDto> deleteArticle(@PathVariable(value = "idCommande") Integer idCommande, @PathVariable(value = "idLigneCommande") Integer idLigneCommande);




    @GetMapping(FIND_COMMANDE_FOURNISSEURS_BY_ID_ENDPOINT)
    CommandeFournisseurDto findById(@PathVariable("idCommandeFournisseur") Integer id);

    @GetMapping(FIND_COMMANDE_FOURNISSEURS_BY_CODE_ENDPOINT)
    CommandeFournisseurDto findByCode(@PathVariable("codeCommandeFournisseur") String code);

    @GetMapping(FIND_ALL_COMMANDE_FOURNISSEURS_ENDPOINT)
    List<CommandeFournisseurDto> findAll();

    @DeleteMapping(DELETE_COMMANDE_FOURNISSEURS_ENDPOINT)
    void delete(@PathVariable("idCommandeFournisseur") Integer id);
}
