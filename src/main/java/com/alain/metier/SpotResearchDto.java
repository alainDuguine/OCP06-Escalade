package com.alain.metier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Classe utilisée pour transférer les résultats de la requête (SpotDaoImpl) public List<SpotResearchDto> findAllForResearch()
 * qui renvoie une liste de spots avec des champs d'aggrégation
 */

public class SpotResearchDto {
    private Long id;
    private String nom;
    private String departementCode;
    private String departementNom;
    private String ville;
    private int nbSecteur;
    private String minCotation;
    private String maxCotation;
    private boolean officiel;

    /* ********************************************************************************************
     **** CONSTRUCTORS      ************************************************************************
     *********************************************************************************************** */

    public SpotResearchDto() {
    }

    public SpotResearchDto(Long id, String nom, String departementCode, String departementNom, String ville, int nbSecteur, String minCotation, String maxCotation, boolean officiel) {
        this.id = id;
        this.nom = nom;
        this.departementCode = departementCode;
        this.departementNom = departementNom;
        this.ville = ville;
        this.nbSecteur = nbSecteur;
        this.minCotation = minCotation;
        this.maxCotation = maxCotation;
        this.officiel = officiel;
    }

    /* ********************************************************************************************
     **** METHODS           ************************************************************************
     ******************************************************************************************** */


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

    public String getDepartementCode() {
        return departementCode;
    }

    public void setDepartementCode(String departementCode) {
        this.departementCode = departementCode;
    }

    public String getDepartementNom() {
        return departementNom;
    }

    public void setDepartementNom(String departementNom) {
        this.departementNom = departementNom;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public int getNbSecteur() {
        return nbSecteur;
    }

    public void setNbSecteur(int nbSecteur) {
        this.nbSecteur = nbSecteur;
    }

    public String getMinCotation() {
        return minCotation;
    }

    public void setMinCotation(String minCotation) {
        this.minCotation = minCotation;
    }

    public String getMaxCotation() {
        return maxCotation;
    }

    public void setMaxCotation(String maxCotation) {
        this.maxCotation = maxCotation;
    }

    public boolean isOfficiel() {
        return officiel;
    }

    public void setOfficiel(boolean officiel) {
        this.officiel = officiel;
    }
}
