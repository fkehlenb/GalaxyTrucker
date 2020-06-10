package com.galaxytrucker.galaxytruckerreloaded.Test.Server.Persistence;


import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import com.galaxytrucker.galaxytruckerreloaded.Server.Database.Database;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.CrewDAO;

import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.UUID;

public class CrewDAOTest {


    /**
     * EntityManager
     */
    private EntityManager entityManager = Database.getEntityManager();

    /**
     * CrewDAO
     */
    private CrewDAO crewDAO = new CrewDAO(entityManager);


    /**
     * Test persisting a crew member to the database
     */
    @Test
    public void testPersist() {
        Crew c = new Crew(UUID.randomUUID().hashCode(), "ahmad", 100, 200);
        try {
            crewDAO.persist(c);
        } catch (Exception e) {
            e.printStackTrace();
        }
        entityManager.getTransaction().begin();
        Crew c2 = entityManager.find(Crew.class, c.getId());
        entityManager.getTransaction().commit();
        Assert.assertEquals(c2.getName(), c.getName());
    }

    /**
     * Test updating a crew member in the database
     */
    @Test
    public void testUpdate() {
        Crew c = new Crew(UUID.randomUUID().hashCode(), "ahmad", 100, 200);
        try {
            crewDAO.persist(c);
        } catch (Exception e) {
            e.printStackTrace();
        }
        c.setName("da3esh");
        try {
            crewDAO.update(c);
        } catch (Exception e) {
            e.printStackTrace();
        }
        entityManager.getTransaction().begin();
        Crew c2 = entityManager.find(Crew.class, c.getId());
        entityManager.getTransaction().commit();
        Assert.assertEquals(c2.getName(), c.getName());
    }

    /**
     * Test removing a crew member from the database
     */
    @Test
    public void testRemove() throws IllegalArgumentException {
        Crew c = new Crew(UUID.randomUUID().hashCode(), "ahmad", 100, 200);
        try {
            crewDAO.persist(c);
            crewDAO.remove(c);
        } catch (Exception e) {
            e.printStackTrace();
        }
        entityManager.getTransaction().begin();
        try {
            entityManager.find(Crew.class, c.getId());
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
        entityManager.getTransaction().commit();
    }

}
