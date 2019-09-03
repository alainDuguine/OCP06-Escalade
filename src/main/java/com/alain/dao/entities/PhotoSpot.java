package com.alain.dao.entities;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.InputStream;

@Entity
@Table
@PrimaryKeyJoinColumn(name = "id")
@OnDelete(action = OnDeleteAction.CASCADE)
public class PhotoSpot extends Photo {

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
