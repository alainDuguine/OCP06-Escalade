package com.alain.dao.impl;

import com.alain.EntityManagerUtil;
import com.alain.dao.contract.EntityRepository;
import com.alain.dao.entities.Photo;
import com.alain.dao.entities.PhotoVoie;
import com.alain.dao.entities.Voie;
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

public class PhotoVoieDaoImpl implements EntityRepository<PhotoVoie>{

    private EntityManager entityManager = EntityManagerUtil.getEntityManager();
    private static final Logger logger = LogManager.getLogger("PhotoVoieDaoImpl");

    /**
     * Enregistre une photo dans une voie en base de données
     * ajoute les associations avec la voie,
     * @param photoVoie à enregistrer
     * @param req requête http
     * @return l'objet photo
     */
    @Override
    public PhotoVoie save(PhotoVoie photoVoie, HttpServletRequest req) {
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            logger.info("Tentative de sauvegarde d'une photoVoie");
            transaction.begin();
            Voie voie = entityManager.find(Voie.class, (req.getAttribute("idElement")));
            // Création des associations bidirectionelles
            voie.addPhoto(photoVoie);
            entityManager.persist(photoVoie);
            transaction.commit();
            logger.info("Photo sauvegardée : " + photoVoie.getId() + ", voie : " + voie.getId());
        }catch (Exception e){
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
            logger.error("Sauvegarde photo échouée : " + Arrays.toString(e.getStackTrace()));
            throw e;
        }
        return photoVoie;
    }

    @Override
    public PhotoVoie update(PhotoVoie photoVoie, HttpServletRequest req) {
        return null;
    }

    /**
     * Supprime une photo dans une voie en base de données
     * Supprime les associations avec la voie,
     * @param idPhoto identifiant de la photo
     * @return booléen
     */
    @Override
    public boolean delete(Long idPhoto){
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            logger.info("Tentative de suppression d'une photoVoie " + idPhoto);
            transaction.begin();
            PhotoVoie photo = entityManager.find(PhotoVoie.class, idPhoto);
            Voie voie = entityManager.find(Voie.class, photo.getVoie().getId());
            voie.removePhoto(photo);
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
    public List<PhotoVoie> findAll() {
        return null;
    }

    @Override
    public PhotoVoie findOne(Long id) {
        return null;
    }

}
