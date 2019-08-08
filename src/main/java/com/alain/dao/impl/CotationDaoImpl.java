package com.alain.dao.impl;

import com.alain.EntityManagerUtil;
import com.alain.dao.contract.EntityRepository;
import com.alain.dao.entities.Cotation;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class CotationDaoImpl implements EntityRepository<Cotation> {

    private EntityManager entityManager = EntityManagerUtil.getEntityManager();

    @Override
    public Cotation save(Cotation cotation, HttpServletRequest req) {
        return null;
    }

    @Override
    public Cotation update(Cotation cotation) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<Cotation> findAll() {
        Query query = entityManager.createQuery("select cotation from Cotation cotation");
        return query.getResultList();
    }

    @Override
    public Cotation findOne(Long id) {
        return null;
    }
}
