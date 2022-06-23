package com.deval.gestiondestock.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "article")
public class Article extends AbstractEntity{


    @Column(name = "codearticle")
    private  String codeArticle;

    @Column(name = "designation")
    private  String Designation;

    @Column(name = "prixunitaireht")
    private  BigDecimal PrixUnitaireHt;

    @Column(name = "tauxtva")
    private BigDecimal TauxTVA;

    @Column(name = "prixunitairettc")
    private  BigDecimal PrixUnitaireTTC;

    @Column(name = "photo")
    private  String Photo;

    @Column(name = "identreprise")
    private  Integer identreprise;

    @ManyToOne
    @JoinColumn(name = "idcategory")
    private  Category category;

    @OneToMany(mappedBy = "article")
    private List<LigneVente> ligneVentes;


    @OneToMany(mappedBy = "article")
    private List<LigneCommandeClient> ligneCommandeClients;

    @OneToMany(mappedBy = "article")
    private List<LigneCommandeFournisseur> ligneCommandeFournisseurs;

    @OneToMany(mappedBy = "article")
    private List<MvtStock> mvtStocks;
}
