package com.alain.dao.entities;

import javax.persistence.*;

@Entity
@Table
@PrimaryKeyJoinColumn(name="id")
public class CommentaireSecteur extends CommentaireSpot {

    @ManyToOne
    private Secteur secteur;

    public CommentaireSecteur() {
    }

    public Secteur getSecteur() {
        return secteur;
    }

    public void setSecteur(Secteur secteur) {
        this.secteur = secteur;
    }
}