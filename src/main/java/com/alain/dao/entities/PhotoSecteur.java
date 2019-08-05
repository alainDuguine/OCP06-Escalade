package com.alain.dao.entities;

import javax.persistence.*;
import java.io.InputStream;

@Entity
@Table
@PrimaryKeyJoinColumn(name="id")
public class PhotoSecteur extends Photo {

    @ManyToOne
    private Secteur secteur;

    /* ********************************************************************************************
     **** CONSTRUCTORS      ************************************************************************
     *********************************************************************************************** */

    public PhotoSecteur() {
    }

    public PhotoSecteur(Secteur secteur) {
        this.secteur = secteur;
    }

    public PhotoSecteur(String nom, String erreur, InputStream contenu, Secteur secteur) {
        super(nom, erreur, contenu);
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
