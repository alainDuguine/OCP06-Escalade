package com.alain.dao.entities;

import com.alain.dao.contract.EntityRepository;
import com.alain.metier.Utilities;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.*;

@Entity
@Table
public class Topo extends Entitie implements Serializable {
    private static final String CHAMP_NOM = "nom";
    private static final String CHAMP_DESCRIPTION = "description";
    private static final String CHAMP_DATEPARUTION = "dateParution";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String dateEdition;
    @Column (length = 2000)
    private String description;
    private boolean disponible = true;

    // Associations
    @ManyToOne
    private Utilisateur utilisateur;

    @OneToMany (mappedBy = "topo")
    private List<Reservation> reservations = new ArrayList<>();

    @ManyToMany
    private List<Spot> spots = new ArrayList<>();

    /* ********************************************************************************************
     **** CONSTRUCTORS      ************************************************************************
     *********************************************************************************************** */

    public Topo() {
    }

    public Topo(String nom, String dateEdition, String description, Boolean disponible) {
        this.nom = nom;
        this.dateEdition = dateEdition;
        this.description = description;
        this.disponible = disponible;
    }

    /* ********************************************************************************************
     **** METHODS           ************************************************************************
     ******************************************************************************************** */

    @Override
    public void hydrate(HttpServletRequest req) {
        this.setNom(Utilities.getValeurChamp(req, CHAMP_NOM));
        this.setDescription(Utilities.getValeurChamp(req, CHAMP_DESCRIPTION));
        this.setDateEdition(Utilities.getValeurChamp(req, CHAMP_DATEPARUTION));
    }

    @Override
    public Map<String, String> checkErreurs(EntityRepository dao, HttpServletRequest req) {
        Map<String, String> listErreur = new HashMap<>();

        if (Utilities.isEmpty(this.nom)) {
            listErreur.put(CHAMP_NOM, "Veuillez entrer le nom du topo");
        }
        if (Utilities.isEmpty(this.description) || this.description.length() < 10) {
            listErreur.put(CHAMP_DESCRIPTION, "Veuillez entrer une description d'au moins 50 caractères");
        }else if (this.description.length() > 2000){
            listErreur.put(CHAMP_DESCRIPTION, "Veuillez entrer une description de maximum 2000 caractères.");
        }
        return listErreur;
    }

    /**
     * Iterator pour enlever un spot d'un topo
     * @param
     */
    public void removeSpot(Spot spot) {
        this.spots.removeIf(spotInList -> spotInList.getId() == spot.getId());
    }

    /**
     * Ajoute un spot à la liste s'il n'est pas déjà présent
     * @param spot
     */
    public void addSpot(Spot spot) {
        if(!this.spots.contains(spot)){
            this.spots.add(spot);
        }
        spot.addTopo(this);
    }

    /**
     * Supprime tous les liens vers le topo dans les spots
     */
    public void removeAllSpots() {
        for (Spot spot : spots){
            spot.removeTopo(this);
        }
        this.spots.clear();
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

    public List<Spot> getSpots() {
        return spots;
    }

    public void setSpots(List<Spot> spot) {
        this.spots = spot;
    }

    public String getDateEdition() {
        return dateEdition;
    }

    public void setDateEdition(String dateEdition) {
        this.dateEdition = dateEdition;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
        utilisateur.addTopo(this);
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
}
