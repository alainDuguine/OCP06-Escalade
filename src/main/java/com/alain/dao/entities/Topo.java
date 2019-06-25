package com.alain.dao.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table
public class Topo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private Date dateEdition;
    private String description;
    private Boolean disponible;

    // Associations
    @ManyToOne
    private Utilisateur utilisateur;
    @ManyToMany
    private List<Utilisateur> empruntUtilisateurs;
    @ManyToMany
    private List<Spot> spot;

    public Topo() {
    }

    public Topo(String nom, Date dateEdition, String description, Boolean disponible) {
        this.nom = nom;
        this.dateEdition = dateEdition;
        this.description = description;
        this.disponible = disponible;
    }

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

    public Date getDateEdition() {
        return dateEdition;
    }

    public void setDateEdition(Date dateEdition) {
        this.dateEdition = dateEdition;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public List<Utilisateur> getEmpruntUtilisateurs() {
        return empruntUtilisateurs;
    }

    public void setEmpruntUtilisateurs(List<Utilisateur> empruntUtilisateurs) {
        this.empruntUtilisateurs = empruntUtilisateurs;
    }

    public List<Spot> getSpot() {
        return spot;
    }

    public void setSpot(List<Spot> spot) {
        this.spot = spot;
    }
}
