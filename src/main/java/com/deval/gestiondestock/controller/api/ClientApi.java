package com.deval.gestiondestock.controller.api;

import com.deval.gestiondestock.dto.ClientDto;
import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.deval.gestiondestock.utils.Constants.APP_ROOT;

@Api(APP_ROOT + "/clients")
public interface ClientApi {

    @PostMapping(value = APP_ROOT + "/clients/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ClientDto save(@RequestBody ClientDto dto);

    @GetMapping(value = APP_ROOT + "/clients/idClient", produces = MediaType.APPLICATION_JSON_VALUE)
    ClientDto findById(@PathVariable("idClient") Integer id);

    @GetMapping(value = APP_ROOT + "/clients/{nom}", produces = MediaType.APPLICATION_JSON_VALUE)
    ClientDto findByNom(@PathVariable("nom") String nom);

    @GetMapping(value = APP_ROOT + "/clients/{mail}", produces = MediaType.APPLICATION_JSON_VALUE)
    ClientDto findByMail(@PathVariable("mail")  String mail);

    @GetMapping(value = APP_ROOT + "/clients/{tel}", produces = MediaType.APPLICATION_JSON_VALUE)
    ClientDto findByNumTel(@PathVariable("mail")   String tel);

    @GetMapping(value = APP_ROOT + "/clients/all", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ClientDto> findAll();

    @DeleteMapping(value = APP_ROOT + "/clients/delete/{idClients}")
    void delete(@PathVariable("idClients") Integer id);

}
