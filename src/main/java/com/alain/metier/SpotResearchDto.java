package com.alain.metier;

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
    private Long minCotationId;
    private Long maxCotationId;
    private String minCotationCode;
    private String maxCotationCode;
    private boolean officiel;

    private static String[] cotationCode = {"3a", "3b", "3c", "4a", "4b", "4c", "5a", "5b", "5c", "6a", "6b", "6c", "7a", "7b", "7c", "8a", "8b", "8c", "9a", "9b", "9c"};

    /* *********************************************************************************************
     **** CONSTRUCTORS      ************************************************************************
     *********************************************************************************************** */

    public SpotResearchDto() {
    }

    public SpotResearchDto(Long id, String nom, String departementCode, String departementNom, String ville, int nbSecteur, Long minCotation, Long maxCotation, boolean officiel) {
        this.id = id;
        this.nom = nom;
        this.departementCode = departementCode;
        this.departementNom = departementNom;
        this.ville = ville;
        this.nbSecteur = nbSecteur;
        this.minCotationId = minCotation;
        this.maxCotationId = maxCotation;
        this.setMinCotationCode(minCotation);
        this.setMaxCotationCode(maxCotation);
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

    public Long getMinCotationId() {
        return minCotationId;
    }

    public void setMinCotationId(Long minCotationId) {
        this.minCotationId = minCotationId;
    }

    public Long getMaxCotationId() {
        return maxCotationId;
    }

    public void setMaxCotationId(Long maxCotationId) {
        this.maxCotationId = maxCotationId;
    }

    public String getMinCotationCode() {
        return minCotationCode;
    }

    public void setMinCotationCode(Long minCotationId) {
        if(minCotationId != null) {
            this.minCotationCode =  cotationCode[minCotationId.intValue()-1];
        }else{
            this.minCotationCode = "";
        }
    }

    public String getMaxCotationCode() {
        return maxCotationCode;
    }

    public void setMaxCotationCode(Long maxCotationId) {
        if(maxCotationId != null) {
            this.maxCotationCode = cotationCode[maxCotationId.intValue()-1];
        }else{
            this.maxCotationCode = "";
        }
    }

    public boolean isOfficiel() {
        return officiel;
    }

    public void setOfficiel(boolean officiel) {
        this.officiel = officiel;
    }
}
