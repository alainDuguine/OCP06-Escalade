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
    private String commentaire;

    // Associations
    @ManyToOne
    private Utilisateur utilisateur;
    @ManyToOne
    private Secteur secteur;

    @OneToMany (mappedBy = "voie")
    private List<CommentaireVoie> commentaires;
    @OneToMany (mappedBy = "voie")
    private List<ComplementVoie> complements;

    public Voie() {
    }

    public Voie(String nom, String cotation, double altitude, int nbLongueurs, String commentaire) {
        this.nom = nom;
        this.cotation = cotation;
        this.altitude = altitude;
        this.nbLongueurs = nbLongueurs;
        this.commentaire = commentaire;
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

    public String getCotation() {
        return cotation;
    }

    public void setCotation(String cotation) {
        this.cotation = cotation;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public int getNbLongueurs() {
        return nbLongueurs;
    }

    public void setNbLongueurs(int nbLongueurs) {
        this.nbLongueurs = nbLongueurs;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Secteur getSecteur() {
        return secteur;
    }

    public void setSecteur(Secteur secteur) {
        this.secteur = secteur;
    }

    public List<CommentaireVoie> getCommentaires() {
        return commentaires;
    }

    public void setCommentaires(List<CommentaireVoie> commentaires) {
        this.commentaires = commentaires;
    }

    public List<ComplementVoie> getComplements() {
        return complements;
    }

    public void setComplements(List<ComplementVoie> complements) {
        this.complements = complements;
    }
}
