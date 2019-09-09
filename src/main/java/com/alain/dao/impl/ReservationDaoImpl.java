package com.alain.dao.impl;

import com.alain.EntityManagerUtil;
import com.alain.dao.contract.EntityRepository;
import com.alain.dao.entities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ReservationDaoImpl extends EntityManagerUtil implements EntityRepository<Reservation> {

    private EntityManager entityManager = EntityManagerUtil.getEntityManager();

    @Override
    public Reservation save(Reservation reservation, HttpServletRequest req) throws Exception {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(reservation);
            ReservationHistorique reservationHistorique = new ReservationHistorique(ReservationStatutEnum.PENDING);
            reservationHistorique.setReservation(reservation);
            reservation.addEvent(reservationHistorique);
            Utilisateur preteur = reservation.getPreteur();
            Utilisateur emprunteur = reservation.getEmprunteur();
            Topo topo = reservation.getTopo();
            topo.addReservation(reservation);
            preteur.addPret(reservation);
            emprunteur.addEmprunt(reservation);
            // Création des associations bidirectionelles
            entityManager.flush();
            transaction.commit();
        }catch (Exception e){
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
            throw e;
        }
        return reservation;
    }

    @Override
    public Reservation update(Reservation reservation, HttpServletRequest req) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public List<Reservation> findAll() {
        return null;
    }

    @Override
    public Reservation findOne(Long id) {
        return null;
    }

    public List<Reservation> findReservationInTopoForUser(Long idTopo, Long idEmprunteur) {
        Query query = entityManager.createQuery("select r from Reservation r where r.emprunteur.id= :idEmprunteur and r.topo.id= :idTopo and r.dernierStatut = 'PENDING'");
        query.setParameter("idEmprunteur", idEmprunteur );
        query.setParameter("idTopo", idTopo);
        return query.getResultList();
    }
}
