package com.alain.dao.impl;

import com.alain.EntityManagerUtil;
import com.alain.dao.contract.EntityRepository;
import com.alain.dao.entities.Cotation;
import com.alain.dao.entities.Secteur;
import com.alain.dao.entities.Utilisateur;
import com.alain.dao.entities.Voie;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

public class VoieDaoImpl implements EntityRepository<Voie> {

    private EntityManager entityManager = EntityManagerUtil.getEntityManager();
    private static final Logger logger = LogManager.getLogger("VoieoImpl");

    @Override
    public Voie save(Voie voie, HttpServletRequest req) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            logger.info("Tentative de sauvegarde d'une Voie");
            transaction.begin();
            UtilisateurDaoImpl utilisateurDao = new UtilisateurDaoImpl();
            Utilisateur utilisateur = utilisateurDao.findByUsername((String) req.getSession().getAttribute("sessionUtilisateur"));
            Secteur secteur = entityManager.find(Secteur.class,Long.parseLong(req.getParameter("idElement")));
            Cotation cotation = entityManager.find(Cotation.class, Long.parseLong(req.getParameter("cotation")));
            // Création des associations bidirectionelles
            voie.setSecteur(secteur);
            voie.setCotation(cotation);
            voie.setUtilisateur(utilisateur);
            entityManager.persist(voie);
            transaction.commit();
            logger.info("Sauvegarde voie réussie : " + voie.getId() + ", secteur :" + secteur.getId());
        }catch (Exception e){
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
            logger.error("Sauvegarde voie échouée : " + Arrays.toString(e.getStackTrace()));
            throw e;
        }
        return voie;
    }

    @Override
    public Voie update(Voie voie,  HttpServletRequest req) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            logger.info("Tentative de modification d'une voie" + voie.getId());
            transaction.begin();
            Cotation cotation = entityManager.find(Cotation.class, Long.parseLong(req.getParameter("cotation")));
            voie.setCotation(cotation);
            entityManager.merge(voie);
            transaction.commit();
            logger.info("Modification voie réussie " + voie.getId());
        }catch (Exception e){
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
            logger.error("Modification voie échouée : " + Arrays.toString(e.getStackTrace()));
            throw e;
        }
        return voie;
    }

    @Override
    public boolean delete(Long id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            logger.info("Tentative de suppression d'une voie " + id);
            transaction.begin();
            Voie voie = entityManager.find(Voie.class, id);
            Secteur secteur = entityManager.find(Secteur.class, voie.getSecteur().getId());
            secteur.removeVoie(voie);
            voie.removeSecteur();
            entityManager.remove(voie);
            entityManager.flush();
            transaction.commit();
            logger.info("Suppression voie réussie " + id);
            return true;
        }catch (Exception e){
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
            logger.error("Suppression voie échouée : " + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    @Override
    public List<Voie> findAll() {
        logger.info("Recherche de toutes les voies");
        Query query = entityManager.createQuery("select voie from Voie voie order by voie.nom asc");
        return query.getResultList();
    }

    @Override
    public Voie findOne(Long id) {
        logger.info("Recherche de la voie " + id);
        return entityManager.find(Voie.class, id);
    }

    public List<Voie> findVoieInSecteur(String nomVoie, Long idSecteur){
        logger.info("Recherche de la voie "+ nomVoie +" dans le secteur " + idSecteur);
        Query query = entityManager.createQuery("select v from Voie v where v.nom= :nom and v.secteur.id= :idSecteur");
        query.setParameter("nom", nomVoie);
        query.setParameter("idSecteur", idSecteur);
        return query.getResultList();
    }

    public List<Voie> findVoieInSecteurForUpdate(Long idVoie, String nomVoie, Long idSecteur) {
        logger.info("Recherche de la voie "+ idVoie +" "+nomVoie +" dans le secteur " + idSecteur + "pour modification");
        Query query = entityManager.createQuery("select v from Voie v where v.nom= :nom and v.secteur.id= :idSecteur and v.id <> :idVoie");
        query.setParameter("nom", nomVoie);
        query.setParameter("idSecteur", idSecteur);
        query.setParameter("idVoie", idVoie);
        return query.getResultList();
    }
}
