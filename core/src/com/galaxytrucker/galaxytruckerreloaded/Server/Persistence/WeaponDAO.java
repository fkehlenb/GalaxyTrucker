package com.galaxytrucker.galaxytruckerreloaded.Server.Persistence;

import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.DuplicateWeaponException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.WeaponNotFoundException;


/**
 * This class manages weapons in the database
 */
public class WeaponDAO extends ObjectDAO<Weapon> {

    /** Instance */
    private static WeaponDAO instance = null;

    /** Get the DAO instance */
    public static WeaponDAO getInstance(){
        if (instance == null){
            instance = new WeaponDAO();
        }
        return instance;
    }

    /**
     * Add a new weapon to the database
     *
     * @param w - the weapon to add
     * @throws DuplicateWeaponException if the weapon already exists in the database
     */
    @Override
    public void persist(Weapon w) throws DuplicateWeaponException {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(w);
            entityManager.getTransaction().commit();
        }
        catch (Exception e){
            e.printStackTrace();
            throw new DuplicateWeaponException();
        }
    }

    /**
     * Edit an existing weapon in the database
     *
     * @param w - the weapon to edit
     * @throws WeaponNotFoundException if the weapon doesn't exist in the database
     */
    public void update(Weapon w) throws WeaponNotFoundException {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(w);
            entityManager.getTransaction().commit();
        }
        catch (Exception e){
            e.printStackTrace();
            throw new WeaponNotFoundException();
        }
    }

    /**
     * Remove an existing weapon from the database
     *
     * @param w - the weapon to remove
     * @throws WeaponNotFoundException if the weapon cannot be found in the database
     */
    @Override
    public void remove(Weapon w) throws WeaponNotFoundException {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(w);
            entityManager.getTransaction().commit();
        }
        catch (Exception e){
            e.printStackTrace();
            throw new WeaponNotFoundException();
        }
    }

}
