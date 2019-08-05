package com.alain.dao.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
@PrimaryKeyJoinColumn(name="id")
public class ComplementSpot extends Complement {

    @ManyToOne
    private Spot spot;

    /* ********************************************************************************************
     **** CONSTRUCTORS      ************************************************************************
     *********************************************************************************************** */

    public ComplementSpot() {
    }

    public ComplementSpot(Spot spot) {
        this.spot = spot;
    }

    public ComplementSpot(Date dateHeure, String contenu, Utilisateur utilisateur, Spot spot) {
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
