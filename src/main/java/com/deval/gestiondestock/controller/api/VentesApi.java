package com.deval.gestiondestock.controller.api;

import com.deval.gestiondestock.dto.UtilisateurDto;
import com.deval.gestiondestock.dto.VentesDto;
import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

import static com.deval.gestiondestock.utils.Constants.*;


@Api(APP_ROOT + "/ventes")
public interface VentesApi {

    @PostMapping(value = CREATE_VENTES_ENDPOINT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    VentesDto save(@RequestBody VentesDto dto);


    @GetMapping(value = FIND_VENTES_BY_ID_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
    VentesDto findById(@PathVariable("idVentes") Integer id);


    @GetMapping(value = FIND_VENTES_BY_CODE_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
    VentesDto findByCode(String code);

    @GetMapping(value = FIND_ALL_VENTES_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
    List<VentesDto> findAll();

    @DeleteMapping(value = DELETE_VENTES_ENDPOINT)
    void delete(@PathVariable("idVentes") Integer id);

}
