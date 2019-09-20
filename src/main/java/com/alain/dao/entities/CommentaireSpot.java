package com.alain.dao.entities;

import com.google.gson.annotations.Expose;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;

/**
 * Seuls les commentaires de spots sont implémentés pour le moment
 * il est possible de garder la même logique pour implémenter les commentaires voies ou secteurs
 */
@Entity
@Table
@OnDelete(action = OnDeleteAction.CASCADE)
public class CommentaireSpot extends Commentaire {

    private static final Logger logger = LogManager.getLogger("CommentaireSpot");

    @Expose(serialize = false, deserialize = false)
    @ManyToOne
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

    public void removeSpot() {
        logger.info("Supression de l'association avec le spot " + spot.getId());
        this.spot = null;
    }

    /* *********************************************************************************************
     **** GETTERS & SETTERS ************************************************************************
     ******************************************************************************************** */

    public Spot getSpot() {
        return spot;
    }

    public void removeRelations() {
        this.getUtilisateur().removeCommentaireSpot(this);
        this.getSpot().removeCommentaire(this);
    }
}
