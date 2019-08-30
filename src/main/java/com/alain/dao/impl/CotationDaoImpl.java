package com.alain.dao.impl;

import com.alain.EntityManagerUtil;
import com.alain.dao.contract.EntityRepository;
import com.alain.dao.entities.Cotation;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class CotationDaoImpl{

    private EntityManager entityManager = EntityManagerUtil.getEntityManager();


    public List<Cotation> findAll() {
        Query query = entityManager.createQuery("select cotation from Cotation cotation");
        return query.getResultList();
    }

    public Cotation findOne(Long id) {
        return entityManager.find(Cotation.class, id);
    }
}
