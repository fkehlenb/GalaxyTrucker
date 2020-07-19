package com.galaxytrucker.galaxytruckerreloaded.Server.Database;

import lombok.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/** Creates the database and entity manager */
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Database {

    /**
     * Entity manager factory
     */
    private static final EntityManagerFactory entityManagerFactory;

    /**
     * Persistence unit name
     */
    private static final String persistenceUnit = "database";

    static {
        entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnit);
    }

    /**
     * Get the current entityManager
     *
     * @return the entityManager
     */
    public static EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }
}
