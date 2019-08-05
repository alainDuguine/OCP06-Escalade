package com.alain.dao.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table
@Inheritance(strategy = InheritanceType.JOINED)
public class Commentaire implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date dateHeure;
    private String contenu;

    // Associations
    @ManyToOne
    private Utilisateur utilisateur;

    /* ***********************************************************************************************
       **** CONSTRUCTORS      ************************************************************************
       *********************************************************************************************** */

    public Commentaire() {
    }

    public Commentaire(Date dateHeure, String contenu, Utilisateur utilisateur) {
        this.dateHeure = dateHeure;
        this.contenu = contenu;
        this.utilisateur = utilisateur;
    }

    /* ********************************************************************************************
    **** METHODS           ************************************************************************
    ******************************************************************************************** */

    /* ***********************************************************************************************
     **** GETTERS & SETTERS ************************************************************************
     *********************************************************************************************** */

    public Date getDateHeure() {
        return dateHeure;
    }

    public void setDateHeure(Date dateHeure) {
        this.dateHeure = dateHeure;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}