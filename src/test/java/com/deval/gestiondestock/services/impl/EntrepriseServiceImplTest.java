package com.deval.gestiondestock.services.impl;

import com.deval.gestiondestock.dto.EntrepriseDto;
import com.deval.gestiondestock.dto.UtilisateurDto;
import com.deval.gestiondestock.services.EntrepriseService;
import com.deval.gestiondestock.services.UtilisateurService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public  class EntrepriseServiceImplTest {

    @Autowired
    private EntrepriseService service;

    @Test
    public void shouldSaveEntrepriseWithSuccess(){

        EntrepriseDto expectedEntreprise = EntrepriseDto.builder()
                .nom("Devalère")
                .codeFiscal("24534634")
                .email("dev@dev.com")
                .numTel("676467228")
                .description("test pour visualiser postMan")
                .build();

        EntrepriseDto savedEntreprise = service.save(expectedEntreprise);

        assertNotNull(savedEntreprise);
        assertNotNull(savedEntreprise.getId());
        assertEquals(expectedEntreprise.getNom(), savedEntreprise.getNom());
        assertEquals(expectedEntreprise.getCodeFiscal(), savedEntreprise.getCodeFiscal());
        assertEquals(expectedEntreprise.getEmail(), savedEntreprise.getEmail());
        assertEquals(expectedEntreprise.getNumTel(), savedEntreprise.getNumTel());
        assertEquals(expectedEntreprise.getDescription(), savedEntreprise.getDescription());

    }

}