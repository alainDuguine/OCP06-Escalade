package com.alain.dao.impl;

import com.alain.EntityManagerUtil;
import com.alain.dao.contract.EntityRepository;
import com.alain.dao.entities.Departement;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class DepartementDaoImpl implements EntityRepository<Departement> {

    EntityManager entityManager = EntityManagerUtil.getEntityManager();

    @Override
    public Departement save(Departement departement, HttpServletRequest req) {
        return null;
    }

    @Override
    public Departement update(Departement departement) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    public List<Departement> findAll(){
        Query query = entityManager.createQuery("select departement from Departement departement");
        return query.getResultList();
    }

    @Override
    public Departement findOne(Long id) {
        return null;
    }

    public Departement getById(String id){
        Departement departement = entityManager.find(Departement.class, id);
        return departement;
    }
}
