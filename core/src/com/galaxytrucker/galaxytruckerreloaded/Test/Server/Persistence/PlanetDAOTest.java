package com.galaxytrucker.galaxytruckerreloaded.Test.Server.Persistence;

import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Planet;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.PlanetEvent;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Server.Database.Database;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.DuplicatePlanetException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.DuplicateTraderException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.PlanetNotFoundException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.PlanetDAO;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.UUID;

/** Test the planet database access object */
public class PlanetDAOTest {

    /** Entity manager */
    private EntityManager entityManager = Database.getEntityManager();

    /** Planet DAO */
    private PlanetDAO planetDAO = new PlanetDAO();

    /** Test adding a new planet to the database */
    @Test
    public void testPersist(){
        Planet planet = new Planet();
        planet.setId(UUID.randomUUID().hashCode());
        planet.setName("testplanet");
        try {
            planetDAO.persist(planet);
            entityManager.getTransaction().begin();
            Assert.assertEquals(planet.getName(),entityManager.find(Planet.class,planet.getId()).getName());
            entityManager.getTransaction().commit();
        }
        catch (Exception e){
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * test persisting an existing planet
     */
    @Test
    public void testPersistExisting() {
        Planet planet = new Planet();
        planet.setId(1);
        planet.setName("testplanet5");
        try {
            planetDAO.persist(planet);
        }
        catch(DuplicatePlanetException e) {
            Assert.fail();
        }

        Planet planet1 = new Planet();
        planet.setId(1);
        planet.setName("testplanet6");

        try {
            planetDAO.persist(planet1);
        }
        catch(DuplicatePlanetException e) {
            entityManager.getTransaction().begin();
            Planet planet2 = entityManager.find(Planet.class, planet1.getId());
            entityManager.getTransaction().commit();
            Assert.assertEquals("testplanet5", planet2.getName());
        }
    }

    /** Test editing a planet in the database */
    @Test
    public void testEdit(){
        Planet planet = new Planet();
        planet.setId(UUID.randomUUID().hashCode());
        planet.setName("testplanet1");
        planet.setEvent(PlanetEvent.SHOP);
        try {
            planetDAO.persist(planet);
            planet.setEvent(PlanetEvent.SHOP);
            planetDAO.update(planet);
            entityManager.getTransaction().begin();
            Assert.assertEquals(PlanetEvent.SHOP,entityManager.find(Planet.class,planet.getId()).getEvent());
            entityManager.getTransaction().commit();
        }
        catch (Exception e){
            e.printStackTrace();
           Assert.fail();
        }
    }

    /**
     * test editing a non existing planet
     */
    @Test(expected = PlanetNotFoundException.class)
    public void testEditNonExisting() throws PlanetNotFoundException{
        Planet planet = new Planet();
        planet.setId(UUID.randomUUID().hashCode());
        planetDAO.update(planet);
    }

    /** Test removing a planet from the database */
    @Test
    public void testRemove(){
        Planet planet = new Planet();
        planet.setId(UUID.randomUUID().hashCode());
        planet.setName("testname2");
        try {
            planetDAO.persist(planet);
            planetDAO.remove(planet);
            entityManager.getTransaction().begin();
            Assert.assertNull(entityManager.find(Planet.class,planet.getId()));
            entityManager.getTransaction().commit();
        }
        catch (Exception e){
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * test removing non existing planet
     */
    @Test(expected = PlanetNotFoundException.class)
    public void testRemoveNonExisting() throws PlanetNotFoundException{
        Planet planet = new Planet();
        planet.setId(UUID.randomUUID().hashCode());
        planetDAO.remove(planet);
    }
}
