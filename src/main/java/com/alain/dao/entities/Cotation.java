package com.alain.dao.entities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Cotation implements Serializable {

    private static final Logger logger = LogManager.getLogger("Cotation");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column (length = 2)
    private String code;

    @OneToMany (mappedBy = "cotation")
    private List<Voie> voies = new ArrayList<>();

    /* ********************************************************************************************
     **** CONSTRUCTORS      ************************************************************************
     *********************************************************************************************** */

    public Cotation() {
    }

    public Cotation(String code, List<Voie> voies) {
        this.code = code;
        this.voies = voies;
    }

    /* ********************************************************************************************
     **** METHODS           ************************************************************************
     ******************************************************************************************** */

    public void removeVoie(Voie voie){
        logger.info("Suppression de l'association avec une voie" + voie.getId());
        this.voies.removeIf(voieInList -> voieInList.getId().equals(voie.getId()));
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Voie> getVoies() {
        return voies;
    }

    public void setVoies(List<Voie> voies) {
        this.voies = voies;
    }

    public void addVoies(Voie voie){
        this.voies.add(voie);
    }
}
