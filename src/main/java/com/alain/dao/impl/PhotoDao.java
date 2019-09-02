package com.alain.dao.impl;

import com.alain.EntityManagerUtil;
import com.alain.dao.contract.EntityRepository;
import com.alain.dao.entities.Photo;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.http.HttpServletRequest;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


public class PhotoDao implements EntityRepository<Photo> {

    private EntityManager entityManager = EntityManagerUtil.getEntityManager();

    @Override
    public boolean delete(Long id){
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Photo photo = entityManager.find(Photo.class, id);
            entityManager.remove(photo);
            entityManager.flush();
            Path path = Paths.get(Photo.getCHEMIN()+photo.getNom());
            Files.delete(path);
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
    public Photo save(Photo photo, HttpServletRequest req) {
        return null;
    }

    @Override
    public Photo update(Photo photo, HttpServletRequest req) {
        return null;
    }

    @Override
    public List<Photo> findAll() {
        return null;
    }

    @Override
    public Photo findOne(Long id) {
        return null;
    }
}
