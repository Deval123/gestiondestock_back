package com.deval.gestiondestock.controller.api;

import com.deval.gestiondestock.dto.EntrepriseDto;
import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.deval.gestiondestock.utils.Constants.APP_ROOT;
@Api(APP_ROOT + "/entreprise")
public interface EntrepriseApi {

    @PostMapping(value = APP_ROOT + "/entreprise/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    EntrepriseDto save(@RequestBody EntrepriseDto dto);

    @GetMapping(value = APP_ROOT + "/entreprise/idEntreprise/idEntreprise", produces = MediaType.APPLICATION_JSON_VALUE)
    EntrepriseDto findById(@PathVariable("idEntreprise") Integer id);

    @GetMapping(value = APP_ROOT + "/entreprise/nom/{nom}", produces = MediaType.APPLICATION_JSON_VALUE)
    EntrepriseDto findByNom(@PathVariable("nom") String nom);

    @GetMapping(value = APP_ROOT + "/entreprise/codefiscal/{codefiscal}", produces = MediaType.APPLICATION_JSON_VALUE)
    EntrepriseDto findByCodeFiscal(@PathVariable("codefiscal") String codefiscal);

    @GetMapping(value = APP_ROOT + "/entreprise/tel/{tel}", produces = MediaType.APPLICATION_JSON_VALUE)
    EntrepriseDto findByNumTel(@PathVariable("tel")  String tel);

    @GetMapping(value = APP_ROOT + "/entreprise/all", produces = MediaType.APPLICATION_JSON_VALUE)
    List<EntrepriseDto> findAll();

    @DeleteMapping(value = APP_ROOT + "/entreprise/delete/{idEntreprise}")
    void delete(@PathVariable("idEntreprise")  Integer id);
}
