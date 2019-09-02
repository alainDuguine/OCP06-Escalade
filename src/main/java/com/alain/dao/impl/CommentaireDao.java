package com.alain.dao.impl;

import com.alain.EntityManagerUtil;
import com.alain.dao.entities.Commentaire;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class CommentaireDao {

    private EntityManager entityManager = EntityManagerUtil.getEntityManager();

    public boolean delete(Long id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Commentaire commentaire = entityManager.find(Commentaire.class, id);
            entityManager.remove(commentaire);
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
}
