package com.alain.dao.entities;

import com.alain.dao.contract.EntityRepository;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@Table
public class Voie extends Entitie implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private double altitude;
    private int nbLongueurs;
    private String description;

    // Associations
    @ManyToOne
    private Utilisateur utilisateur;
    @ManyToOne
    private Secteur secteur;
    @ManyToOne
    private Cotation cotation;

    @OneToMany (mappedBy = "voie")
    private List<CommentaireVoie> commentaires;
    @OneToMany (mappedBy = "voie")
    private List<ComplementVoie> complements;
    @OneToMany (mappedBy = "voie")
    private List<PhotoVoie> photos;

    /* ********************************************************************************************
     **** CONSTRUCTORS      ************************************************************************
     *********************************************************************************************** */

    public Voie() {
    }

    public Voie(String nom, double altitude, int nbLongueurs, String commentaire) {
        this.nom = nom;
        this.altitude = altitude;
        this.nbLongueurs = nbLongueurs;
        this.description = commentaire;
    }

    /* ********************************************************************************************
     **** METHODS           ************************************************************************
     ******************************************************************************************** */

    /* ***********************************************************************************************
     **** GETTERS & SETTERS ************************************************************************
     *********************************************************************************************** */

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
        secteur.addVoie(this);
    }

    public void addPhoto(PhotoVoie photo) {
        photo.setVoie(this);
        if (this.photos.isEmpty()){
            this.photos = new ArrayList<>();
        }
        this.photos.add(photo);
    }

    public Cotation getCotation() {
        return cotation;
    }

    public void setCotation(Cotation cotation) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
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

    public List<PhotoVoie> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotoVoie> photos) {
        this.photos = photos;
    }

    @Override
    public void hydrate(HttpServletRequest req) {

    }

    @Override
    public Map<String, String> checkErreurs(EntityRepository dao, HttpServletRequest req) {
        return null;
    }
}
