package com.deval.gestiondestock.validator;

import com.deval.gestiondestock.dto.CommandeClientDto;
import com.deval.gestiondestock.dto.CommandeFournisseurDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CommandeFournisseurValidator {

    public static List<String> validate(CommandeFournisseurDto commandeFournisseurDto){
        List<String> errors = new ArrayList<>();
        if (commandeFournisseurDto == null){
            errors.add("Veuillez renseigner le code de la commande");
            errors.add("Veuillez renseigner le date de la commande");
            errors.add("Veuillez renseigner l'etat de la commande");
            errors.add("Veuillez renseigner le Fournisseur");
            return errors;
        }

        if(!StringUtils.hasLength(commandeFournisseurDto.getCode())){
            errors.add("Veuillez renseigner le code de la commande");
        }
        if(!StringUtils.hasLength(commandeFournisseurDto.getCode())){
            errors.add("Veuillez renseigner le date de la commande");
        }
        if(!StringUtils.hasLength(commandeFournisseurDto.getCode())){
            errors.add("Veuillez renseigner l'etat de la commande");
        }
        if(commandeFournisseurDto.getFournisseur() == null || commandeFournisseurDto.getId() == null){
            errors.add("Veuillez renseigner le Fournisseur");
        }
        return errors;
    }
}
