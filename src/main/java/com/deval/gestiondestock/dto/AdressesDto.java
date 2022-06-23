package com.deval.gestiondestock.dto;

import com.deval.gestiondestock.model.Adresses;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized //missing
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect
public class AdressesDto {

    private  String adresse1;

    private  String adresse2;

    private  String Ville;

    private  String codepostale;

    private  String Pays;

    public static AdressesDto fromEntity(Adresses adresses){
        if (adresses == null){
            return null;
        }
        return AdressesDto.builder()
                .adresse1(adresses.getAdresse1())
                .adresse2(adresses.getAdresse2())
                .Ville(adresses.getVille())
                .codepostale(adresses.getCodepostale())
                .Pays(adresses.getPays())
                .build();
    }

    public static Adresses toEntity(AdressesDto adressesDto){
        if (adressesDto == null){
            return null;
        }

        Adresses adresses = new Adresses();
        adresses.setAdresse1(adressesDto.getAdresse1());
        adresses.setAdresse2(adressesDto.getAdresse2());
        adresses.setVille(adressesDto.getVille());
        adresses.setCodepostale(adressesDto.getCodepostale());
        adresses.setPays(adressesDto.getPays());
        return adresses;
    }
}
