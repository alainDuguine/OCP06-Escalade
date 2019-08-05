package com.alain.dao.entities;

import javax.persistence.*;
import java.io.InputStream;

@Entity
@Table
@PrimaryKeyJoinColumn(name = "id")
class PhotoVoie extends Photo {

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
