package com.alain.dao.impl;

import com.alain.EntityManagerUtil;
import com.alain.dao.contract.EntityRepository;
import com.alain.dao.entities.Photo;
import com.alain.dao.entities.PhotoSpot;
import com.alain.dao.entities.Spot;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class PhotoDaoImpl extends EntityManagerUtil implements EntityRepository<Photo> {

    EntityManager entityManager = EntityManagerUtil.getEntityManager();

    @Override
    public Photo save(Photo photo, HttpServletRequest req) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        SpotDaoImpl spotDao = new SpotDaoImpl();
        Spot spot = spotDao.findOne(Long.parseLong(req.getParameter("idSpot")));
        // Création des associations bidirectionelles
        spot.addPhoto((PhotoSpot)photo);
        entityManager.persist(photo);
        transaction.commit();
        return photo;
    }

    @Override
    public Photo update(Photo photo) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List findAll() {
        return null;
    }

    @Override
    public Photo findOne(Long id) {
        return null;
    }


}
