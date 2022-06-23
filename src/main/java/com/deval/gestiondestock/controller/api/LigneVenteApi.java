package com.deval.gestiondestock.controller.api;

import com.deval.gestiondestock.dto.LigneVenteDto;
import com.deval.gestiondestock.dto.VentesDto;
import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.deval.gestiondestock.utils.Constants.APP_ROOT;


@Api(APP_ROOT + "/ligneVente")
public interface LigneVenteApi {

    @PostMapping(value = APP_ROOT + "/ligneVente/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    LigneVenteDto save(@RequestBody LigneVenteDto dto);

    @GetMapping(value = APP_ROOT + "/ligneVente/idLigneVente", produces = MediaType.APPLICATION_JSON_VALUE)
    LigneVenteDto findById(@PathVariable("idLigneVente") Integer id);

    @GetMapping(value = APP_ROOT + "/ligneVente/all", produces = MediaType.APPLICATION_JSON_VALUE)
    List<LigneVenteDto> findAll();

    @DeleteMapping(value = APP_ROOT + "/ligneVente/delete/{id}")
    void delete(@PathVariable("id") Integer id);
}
