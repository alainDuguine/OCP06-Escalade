package com.alain.dao.entities;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
@PrimaryKeyJoinColumn(name="id")
public class CommentaireSpot extends Commentaire {

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


    /* *********************************************************************************************
     **** GETTERS & SETTERS ************************************************************************
     ******************************************************************************************** */

    public Spot getSpot() {
        return spot;
    }

    public void setSpot(Spot spot) {
        this.spot = spot;
    }

    public void removeRelation() {
        this.spot = null;
    }
}
