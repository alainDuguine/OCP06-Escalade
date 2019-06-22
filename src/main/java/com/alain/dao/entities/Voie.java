package com.alain.dao.entities;

import javax.persistence.*;

@Entity
@Table
public class Voie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    @Column (length = 2)
    private String cotation;
    private double altitude;
    private int nbLongueurs;
    private String commentaire;

    @ManyToOne
    private Secteur secteur;

    public Voie() {
    }

}
