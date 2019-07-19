package com.alain.dao.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table
public class Departement implements Serializable {

    @Id
    private String code;
    private String nom;


    @OneToMany (mappedBy = "departement")
    private List<Ville> villes;
    @OneToMany (mappedBy = "departement")
    private List<Spot> spots;

    public Departement() {
    }

    public Departement(String code, String nom, List<Ville> villes) {
        this.code = code;
        this.nom = nom;
        this.villes = villes;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Ville> getVilles() {
        return villes;
    }

    public void setVilles(List<Ville> villes) {
        this.villes = villes;
    }

    public void addSpot(Spot spot) {
        this.spots.add(spot);
    }
}
