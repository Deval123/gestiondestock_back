package com.deval.gestiondestock.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "category")
public class Category extends AbstractEntity{

    @Column(name = "codecategory")
    private  String codecategory;

    @Column(name = "designation")
    private  String designation;

    @Column(name = "identreprise")
    private  Integer identreprise;

    @OneToMany(mappedBy = "category")
    private List<Article> article;
}
