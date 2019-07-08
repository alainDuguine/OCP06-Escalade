package com.alain.dao.impl;

import com.alain.EntityManagerUtil;
import com.alain.dao.contract.EntityRepository;
import com.alain.dao.entities.Spot;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

public class SpotDaoImpl extends EntityManagerUtil implements EntityRepository<Spot> {

    EntityManager entityManager = EntityManagerUtil.getEntityManager();

    @Override
    public Spot save(Spot spot) {
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        entityManager.persist(spot);
        transaction.commit();
        Long id = spot.getId();
        System.out.println(id);
        return spot;
    }

    @Override
    public Spot update(Spot spot) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<Spot> findAll() {
        Query query = entityManager.createQuery("select spot from Spot spot");
        List <Spot> listSpots = query.getResultList();
        return listSpots;
    }

    @Override
    public Spot findOne(Long id) {
        return null;
    }

    @Override
    public Spot findByEmail(String email) {
        return null;
    }

    @Override
    public List<Spot> findByDesignation(String des) {
        return null;
    }
}
