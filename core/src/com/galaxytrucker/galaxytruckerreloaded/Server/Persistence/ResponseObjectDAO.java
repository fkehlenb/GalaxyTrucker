package com.galaxytrucker.galaxytruckerreloaded.Server.Persistence;

import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.DuplicateResponseObjectException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.ResponseObjectNotFoundException;
import com.galaxytrucker.galaxytruckerreloaded.Server.ResponseObject;

/** Manages response objects in the database */
public class ResponseObjectDAO extends ObjectDAO<ResponseObject> {

    /** Add a new response object to the database
     * @param o - the response object to add to the database
     * @throws DuplicateResponseObjectException if the response object already exists in the database */
    @Override
    public void persist(ResponseObject o) throws DuplicateResponseObjectException {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(o);
            entityManager.getTransaction().commit();
        }
        catch (Exception e){
            e.printStackTrace();
            throw new DuplicateResponseObjectException();
        }
    }

    /** Remove a response object from the database
     * @param o - the response object to remove
     * @throws ResponseObjectNotFoundException if the response object cannot be found in the database */
    @Override
    public void remove(ResponseObject o) throws ResponseObjectNotFoundException {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(o);
            entityManager.getTransaction().commit();
        }
        catch (Exception e){
            e.printStackTrace();
            throw new ResponseObjectNotFoundException();
        }
    }
}