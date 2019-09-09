package com.alain.dao.entities;

import com.alain.dao.contract.EntityRepository;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@Table
public class Reservation extends Entitie implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Utilisateur emprunteur;
    @ManyToOne
    private Utilisateur preteur;
    @ManyToOne
    private Topo topo;

    @OneToMany (mappedBy = "reservation")
    private ArrayList<ReservationHistorique> listHistorique = new ArrayList<>();


    /* *********************************************************************************************
     **** CONSTRUCTORS      ************************************************************************
     *********************************************************************************************** */

    public Reservation() {
    }

    public Reservation(Utilisateur emprunteur, Utilisateur preteur, Topo topo, ArrayList<ReservationHistorique> listHistorique) {
        this.emprunteur = emprunteur;
        this.preteur = preteur;
        this.topo = topo;
        this.listHistorique = listHistorique;
    }

    /* *********************************************************************************************
     **** METHODS           ************************************************************************
     *********************************************************************************************** */

    @Override
    public void hydrate(HttpServletRequest req) {

    }

    @Override
    public Map<String, String> checkErreurs(EntityRepository dao, HttpServletRequest req) {
        return null;
    }


    /* *********************************************************************************************
     **** GETTERS & SETTERS ************************************************************************
     *********************************************************************************************** */

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }

    public Utilisateur getEmprunteur() {
        return emprunteur;
    }

    public void setEmprunteur(Utilisateur emprunteur) {
        this.emprunteur = emprunteur;
    }

    public Utilisateur getPreteur() {
        return preteur;
    }

    public void setPreteur(Utilisateur preteur) {
        this.preteur = preteur;
    }

    public Topo getTopo() {
        return topo;
    }

    public void setTopo(Topo topo) {
        this.topo = topo;
    }

    public ArrayList<ReservationHistorique> getListHistorique() {
        return listHistorique;
    }

    public void setListHistorique(ArrayList<ReservationHistorique> listHistorique) {
        this.listHistorique = listHistorique;
    }
}
