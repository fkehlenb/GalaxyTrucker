package com.galaxytrucker.galaxytruckerreloaded.Server.Persistence;

/** Template for DAOs */
public abstract class ObjectDAO<T> {

    /**
     * Save the object to the database
     *
     * @param o - the object to save
     */
    public abstract void persist(T o);

    /**
     * Remove an object from the database
     *
     * @param o - the object to remove
     */
    public abstract void remove(T o);
}
