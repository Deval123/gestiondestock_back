package com.deval.gestiondestock.controller;

import com.deval.gestiondestock.controller.api.ClientApi;
import com.deval.gestiondestock.dto.ClientDto;
import com.deval.gestiondestock.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class ClientController implements ClientApi {

    //Field Injection
    @Autowired
    private ClientService clientService;

    //Constructor Injection
    @Autowired
    public ClientController(ClientService clientService){
         this.clientService = clientService;
    }

    @Override
    public ClientDto save(ClientDto dto) {
        return clientService.save(dto);
    }

    @Override
    public ClientDto findById(Integer id) {
        return clientService.findById(id);
    }

    @Override
    public ClientDto findByNom(String nom) {
        return clientService.findByNom(nom);
    }


    @Override
    public ClientDto findByMail(String mail) {
        return clientService.findByMail(mail);
    }

    @Override
    public ClientDto findByNumTel(String tel) {
        return clientService.findByNumTel(tel);
    }

    @Override
    public List<ClientDto> findAll() {
        return clientService.findAll();
    }

    @Override
    public void delete(Integer id) {
        clientService.delete(id);
    }
}
