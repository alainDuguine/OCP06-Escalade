package com.alain.dao.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
@PrimaryKeyJoinColumn(name="id")
public class CommentaireSpot extends Commentaire {

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

    public CommentaireSpot(Date dateHeure, String contenu, Utilisateur utilisateur, Spot spot) {
        super(dateHeure, contenu, utilisateur);
        this.spot = spot;
    }

    /* ********************************************************************************************
     **** METHODS           ************************************************************************
     ******************************************************************************************** */


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
