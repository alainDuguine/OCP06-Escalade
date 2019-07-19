package com.alain.dao.impl;

import com.alain.EntityManagerUtil;
import com.alain.dao.contract.EntityRepository;
import com.alain.dao.entities.Departement;
import com.alain.dao.entities.Spot;
import com.alain.dao.entities.Ville;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class SpotDaoImpl implements EntityRepository<Spot> {

    EntityManager entityManager = EntityManagerUtil.getEntityManager();

    @Override
    public Spot save(Spot spot, HttpServletRequest req) {
        EntityTransaction transaction = entityManager.getTransaction();
        DepartementDaoImpl departementDao = new DepartementDaoImpl();
        VilleDaoImpl villeDao = new VilleDaoImpl();
        transaction.begin();
        Departement departement = departementDao.getById(req.getParameter("departement"));
        Ville ville = villeDao.findOne(Long.parseLong(req.getParameter("ville")));
        spot.setDepartement(departement);
        spot.setVille(ville);
        entityManager.persist(spot);
        transaction.commit();
        return spot;
    }

    @Override
    public Spot update(Spot spot) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.merge(spot);
        transaction.commit();
        return spot;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<Spot> findAll() {
        Query query = entityManager.createQuery("select spot from Spot spot");
        return query.getResultList();
    }

    @Override
    public Spot findOne(Long id) {
        return entityManager.find(Spot.class, id);
    }

    public List<Spot> findSpotInDepartement(String nomSpot, String departement) {
        Query query = entityManager.createQuery("select s from Spot s where s.nom= :nom and s.departement.code= :departement");
        query.setParameter("nom", nomSpot );
        query.setParameter("departement", departement);
        return query.getResultList();
    }
}
