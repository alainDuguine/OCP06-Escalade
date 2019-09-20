package com.alain.dao.entities;

import javax.persistence.*;
import java.util.Date;


/**
 * Non implémenté - voir Complement
 */
@Entity
@Table
@PrimaryKeyJoinColumn(name="id")
public class ComplementSecteur extends Complement {

    @ManyToOne
    private Secteur secteur;

    /* ********************************************************************************************
     **** CONSTRUCTORS      ************************************************************************
     *********************************************************************************************** */

    public ComplementSecteur() {
    }

    public ComplementSecteur(Secteur secteur) {
        this.secteur = secteur;
    }

    public ComplementSecteur(Date dateHeure, String contenu, Utilisateur utilisateur, Secteur secteur) {
        super(dateHeure, contenu, utilisateur);
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
