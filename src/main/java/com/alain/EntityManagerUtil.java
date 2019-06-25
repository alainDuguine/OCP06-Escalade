package com.alain;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public abstract class EntityManagerUtil{

    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;

    public EntityManagerUtil() {
        entityManagerFactory = Persistence.createEntityManagerFactory( "DB_ESCALADE" );
        entityManager = entityManagerFactory.createEntityManager();
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }
}
