package com.alain.dao.impl;

import com.alain.EntityManagerUtil;
import com.alain.dao.entities.Photo;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;


public class PhotoDao{

    private EntityManager entityManager = EntityManagerUtil.getEntityManager();

    public boolean deletePhoto(Long id){
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Photo photo = entityManager.find(Photo.class, id);
            entityManager.remove(photo);
            transaction.commit();
            return true;
        }catch (Exception e){
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

}
