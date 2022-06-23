package com.deval.gestiondestock.validator;

import com.deval.gestiondestock.dto.LigneVenteDto;

import java.util.ArrayList;
import java.util.List;

public class LigneVenteValidator {
    public static List<String> validate(LigneVenteDto ligneVenteDto){
        List<String> errors = new ArrayList<>();
        if(ligneVenteDto == null){
            errors.add("Le champs quantité est obligatoire");
            errors.add("Le champs prix unitaire est obligatoire");
            errors.add("Le champs vente est obligatoire");
            return errors;
        }

        if(ligneVenteDto.getQuantite() == null){
            errors.add("Le champs quantité est obligatoire");
        }
        if(ligneVenteDto.getPrixUnitaire() == null){
            errors.add("Le champs prix unitaire est obligatoire");
        }
        if(ligneVenteDto.getVente() == null){
            errors.add("Le champs vente est obligatoire");
        }
        return errors;
    }
}
