package com.alain.dao.entities;

import com.google.gson.annotations.Expose;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
@PrimaryKeyJoinColumn(name="id")
public class CommentaireSpot extends Commentaire {

    private static final Logger logger = LogManager.getLogger("CommentaireSpot");

    @Expose(serialize = false, deserialize = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Spot spot;

    /* ********************************************************************************************
    **** CONSTRUCTORS      ************************************************************************
    *********************************************************************************************** */

    public CommentaireSpot() {
    }

    public CommentaireSpot(Spot spot) {
        super();
        this.spot = spot;
    }


    /* ********************************************************************************************
     **** METHODS          ************************************************************************
     ******************************************************************************************** */

    public void setSpot(Spot spot) {
        logger.info("Association avec le spot " + spot.getId());
        this.spot = spot;
    }

    public void removeRelation() {
        logger.info("Suprression de l'aasssociation avec le spot " + spot.getId());
        this.spot = null;
    }

    /* *********************************************************************************************
     **** GETTERS & SETTERS ************************************************************************
     ******************************************************************************************** */

    public Spot getSpot() {
        return spot;
    }

}
