package com.alain;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerUtil{

    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;

    static {
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("DB_ESCALADE");
            entityManager = entityManagerFactory.createEntityManager();
        }catch (Exception e){
            e.getMessage();
            e.printStackTrace();
        }
    }


    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public static EntityManager getEntityManager() {
        return entityManager;
    }
}
