package com.galaxytrucker.galaxytruckerreloaded.Server.Persistence;

import com.j256.ormlite.support.ConnectionSource;

/** Template for DAOs */
public abstract class ObjectDAO<T> {

    /**
     * Database connection
     */
    private ConnectionSource source;

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
