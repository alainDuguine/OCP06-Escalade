package com.alain.dao.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table
public class Utilisateur implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String motDePasse;
    private String nom;
    private String prenom;
    private String role;

    public Utilisateur() {
    }

    public Utilisateur(String email, String motDePasse, String nom, String prenom, String role) {
        this.email = email;
        this.motDePasse = motDePasse;
        this.nom = nom;
        this.prenom = prenom;
        this.role = role;
    }


    @OneToMany (mappedBy = "utilisateur")
    private List<Spot> spots;
    @OneToMany (mappedBy = "utilisateur")
    private List<Secteur> secteurs;
    @OneToMany (mappedBy = "utilisateur")
    private List<Voie> voies;
    @OneToMany (mappedBy = "utilisateur")
    private List<CommentaireSpot> commentaireSpots;
    @OneToMany (mappedBy = "utilisateur")
    private List<CommentaireSecteur> commentaireSecteurs;
    @OneToMany (mappedBy = "utilisateur")
    private List<CommentaireVoie> commentaireVoies;
    @OneToMany (mappedBy = "utilisateur")
    private List<ComplementSpot> complementSpots;
    @OneToMany (mappedBy = "utilisateur")
    private List<ComplementSecteur> complementSecteurs;
    @OneToMany (mappedBy = "utilisateur")
    private List<ComplementVoie> complementVoies;
    @OneToMany (mappedBy = "utilisateur")
    private List<Topo> topos;

    @ManyToMany (mappedBy = "empruntUtilisateurs")
    private List<Topo> empruntsTopos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Spot> getSpots() {
        return spots;
    }

    public void setSpots(List<Spot> spots) {
        this.spots = spots;
    }

    public List<Secteur> getSecteurs() {
        return secteurs;
    }

    public void setSecteurs(List<Secteur> secteurs) {
        this.secteurs = secteurs;
    }

    public List<Voie> getVoies() {
        return voies;
    }

    public void setVoies(List<Voie> voies) {
        this.voies = voies;
    }

    public List<CommentaireSpot> getCommentaireSpots() {
        return commentaireSpots;
    }

    public void setCommentaireSpots(List<CommentaireSpot> commentaireSpots) {
        this.commentaireSpots = commentaireSpots;
    }

    public List<CommentaireSecteur> getCommentaireSecteurs() {
        return commentaireSecteurs;
    }

    public void setCommentaireSecteurs(List<CommentaireSecteur> commentaireSecteurs) {
        this.commentaireSecteurs = commentaireSecteurs;
    }

    public List<CommentaireVoie> getCommentaireVoies() {
        return commentaireVoies;
    }

    public void setCommentaireVoies(List<CommentaireVoie> commentaireVoies) {
        this.commentaireVoies = commentaireVoies;
    }

    public List<ComplementSpot> getComplementSpots() {
        return complementSpots;
    }

    public void setComplementSpots(List<ComplementSpot> complementSpots) {
        this.complementSpots = complementSpots;
    }

    public List<ComplementSecteur> getComplementSecteurs() {
        return complementSecteurs;
    }

    public void setComplementSecteurs(List<ComplementSecteur> complementSecteurs) {
        this.complementSecteurs = complementSecteurs;
    }

    public List<ComplementVoie> getComplementVoies() {
        return complementVoies;
    }

    public void setComplementVoies(List<ComplementVoie> complementVoies) {
        this.complementVoies = complementVoies;
    }

    public List<Topo> getTopos() {
        return topos;
    }

    public void setTopos(List<Topo> topos) {
        this.topos = topos;
    }

    public List<Topo> getEmpruntsTopos() {
        return empruntsTopos;
    }

    public void setEmpruntsTopos(List<Topo> empruntsTopos) {
        this.empruntsTopos = empruntsTopos;
    }
}
