package com.deval.gestiondestock.validator;

import com.deval.gestiondestock.dto.LigneCommandeFournisseurDto;

import java.util.ArrayList;
import java.util.List;

public class LigneCommandeFournisseurValidator {
    public static List<String> validate(LigneCommandeFournisseurDto ligneCommandeFournisseurDto){
        List<String> errors = new ArrayList<>();
        if (ligneCommandeFournisseurDto == null){
            errors.add("Veuillez renseigner la quantite de la Ligne Commande fournisseur");
            errors.add("Veuillez renseigner le prix unitaire de la Ligne Commande fournisseur");
            errors.add("Veuillez renseigner la Ligne Commande fournisseur");
            errors.add("Veuillez renseigner les articles Ligne Commande fournisseur");
            return errors;
        }
        if(ligneCommandeFournisseurDto.getArticle() == null){
            errors.add("Veuillez renseigner les articles de la Ligne de Commande fournisseur");
        }
        if(ligneCommandeFournisseurDto.getCommandeFournisseur() == null){
            errors.add("Veuillez renseigner la commande de la Ligne de Commande fournisseur");
        }
        if(ligneCommandeFournisseurDto.getQuantite() == null){
            errors.add("Veuillez renseigner la quantite de la Ligne de Commande fournisseur");
        }
        if(ligneCommandeFournisseurDto.getPrixUnitaire() == null){
            errors.add("Veuillez renseigner le prix unitaire de la Ligne de Commande fournisseur");
        }
        return errors;
    }

}
