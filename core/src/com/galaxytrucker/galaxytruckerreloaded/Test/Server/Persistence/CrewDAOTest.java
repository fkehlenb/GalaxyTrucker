package com.galaxytrucker.galaxytruckerreloaded.Test.Server.Persistence;

import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import com.galaxytrucker.galaxytruckerreloaded.Server.Database.Database;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.CrewNotFoundException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.DuplicateCrewException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.CrewDAO;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.UUID;

public class CrewDAOTest {

    private EntityManager entityManager = Database.getEntityManager();

    private CrewDAO CrewDAO = new CrewDAO();

    /**
     * test persisting an existing
     */
    @Test
    public void testPersist() {
        Crew crew = new Crew();
        crew.setId(UUID.randomUUID().hashCode());
        crew.setPrice(5);
        try {
            CrewDAO.persist(crew);
            entityManager.getTransaction().begin();
            Crew crew1 = entityManager.find(Crew.class, crew.getId());
            entityManager.getTransaction().commit();
            Assert.assertEquals(5, crew1.getPrice());
        }
        catch(DuplicateCrewException e) {
            Assert.fail();
        }
    }

    /**
     * test persisting an already existing 
     */
    @Test
    public void testPersistDuplicate() {
        Crew crew = new Crew();
        crew.setId(1);
        crew.setPrice(5);
        try {
            CrewDAO.persist(crew);
        }
        catch (DuplicateCrewException e) {
            Assert.fail();
        }
        Crew crew1 = new Crew();
        crew1.setId(1);
        crew1.setPrice(7);
        try {
            CrewDAO.persist(crew1);
        }
        catch(DuplicateCrewException e) {
            entityManager.getTransaction().begin();
            Crew crew2 = entityManager.find(Crew.class, crew1.getId());
            entityManager.getTransaction().commit();
            Assert.assertEquals(5, crew2.getPrice());
        }
    }

    /**
     * test updating an exisitng 
     */
    @Test
    public void testUpdate() {
        Crew crew = new Crew();
        crew.setId(UUID.randomUUID().hashCode());
        crew.setPrice(5);
        try {
            CrewDAO.persist(crew);
        }
        catch(DuplicateCrewException e) {
            Assert.fail();
        }
        crew.setPrice(6);
        try {
            CrewDAO.update(crew);
            entityManager.getTransaction().begin();
            Crew crew1 = entityManager.find(Crew.class, crew.getId());
            entityManager.getTransaction().commit();
            Assert.assertEquals(6, crew1.getPrice());
        }
        catch(CrewNotFoundException e) {
            Assert.fail();
        }
    }

    /**
     * test updating a non existing
     */
    @Test(expected = CrewNotFoundException.class)
    public void testUpdateNonExisting() throws CrewNotFoundException {
        Crew crew = new Crew();
        crew.setId(UUID.randomUUID().hashCode());
        crew.setPrice(5);
        CrewDAO.update(crew);
    }

    /**
     * test removing an existing
     */
    @Test
    public void testRemove() {
        Crew crew = new Crew();
        crew.setId(UUID.randomUUID().hashCode());
        crew.setPrice(5);
        try {
            CrewDAO.persist(crew);
            CrewDAO.remove(crew);
            entityManager.getTransaction().begin();
            Crew crew1 = entityManager.find(Crew.class, crew.getId());
            entityManager.getTransaction().commit();
            Assert.assertNull(crew1);
        }
        catch (DuplicateCrewException | CrewNotFoundException e) {
            Assert.fail();
        }
    }

    /**
     * test removing a non existing
     */
    @Test(expected = CrewNotFoundException.class)
    public void testRemoveNonExisting() throws CrewNotFoundException{
        Crew crew = new Crew();
        crew.setId(UUID.randomUUID().hashCode());
        crew.setPrice(5);
        CrewDAO.remove(crew);
    }
}
