package com.galaxytrucker.galaxytruckerreloaded.Test.Server.Persistence;

import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Overworld;
import com.galaxytrucker.galaxytruckerreloaded.Server.Database.Database;
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
        try {
            Overworld overworld = new Overworld(UUID.randomUUID().hashCode(), UUID.randomUUID().hashCode(), "test");
            overworldDAO.persist(overworld);
            entityManager.getTransaction().begin();
            Overworld o2 = entityManager.find(Overworld.class,overworld.getId());
            entityManager.getTransaction().commit();
            Assert.assertEquals(overworld.getSeed(),o2.getSeed());
        }
        catch (Exception e){
            e.printStackTrace();
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /** Test removing an existing overworld from the database */
    @Test
    public void testRemove(){
        try {
            Overworld overworld = new Overworld(UUID.randomUUID().hashCode(),UUID.randomUUID().hashCode(),"test");
            overworldDAO.persist(overworld);
            overworldDAO.remove(overworld);
            entityManager.getTransaction().begin();
            Assert.assertNull(entityManager.find(Overworld.class,overworld.getId()));
            entityManager.getTransaction().commit();
        }
        catch (Exception e){
            e.printStackTrace();
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
