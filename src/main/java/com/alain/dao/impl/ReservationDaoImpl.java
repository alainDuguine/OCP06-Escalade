package com.alain.dao.impl;

import com.alain.EntityManagerUtil;
import com.alain.dao.contract.EntityRepository;
import com.alain.dao.entities.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class ReservationDaoImpl extends EntityManagerUtil implements EntityRepository<Reservation> {

    private EntityManager entityManager = EntityManagerUtil.getEntityManager();
    private static final Logger logger = LogManager.getLogger("RéservationDaoImpl");

    /**
     * Enregistre une réservation pour un topo en base de données
     * ajoute les associations avec les utilisateurs,
     * @param reservation à enregistrer
     * @param req requête http
     * @return l'objet reservation
     */
    @Override
    public Reservation save(Reservation reservation, HttpServletRequest req){
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            logger.info("Tentative de sauvegarde d'une réservation");
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
            logger.info("Réservation sauvegardée : " + reservation.getId() + ", topo : " + topo.getId()+ ", preteur : " + preteur.getUsername() + ", emprunteur : " + emprunteur.getUsername() );
        }catch (Exception e){
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
            logger.error("Réservation échouée : " + Arrays.toString(e.getStackTrace()));
            throw e;
        }
        return reservation;
    }

    /**
     * Modifie du statut d'une réservation et enregistrement en base de données
     * @param reservation à modifier
     * @param req requête http
     * @return l'objet commentaire
     */
    @Override
    public Reservation update(Reservation reservation, HttpServletRequest req) {
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            logger.info("Tentative de modification d'une réservation" + reservation.getId());
            transaction.begin();
            String path = req.getServletPath();
            ReservationHistorique reservationHistorique = new ReservationHistorique();
            reservationHistorique.setDateTime(LocalDateTime.now());
            if(path.contains("accepter")){
                reservationHistorique.setReservationStatut(ReservationStatutEnum.APPROVED);
                reservation.getTopo().setDisponible(false);
                logger.info("Statut réservaton : acceptée " + reservation.getId());
            }else if (path.contains("refuser")){
                reservationHistorique.setReservationStatut(ReservationStatutEnum.REFUSED);
                logger.info("Statut réservaton : refusée " + reservation.getId());
            }else{
                reservationHistorique.setReservationStatut(ReservationStatutEnum.FINISHED);
                logger.info("Statut réservaton : terminée " + reservation.getId());
            }
            reservationHistorique.setReservation(reservation);
            reservation.addEvent(reservationHistorique);
            entityManager.flush();
            transaction.commit();
            logger.info("Modification Statut Réservation effectuée");
        }catch (Exception e){
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
            logger.error("Modification réservation échouée : " + Arrays.toString(e.getStackTrace()));
            throw e;
        }
        return reservation;
    }

    /**
     * Supprime une réservation en base de données
     * supprime les associations
     * @param id de la réservation
     * @return booléen
     */
    @Override
    public boolean delete(Long id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            logger.info("Tentative de suppression d'une réservation " + id);
            transaction.begin();
            Reservation reservation = entityManager.find(Reservation.class, id);
            reservation.removeHistorique();
            reservation.getTopo().removeReservation(reservation);
            reservation.getEmprunteur().removeReservation(reservation);
            reservation.getPreteur().removeReservation(reservation);
            entityManager.remove(reservation);
            entityManager.flush();
            transaction.commit();
            logger.info("Suppression réservation réussie " + id);
            return true;
        }catch (Exception e){
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
            logger.error("Suppression réservation échouée " + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    @Override
    public List<Reservation> findAll() {
        logger.info("Recherche de toutes les réservations");
        Query query = entityManager.createQuery("select reservation from Reservation reservation order by reservation.id desc");
        return query.getResultList();
    }

    @Override
    public Reservation findOne(Long id) {
        logger.info("Recherche d'une réservation : " + id);
        return entityManager.find(Reservation.class, id);
    }

    /**
     * Retourne toutes les réservations pour un topo et pour un emprunteur
     * @param idTopo identifiant du topo
     * @param idEmprunteur identifiant de l'emprunteur
     * @return liste des réservations
     */
    public List<Reservation> findReservationInTopoForUser(Long idTopo, Long idEmprunteur) {
        logger.info("Recherche de toutes les réservations concernant le topo " + idTopo + " et l'emprunteur " + idEmprunteur);
        Query query = entityManager.createQuery("select r from Reservation r where r.emprunteur.id= :idEmprunteur and r.topo.id= :idTopo and r.dernierStatut = 'PENDING'");
        query.setParameter("idEmprunteur", idEmprunteur );
        query.setParameter("idTopo", idTopo);
        return query.getResultList();
    }
}
