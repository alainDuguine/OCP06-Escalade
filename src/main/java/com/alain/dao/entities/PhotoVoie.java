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
@PrimaryKeyJoinColumn(name = "id")
@OnDelete(action = OnDeleteAction.CASCADE)
public class PhotoVoie extends Photo {

    private static final Logger logger = LogManager.getLogger("PhotoVoie");

    @ManyToOne
    private Voie voie;

    /* ********************************************************************************************
     **** CONSTRUCTORS      ************************************************************************
     *********************************************************************************************** */

    public PhotoVoie() {
    }

    public PhotoVoie(Voie voie) {
        this.voie = voie;
    }

    public PhotoVoie(String nom, String erreur, InputStream contenu, Voie voie) {
        super(nom, erreur, contenu);
        this.voie = voie;
    }

    /* ********************************************************************************************
     **** METHODS           ************************************************************************
     ******************************************************************************************** */

    public void removeRelation() {
        logger.info("Suppression de l'association avec la voie : " + this.voie.getId());
        this.voie = null;
    }

    /* ***********************************************************************************************
     **** GETTERS & SETTERS ************************************************************************
     *********************************************************************************************** */

    public Voie getVoie() {
        return voie;
    }

    public void setVoie(Voie voie) {
        this.voie = voie;
    }

}
