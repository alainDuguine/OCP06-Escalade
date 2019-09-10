package com.alain.dao.impl;

import com.alain.EntityManagerUtil;
import com.alain.dao.contract.EntityRepository;
import com.alain.dao.entities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
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
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            String path = req.getServletPath();
            ReservationHistorique reservationHistorique = new ReservationHistorique();
            reservationHistorique.setDateTime(LocalDateTime.now());
            if(path.contains("accepter")){
                reservationHistorique.setReservationStatut(ReservationStatutEnum.APPROVED);
                reservation.getTopo().setDisponible(false);
            }else if (path.contains("refuser")){
                reservationHistorique.setReservationStatut(ReservationStatutEnum.REFUSED);
            }else{
                reservationHistorique.setReservationStatut(ReservationStatutEnum.FINISHED);
            }
            reservationHistorique.setReservation(reservation);
            reservation.addEvent(reservationHistorique);
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
    public boolean delete(Long id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            Reservation reservation = entityManager.find(Reservation.class, id);
            reservation.removeHistorique();
            reservation.getTopo().removeReservation(reservation);
            reservation.getEmprunteur().removeReservation(reservation);
            reservation.getPreteur().removeReservation(reservation);
            entityManager.remove(reservation);
            entityManager.flush();
            transaction.commit();
            return true;
        }catch (Exception e){
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Reservation> findAll() {
        Query query = entityManager.createQuery("select reservation from Reservation reservation order by reservation.id desc");
        return query.getResultList();
    }

    @Override
    public Reservation findOne(Long id) {
        return entityManager.find(Reservation.class, id);
    }

    public List<Reservation> findReservationInTopoForUser(Long idTopo, Long idEmprunteur) {
        Query query = entityManager.createQuery("select r from Reservation r where r.emprunteur.id= :idEmprunteur and r.topo.id= :idTopo and r.dernierStatut = 'PENDING'");
        query.setParameter("idEmprunteur", idEmprunteur );
        query.setParameter("idTopo", idTopo);
        return query.getResultList();
    }
}
