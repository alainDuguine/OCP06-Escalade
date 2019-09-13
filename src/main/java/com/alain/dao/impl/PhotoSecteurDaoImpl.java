package com.alain.dao.impl;

import com.alain.EntityManagerUtil;
import com.alain.dao.contract.EntityRepository;
import com.alain.dao.entities.Photo;
import com.alain.dao.entities.PhotoSecteur;
import com.alain.dao.entities.Secteur;
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

public class PhotoSecteurDaoImpl implements EntityRepository<PhotoSecteur>{

    private EntityManager entityManager = EntityManagerUtil.getEntityManager();
    private static final Logger logger = LogManager.getLogger("PhotoSecteurDaoImpl");

    @Override
    public PhotoSecteur save(PhotoSecteur photoSecteur, HttpServletRequest req) {
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            logger.info("Tentative de sauvegarde d'une photoSecteur");
            transaction.begin();
            Secteur secteur = entityManager.find(Secteur.class, (req.getAttribute("idElement")));
            // Création des associations bidirectionelles
            secteur.addPhoto(photoSecteur);
            entityManager.persist(photoSecteur);
            transaction.commit();
            logger.info("Photo sauvegardée : " + photoSecteur.getId() + ", secteur : " + secteur.getId());
        }catch (Exception e){
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
            logger.error("Sauvegarde photo échouée : " + Arrays.toString(e.getStackTrace()));
            throw e;
        }
            return photoSecteur;
    }

    @Override
    public PhotoSecteur update(PhotoSecteur photoSecteur, HttpServletRequest req) {
        return null;
    }

    @Override
    public boolean delete(Long idPhoto){
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            logger.info("Tentative de suppression d'une photoSecteur " + idPhoto);
            transaction.begin();
            PhotoSecteur photo = entityManager.find(PhotoSecteur.class, idPhoto);
            Secteur secteur = entityManager.find(Secteur.class, photo.getSecteur().getId());
            secteur.removePhoto(photo);
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
    public List<PhotoSecteur> findAll() {
        return null;
    }

    @Override
    public PhotoSecteur findOne(Long id) {
        return null;
    }

}
