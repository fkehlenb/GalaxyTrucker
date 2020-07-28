package com.galaxytrucker.galaxytruckerreloaded.Server.Persistence;

import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Tile;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.DuplicateTileException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.TileNotFoundException;

import java.io.Serializable;

/** Handles tile objects in the database */
public class TileDAO extends ObjectDAO<Tile> {

    /** Instance */
    private static TileDAO instance = null;

    /** Get the DAO instance */
    public static TileDAO getInstance(){
        if (instance == null){
            instance = new TileDAO();
        }
        return instance;
    }

    /** Add a new tile to the database
     * @param t - the tile to add
     * @throws DuplicateTileException if the tile already exists in the database */
    @Override
    public void persist(Tile t) throws DuplicateTileException {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(t);
            entityManager.getTransaction().commit();
        }
        catch (Exception e){
            e.printStackTrace();
            entityManager.getTransaction().rollback();
            throw new DuplicateTileException();
        }
    }

    /** Update an existing tile in the database
     * @param t - the tile to update
     * @throws TileNotFoundException if the tile cannot be found in the database */
    public void update(Tile t) throws TileNotFoundException {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(t);
            entityManager.getTransaction().commit();
        }
        catch (Exception e){
            e.printStackTrace();
            entityManager.getTransaction().rollback();
            throw new TileNotFoundException();
        }
    }

    /** Remove a tile from the database
     * @param t - the tile to remove
     * @throws TileNotFoundException if the tile cannot be found in the database */
    @Override
    public void remove(Tile t) throws TileNotFoundException {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(t);
            entityManager.getTransaction().commit();
        }
        catch (Exception e){
            e.printStackTrace();
            entityManager.getTransaction().rollback();
            throw new TileNotFoundException();
        }
    }
}
