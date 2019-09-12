package com.alain.dao.impl;

import com.alain.EntityManagerUtil;
import com.alain.dao.entities.Departement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class DepartementDaoImpl {

    EntityManager entityManager = EntityManagerUtil.getEntityManager();
    private static final Logger logger = LogManager.getLogger("DepartementDaoImpl");

    public List<Departement> findAll(){
        logger.info("Recherche de la liste des départements");
        Query query = entityManager.createQuery("select departement from Departement departement");
        return query.getResultList();
    }

    public Departement getById(String id){
        Departement departement = entityManager.find(Departement.class, id);
        return departement;
    }
}
