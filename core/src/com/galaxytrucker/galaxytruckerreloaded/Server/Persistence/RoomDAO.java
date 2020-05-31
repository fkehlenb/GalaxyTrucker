package com.galaxytrucker.galaxytruckerreloaded.Server.Persistence;

import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.DuplicateRoomException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.RoomNotFoundException;
import com.j256.ormlite.dao.Dao;

/** This class manages room objects in the database */
public class RoomDAO extends ObjectDAO<Room> {

    /** RoomDAO */
    private Dao<Room,String> roomDAO;

    /**
     * Add a new room to the database
     *
     * @param r - the room to add
     * @throws DuplicateRoomException if the system already exists in the database
     */
    @Override
    public void persist(Room r) throws DuplicateRoomException {

    }

    /**
     * Edit an existing room in the database
     *
     * @param r - the room to edit
     * @throws RoomNotFoundException if the room cannot be found in the database
     */
    public void update(Room r) throws RoomNotFoundException {

    }

    /**
     * Remove an existing room from the database
     *
     * @param r - the room to remove
     * @throws RoomNotFoundException if the room cannot be found in the database
     */
    @Override
    public void remove(Room r) throws RoomNotFoundException {

    }
}
