package com.alain.metier;

public class SpotResearchDto {
    private Long id;
    private String nom;
    private String departement;
    private String ville;
    private int nbSecteur;
    private String minCotation;
    private String maxCotation;
    private boolean officiel;

    public SpotResearchDto() {
    }

    public SpotResearchDto(Long id, String nom, String departement, String ville, int nbSecteur, String minCotation, String maxCotation, boolean officiel) {
        this.id = id;
        this.nom = nom;
        this.departement = departement;
        this.ville = ville;
        this.nbSecteur = nbSecteur;
        this.minCotation = minCotation;
        this.maxCotation = maxCotation;
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

    public String getDepartement() {
        return departement;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
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
