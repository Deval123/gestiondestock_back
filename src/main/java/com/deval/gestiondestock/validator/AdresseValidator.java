package com.deval.gestiondestock.validator;

import com.deval.gestiondestock.dto.AdressesDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class AdresseValidator {
    public static List<String> validate(AdressesDto adressesDto){
        List<String> errors = new ArrayList<>();
        if (adressesDto == null){
            errors.add("Veuillez renseigner le code de la adresse");
            errors.add("Veuillez renseigner une adresse");
            errors.add("Veuillez renseigner la ville");
            errors.add("Veuillez renseigner le Pays");
            errors.add("Veuillez renseigner le Code postale");
            return errors;
        }
        if (!StringUtils.hasLength(adressesDto.getAdresse1())){
            errors.add("Veuillez renseigner une adresse");
        }
        if (!StringUtils.hasLength(adressesDto.getVille())){
            errors.add("Veuillez renseigner la ville");
        }
        if (!StringUtils.hasLength(adressesDto.getPays())){
            errors.add("Veuillez renseigner le Pays");
        }
        if (!StringUtils.hasLength(adressesDto.getCodepostale())){
            errors.add("Veuillez renseigner le Code postale");
        }
        return errors;
    }
}
