package com.alain.dao.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
@PrimaryKeyJoinColumn(name="id")
public class ComplementVoie extends Complement {

    @ManyToOne
    private Voie voie;

    /* ********************************************************************************************
     **** CONSTRUCTORS      ************************************************************************
     *********************************************************************************************** */

    public ComplementVoie() {
    }

    public ComplementVoie(Voie voie) {
        this.voie = voie;
    }

    public ComplementVoie(Date dateHeure, String contenu, Utilisateur utilisateur, Voie voie) {
        super(dateHeure, contenu, utilisateur);
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
