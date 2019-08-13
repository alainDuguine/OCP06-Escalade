package com.alain.dao.impl;

import com.alain.EntityManagerUtil;
import com.alain.dao.contract.EntityRepository;
import com.alain.dao.entities.Ville;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class VilleDaoImpl implements EntityRepository<Ville> {

    private EntityManager entityManager = EntityManagerUtil.getEntityManager();

    public List<Ville> findAllInDep(String codeDep){
        Query query = entityManager.createQuery("select v.id, v.nom from Ville v where v.departement.code= :x order by v.nom");
        query.setParameter("x", codeDep);
        return query.getResultList();
    }

    public Ville getById(Long id){
        return entityManager.find(Ville.class, id);
    }

    @Override
    public Ville save(Ville ville, HttpServletRequest req) {
        return null;
    }

    @Override
    public Ville update(Ville ville) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<Ville> findAll() {
        return null;
    }

    @Override
    public Ville findOne(Long id) {
        return entityManager.find(Ville.class, id);
    }
}