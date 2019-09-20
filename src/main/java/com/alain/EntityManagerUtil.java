package com.alain;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Arrays;

/**
 * Classe d'accès à l'unité de persistence
 * création de la base si demandé
 * voir resources/META-INF/persistence.xml pour configuration
 */
public class EntityManagerUtil{

    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;
    private static final Logger logger = LogManager.getLogger("EntityManagerUtil");

    static {
        try {
            logger.info("Création entityManager");
            entityManagerFactory = Persistence.createEntityManagerFactory("DB_ESCALADE");
            entityManager = entityManagerFactory.createEntityManager();
        }catch (Exception e){
            e.getMessage();
            e.printStackTrace();
            logger.error("Impossible de créer l'entityManager" + e.getMessage() + " / " + Arrays.toString(e.getStackTrace()));
        }
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public static EntityManager getEntityManager() {
        return entityManager;
    }
}
