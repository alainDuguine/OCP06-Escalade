package com.alain.dao.entities;

import com.alain.dao.contract.EntityRepository;
import com.alain.metier.Utilities;
import com.google.gson.annotations.Expose;
import org.apache.commons.lang.StringEscapeUtils;


import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Commentaire extends Entitie implements Serializable {
    private static final String CHAMP_CONTENU = "contenu";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dateTime;
    @Expose()
    private String dateFormat;
    @Expose()
    private String contenu;

    // Associations
    @ManyToOne
    private Utilisateur utilisateur;

    // Champ username pour requête ajax lors de la création d'un commentaire
    @Expose()
    @Transient
    private String username;

    /* ***********************************************************************************************
       **** CONSTRUCTORS      ************************************************************************
       *********************************************************************************************** */

    public Commentaire() {
    }

    /* ********************************************************************************************
    **** METHODS           ************************************************************************
    ******************************************************************************************** */

    @Override
    public void hydrate(HttpServletRequest req) {
        this.contenu = Utilities.getValeurChamp(req, CHAMP_CONTENU);
        this.dateTime = LocalDateTime.now();
        this.dateFormat = this.setDateFormat();
    }

    @Override
    public Map<String, String> checkErreurs(EntityRepository dao, HttpServletRequest req) {
        Map<String, String> listErreur = new HashMap<>();

        if (this.contenu == null) {
            listErreur.put("erreur", "Le commentaire est vide");
        }else if (this.contenu.length() > 255){
            listErreur.put("erreur", "Un commentaire peut au maximum contenir 255 caractères");
        }
        return listErreur;
    }

    /* ***********************************************************************************************
     **** GETTERS & SETTERS ************************************************************************
     *********************************************************************************************** */


    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public String setDateFormat() {
        return Utilities.dateStringFr(this.dateTime);
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

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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