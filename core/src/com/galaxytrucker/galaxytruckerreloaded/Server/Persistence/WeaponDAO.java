package com.galaxytrucker.galaxytruckerreloaded.Server.Persistence;

import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.DuplicateWeaponException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.WeaponNotFoundException;

import java.io.Serializable;


/**
 * This class manages weapons in the database
 */
public class WeaponDAO extends ObjectDAO<Weapon> implements Serializable {

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

    /** Get a weapon using its id
     * @param id - the weapons id
     * @return the weapon with a matching id
     * @throws WeaponNotFoundException if the weapon cannot be found */
    public Weapon getById(int id) throws WeaponNotFoundException{
        try {
            Weapon w = null;
            entityManager.getTransaction().begin();
            w = entityManager.createNamedQuery("Weapon.getById",Weapon.class).setParameter("id",id).getSingleResult();
            entityManager.getTransaction().commit();
            if (w==null){
                throw new NullPointerException();
            }
            return w;
        }
        catch (Exception e){
            e.printStackTrace();
            throw new WeaponNotFoundException();
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
