package com.alain.dao.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table
public class Secteur implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String description;

//    // Associations
//    @ManyToOne
//    private Utilisateur utilisateur;
//    @ManyToOne
//    private Spot spot;
//
//    @OneToMany (mappedBy = "secteur")
//    private List<Voie> voies;
//    @OneToMany (mappedBy = "secteur")
//    private List<CommentaireVoie> commentaires;
//    @OneToMany (mappedBy = "secteur")
//    private List<ComplementVoie> complements;

    public Secteur() {
    }

    public Secteur(String nom, String description) {
        this.nom = nom;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public Utilisateur getUtilisateur() {
//        return utilisateur;
//    }
//
//    public void setUtilisateur(Utilisateur utilisateur) {
//        this.utilisateur = utilisateur;
//    }
//
//    public Spot getSpot() {
//        return spot;
//    }
//
//    public void setSpot(Spot spot) {
//        this.spot = spot;
//    }
//
//    public List<Voie> getVoies() {
//        return voies;
//    }
//
//    public void setVoies(List<Voie> voies) {
//        this.voies = voies;
//    }
//
//    public List<CommentaireVoie> getCommentaires() {
//        return commentaires;
//    }
//
//    public void setCommentaires(List<CommentaireVoie> commentaires) {
//        this.commentaires = commentaires;
//    }
//
//    public List<ComplementVoie> getComplements() {
//        return complements;
//    }
//
//    public void setComplements(List<ComplementVoie> complements) {
//        this.complements = complements;
//    }
}

