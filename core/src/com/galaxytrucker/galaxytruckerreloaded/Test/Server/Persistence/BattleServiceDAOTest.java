package com.galaxytrucker.galaxytruckerreloaded.Test.Server.Persistence;

import com.galaxytrucker.galaxytruckerreloaded.Server.Database.Database;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.BattleServiceNotFoundException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.DuplicateBattleServiceException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.BattleServiceDAO;
import com.galaxytrucker.galaxytruckerreloaded.Server.Services.BattleService;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.UUID;

public class BattleServiceDAOTest {

    private EntityManager entityManager = Database.getEntityManager();

    private BattleServiceDAO battleServiceDAO = new BattleServiceDAO();

    /**
     * test persisting an existing
     */
    @Test
    public void testPersist() {
        BattleService service = new BattleService();
        service.setId(UUID.randomUUID());
        service.setCurrentRound(5);
        try {
            battleServiceDAO.persist(service);
            entityManager.getTransaction().begin();
            BattleService service1 = entityManager.find(BattleService.class, service.getId());
            entityManager.getTransaction().commit();
            Assert.assertEquals(5, service1.getCurrentRound());
        }
        catch(DuplicateBattleServiceException e) {
            Assert.fail();
        }
    }

    /**
     * test persisting an already existing 
     */
    @Test
    public void testPersistDuplicate() {
        UUID id = UUID.randomUUID();
        BattleService service = new BattleService();
        service.setId(id);
        service.setCurrentRound(5);
        try {
            battleServiceDAO.persist(service);
        }
        catch (DuplicateBattleServiceException e) {
            Assert.fail();
        }
        BattleService service1 = new BattleService();
        service1.setId(id);
        service1.setCurrentRound(7);
        try {
            battleServiceDAO.persist(service1);
        }
        catch(DuplicateBattleServiceException e) {
            entityManager.getTransaction().begin();
            BattleService service2 = entityManager.find(BattleService.class, service1.getId());
            entityManager.getTransaction().commit();
            Assert.assertEquals(5, service2.getCurrentRound());
        }
    }

    /**
     * test updating an exisitng 
     */
    @Test
    public void testUpdate() {
        BattleService service = new BattleService();
        service.setId(UUID.randomUUID());
        service.setCurrentRound(5);
        try {
            battleServiceDAO.persist(service);
        }
        catch(DuplicateBattleServiceException e) {
            Assert.fail();
        }
        service.setCurrentRound(6);
        try {
            battleServiceDAO.update(service);
            entityManager.getTransaction().begin();
            BattleService service1 = entityManager.find(BattleService.class, service.getId());
            entityManager.getTransaction().commit();
            Assert.assertEquals(6, service1.getCurrentRound());
        }
        catch(BattleServiceNotFoundException e) {
            Assert.fail();
        }
    }

    /**
     * test updating a non existing
     */
    @Test(expected = BattleServiceNotFoundException.class)
    public void testUpdateNonExisting() throws BattleServiceNotFoundException {
        BattleService service = new BattleService();
        service.setId(UUID.randomUUID());
        service.setCurrentRound(5);
        battleServiceDAO.update(service);
    }

    /**
     * test removing an existing
     */
    @Test
    public void testRemove() {
        BattleService service = new BattleService();
        service.setId(UUID.randomUUID());
        service.setCurrentRound(5);
        try {
            battleServiceDAO.persist(service);
            battleServiceDAO.remove(service);
            entityManager.getTransaction().begin();
            BattleService service1 = entityManager.find(BattleService.class, service.getId());
            entityManager.getTransaction().commit();
            Assert.assertNull(service1);
        }
        catch (DuplicateBattleServiceException | BattleServiceNotFoundException e) {
            Assert.fail();
        }
    }

    /**
     * test removing a non existing
     */
    @Test(expected = BattleServiceNotFoundException.class)
    public void testRemoveNonExisting() throws BattleServiceNotFoundException{
        BattleService service = new BattleService();
        service.setId(UUID.randomUUID());
        service.setCurrentRound(5);
        battleServiceDAO.remove(service);
    }
}
