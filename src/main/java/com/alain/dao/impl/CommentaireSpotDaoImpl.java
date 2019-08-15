package com.alain.dao.impl;

import com.alain.EntityManagerUtil;
import com.alain.dao.contract.EntityRepository;
import com.alain.dao.entities.CommentaireSpot;
import com.alain.dao.entities.Spot;

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
        transaction.begin();
        SpotDaoImpl spotDao = new SpotDaoImpl();
        Long idSpot = Long.parseLong(req.getParameter("idSpot"));
        Spot spot = spotDao.findOne(idSpot);
        // Création des associations bidirectionelles
        spot.addCommentaire(commentaire);
        entityManager.persist(commentaire);
        transaction.commit();
        return commentaire;
    }

    @Override
    public CommentaireSpot update(CommentaireSpot commentaireSpot) {
        return null;
    }

    @Override
    public void delete(Long id) {

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
