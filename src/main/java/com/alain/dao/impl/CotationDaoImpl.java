package com.alain.dao.impl;

import com.alain.EntityManagerUtil;
import com.alain.dao.entities.Cotation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class CotationDaoImpl{

    private EntityManager entityManager = EntityManagerUtil.getEntityManager();
    private static final Logger logger = LogManager.getLogger("CotationDaoImpl");

    public List<Cotation> findAll() {
        logger.info("Recherche de la liste des cotations");
        Query query = entityManager.createQuery("select cotation from Cotation cotation");
        return query.getResultList();
    }

    public Cotation findOne(Long id) {
        logger.info("Recherche d'une cotation");
        return entityManager.find(Cotation.class, id);
    }
}
