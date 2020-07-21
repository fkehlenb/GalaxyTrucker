package com.galaxytrucker.galaxytruckerreloaded.Server.Persistence;

import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.DuplicateRoomException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.RoomNotFoundException;


/** This class manages room objects in the database */
public class RoomDAO extends ObjectDAO<Room> {

    /** Instance */
    private static RoomDAO instance = null;

    /** Get the DAO instance */
    public static RoomDAO getInstance(){
        if (instance == null){
            instance = new RoomDAO();
        }
        return instance;
    }

    /**
     * Add a new room to the database
     *
     * @param r - the room to add
     * @throws DuplicateRoomException if the system already exists in the database
     */
    @Override
    public void persist(Room r) throws DuplicateRoomException {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(r);
            entityManager.getTransaction().commit();
        }
        catch (Exception e){
            e.printStackTrace();
            throw new DuplicateRoomException();
        }
    }

    /** Grab a room from the database using its id
     * @param id - the id of the room to fetch
     * @return the room
     * @throws RoomNotFoundException if the room cannot be found in the database */
    public Room getById(int id) throws RoomNotFoundException{
        try {
            Room r = null;
            entityManager.getTransaction().begin();
            r = entityManager.createNamedQuery("Room.getById",Room.class).setParameter("id",id).getSingleResult();
            entityManager.getTransaction().commit();
            if (r==null){
                throw new NullPointerException();
            }
            return r;
        }
        catch (Exception e){
            e.printStackTrace();
            throw new RoomNotFoundException();
        }
    }

    /**
     * Edit an existing room in the database
     *
     * @param r - the room to edit
     * @throws RoomNotFoundException if the room cannot be found in the database
     */
    public void update(Room r) throws RoomNotFoundException {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(r);
            entityManager.getTransaction().commit();
        }
        catch (Exception e){
            e.printStackTrace();
            throw new RoomNotFoundException();
        }
    }

    /**
     * Remove an existing room from the database
     *
     * @param r - the room to remove
     * @throws RoomNotFoundException if the room cannot be found in the database
     */
    @Override
    public void remove(Room r) throws RoomNotFoundException {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(r);
            entityManager.getTransaction().commit();
        }
        catch (Exception e){
            e.printStackTrace();
            throw new RoomNotFoundException();
        }
    }
}
