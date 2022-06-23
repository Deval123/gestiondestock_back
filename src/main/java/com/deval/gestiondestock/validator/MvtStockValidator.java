package com.deval.gestiondestock.validator;

import com.deval.gestiondestock.dto.MvtStockDto;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MvtStockValidator {
    public static List<String> validate(MvtStockDto mvtStockDto){
        List<String> errors = new ArrayList<>();
        if (mvtStockDto == null){
            errors.add("Veuillez renseigner les articles du mouvement de stock");
            errors.add("Veuillez renseigner la date du mouvement de stock");
            errors.add("Veuillez renseigner la quantite du mouvement de stock");
            errors.add("Veuillez renseigner le type de mouvement");
            return errors;
        }
        if(mvtStockDto.getArticle() == null || mvtStockDto.getArticle().getId() == null){
            errors.add("Veuillez renseigner les articles du mouvement de stock");
        }
        if(mvtStockDto.getDateMvt() == null){
            errors.add("Veuillez renseigner la date du mouvement de stock");
        }
        if(mvtStockDto.getQuantite() == null || mvtStockDto.getQuantite().compareTo(BigDecimal.ZERO) == 0){
            errors.add("Veuillez renseigner la quantite du mouvement de stock");
        }
        if(!StringUtils.hasLength(mvtStockDto.getTypeMvt().name())){
            errors.add("Veuillez renseigner le type de mouvement");
        }
        return errors;
    }

}
