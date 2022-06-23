package com.deval.gestiondestock.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
@Jacksonized //missing
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect
public class Adresses{

    @Column(name = "adresse1")
    private  String adresse1;

    @Column(name = "adresse2")
    private  String adresse2;

    @Column(name = "ville")
    private  String Ville;

    @Column(name = "codepostale")
    private  String codepostale;

    @Column(name = "pays")
    private  String Pays;
}
