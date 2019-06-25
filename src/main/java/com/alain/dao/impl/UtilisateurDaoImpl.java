package com.alain.dao.impl;

import com.alain.EntityManagerUtil;
import com.alain.dao.contract.EntityRepository;
import com.alain.dao.entities.Utilisateur;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

public class UtilisateurDaoImpl extends EntityManagerUtil implements EntityRepository<Utilisateur>{

    private EntityManager entityManager = getEntityManager();

    public Utilisateur save(Utilisateur utilisateur) {
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        // Enregistrement de l'utilisateur
        entityManager.persist(utilisateur);
        // Récupération de l'id et renvoi de l'utilisateur
        Query query = entityManager.createQuery("select MAX(u.id) from Utilisateur u");
        utilisateur.setId((Long) query.getSingleResult());
        transaction.commit();
        return utilisateur;
    }

    public Utilisateur update(Utilisateur utilisateur) {
        entityManager.merge(utilisateur);
        return utilisateur;
    }

    public void delete(Long id) {
        Utilisateur utilisateur = entityManager.find(Utilisateur.class, id);
        entityManager.remove(utilisateur);
    }

    public List<Utilisateur> findAll() {
        Query query = entityManager.createQuery("select u from Utilisateur u");
        return query.getResultList();
    }

    public Utilisateur findOne(Long id) {
        return entityManager.find(Utilisateur.class, id);
    }

    public List<Utilisateur> findByDesignation(String des) {
        return null;
    }

}
