package com.alain.dao.impl;

import com.alain.EntityManagerUtil;
import com.alain.dao.contract.EntityRepository;
import com.alain.dao.entities.Photo;
import com.alain.dao.entities.PhotoSecteur;
import com.alain.dao.entities.PhotoSpot;
import com.alain.dao.entities.Secteur;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.http.HttpServletRequest;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class PhotoSecteurDaoImpl implements EntityRepository<PhotoSecteur>{

    private EntityManager entityManager = EntityManagerUtil.getEntityManager();

    @Override
    public PhotoSecteur save(PhotoSecteur photoSecteur, HttpServletRequest req) {
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            SecteurDaoImpl secteurDao = new SecteurDaoImpl();
            Long idSecteur = (Long) req.getAttribute("idElement");
            Secteur secteur = secteurDao.findOne(idSecteur);
            // Création des associations bidirectionelles
            secteur.addPhoto(photoSecteur);
            entityManager.persist(photoSecteur);
            transaction.commit();
        }catch (Exception e){
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
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
            return true;
        }catch (Exception e){
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
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
