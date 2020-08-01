package com.galaxytrucker.galaxytruckerreloaded.Test.Server.Persistence;

import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Server.Database.Database;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.DuplicateShipException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.ShipNotFoundException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.ShipDAO;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.UUID;

public class ShipDAOTest {

    private EntityManager entityManager = Database.getEntityManager();

    private com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.ShipDAO ShipDAO = new ShipDAO();

    /**
     * test persisting an existing
     */
    @Test
    public void testPersist() {
        Ship testObject = new Ship();
        testObject.setId(UUID.randomUUID().hashCode());
        testObject.setCoins(5);
        try {
            ShipDAO.persist(testObject);
            entityManager.getTransaction().begin();
            Ship testObject1 = entityManager.find(Ship.class, testObject.getId());
            entityManager.getTransaction().commit();
            Assert.assertEquals(5, testObject1.getCoins());
        }
        catch(DuplicateShipException e) {
            Assert.fail();
        }
    }

    /**
     * test persisting an already existing 
     */
    @Test
    public void testPersistDuplicate() {
        Ship testObject = new Ship();
        testObject.setId(1);
        testObject.setCoins(5);
        try {
            ShipDAO.persist(testObject);
        }
        catch (DuplicateShipException e) {
            Assert.fail();
        }
        Ship testObject1 = new Ship();
        testObject1.setId(1);
        testObject1.setCoins(7);
        try {
            ShipDAO.persist(testObject1);
        }
        catch(DuplicateShipException e) {
            entityManager.getTransaction().begin();
            Ship testObject2 = entityManager.find(Ship.class, testObject1.getId());
            entityManager.getTransaction().commit();
            Assert.assertEquals(5, testObject2.getCoins());
        }
    }

    /**
     * test updating an exisitng 
     */
    @Test
    public void testUpdate() {
        Ship testObject = new Ship();
        testObject.setId(UUID.randomUUID().hashCode());
        testObject.setCoins(5);
        try {
            ShipDAO.persist(testObject);
        }
        catch(DuplicateShipException e) {
            Assert.fail();
        }
        testObject.setCoins(6);
        try {
            ShipDAO.update(testObject);
            entityManager.getTransaction().begin();
            Ship testObject1 = entityManager.find(Ship.class, testObject.getId());
            entityManager.getTransaction().commit();
            Assert.assertEquals(6, testObject1.getCoins());
        }
        catch(ShipNotFoundException e) {
            Assert.fail();
        }
    }

    /**
     * test updating a non existing
     */
    @Test(expected = ShipNotFoundException.class)
    public void testUpdateNonExisting() throws ShipNotFoundException {
        Ship testObject = new Ship();
        testObject.setId(UUID.randomUUID().hashCode());
        testObject.setCoins(5);
        ShipDAO.update(testObject);
    }

    /**
     * test removing an existing
     */
    @Test
    public void testRemove() {
        Ship testObject = new Ship();
        testObject.setId(UUID.randomUUID().hashCode());
        testObject.setCoins(5);
        try {
            ShipDAO.persist(testObject);
            ShipDAO.remove(testObject);
            entityManager.getTransaction().begin();
            Ship testObject1 = entityManager.find(Ship.class, testObject.getId());
            entityManager.getTransaction().commit();
            Assert.assertNull(testObject1);
        }
        catch (DuplicateShipException | ShipNotFoundException e) {
            Assert.fail();
        }
    }

    /**
     * test removing a non existing
     */
    @Test(expected = ShipNotFoundException.class)
    public void testRemoveNonExisting() throws ShipNotFoundException{
        Ship testObject = new Ship();
        testObject.setId(UUID.randomUUID().hashCode());
        testObject.setCoins(5);
        ShipDAO.remove(testObject);
    }
}
