package com.alain.dao.impl;

import com.alain.EntityManagerUtil;
import com.alain.dao.contract.EntityRepository;
import com.alain.dao.entities.Photo;
import com.alain.dao.entities.PhotoSpot;
import com.alain.dao.entities.Spot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.http.HttpServletRequest;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class PhotoSpotDaoImpl implements EntityRepository<PhotoSpot>{

    private EntityManager entityManager = EntityManagerUtil.getEntityManager();
    private static final Logger logger = LogManager.getLogger("PhotoSpotDaoImpl");

    @Override
    public PhotoSpot save(PhotoSpot photoSpot, HttpServletRequest req) {
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            logger.info("Tentative de sauvegarde d'une photoSpot");
            transaction.begin();
            Spot spot = entityManager.find(Spot.class, (req.getAttribute("idElement")));
            // Création des associations bidirectionelles
            spot.addPhoto(photoSpot);
            entityManager.persist(photoSpot);
            transaction.commit();
            logger.info("Photo sauvegardée : " + photoSpot.getId() + ", spot : " + spot.getId());
        }catch (Exception e){
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
            logger.error("Sauvegarde photo échouée : " + Arrays.toString(e.getStackTrace()));
            throw e;
        }
        return photoSpot;
    }

    @Override
    public PhotoSpot update(PhotoSpot photoSpot, HttpServletRequest req) {
        return null;
    }

    @Override
    public boolean delete(Long idPhoto){
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            logger.info("Tentative de suppression d'une photoSpot " + idPhoto);
            transaction.begin();
            PhotoSpot photo = entityManager.find(PhotoSpot.class, idPhoto);
            Spot spot = entityManager.find(Spot.class, photo.getSpot().getId());
            spot.removePhoto(photo);
            photo.removeRelation();
            entityManager.remove(photo);
            entityManager.flush();
            Path path = Paths.get(Photo.getCHEMIN()+photo.getNom());
            Files.delete(path);
            transaction.commit();
            logger.info("Suppression photo réussie " + idPhoto);
            return true;
        }catch (Exception e){
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
            logger.error("Suppression photo échouée : " + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    @Override
    public List<PhotoSpot> findAll() {
        return null;
    }

    @Override
    public PhotoSpot findOne(Long id) {
        return null;
    }

}
