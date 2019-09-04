package com.alain.dao.impl;

import com.alain.EntityManagerUtil;
import com.alain.dao.contract.EntityRepository;
import com.alain.dao.entities.Secteur;
import com.alain.dao.entities.Spot;
import com.alain.dao.entities.Utilisateur;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class SecteurDaoImpl extends EntityManagerUtil implements EntityRepository<Secteur> {

    private EntityManager entityManager = EntityManagerUtil.getEntityManager();

    public Secteur save(Secteur secteur, HttpServletRequest req) {
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            UtilisateurDaoImpl utilisateurDao = new UtilisateurDaoImpl();
            Utilisateur utilisateur = utilisateurDao.findByUsername((String) req.getSession().getAttribute("sessionUtilisateur"));
            Spot spot = entityManager.find(Spot.class, Long.parseLong(req.getParameter("idElement")));
            // Création des associations bidirectionelles
            secteur.setSpot(spot);
            secteur.setUtilisateur(utilisateur);
            entityManager.persist(secteur);
            transaction.commit();
        }catch (Exception e){
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
        }
        return secteur;
    }

    @Override
    public Secteur update(Secteur secteur,  HttpServletRequest req) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(secteur);
            transaction.commit();
        } catch (Exception e){
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
        }
        return secteur;
    }

    @Override
    public boolean delete(Long id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Secteur secteur = entityManager.find(Secteur.class, id);
            entityManager.remove(secteur);
            entityManager.flush();
            transaction.commit();
            return true;
        }catch (Exception e){
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Secteur> findAll() {
        Query query = entityManager.createQuery("select secteur from Secteur secteur order by secteur.nom asc");
        return query.getResultList();
    }

    @Override
    public Secteur findOne( Long id) {
        return entityManager.find(Secteur.class, id);
    }

    public List<Secteur> findSecteurInSpot(String nomSecteur, Long idSpot){
        Query query = entityManager.createQuery("select s from Secteur s where s.nom= :nom and s.spot.id= :idSpot");
        query.setParameter("nom", nomSecteur);
        query.setParameter("idSpot", idSpot);
        return query.getResultList();
    }

    public List<Secteur> findSecteurInSpotForUpdate(Long idSecteur, String nomSecteur, long idSpot) {
        Query query = entityManager.createQuery("select s from Secteur s where s.nom= :nom and s.spot.id= :idSpot and s.id <> :idSecteur");
        query.setParameter("nom", nomSecteur);
        query.setParameter("idSpot", idSpot);
        query.setParameter("idSecteur", idSecteur);
        return query.getResultList();
    }
}
