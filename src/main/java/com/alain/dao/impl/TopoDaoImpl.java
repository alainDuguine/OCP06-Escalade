package com.alain.dao.impl;

import com.alain.EntityManagerUtil;
import com.alain.dao.contract.EntityRepository;
import com.alain.dao.entities.Topo;
import com.alain.dao.entities.Utilisateur;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class TopoDaoImpl implements EntityRepository<Topo> {

    private EntityManager entityManager = EntityManagerUtil.getEntityManager();


    @Override
    public Topo save(Topo topo, HttpServletRequest req) throws Exception {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            UtilisateurDaoImpl utilisateurDao = new UtilisateurDaoImpl();
            Utilisateur utilisateur = utilisateurDao.findByUsername((String) req.getSession().getAttribute("sessionUtilisateur"));
            topo.setUtilisateur(utilisateur);
            entityManager.persist(topo);
            transaction.commit();
        } catch (Exception e){
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
        }
        return topo;
    }

    @Override
    public Topo update(Topo topo, HttpServletRequest req) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(topo);
            entityManager.flush();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
        }
        return topo;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public List<Topo> findAll() {
        Query query = entityManager.createQuery("select t from Topo t order by t.nom asc");
        return query.getResultList();
    }

    @Override
    public Topo findOne(Long id) {
        return entityManager.find(Topo.class, id);
    }
}
