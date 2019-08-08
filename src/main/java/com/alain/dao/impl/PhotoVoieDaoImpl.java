package com.alain.dao.impl;

import com.alain.EntityManagerUtil;
import com.alain.dao.contract.EntityRepository;
import com.alain.dao.entities.PhotoVoie;
import com.alain.dao.entities.Voie;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class PhotoVoieDaoImpl implements EntityRepository<PhotoVoie> {

    private EntityManager entityManager = EntityManagerUtil.getEntityManager();

    @Override
    public PhotoVoie save(PhotoVoie photoVoie, HttpServletRequest req) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        VoieDaoImpl voieDao = new VoieDaoImpl();
        Long idVoie = (Long) req.getAttribute("idVoie");
        Voie voie = voieDao.findOne(idVoie);
        // Création des associations bidirectionelles
        voie.addPhoto(photoVoie);
        entityManager.persist(photoVoie);
        transaction.commit();
        return photoVoie;
    }

    @Override
    public PhotoVoie update(PhotoVoie photoVoie) {
        return null;
    }

    @Override
    public void delete(Long id) {

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
