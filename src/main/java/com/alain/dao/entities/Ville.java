package com.alain.dao.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table
public class Ville implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;

    @ManyToOne
    private Departement departement;

    @OneToMany (mappedBy = "ville")
    private List<Spot> spots;

    public Ville() {
    }

    public Ville(String nom, Departement departement) {
        this.nom = nom;
        this.departement = departement;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void addSpot(Spot spot) {
        this.spots.add(spot);
    }
}
