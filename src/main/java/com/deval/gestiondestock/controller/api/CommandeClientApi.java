package com.deval.gestiondestock.controller.api;

import com.deval.gestiondestock.dto.ClientDto;
import com.deval.gestiondestock.dto.CommandeClientDto;
import com.deval.gestiondestock.dto.LigneCommandeClientDto;
import com.deval.gestiondestock.model.EtatCommande;
import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static com.deval.gestiondestock.utils.Constants.APP_ROOT;
@Api(APP_ROOT + "/commandeClients")
public interface CommandeClientApi {

    @PostMapping(APP_ROOT + "/CommandeClients/create")
    ResponseEntity<CommandeClientDto> save(@RequestBody CommandeClientDto dto);


    @PatchMapping(APP_ROOT + "/CommandeClients/update/etat/{idCommande}/{etatCommande}")
    ResponseEntity<CommandeClientDto> updateEtatCommande(@PathVariable(value = "idCommande") Integer idCommande,@PathVariable(value = "etatCommande") EtatCommande etatCommande);


    @PatchMapping(APP_ROOT + "/CommandeClients/update/quantite/{idCommande}/{idLigneCommande}/{quantite}")
    ResponseEntity<CommandeClientDto> updateQuantiteCommande(@PathVariable(value = "idCommande")  Integer idCommande,
         @PathVariable(value = "idLigneCommande") Integer idLigneCommande, @PathVariable(value = "quantite") BigDecimal quantite);

    @PatchMapping(APP_ROOT + "/CommandeClients/update/client/{idCommande}/{idClient}")
    ResponseEntity<CommandeClientDto> updateClient(@PathVariable(value = "idCommande") Integer idCommande, @PathVariable(value = "idClient") Integer idClient);

    @PatchMapping(APP_ROOT + "/CommandeClients/update/article/{idCommande}/{idLigneCommande}/{idArticle}")
    ResponseEntity<CommandeClientDto> updateArticle(@PathVariable(value = "idCommande") Integer idCommande,
        @PathVariable(value = "idLigneCommande") Integer idLigneCommande, @PathVariable(value = "idArticle") Integer idArticle);


    @GetMapping(APP_ROOT + "/CommandeClients/ligneCommandes/{idCommande}")
    ResponseEntity<List<LigneCommandeClientDto>> findAllLigneCommandesClientByCommandeClientId(@PathVariable(value = "idCommande") Integer idCommande);

    @DeleteMapping(APP_ROOT + "/CommandeClients/delete/article/{idCommande}/{idLigneCommande}}")
    ResponseEntity<CommandeClientDto> deleteArticle(@PathVariable(value = "idCommande") Integer idCommande, @PathVariable(value = "idLigneCommande") Integer idLigneCommande);

    @GetMapping(APP_ROOT + "/CommandeClients/{idCommandeClient}")
    ResponseEntity<CommandeClientDto> findById(@PathVariable("idCommandeClient") Integer idCommandeClient);

    @GetMapping(APP_ROOT + "/CommandeClients/{codeCommandeClient}")
    ResponseEntity<CommandeClientDto> findByCode(@PathVariable("codeCommandeClient") String code);

    @GetMapping(APP_ROOT + "/CommandeClients/all")
    ResponseEntity<List<CommandeClientDto>>  findAll();

    @DeleteMapping(APP_ROOT + "/CommandeClients/delete/{idCommandeClient}")
    ResponseEntity<Void> delete(@PathVariable("idCommandeClient") Integer id);
}
