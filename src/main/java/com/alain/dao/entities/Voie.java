package com.alain.dao.entities;

import javax.persistence.*;
import java.util.List;

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
    private String description;

    // Associations
    @ManyToOne
    private Utilisateur utilisateur;
    @ManyToOne
    private Secteur secteur;

    @OneToMany (mappedBy = "voie")
    private List<CommentaireVoie> commentaires;
    @OneToMany (mappedBy = "voie")
    private List<ComplementVoie> complements;
    @OneToMany (mappedBy = "voie")
    private List<PhotoVoie> photos;

    public Voie() {
    }

    public Voie(String nom, String cotation, double altitude, int nbLongueurs, String commentaire) {
        this.nom = nom;
        this.cotation = cotation;
        this.altitude = altitude;
        this.nbLongueurs = nbLongueurs;
        this.description = commentaire;
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

    public Secteur getSecteur() {
        return secteur;
    }

    public void setSecteur(Secteur secteur) {
        this.secteur = secteur;
    }
}
