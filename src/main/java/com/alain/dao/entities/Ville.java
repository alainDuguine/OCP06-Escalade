package com.alain.dao.entities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Objet représentangt une ville française.
 * Elles sont reliées aux départements, et sont chargées en base de donnée au premier démarrage de l'application via data.sql
 */
@Entity
@Table
public class Ville implements Serializable {

    private static final Logger logger = LogManager.getLogger("Ville");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;

    @ManyToOne
    private Departement departement;

    @OneToMany (mappedBy = "ville")
    private List<Spot> spots;

    /* ********************************************************************************************
     **** CONSTRUCTORS      ************************************************************************
     *********************************************************************************************** */

    public Ville() {
    }

    public Ville(String nom, Departement departement, List<Spot> spots) {
        this.nom = nom;
        this.departement = departement;
        this.spots = spots;
    }

    /* ********************************************************************************************
     **** METHODS           ************************************************************************
     ******************************************************************************************** */

    /**
     * Supprime une association avec un spot
     * @param spot à dissocier
     */
    public void removeSpot(Spot spot){
        logger.info("Suppression de l'association avec un spot " + spot.getId());
        this.spots.removeIf(spotInList -> spotInList.getId().equals(spot.getId()));
    }

    /* ***********************************************************************************************
     **** GETTERS & SETTERS ************************************************************************
     *********************************************************************************************** */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void addSpot(Spot spot) {
        this.spots.add(spot);
    }

    public Departement getDepartement() {
        return departement;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
    }

    public List<Spot> getSpots() {
        return spots;
    }

    public void setSpots(List<Spot> spots) {
        this.spots = spots;
    }
}
