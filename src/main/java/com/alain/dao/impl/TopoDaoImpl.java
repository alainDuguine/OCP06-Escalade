package com.alain.dao.impl;

import com.alain.EntityManagerUtil;
import com.alain.dao.contract.EntityRepository;
import com.alain.dao.entities.Spot;
import com.alain.dao.entities.Topo;
import com.alain.dao.entities.Utilisateur;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

public class TopoDaoImpl implements EntityRepository<Topo> {

    private EntityManager entityManager = EntityManagerUtil.getEntityManager();
    private static final Logger logger = LogManager.getLogger("TopoDaoImpl");

    @Override
    public Topo save(Topo topo, HttpServletRequest req){
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            logger.info("Tentative de sauvegarde d'un topo");
            transaction.begin();
            UtilisateurDaoImpl utilisateurDao = new UtilisateurDaoImpl();
            Utilisateur utilisateur = utilisateurDao.findByUsername((String) req.getSession().getAttribute("sessionUtilisateur"));
            topo.setUtilisateur(utilisateur);
            entityManager.persist(topo);
            transaction.commit();
            logger.info("Sauvegarde topo réussie :" + topo.getId());
        } catch (Exception e){
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
            logger.error("Sauvegarde topo échouée :" + Arrays.toString(e.getStackTrace()));
            throw e;
        }
        return topo;
    }

    @Override
    public Topo update(Topo topo, HttpServletRequest req) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            logger.info("Tentative de modification d'un topo");
            transaction.begin();
            entityManager.merge(topo);
            entityManager.flush();
            transaction.commit();
            logger.info("Modification topo réussie :" + topo.getId());
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
            logger.error("Modification topo échouée :" + Arrays.toString(e.getStackTrace()));
            throw e;
        }
        return topo;
    }

    @Override
    public boolean delete(Long id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            logger.info("Tentative de suppression d'un topo");
            transaction.begin();
            Topo topo = entityManager.find(Topo.class, id);
            topo.removeAllSpots();
            topo.removeAllReservations();
            entityManager.remove(topo);
            entityManager.flush();
            transaction.commit();
            logger.info("Suppression topo réussie :" + topo.getId());
            return true;
        }catch (Exception e){
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
            logger.error("Suppression topo échouée :" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    @Override
    public List<Topo> findAll() {
        logger.info("Recherche de tous les topos");
        Query query = entityManager.createQuery("select t from Topo t order by t.nom asc");
        return query.getResultList();
    }

    @Override
    public Topo findOne(Long id) {
        logger.info("Recherche du topo" + id);
        return entityManager.find(Topo.class, id);
    }

    public boolean deleteSpotFromTopo(Long idTopo, Long idSpot) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            logger.info("Tentative de suppression du spot " + idSpot + "du topo " + idTopo);
            transaction.begin();
            Topo topo = entityManager.find(Topo.class, idTopo);
            Spot spot = entityManager.find(Spot.class, idSpot);
            //Supprime le topo dans le spot et le spot dans le topo
            topo.removeSpot(spot);
            spot.removeFromTopo(topo);
            entityManager.merge(topo);
            entityManager.flush();
            transaction.commit();
            logger.info("Suppression réussie");
            return true;
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
            logger.error("Suppression échouée : " + Arrays.toString(e.getStackTrace()));
            return false;
        }

    }

    public boolean addSpotInTopo(Long idTopo, Long idSpot) {
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            logger.info("Tentative d'ajout du spot " + idSpot + "au topo " + idTopo);
            transaction.begin();
            Topo topo = entityManager.find(Topo.class, idTopo);
            Spot spot = entityManager.find(Spot.class, idSpot);
            // Ajoute le spot dans le topo et le topo dans le spot
            topo.addSpot(spot);
            entityManager.merge(topo);
            entityManager.flush();
            transaction.commit();
            logger.info("Ajout de relation réussi");
            return true;
        } catch (Exception e){
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
            logger.info("Ajout de relation échoué " + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
}
