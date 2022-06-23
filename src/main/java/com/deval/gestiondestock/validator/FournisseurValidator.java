package com.deval.gestiondestock.validator;

import com.deval.gestiondestock.dto.AdressesDto;
import com.deval.gestiondestock.dto.FournisseurDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class FournisseurValidator {
    public static List<String> validate(FournisseurDto fournisseurDto){
        List<String> errors = new ArrayList<>();

        if (fournisseurDto == null){
            errors.add("Veuillez renseigner le nom du fournisseur");
            errors.add("Veuillez renseigner le prénom du fournisseur");
            errors.add("Veuillez renseigner le mail du fournisseur");
            errors.add("Veuillez renseigner le numéro de téléphone du fournisseur");
            errors.addAll(AdresseValidator.validate(null));
            return errors;
        }

        if(!StringUtils.hasLength(fournisseurDto.getNom())){
            errors.add("Veuillez renseigner le nom du fournisseur");
        }
        if(!StringUtils.hasLength(fournisseurDto.getPrenom())){
            errors.add("Veuillez renseigner le Prénom du fournisseur");
        }
        if(!StringUtils.hasLength(fournisseurDto.getMail())){
            errors.add("Veuillez renseigner le mail du fournisseur");
        }
        if(!StringUtils.hasLength(fournisseurDto.getNumTel())){
            errors.add("Veuillez renseigner le numéro de téléphone du fournisseur");
        }
        errors.addAll(AdresseValidator.validate(fournisseurDto.getAdresse()));
        return errors;
    }
}
