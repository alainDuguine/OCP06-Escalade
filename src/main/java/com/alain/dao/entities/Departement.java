package com.alain.dao.entities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Departement implements Serializable {

    private static final Logger logger = LogManager.getLogger("Département");

    @Id
    private String code;
    private String nom;


    @OneToMany (mappedBy = "departement")
    private List<Ville> villes = new ArrayList<>();
    @OneToMany (mappedBy = "departement")
    private List<Spot> spots = new ArrayList<>();

    /* ********************************************************************************************
     **** CONSTRUCTORS      ************************************************************************
     *********************************************************************************************** */

    public Departement() {
    }

    public Departement(String code, String nom, List<Ville> villes, List<Spot> spots) {
        this.code = code;
        this.nom = nom;
        this.villes = villes;
        this.spots = spots;
    }

    /* ********************************************************************************************
     **** METHODS           ************************************************************************
     ******************************************************************************************** */

    public void removeSpot(Spot spot){
        logger.info("Suppression de l'association avec un spot " + spot.getId());
        this.spots.removeIf(spotInList -> spotInList.getId().equals(spot.getId()));
    }

    /* ***********************************************************************************************
     **** GETTERS & SETTERS ************************************************************************
     *********************************************************************************************** */

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void addSpot(Spot spot) {
        this.spots.add(spot);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Ville> getVilles() {
        return villes;
    }

    public void setVilles(List<Ville> villes) {
        this.villes = villes;
    }

    public List<Spot> getSpots() {
        return spots;
    }

    public void setSpots(List<Spot> spots) {
        this.spots = spots;
    }
}
