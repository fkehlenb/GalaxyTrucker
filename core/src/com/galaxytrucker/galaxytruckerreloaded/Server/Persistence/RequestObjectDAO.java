package com.galaxytrucker.galaxytruckerreloaded.Server.Persistence;

import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.DuplicateRequestObjectException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.RequestObjectNotFoundException;
import com.galaxytrucker.galaxytruckerreloaded.Server.RequestObject;

import java.io.Serializable;

/** Used for retracing game steps */
public class RequestObjectDAO extends ObjectDAO<RequestObject> implements Serializable {

    /** Instance */
    private static RequestObjectDAO instance = null;

    /** Get the DAO instance */
    public static RequestObjectDAO getInstance(){
        if (instance == null){
            instance = new RequestObjectDAO();
        }
        return instance;
    }

    /** Add a new request object to the database
     * @param o - the request object to add
     * @throws DuplicateRequestObjectException if the request object already exists in the database */
    @Override
    public void persist(RequestObject o) throws DuplicateRequestObjectException {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(o);
            entityManager.getTransaction().commit();
        }
        catch (Exception e){
            e.printStackTrace();
            throw new DuplicateRequestObjectException();
        }
    }

    /** Remove an existing request object from the database
     * @param o - the request object to remove
     * @throws RequestObjectNotFoundException if the request object cannot be found in the database */
    @Override
    public void remove(RequestObject o) throws RequestObjectNotFoundException {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(o);
            entityManager.getTransaction().commit();
        }
        catch (Exception e){
            e.printStackTrace();
            throw new RequestObjectNotFoundException();
        }
    }
}
