package com.deval.gestiondestock.controller.api;

import com.deval.gestiondestock.dto.AdressesDto;
import com.deval.gestiondestock.dto.FournisseurDto;
import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.deval.gestiondestock.utils.Constants.APP_ROOT;


@Api(APP_ROOT + "/fournisseurs")
public interface FournisseurApi {

    @PostMapping(value = APP_ROOT + "/fournisseur/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    FournisseurDto save(@RequestBody FournisseurDto dto);

    @GetMapping(value = APP_ROOT + "/fournisseur/idFournisseur", produces = MediaType.APPLICATION_JSON_VALUE)
    FournisseurDto findById(@PathVariable("idFournisseur") Integer id);

    @GetMapping(value = APP_ROOT + "/fournisseur/{nom}", produces = MediaType.APPLICATION_JSON_VALUE)
    FournisseurDto findByNom(@PathVariable("nom") String nom);

    @GetMapping(value = APP_ROOT + "/fournisseur/all", produces = MediaType.APPLICATION_JSON_VALUE)
    List<FournisseurDto> findAll();

    @DeleteMapping(value = APP_ROOT + "/fournisseur/delete/{idFournisseur}")
    void delete(Integer id);
}
