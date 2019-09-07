package com.alain.dao.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table
public class Reservation implements Serializable {

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
    private List<ReservationHistorique> reservationHistorique;


    /* ***********************************************************************************************
     **** CONSTRUCTORS      ************************************************************************
     *********************************************************************************************** */

    public Reservation() {
    }

    public Reservation(Utilisateur emprunteur, Utilisateur preteur, Topo topo) {
        this.emprunteur = emprunteur;
        this.preteur = preteur;
        this.topo = topo;
    }

    /* ***********************************************************************************************
     **** METHODS           ************************************************************************
     *********************************************************************************************** */

    /* ***********************************************************************************************
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

    public List<ReservationHistorique> getReservationHistorique() {
        return reservationHistorique;
    }

    public void setReservationHistorique(List<ReservationHistorique> reservationHistorique) {
        this.reservationHistorique = reservationHistorique;
    }
}
