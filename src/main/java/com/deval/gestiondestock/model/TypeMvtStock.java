package com.deval.gestiondestock.model;


import javax.persistence.Table;

@Table(name = "typemvtstock")
public enum TypeMvtStock {
    ENTREE , SORTIE, CORRECTION_POS, CORRECTION_NEG;

}
