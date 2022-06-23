package com.deval.gestiondestock.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "utilisateur")
@Jacksonized
public class Utilisateur extends AbstractEntity{

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "email")
    private String email;

    @Column(name = "datedenaissance")
    private Instant dateDeNaissance;

    @Column(name = "motdepasse")
    private String motDePasse;

    @Embedded
    private Adresses adresse;

    @Column(name = "photo")
    private String photo;

    @Column(name = "numtel")
    private  String numTel;

    @ManyToOne
    @JoinColumn(name = "identreprise")
    private Entreprise entreprise;

    @OneToMany(mappedBy = "utilisateur")
    private List<Roles> roles;
}
