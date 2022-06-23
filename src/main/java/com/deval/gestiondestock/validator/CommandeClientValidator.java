package com.deval.gestiondestock.validator;

import com.deval.gestiondestock.dto.CommandeClientDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CommandeClientValidator {
    public static List<String> validate(CommandeClientDto commandeClientDto){
        List<String> errors = new ArrayList<>();
        if (commandeClientDto == null){
            errors.add("Veuillez renseigner le code de la commande");
            errors.add("Veuillez renseigner le date de la commande");
            errors.add("Veuillez renseigner l'etat de la commande");
            errors.add("Veuillez renseigner le client");
            return errors;
        }

        if(!StringUtils.hasLength(commandeClientDto.getCode())){
            errors.add("Veuillez renseigner le code de la commande");
        }
        if(!StringUtils.hasLength(commandeClientDto.getCode())){
            errors.add("Veuillez renseigner le date de la commande");
        }
        if(!StringUtils.hasLength(commandeClientDto.getCode())){
            errors.add("Veuillez renseigner l'etat de la commande");
        }
        if(commandeClientDto.getClient() == null || commandeClientDto.getId() == null){
            errors.add("Veuillez renseigner le client");
        }
        return errors;
    }
}
