package com.alain.dao.impl;

import com.alain.EntityManagerUtil;
import com.alain.dao.contract.EntityRepository;
import com.alain.dao.entities.Commentaire;
import com.alain.dao.entities.CommentaireSpot;
import com.alain.dao.entities.Spot;
import com.alain.dao.entities.Utilisateur;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class CommentaireSpotDaoImpl implements EntityRepository<CommentaireSpot> {

    private EntityManager entityManager = EntityManagerUtil.getEntityManager();

    @Override
    public CommentaireSpot save(CommentaireSpot commentaire, HttpServletRequest req) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            UtilisateurDaoImpl utilisateurDao = new UtilisateurDaoImpl();
            Utilisateur utilisateur = utilisateurDao.findByUsername((String) req.getSession().getAttribute("sessionUtilisateur"));
            SpotDaoImpl spotDao = new SpotDaoImpl();
            Long idSpot = Long.parseLong(req.getParameter("idSpot"));
            Spot spot = spotDao.findOne(idSpot);
            // Création des associations bidirectionelles
            spot.addCommentaire(commentaire);
            utilisateur.addCommentaireSpot(commentaire);
            entityManager.persist(commentaire);
            transaction.commit();
        }catch (Exception e){
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
        }
        return commentaire;
    }

    @Override
    public CommentaireSpot update(CommentaireSpot commentaireSpot, HttpServletRequest req) {
        return null;
    }


    @Override
    public boolean delete(Long id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Commentaire commentaire = entityManager.find(Commentaire.class, id);
            entityManager.remove(commentaire);
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
    public List<CommentaireSpot> findAll() {
        return null;
    }

    @Override
    public CommentaireSpot findOne(Long id) {
        return null;
    }

    public List<CommentaireSpot> findAllInSpot(Long id){
        Query query = entityManager.createQuery("select c from CommentaireSpot c where c.spot.id= :x order by c.dateTime desc");
        query.setParameter("x", id);
        return query.getResultList();
    }

}
