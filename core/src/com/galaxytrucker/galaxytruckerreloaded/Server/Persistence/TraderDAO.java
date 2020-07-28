package com.galaxytrucker.galaxytruckerreloaded.Server.Persistence;

import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Trader;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.DuplicateTraderException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.TraderNotFoundException;

import java.io.Serializable;

/**
 * This class handles trader objects in the database
 */
public class TraderDAO extends ObjectDAO<Trader> {

    /** Instance */
    private static TraderDAO instance = null;

    /** Get the DAO instance */
    public static TraderDAO getInstance(){
        if (instance == null){
            instance = new TraderDAO();
        }
        return instance;
    }

    /**
     * Add a new trader to the database
     *
     * @param t - the trader to add
     * @throws DuplicateTraderException if the trader already exists in the database
     */
    @Override
    public void persist(Trader t) throws DuplicateTraderException {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(t);
            entityManager.getTransaction().commit();
        }
        catch (Exception e){
            e.printStackTrace();
            entityManager.getTransaction().rollback();
            throw new DuplicateTraderException();
        }
    }

    /** Get a trader using his id
     * @param id - the trader id
     * @return the trader with a matching id
     * @throws TraderNotFoundException if the trader cannot be found */
    public Trader getById(int id) throws TraderNotFoundException{
        try {
            Trader t = null;
            entityManager.getTransaction().begin();
            entityManager.clear();
            t = entityManager.createNamedQuery("Trader.getById",Trader.class).setParameter("id",id).getSingleResult();
            entityManager.getTransaction().commit();
            if (t==null){
                throw new NullPointerException();
            }
            return t;
        }
        catch (Exception e){
            e.printStackTrace();
            entityManager.getTransaction().rollback();
            throw new TraderNotFoundException();
        }
    }

    /**
     * Edit an existing trader in the database
     *
     * @param t - the trader to edit
     * @throws TraderNotFoundException if the trader cannot be found in the database
     */
    public void update(Trader t) throws TraderNotFoundException {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(t);
            entityManager.getTransaction().commit();
        }
        catch (Exception e){
            e.printStackTrace();
            throw new TraderNotFoundException();
        }
    }

    /**
     * Remove an existing trader from the database
     *
     * @param t - the trader to remove
     * @throws TraderNotFoundException if the trader cannot be found in the database
     */
    @Override
    public void remove(Trader t) throws TraderNotFoundException {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(t);
            entityManager.getTransaction().commit();
        }
        catch (Exception e){
            e.printStackTrace();
            throw new TraderNotFoundException();
        }
    }
}
