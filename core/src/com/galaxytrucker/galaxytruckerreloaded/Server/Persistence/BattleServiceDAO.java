package com.galaxytrucker.galaxytruckerreloaded.Server.Persistence;

import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.BattleServiceNotFoundException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.DuplicateBattleServiceException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Services.BattleService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** Manages battle service objects in the database */
public class BattleServiceDAO extends ObjectDAO<BattleService> implements Serializable {

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

    /** Get all battle services from the database
     * @return a list of all battle services or an empty arraylist */
    public List<BattleService> getAll(){
        try {
            List<BattleService> battleServices;
            entityManager.getTransaction().begin();
            battleServices = entityManager.createNamedQuery("BattleService.fetchAll",BattleService.class).getResultList();
            entityManager.getTransaction().commit();
            if (!battleServices.isEmpty()){
                return new ArrayList<>(battleServices);
            }
            return new ArrayList<>();
        }
        catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
