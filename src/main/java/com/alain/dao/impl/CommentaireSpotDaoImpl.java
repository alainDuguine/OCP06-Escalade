package com.alain.dao.impl;

import com.alain.EntityManagerUtil;
import com.alain.dao.contract.EntityRepository;
import com.alain.dao.entities.Commentaire;
import com.alain.dao.entities.CommentaireSpot;
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

public class CommentaireSpotDaoImpl implements EntityRepository<CommentaireSpot> {

    private static final Logger logger = LogManager.getLogger("CommentaireSpotDaoImpl");
    private EntityManager entityManager = EntityManagerUtil.getEntityManager();

    /**
     * Enregistre un commentaire en base de données
     * ajoute les associations avec le spot et l'utilisateur,
     * @param commentaire à enregistrer
     * @param req requête http
     * @return l'objet commentaire
     */
    @Override
    public CommentaireSpot save(CommentaireSpot commentaire, HttpServletRequest req){
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            logger.info("Tentative de sauvegarde d'un commentaire");
            transaction.begin();
            UtilisateurDaoImpl utilisateurDao = new UtilisateurDaoImpl();
            Utilisateur utilisateur = utilisateurDao.findByUsername((String) req.getSession().getAttribute("sessionUtilisateur"));
            Spot spot = entityManager.find(Spot.class, Long.parseLong(req.getParameter("idSpot")));
            // Création des associations bidirectionelles
            spot.addCommentaire(commentaire);
            utilisateur.addCommentaireSpot(commentaire);
            entityManager.persist(commentaire);
            transaction.commit();
            logger.info("Commmentaire sauvegardé : " + commentaire.getId() + ", spot : " + spot.getId());
        }catch (Exception e){
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
            logger.error("Sauvegarde commentaire échouée : " + Arrays.toString(e.getStackTrace()));
            throw e;
        }
        return commentaire;
    }

    /**
     * Modifie un commentaire en base de données
     * @param commentaireSpot à modifier
     * @param req requête http
     * @return l'objet commentaire
     */
    @Override
    public CommentaireSpot update(CommentaireSpot commentaireSpot, HttpServletRequest req) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            logger.info("Tentative de modification d'un commentaire " + commentaireSpot.getId());
            transaction.begin();
            entityManager.merge(commentaireSpot);
            transaction.commit();
            logger.info("Modification commentaire réussie " + commentaireSpot.getId());
        } catch (Exception e){
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
            logger.error("Modification commentaire échouée : " + Arrays.toString(e.getStackTrace()));
            throw e;
        }
        return commentaireSpot;
    }

    /**
     * Supprime un commentaire en base de données
     * supprime les associations
     * @param id du commentaire
     * @return booléen
     */
    @Override
    public boolean delete(Long id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            logger.info("Tentative de suppression d'un commentaire " + id);
            transaction.begin();
            CommentaireSpot commentaireSpot = entityManager.find(CommentaireSpot.class, id);
//            Commentaire commentaire = entityManager.find(Commentaire.class, id);
//            Spot spot = entityManager.find(Spot.class, commentaireSpot.getSpot().getId());
            commentaireSpot.removeRelations();
//            commentaireSpot.removeSpot();
            entityManager.remove(commentaireSpot);
//            entityManager.remove(commentaire);
            entityManager.flush();
            transaction.commit();
            logger.info("Suppression commentaire réussie");
            return true;
        }catch (Exception e){
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
            logger.error("Suppression commentaire échouée : " + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    @Override
    public List<CommentaireSpot> findAll() {
        return null;
    }

    @Override
    public CommentaireSpot findOne(Long id) {
        logger.info("Recherche d'un commentaire " + id);
        return entityManager.find(CommentaireSpot.class, id);
    }

    /**
     * Retourne tous les commentaires enregistrées dans un spot
     * @param id du spot
     * @return liste des commentaires
     */
    public List<CommentaireSpot> findAllInSpot(Long id){
        logger.info("Recherche de tous les commentaires pour le spot " + id);
        Query query = entityManager.createQuery("select c from CommentaireSpot c where c.spot.id= :x order by c.dateTime desc");
        query.setParameter("x", id);
        return query.getResultList();
    }

}
