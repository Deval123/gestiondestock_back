package com.deval.gestiondestock.validator;

import com.deval.gestiondestock.dto.LigneCommandeClientDto;

import java.util.ArrayList;
import java.util.List;

public class LigneCommandeClientValidator {
    public static List<String> validate(LigneCommandeClientDto ligneCommandeClientDto){
        List<String> errors = new ArrayList<>();
/*        if (ligneCommandeClientDto == null){
            errors.add("Veuillez renseigner la quantite de la Ligne Commande Client");
            errors.add("Veuillez renseigner le prix unitaire de la Ligne Commande Client");
            errors.add("Veuillez renseigner la Ligne Commande Client");
            errors.add("Veuillez renseigner les articles Ligne Commande Client");
            return errors;
        }
        if(ligneCommandeClientDto.getArticle() == null){
            errors.add("Veuillez renseigner les articles de la Ligne de Commande Client");
        }
        if(ligneCommandeClientDto.getCommandeclient() == null){
            errors.add("Veuillez renseigner la commande de la Ligne de Commande Client");
        }
        if(ligneCommandeClientDto.getQuantite() == null){
            errors.add("Veuillez renseigner la quantite de la Ligne de Commande Client");
        }
        if(ligneCommandeClientDto.getPrixUnitaire() == null){
            errors.add("Veuillez renseigner le prix unitaire de la Ligne de Commande Client");
        }*/
        return errors;
    }
}
