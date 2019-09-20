package com.alain.dao.entities;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.InputStream;

/**
 * Voir Photo
 */
@Entity
@Table
@PrimaryKeyJoinColumn(name="id")
@OnDelete(action = OnDeleteAction.CASCADE)
public class PhotoSecteur extends Photo {

    private static final Logger logger = LogManager.getLogger("PhotoSecteur");

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

    public void removeRelation() {
        logger.info("Suppression de l'association avec le secteur : " + this.secteur.getId());
        this.secteur = null;
    }

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
