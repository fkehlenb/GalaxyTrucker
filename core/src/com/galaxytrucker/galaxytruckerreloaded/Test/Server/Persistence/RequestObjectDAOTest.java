package com.galaxytrucker.galaxytruckerreloaded.Test.Server.Persistence;

import com.galaxytrucker.galaxytruckerreloaded.Server.Database.Database;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.RequestObjectNotFoundException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.DuplicateRequestObjectException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.RequestObjectDAO;
import com.galaxytrucker.galaxytruckerreloaded.Server.RequestObject;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.UUID;

public class RequestObjectDAOTest {

    private EntityManager entityManager = Database.getEntityManager();

    private com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.RequestObjectDAO RequestObjectDAO = new RequestObjectDAO();

    /**
     * test persisting an existing
     */
    @Test
    public void testPersist() {
        RequestObject RequestObject = new RequestObject();
        RequestObject.setId(UUID.randomUUID().hashCode());
        RequestObject.setDamageAmount(5);
        try {
            RequestObjectDAO.persist(RequestObject);
            entityManager.getTransaction().begin();
            RequestObject RequestObject1 = entityManager.find(RequestObject.class, RequestObject.getId());
            entityManager.getTransaction().commit();
            Assert.assertEquals(5, RequestObject1.getDamageAmount());
        }
        catch(DuplicateRequestObjectException e) {
            Assert.fail();
        }
    }

    /**
     * test persisting an already existing 
     */
    @Test
    public void testPersistDuplicate() {
        RequestObject RequestObject = new RequestObject();
        RequestObject.setId(1);
        RequestObject.setDamageAmount(5);
        try {
            RequestObjectDAO.persist(RequestObject);
        }
        catch (DuplicateRequestObjectException e) {
            Assert.fail();
        }
        RequestObject RequestObject1 = new RequestObject();
        RequestObject1.setId(1);
        RequestObject1.setDamageAmount(7);
        try {
            RequestObjectDAO.persist(RequestObject1);
        }
        catch(DuplicateRequestObjectException e) {
            entityManager.getTransaction().begin();
            RequestObject RequestObject2 = entityManager.find(RequestObject.class, RequestObject1.getId());
            entityManager.getTransaction().commit();
            Assert.assertEquals(5, RequestObject2.getDamageAmount());
        }
    }

    /**
     * test removing an existing
     */
    @Test
    public void testRemove() {
        RequestObject RequestObject = new RequestObject();
        RequestObject.setId(UUID.randomUUID().hashCode());
        RequestObject.setDamageAmount(5);
        try {
            RequestObjectDAO.persist(RequestObject);
            RequestObjectDAO.remove(RequestObject);
            entityManager.getTransaction().begin();
            RequestObject RequestObject1 = entityManager.find(RequestObject.class, RequestObject.getId());
            entityManager.getTransaction().commit();
            Assert.assertNull(RequestObject1);
        }
        catch (DuplicateRequestObjectException | RequestObjectNotFoundException e) {
            Assert.fail();
        }
    }

    /**
     * test removing a non existing
     */
    @Test(expected = RequestObjectNotFoundException.class)
    public void testRemoveNonExisting() throws RequestObjectNotFoundException{
        RequestObject RequestObject = new RequestObject();
        RequestObject.setId(UUID.randomUUID().hashCode());
        RequestObject.setDamageAmount(5);
        RequestObjectDAO.remove(RequestObject);
    }
}
