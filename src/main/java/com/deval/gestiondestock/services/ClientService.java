package com.deval.gestiondestock.services;

import com.deval.gestiondestock.dto.ArticleDto;
import com.deval.gestiondestock.dto.CategoryDto;
import com.deval.gestiondestock.dto.ClientDto;

import java.util.List;

public interface ClientService {

    ClientDto save(ClientDto dto);

    ClientDto findById(Integer id);

    ClientDto findByNom(String nom);

    ClientDto findByMail(String mail);

    ClientDto findByNumTel(String tel);

    List<ClientDto> findAll();

    void delete(Integer id);
}
