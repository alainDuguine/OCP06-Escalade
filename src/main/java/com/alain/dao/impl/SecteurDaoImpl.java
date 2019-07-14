package com.alain.dao.impl;

import com.alain.EntityManagerUtil;
import com.alain.dao.contract.EntityRepository;
import com.alain.dao.entities.Secteur;
import com.alain.dao.entities.Spot;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class SecteurDaoImpl extends EntityManagerUtil implements EntityRepository<Secteur> {

    EntityManager entityManager = EntityManagerUtil.getEntityManager();

    @Override
    public Secteur save(Secteur secteur, Long idSpot) {
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        SpotDaoImpl spotDao = new SpotDaoImpl();
        Spot spot = spotDao.findOne(idSpot);
        // Création des associations bidirectionelles
        secteur.setSpot(spot);
        entityManager.merge(spot);
        entityManager.persist(secteur);
        transaction.commit();

        return secteur;
    }

    @Override
    public Secteur update(Secteur secteur) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<Secteur> findAll() {
        return null;
    }

    @Override
    public Secteur findOne(Long id) {
        return null;
    }

}
