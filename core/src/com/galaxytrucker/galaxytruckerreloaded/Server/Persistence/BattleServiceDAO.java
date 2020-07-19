package com.galaxytrucker.galaxytruckerreloaded.Server.Persistence;

import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.BattleServiceNotFoundException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.DuplicateBattleServiceException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Services.BattleService;

/** Manages battle service objects in the database */
public class BattleServiceDAO extends ObjectDAO<BattleService> {

    /** Instance */
    private static BattleServiceDAO instance = null;

    /** Get instance */
    public static BattleServiceDAO getInstance(){
        if (instance == null){
            instance = new BattleServiceDAO();
        }
        return instance;
    }

    /** Add a new battle service object to the database
     * @param o - the battle service object to add
     * @throws DuplicateBattleServiceException if the object already exists in the database */
    @Override
    public void persist(BattleService o) throws DuplicateBattleServiceException {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(o);
            entityManager.getTransaction().commit();
        }
        catch (Exception e){
            e.printStackTrace();
            throw new DuplicateBattleServiceException();
        }
    }

    /** Update an existing battle service object in the database
     * @param o - the battle service object to update
     * @throws BattleServiceNotFoundException if the battle service object cannot be found in the database */
    public void update(BattleService o) throws BattleServiceNotFoundException{
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(o);
            entityManager.getTransaction().commit();
        }
        catch (Exception e){
            e.printStackTrace();
            throw new BattleServiceNotFoundException();
        }
    }

    /** Remove an existing battle service object from the database
     * @param o - the battle service object to remove
     * @throws BattleServiceNotFoundException if the battle service object cannot be found */
    @Override
    public void remove(BattleService o) throws BattleServiceNotFoundException {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(o);
            entityManager.getTransaction().commit();
        }
        catch (Exception e){
            e.printStackTrace();
            throw new BattleServiceNotFoundException();
        }
    }
}
