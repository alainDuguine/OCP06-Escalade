package com.alain.dao.impl;

import com.alain.EntityManagerUtil;
import com.alain.dao.contract.EntityRepository;
import com.alain.dao.entities.Departement;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class DepartementDaoImpl {

    EntityManager entityManager = EntityManagerUtil.getEntityManager();

    public List<Departement> findAll(){
        Query query = entityManager.createQuery("select departement from Departement departement");
        return query.getResultList();
    }

    public Departement getById(String id){
        Departement departement = entityManager.find(Departement.class, id);
        return departement;
    }
}
