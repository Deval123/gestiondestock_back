package com.deval.gestiondestock.model;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import javax.persistence.Entity;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "commandeclient")
public class CommandeClient extends AbstractEntity{

    @Column(name = "code")
    private  String code;

    @Column(name = "datecommande")
    private Instant dateCommande;

    @Column(name = "etatcommande")
    private EtatCommande etatCommande;

    @Column(name = "identreprise")
    private  Integer identreprise;

    @ManyToOne
    @JoinColumn(name = "idclient")
    private  Client client;


    @OneToMany(mappedBy = "commandeClient")
    private List<LigneCommandeClient> ligneCommandeClients;
}
