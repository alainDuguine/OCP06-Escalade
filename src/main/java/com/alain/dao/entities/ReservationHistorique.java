package com.alain.dao.entities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table
public class ReservationHistorique implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateTime;

    @Enumerated(EnumType.STRING)
    private ReservationStatutEnum reservationStatut;

    @ManyToOne
    private Reservation reservation;

    /* ***********************************************************************************************
     **** CONSTRUCTORS      ************************************************************************
     *********************************************************************************************** */

    public ReservationHistorique() {
    }

    public ReservationHistorique(ReservationStatutEnum reservationStatut) {
        this.dateTime = LocalDateTime.now();
        this.reservationStatut = reservationStatut;
    }

    /* ***********************************************************************************************
     **** METHODS           ************************************************************************
     *********************************************************************************************** */

    /* ***********************************************************************************************
     **** GETTERS & SETTERS ************************************************************************
     *********************************************************************************************** */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public ReservationStatutEnum getReservationStatut() {
        return reservationStatut;
    }

    public void setReservationStatut(ReservationStatutEnum reservationStatut) {
        this.reservationStatut = reservationStatut;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

}
