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
public class PhotoSpot extends Photo {

    private static final Logger logger = LogManager.getLogger("PhotoSpot");

    @ManyToOne
    private Spot spot;

    /* ********************************************************************************************
     **** CONSTRUCTORS      ************************************************************************
     *********************************************************************************************** */

    public PhotoSpot() {
    }

    public PhotoSpot(Spot spot) {
        this.spot = spot;
    }

    public PhotoSpot(String nom, String erreur, InputStream contenu, Spot spot) {
        super(nom, erreur, contenu);
        this.spot = spot;
    }

    /* ********************************************************************************************
     **** METHODS           ************************************************************************
     ******************************************************************************************** */
    public void removeRelation(){
        logger.info("Suppression de l'association avec le spot : " + this.spot.getId());
        this.spot = null;
    }

    /* ***********************************************************************************************
     **** GETTERS & SETTERS ************************************************************************
     *********************************************************************************************** */

    public Spot getSpot() {
        return spot;
    }

    public void setSpot(Spot spot) {
        this.spot = spot;
    }
}
