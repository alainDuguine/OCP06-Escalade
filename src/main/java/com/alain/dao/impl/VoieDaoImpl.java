package com.alain.dao.impl;

import com.alain.EntityManagerUtil;
import com.alain.dao.contract.EntityRepository;
import com.alain.dao.entities.Cotation;
import com.alain.dao.entities.Secteur;
import com.alain.dao.entities.Voie;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class VoieDaoImpl implements EntityRepository<Voie> {

    private EntityManager entityManager = EntityManagerUtil.getEntityManager();

    @Override
    public Voie save(Voie voie, HttpServletRequest req) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        SecteurDaoImpl secteurDao = new SecteurDaoImpl();
        Secteur secteur = secteurDao.findOne(Long.parseLong(req.getParameter("idSecteur")));
        CotationDaoImpl cotationDao = new CotationDaoImpl();
        Cotation cotation = cotationDao.findOne(Long.parseLong(req.getParameter("cotation")));
        // Création des associations bidirectionelles
        voie.setSecteur(secteur);
        voie.setCotation(cotation);
        entityManager.persist(voie);
        transaction.commit();
        return voie;
    }

    @Override
    public Voie update(Voie voie) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<Voie> findAll() {
        return null;
    }

    @Override
    public Voie findOne(Long id) {
        return entityManager.find(Voie.class, id);
    }

    public List<Voie> findVoieInSecteur(String nomVoie, Long idSecteur){
        Query query = entityManager.createQuery("select v from Voie v where v.nom= :nom and v.secteur.id= :idSecteur");
        query.setParameter("nom", nomVoie);
        query.setParameter("idSecteur", idSecteur);
        return query.getResultList();
    }
}
