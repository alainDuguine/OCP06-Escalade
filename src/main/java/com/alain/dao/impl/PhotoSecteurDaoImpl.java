package com.alain.dao.impl;

import com.alain.EntityManagerUtil;
import com.alain.dao.contract.EntityRepository;
import com.alain.dao.entities.PhotoSecteur;
import com.alain.dao.entities.Secteur;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class PhotoSecteurDaoImpl implements EntityRepository<PhotoSecteur> {

    private EntityManager entityManager = EntityManagerUtil.getEntityManager();

    @Override
    public PhotoSecteur save(PhotoSecteur photoSecteur, HttpServletRequest req) {
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            SecteurDaoImpl secteurDao = new SecteurDaoImpl();
            Long idSecteur = (Long) req.getAttribute("idSecteur");
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
    public PhotoSecteur update(PhotoSecteur photoSecteur) {
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
    public PhotoSecteur findOne(Long id) {
        return null;
    }

}
