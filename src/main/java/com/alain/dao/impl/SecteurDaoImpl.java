package com.alain.dao.impl;

import com.alain.EntityManagerUtil;
import com.alain.dao.contract.EntityRepository;
import com.alain.dao.entities.Secteur;
import com.alain.dao.entities.Spot;
import com.alain.dao.entities.Utilisateur;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

public class SecteurDaoImpl extends EntityManagerUtil implements EntityRepository<Secteur> {

    private EntityManager entityManager = EntityManagerUtil.getEntityManager();
    private static final Logger logger = LogManager.getLogger("SecteurDaoImpl");


    public Secteur save(Secteur secteur, HttpServletRequest req) {
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            logger.info("Tentative de sauvegarde d'un Secteur");
            transaction.begin();
            UtilisateurDaoImpl utilisateurDao = new UtilisateurDaoImpl();
            Utilisateur utilisateur = utilisateurDao.findByUsername((String) req.getSession().getAttribute("sessionUtilisateur"));
            Spot spot = entityManager.find(Spot.class, Long.parseLong(req.getParameter("idElement")));
            // Création des associations bidirectionelles
            secteur.setSpot(spot);
            secteur.setUtilisateur(utilisateur);
            entityManager.persist(secteur);
            transaction.commit();
            logger.info("Sauvegarde secteur réussie :" + secteur.getId() + ", spot : " + spot.getId());
        }catch (Exception e){
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
            logger.error("Sauvegarde secteur échouée :" + Arrays.toString(e.getStackTrace()));
            throw e;
        }
        return secteur;
    }

    @Override
    public Secteur update(Secteur secteur,  HttpServletRequest req) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            logger.info("Tentative de modification d'un Secteur" + secteur.getId());
            transaction.begin();
            entityManager.merge(secteur);
            transaction.commit();
            logger.info("Modification secteur réussie" + secteur.getId());
        } catch (Exception e){
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
            logger.error("Modification secteur échouée :" + Arrays.toString(e.getStackTrace()));
            throw e;
        }
        return secteur;
    }

    @Override
    public boolean delete(Long id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            logger.info("Tentative de suppression d'un Secteur" + id);
            transaction.begin();
            Secteur secteur = entityManager.find(Secteur.class, id);
            Spot spot = entityManager.find(Spot.class, secteur.getSpot().getId());
            spot.removeSecteur(secteur);
            secteur.removeSpot();
            entityManager.remove(secteur);
            entityManager.flush();
            transaction.commit();
            logger.info("Suppression secteur réussie" + id);
            return true;
        }catch (Exception e){
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
            logger.error("Suppression secteur échouée :" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    @Override
    public List<Secteur> findAll() {
        logger.info("Recherche de tous les secteurs");
        Query query = entityManager.createQuery("select secteur from Secteur secteur order by secteur.nom asc");
        return query.getResultList();
    }

    @Override
    public Secteur findOne( Long id) {
        logger.info("Recherche du secteur " + id);
        return entityManager.find(Secteur.class, id);
    }

    public List<Secteur> findSecteurInSpot(String nomSecteur, Long idSpot){
        logger.info("Recherche du secteur "+ nomSecteur +" dans le spot " + idSpot);
        Query query = entityManager.createQuery("select s from Secteur s where s.nom= :nom and s.spot.id= :idSpot");
        query.setParameter("nom", nomSecteur);
        query.setParameter("idSpot", idSpot);
        return query.getResultList();
    }

    public List<Secteur> findSecteurInSpotForUpdate(Long idSecteur, String nomSecteur, long idSpot) {
        logger.info("Recherche du secteur "+ nomSecteur +" dans le spot " + idSpot + "pour modification");
        Query query = entityManager.createQuery("select s from Secteur s where s.nom= :nom and s.spot.id= :idSpot and s.id <> :idSecteur");
        query.setParameter("nom", nomSecteur);
        query.setParameter("idSpot", idSpot);
        query.setParameter("idSecteur", idSecteur);
        return query.getResultList();
    }
}
