package com.galaxytrucker.galaxytruckerreloaded.Test.Server.Persistence;

import com.galaxytrucker.galaxytruckerreloaded.Server.Database.Database;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.ResponseObjectNotFoundException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.DuplicateResponseObjectException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.ResponseObjectDAO;
import com.galaxytrucker.galaxytruckerreloaded.Server.ResponseObject;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.UUID;

public class ResponseObjectDAOTest {

    private EntityManager entityManager = Database.getEntityManager();

    private com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.ResponseObjectDAO ResponseObjectDAO = new ResponseObjectDAO();

    /**
     * test persisting an existing
     */
    @Test
    public void testPersist() {
        ResponseObject ResponseObject = new ResponseObject();
        ResponseObject.setId(UUID.randomUUID().hashCode());
        ResponseObject.setRewardCash(5);
        try {
            ResponseObjectDAO.persist(ResponseObject);
            entityManager.getTransaction().begin();
            ResponseObject ResponseObject1 = entityManager.find(ResponseObject.class, ResponseObject.getId());
            entityManager.getTransaction().commit();
            Assert.assertEquals(5, ResponseObject1.getRewardCash());
        }
        catch(DuplicateResponseObjectException e) {
            Assert.fail();
        }
    }

    /**
     * test persisting an already existing 
     */
    @Test
    public void testPersistDuplicate() {
        ResponseObject ResponseObject = new ResponseObject();
        ResponseObject.setId(1);
        ResponseObject.setRewardCash(5);
        try {
            ResponseObjectDAO.persist(ResponseObject);
        }
        catch (DuplicateResponseObjectException e) {
            Assert.fail();
        }
        com.galaxytrucker.galaxytruckerreloaded.Server.ResponseObject ResponseObject1 = new ResponseObject();
        ResponseObject1.setId(1);
        ResponseObject1.setRewardCash(7);
        try {
            ResponseObjectDAO.persist(ResponseObject1);
        }
        catch(DuplicateResponseObjectException e) {
            entityManager.getTransaction().begin();
            ResponseObject ResponseObject2 = entityManager.find(ResponseObject.class, ResponseObject1.getId());
            entityManager.getTransaction().commit();
            Assert.assertEquals(5, ResponseObject2.getRewardCash());
        }
    }

    /**
     * test removing an existing
     */
    @Test
    public void testRemove() {
        ResponseObject ResponseObject = new ResponseObject();
        ResponseObject.setId(UUID.randomUUID().hashCode());
        ResponseObject.setRewardCash(5);
        try {
            ResponseObjectDAO.persist(ResponseObject);
            ResponseObjectDAO.remove(ResponseObject);
            entityManager.getTransaction().begin();
            ResponseObject ResponseObject1 = entityManager.find(ResponseObject.class, ResponseObject.getId());
            entityManager.getTransaction().commit();
            Assert.assertNull(ResponseObject1);
        }
        catch (DuplicateResponseObjectException | ResponseObjectNotFoundException e) {
            Assert.fail();
        }
    }

    /**
     * test removing a non existing
     */
    @Test(expected = ResponseObjectNotFoundException.class)
    public void testRemoveNonExisting() throws ResponseObjectNotFoundException{
        ResponseObject ResponseObject = new ResponseObject();
        ResponseObject.setId(UUID.randomUUID().hashCode());
        ResponseObject.setRewardCash(5);
        ResponseObjectDAO.remove(ResponseObject);
    }
}
