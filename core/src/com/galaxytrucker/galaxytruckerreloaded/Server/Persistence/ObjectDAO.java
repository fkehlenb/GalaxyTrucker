package com.galaxytrucker.galaxytruckerreloaded.Server.Persistence;

import com.galaxytrucker.galaxytruckerreloaded.Server.Database.Database;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/** Template for DAOs */
public abstract class ObjectDAO<T> {

    /** EntityManager */
    @PersistenceContext(name = "database")
    public EntityManager entityManager = Database.getEntityManager();

    /**
     * Save the object to the database
     *
     * @param o - the object to save
     */
    public abstract void persist(T o) throws Exception;

    /**
     * Remove an object from the database
     *
     * @param o - the object to remove
     */
    public abstract void remove(T o) throws Exception;
}
