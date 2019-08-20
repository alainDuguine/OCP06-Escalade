package com.alain.dao.impl;

import com.alain.EntityManagerUtil;
import com.alain.dao.contract.EntityRepository;
import com.alain.dao.entities.PhotoSpot;
import com.alain.dao.entities.Spot;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class PhotoSpotDaoImpl implements EntityRepository<PhotoSpot> {

    private EntityManager entityManager = EntityManagerUtil.getEntityManager();

    @Override
    public PhotoSpot save(PhotoSpot photoSpot, HttpServletRequest req) {
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            SpotDaoImpl spotDao = new SpotDaoImpl();
            Long idSpot = (Long) req.getAttribute("idSpot");
            Spot spot = spotDao.findOne(idSpot);
            // Création des associations bidirectionelles
            spot.addPhoto(photoSpot);
            entityManager.persist(photoSpot);
            transaction.commit();
        }catch (Exception e){
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
        }
        return photoSpot;
    }

    @Override
    public PhotoSpot update(PhotoSpot photoSpot) {
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
    public PhotoSpot findOne(Long id) {
        return null;
    }

}
