package com.alain.dao.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table
public class CommentaireSpot implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date dateHeure;
    private String contenu;

//    // Associations
//    @ManyToOne
//    private Utilisateur utilisateur;
//    @ManyToOne
//    private Spot spot;

    public CommentaireSpot() {
    }

    public CommentaireSpot(Date dateHeure, String contenu) {
        this.dateHeure = dateHeure;
        this.contenu = contenu;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
//
//    public Utilisateur getUtilisateur() {
//        return utilisateur;
//    }
//
//    public void setUtilisateur(Utilisateur utilisateur) {
//        this.utilisateur = utilisateur;
//    }
//
//    public Spot getSpot() {
//        return spot;
//    }
//
//    public void setSpot(Spot spot) {
//        this.spot = spot;
//    }
}
