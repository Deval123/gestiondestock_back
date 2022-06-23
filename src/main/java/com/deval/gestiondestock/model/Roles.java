package com.deval.gestiondestock.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "roles")
@Jacksonized
public class Roles extends AbstractEntity{

    @Column(name = "rolename")
    private String roleName;

    @ManyToOne
    @JoinColumn(name = "idutilisateur")
    private Utilisateur utilisateur;
}
