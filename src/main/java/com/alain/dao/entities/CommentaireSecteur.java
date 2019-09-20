package com.alain.dao.entities;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

/**
 * Non implémenté - voir CommentaireSpot
 */
@Entity
@Table
@PrimaryKeyJoinColumn(name="id")
@OnDelete(action = OnDeleteAction.CASCADE)
public class CommentaireSecteur extends CommentaireSpot {

    @ManyToOne
    private Secteur secteur;

    /* ********************************************************************************************
    **** CONSTRUCTORS      ************************************************************************
    *********************************************************************************************** */

    public CommentaireSecteur() {
    }

    public CommentaireSecteur(Secteur secteur) {
        this.secteur = secteur;
    }

    /* ********************************************************************************************
     **** METHODS           ************************************************************************
     ******************************************************************************************** */


    /* ***********************************************************************************************
     **** GETTERS & SETTERS ************************************************************************
     *********************************************************************************************** */

    public Secteur getSecteur() {
        return secteur;
    }

    public void setSecteur(Secteur secteur) {
        this.secteur = secteur;
    }


}