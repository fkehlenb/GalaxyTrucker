package com.galaxytrucker.galaxytruckerreloaded.Test.Server.Persistence;

import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.Server.Database.Database;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.RoomNotFoundException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.DuplicateRoomException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.RoomDAO;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.UUID;

public class RoomDAOTest {

    private EntityManager entityManager = Database.getEntityManager();

    private com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.RoomDAO RoomDAO = new RoomDAO();

    /**
     * test persisting an existing
     */
    @Test
    public void testPersist() {
        Room testObject = new Room();
        testObject.setId(UUID.randomUUID().hashCode());
        testObject.setOxygen(5);
        try {
            RoomDAO.persist(testObject);
            entityManager.getTransaction().begin();
            Room testObject1 = entityManager.find(Room.class, testObject.getId());
            entityManager.getTransaction().commit();
            Assert.assertEquals(5, testObject1.getOxygen());
        }
        catch(DuplicateRoomException e) {
            Assert.fail();
        }
    }

    /**
     * test persisting an already existing 
     */
    @Test
    public void testPersistDuplicate() {
        Room testObject = new Room();
        testObject.setId(1);
        testObject.setOxygen(5);
        try {
            RoomDAO.persist(testObject);
        }
        catch (DuplicateRoomException e) {
            Assert.fail();
        }
        Room testObject1 = new Room();
        testObject1.setId(1);
        testObject1.setOxygen(7);
        try {
            RoomDAO.persist(testObject1);
        }
        catch(DuplicateRoomException e) {
            entityManager.getTransaction().begin();
            Room testObject2 = entityManager.find(Room.class, testObject1.getId());
            entityManager.getTransaction().commit();
            Assert.assertEquals(5, testObject2.getOxygen());
        }
    }

    /**
     * test updating an exisitng 
     */
    @Test
    public void testUpdate() {
        Room testObject = new Room();
        testObject.setId(UUID.randomUUID().hashCode());
        testObject.setOxygen(5);
        try {
            RoomDAO.persist(testObject);
        }
        catch(DuplicateRoomException e) {
            Assert.fail();
        }
        testObject.setOxygen(6);
        try {
            RoomDAO.update(testObject);
            entityManager.getTransaction().begin();
            Room testObject1 = entityManager.find(Room.class, testObject.getId());
            entityManager.getTransaction().commit();
            Assert.assertEquals(6, testObject1.getOxygen());
        }
        catch(RoomNotFoundException e) {
            Assert.fail();
        }
    }

    /**
     * test updating a non existing
     */
    @Test(expected = RoomNotFoundException.class)
    public void testUpdateNonExisting() throws RoomNotFoundException {
        Room testObject = new Room();
        testObject.setId(UUID.randomUUID().hashCode());
        testObject.setOxygen(5);
        RoomDAO.update(testObject);
    }

    /**
     * test removing an existing
     */
    @Test
    public void testRemove() {
        Room testObject = new Room();
        testObject.setId(UUID.randomUUID().hashCode());
        testObject.setOxygen(5);
        try {
            RoomDAO.persist(testObject);
            RoomDAO.remove(testObject);
            entityManager.getTransaction().begin();
            Room testObject1 = entityManager.find(Room.class, testObject.getId());
            entityManager.getTransaction().commit();
            Assert.assertNull(testObject1);
        }
        catch (DuplicateRoomException | RoomNotFoundException e) {
            Assert.fail();
        }
    }

    /**
     * test removing a non existing
     */
    @Test(expected = RoomNotFoundException.class)
    public void testRemoveNonExisting() throws RoomNotFoundException{
        Room testObject = new Room();
        testObject.setId(UUID.randomUUID().hashCode());
        testObject.setOxygen(5);
        RoomDAO.remove(testObject);
    }
}
