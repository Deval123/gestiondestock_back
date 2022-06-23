package com.deval.gestiondestock.services.impl;

import com.deval.gestiondestock.dto.CategoryDto;
import com.deval.gestiondestock.dto.UtilisateurDto;
import com.deval.gestiondestock.services.UtilisateurService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UtilisateurServiceImplTest {


    @Autowired
    private UtilisateurService service;

    @Test
    public void shouldSaveUtilisateurWithSuccess(){

        UtilisateurDto expectedUtilisateur = UtilisateurDto.builder()
                .nom("Devalère")
                .prenom("Luna")
                .email("dev@dev.com")
                .numTel("676467228")
                .build();

        UtilisateurDto savedUtilisateur = service.save(expectedUtilisateur);

        assertNotNull(savedUtilisateur);
        assertNotNull(savedUtilisateur.getId());
        assertEquals(expectedUtilisateur.getNom(), savedUtilisateur.getNom());
        assertEquals(expectedUtilisateur.getPrenom(), savedUtilisateur.getPrenom());
        assertEquals(expectedUtilisateur.getEmail(), savedUtilisateur.getEmail());
        assertEquals(expectedUtilisateur.getNumTel(), savedUtilisateur.getNumTel());
    }

}