package com.galaxytrucker.galaxytruckerreloaded.Server.Persistence;

import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Trader;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.DuplicateTraderException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.TraderNotFoundException;

/**
 * This class handles trader objects in the database
 */
public class TraderDAO extends ObjectDAO<Trader> {

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
            throw new DuplicateTraderException();
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
