package com.galaxytrucker.galaxytruckerreloaded.Test.Server.Persistence;

import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Overworld;
import com.galaxytrucker.galaxytruckerreloaded.Server.Database.Database;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.DuplicateOverworldException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.DuplicatePlanetException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.OverworldNotFoundException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.OverworldDAO;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.UUID;

/** Test the overworld database access object */
public class OverworldDAOTest {

    /** EntityManager */
    private EntityManager entityManager = Database.getEntityManager();

    /** OverworldDAO */
    private OverworldDAO overworldDAO = new OverworldDAO();

    /** Test adding a new overworld to the database */
    @Test
    public void testPersist() {
        Overworld overworld = new Overworld();
        overworld.setId(UUID.randomUUID().hashCode());
        overworld.setSeed(5);
        try {
            overworldDAO.persist(overworld);
            entityManager.getTransaction().begin();
            Overworld o2 = entityManager.find(Overworld.class,overworld.getId());
            entityManager.getTransaction().commit();
            Assert.assertEquals(overworld.getSeed(),o2.getSeed());
        }
        catch (Exception e){
            Assert.fail();
        }
    }

    /**
     * test persisting existing overworld
     */
    @Test
    public void testPersistExisting() {
        Overworld overworld = new Overworld();
        overworld.setId(1);
        overworld.setSeed(5);
        try {
            overworldDAO.persist(overworld);
        }
        catch(DuplicateOverworldException e) {
            Assert.fail();
        }

        Overworld overworld1 = new Overworld();
        overworld1.setId(1);
        overworld1.setSeed(6);

        try {
            overworldDAO.persist(overworld1);
        }
        catch(DuplicateOverworldException e) {
            entityManager.getTransaction().begin();
            Overworld overworld2 = entityManager.find(Overworld.class, overworld1.getId());
            entityManager.getTransaction().commit();
            Assert.assertEquals(5, overworld2.getSeed());
        }
    }

    /** Test removing an existing overworld from the database */
    @Test
    public void testRemove(){
        Overworld overworld = new Overworld();
        overworld.setId(UUID.randomUUID().hashCode());
        try {
            overworldDAO.persist(overworld);
            overworldDAO.remove(overworld);
            entityManager.getTransaction().begin();
            Assert.assertNull(entityManager.find(Overworld.class,overworld.getId()));
            entityManager.getTransaction().commit();
        }
        catch (Exception e){
            Assert.fail();
        }
    }

    /**
     * test removing a non existing overworld
     */
    @Test(expected = OverworldNotFoundException.class)
    public void testRemoveNonExisting() throws OverworldNotFoundException{
        Overworld overworld = new Overworld();
        overworld.setId(UUID.randomUUID().hashCode());
        overworldDAO.remove(overworld);
    }

    /**
     * test updating an existing overworld
     */
    @Test
    public void testUpdate() {
        Overworld overworld = new Overworld();
        overworld.setId(UUID.randomUUID().hashCode());
        overworld.setSeed(5);
        try {
            overworldDAO.persist(overworld);
        }
        catch(DuplicateOverworldException e) {
            Assert.fail();
        }

        overworld.setSeed(6);
        try {
            overworldDAO.update(overworld);
            entityManager.getTransaction().begin();
            Overworld overworld1 = entityManager.find(Overworld.class, overworld.getId());
            entityManager.getTransaction().commit();
            Assert.assertEquals(6, overworld1.getSeed());
        }
        catch(OverworldNotFoundException e) {
            Assert.fail();
        }
    }

    /**
     * test editing a non existing overworld
     */
    @Test(expected = OverworldNotFoundException.class)
    public void testEditNonExisting() throws OverworldNotFoundException{
        Overworld overworld = new Overworld();
        overworld.setId(UUID.randomUUID().hashCode());
        overworldDAO.update(overworld);
    }
}
