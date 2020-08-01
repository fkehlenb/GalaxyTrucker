package com.galaxytrucker.galaxytruckerreloaded.Test.Server.Persistence;

import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Tile;
import com.galaxytrucker.galaxytruckerreloaded.Server.Database.Database;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.DuplicateTileException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.TileNotFoundException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.TileDAO;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.UUID;

public class TileDAOTest {

    private EntityManager entityManager = Database.getEntityManager();

    private com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.TileDAO TileDAO = new TileDAO();

    /**
     * test persisting an existing
     */
    @Test
    public void testPersist() {
        Tile testObject = new Tile();
        testObject.setId(UUID.randomUUID().hashCode());
        testObject.setPosX(5);
        try {
            TileDAO.persist(testObject);
            entityManager.getTransaction().begin();
            Tile testObject1 = entityManager.find(Tile.class, testObject.getId());
            entityManager.getTransaction().commit();
            Assert.assertEquals(5, testObject1.getPosX());
        }
        catch(DuplicateTileException e) {
            Assert.fail();
        }
    }

    /**
     * test persisting an already existing 
     */
    @Test
    public void testPersistDuplicate() {
        Tile testObject = new Tile();
        testObject.setId(1);
        testObject.setPosX(5);
        try {
            TileDAO.persist(testObject);
        }
        catch (DuplicateTileException e) {
            Assert.fail();
        }
        Tile testObject1 = new Tile();
        testObject1.setId(1);
        testObject1.setPosX(7);
        try {
            TileDAO.persist(testObject1);
        }
        catch(DuplicateTileException e) {
            entityManager.getTransaction().begin();
            Tile testObject2 = entityManager.find(Tile.class, testObject1.getId());
            entityManager.getTransaction().commit();
            Assert.assertEquals(5, testObject2.getPosX());
        }
    }

    /**
     * test updating an exisitng 
     */
    @Test
    public void testUpdate() {
        Tile testObject = new Tile();
        testObject.setId(UUID.randomUUID().hashCode());
        testObject.setPosX(5);
        try {
            TileDAO.persist(testObject);
        }
        catch(DuplicateTileException e) {
            Assert.fail();
        }
        testObject.setPosX(6);
        try {
            TileDAO.update(testObject);
            entityManager.getTransaction().begin();
            Tile testObject1 = entityManager.find(Tile.class, testObject.getId());
            entityManager.getTransaction().commit();
            Assert.assertEquals(6, testObject1.getPosX());
        }
        catch(TileNotFoundException e) {
            Assert.fail();
        }
    }

    /**
     * test updating a non existing
     */
    @Test(expected = TileNotFoundException.class)
    public void testUpdateNonExisting() throws TileNotFoundException {
        Tile testObject = new Tile();
        testObject.setId(UUID.randomUUID().hashCode());
        testObject.setPosX(5);
        TileDAO.update(testObject);
    }

    /**
     * test removing an existing
     */
    @Test
    public void testRemove() {
        Tile testObject = new Tile();
        testObject.setId(UUID.randomUUID().hashCode());
        testObject.setPosX(5);
        try {
            TileDAO.persist(testObject);
            TileDAO.remove(testObject);
            entityManager.getTransaction().begin();
            Tile testObject1 = entityManager.find(Tile.class, testObject.getId());
            entityManager.getTransaction().commit();
            Assert.assertNull(testObject1);
        }
        catch (DuplicateTileException | TileNotFoundException e) {
            Assert.fail();
        }
    }

    /**
     * test removing a non existing
     */
    @Test(expected = TileNotFoundException.class)
    public void testRemoveNonExisting() throws TileNotFoundException{
        Tile testObject = new Tile();
        testObject.setId(UUID.randomUUID().hashCode());
        testObject.setPosX(5);
        TileDAO.remove(testObject);
    }
}
