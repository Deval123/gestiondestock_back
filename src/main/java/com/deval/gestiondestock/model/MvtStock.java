package com.deval.gestiondestock.model;

import lombok.*;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;
import javax.persistence.Column;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "mvtstock")
public class MvtStock extends AbstractEntity{

    @Column(name = "datemvt")
    private Instant dateMvt;

    @Column(name = "quantite")
    private BigDecimal quantite;

    @ManyToOne
    @JoinColumn(name = "idarticle")
    private  Article article;

    @Column(name = "typemvt")
    private  TypeMvtStock typeMvt;


    @Column(name = "sourcemvt")
    private  SourceMvtStock SourceMvt;

    @Column(name = "identreprise")
    private  Integer identreprise;
}
