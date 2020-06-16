package com.galaxytrucker.galaxytruckerreloaded.Test.Server.Persistence;

import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Planet;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.PlanetEvent;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Server.Database.Database;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.PlanetDAO;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.ArrayList;

/** Test the planet database access object */
public class PlanetDAOTest {

    /** Entity manager */
    private EntityManager entityManager = Database.getEntityManager();

    /** Planet DAO */
    private PlanetDAO planetDAO = new PlanetDAO();

    /** Test adding a new planet to the database */
    @Test
    public void testPersist(){
        Planet planet = new Planet(TraderDAOTest.planetNameGenerator(),0,0, PlanetEvent.VOID,new ArrayList<Ship>());
        try {
            planetDAO.persist(planet);
            entityManager.getTransaction().begin();
            Assert.assertEquals(planet.getName(),entityManager.find(Planet.class,planet.getName()).getName());
            entityManager.getTransaction().commit();
        }
        catch (Exception e){
            e.printStackTrace();
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /** Test editing a planet in the database */
    @Test
    public void testEdit(){
        Planet planet = new Planet(TraderDAOTest.planetNameGenerator(),0,0, PlanetEvent.VOID,new ArrayList<Ship>());
        try {
            planetDAO.persist(planet);
            planet.setEvent(PlanetEvent.SHOP);
            planetDAO.update(planet);
            entityManager.getTransaction().begin();
            Assert.assertEquals(PlanetEvent.SHOP,entityManager.find(Planet.class,planet.getName()).getEvent());
            entityManager.getTransaction().commit();
        }
        catch (Exception e){
            e.printStackTrace();
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /** Test removing a planet from the database */
    @Test
    public void testRemove(){
        Planet planet = new Planet(TraderDAOTest.planetNameGenerator(),0,0, PlanetEvent.VOID,new ArrayList<Ship>());
        try {
            planetDAO.persist(planet);
            planetDAO.remove(planet);
            entityManager.getTransaction().begin();
            Assert.assertNull(entityManager.find(Planet.class,planet.getName()));
            entityManager.getTransaction().commit();
        }
        catch (Exception e){
            e.printStackTrace();
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
