package com.alain.dao.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table
public class Spot implements Serializable {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String adresse;
    @Column (length = 5)
    private String codePostal;
    private String ville;
    private String departement;
    private String region;
    private String description;
    private Boolean officiel;

    // Associations
    @ManyToOne
    private Utilisateur utilisateur;

    @OneToMany (mappedBy ="spot")
    private List<Secteur> secteurs;
    @OneToMany (mappedBy = "spot")
    private List<CommentaireSpot> commentaires;
    @OneToMany(mappedBy = "spot")
    private List<ComplementSpot> topo;
    @ManyToMany (mappedBy = "spot")
    private List<Topo> topos;

    public Spot() {
    }

    public Spot(String nom, String adresse, String codePostal, String ville, String departement, String region, String description, Boolean officiel) {
        this.nom = nom;
        this.adresse = adresse;
        this.codePostal = codePostal;
        this.ville = ville;
        this.departement = departement;
        this.region = region;
        this.description = description;

        this.officiel = officiel;
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

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getDepartement() {
        return departement;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Boolean getOfficiel() {
        return officiel;
    }

    public void setOfficiel(Boolean officiel) {
        this.officiel = officiel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public List<Secteur> getSecteurs() {
        return secteurs;
    }

    public void setSecteurs(List<Secteur> secteurs) {
        this.secteurs = secteurs;
    }

    public List<CommentaireSpot> getCommentaires() {
        return commentaires;
    }

    public void setCommentaires(List<CommentaireSpot> commentaires) {
        this.commentaires = commentaires;
    }

    public List<ComplementSpot> getTopo() {
        return topo;
    }

    public void setTopo(List<ComplementSpot> topo) {
        this.topo = topo;
    }

    public List<Topo> getTopos() {
        return topos;
    }

    public void setTopos(List<Topo> topos) {
        this.topos = topos;
    }
}
