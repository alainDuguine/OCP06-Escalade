package com.alain.dao.impl;

import com.alain.EntityManagerUtil;
import com.alain.dao.contract.EntityRepository;
import com.alain.dao.entities.Secteur;
import com.alain.dao.entities.Spot;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class SecteurDaoImpl extends EntityManagerUtil implements EntityRepository<Secteur> {

    EntityManager entityManager = EntityManagerUtil.getEntityManager();

    public Secteur save(Secteur secteur, HttpServletRequest req) {
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        SpotDaoImpl spotDao = new SpotDaoImpl();
        Spot spot = spotDao.findOne(Long.parseLong(req.getParameter("idSpot")));
        // Création des associations bidirectionelles
        secteur.setSpot(spot);
        entityManager.merge(spot);
        entityManager.persist(secteur);
        transaction.commit();

        return secteur;
    }

    @Override
    public Secteur update(Secteur secteur) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<Secteur> findAll() {
        return null;
    }

    @Override
    public Secteur findOne( Long id) {
        return null;
    }

    public List<Secteur> findSecteurInSpot(String nomSecteur, Long idSpot) throws Exception {
        Query query = null;
        try {
            query = entityManager.createQuery("select s from Secteur s where s.nom= :nom and s.spot.id= :idSpot");
            query.setParameter("nom", nomSecteur);
            query.setParameter("idSpot", idSpot);
        } catch (Exception e){};
        return query.getResultList();
    }

}
