package com.alain.dao.impl;

import com.alain.EntityManagerUtil;
import com.alain.dao.entities.Ville;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class VilleDaoImpl{

    private EntityManager entityManager = EntityManagerUtil.getEntityManager();
    private static final Logger logger = LogManager.getLogger("VilleDaoImpl");


    public List<Ville> findAllInDep(String codeDep){
        logger.info("Recherche des villes dans le département " + codeDep);
        Query query = entityManager.createQuery("select v.id, v.nom from Ville v where v.departement.code= :x order by v.nom");
        query.setParameter("x", codeDep);
        return query.getResultList();
    }

    public Ville getById(Long id){
        logger.info("Recherche de la ville" + id);
        return entityManager.find(Ville.class, id);
    }

    public Ville findOne(Long id) {
        logger.info("Recherche de la ville" + id);
        return entityManager.find(Ville.class, id);
    }
}