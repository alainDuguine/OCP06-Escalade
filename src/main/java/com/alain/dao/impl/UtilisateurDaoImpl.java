package com.alain.dao.impl;

import com.alain.EntityManagerUtil;
import com.alain.dao.contract.EntityRepository;
import com.alain.dao.entities.Utilisateur;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

public class UtilisateurDaoImpl implements EntityRepository<Utilisateur>{

    private EntityManager entityManager = EntityManagerUtil.getEntityManager();
    private static final Logger logger = LogManager.getLogger("UtilisateurDaoImpl");

    /**
     * Enregistre un utilisateur en base de données
     * @param utilisateur à enregistrer
     * @param req requête http
     * @return l'objet utilisateur
     */
    public Utilisateur save(Utilisateur utilisateur, HttpServletRequest req) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            logger.info("Tentative de sauvegarde d'un utilisateur");
            transaction.begin();
            entityManager.persist(utilisateur);
            transaction.commit();
            logger.info("Sauvegarde utilisateur réussie " + utilisateur.getId());
        } catch (Exception e){
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
            logger.error("Sauvegarde utilisateur échouée : " + Arrays.toString(e.getStackTrace()));
            throw e;
        }
        return utilisateur;
    }

    /**
     * Modifie un utilisateur en base de données
     * @param utilisateur à modifier
     * @param req requête http
     * @return l'objet utilisateur
     */
    public Utilisateur update(Utilisateur utilisateur,  HttpServletRequest req) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            logger.info("Tentative de modification d'un utilisateur " + utilisateur.getId());
            transaction.begin();
            entityManager.merge(utilisateur);
            entityManager.flush();
            transaction.commit();
            logger.info("Modification utilisateur réussie " + utilisateur.getId());
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
            logger.error("Modification utilisateur échouée : " + Arrays.toString(e.getStackTrace()));
            throw e;
        }
        return utilisateur;
    }

    /**
     * Supprime un utilisateur en base de données
     * @param id de l'utilisateur
     * @return booléen
     */
    public boolean delete(Long id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            logger.info("Tentative de suppression d'un utilisateur " + id);
            transaction.begin();
            Utilisateur utilisateur = entityManager.find(Utilisateur.class, id);
            entityManager.remove(utilisateur);
            transaction.commit();
            logger.info("Suppression utilisateur réussie " + id);
            return true;
        }catch (Exception e){
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
            logger.error("Suppression utilisateur échouée : " + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    public List<Utilisateur> findAll() {
        logger.info("Recherche de tous les utilisateurs");
        Query query = entityManager.createQuery("select u from Utilisateur u order by u.id asc");
        return query.getResultList();
    }

    public Utilisateur findOne(Long id) {
        logger.info("Recherche de l'utilisateur " + id);
        return entityManager.find(Utilisateur.class, id);
    }

    public Utilisateur findByEmail(String email) {
        logger.info("Recherche de l'utilisateur " + email);
        Query query = entityManager.createQuery( "select u from Utilisateur u where u.email = :email");
        query.setParameter("email", email);
        try {
            return (Utilisateur) query.getSingleResult();
        } catch (Exception e){
            logger.warn("Utilisateur introuvable " + email);
            return null;
        }
    }

    public Utilisateur findByUsername(String username) {
        logger.info("Recherche de l'utilisateur " + username);
        Query query = entityManager.createQuery("select u from Utilisateur u where u.username= :username");
        query.setParameter("username", username );
        try {
            return (Utilisateur) query.getSingleResult();
        } catch (Exception e){
            logger.warn("Utilisateur introuvable " + username);
            return null;
        }
    }
}
