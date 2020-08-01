package com.galaxytrucker.galaxytruckerreloaded.Test.Server.Services;

import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Server.Database.Database;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.ShipDAO;
import com.galaxytrucker.galaxytruckerreloaded.Server.ResponseObject;
import com.galaxytrucker.galaxytruckerreloaded.Server.Services.PVPService;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.UUID;

public class PVPServiceTest {

    private EntityManager entityManager = Database.getEntityManager();

    private PVPService service = new PVPService();

    private ShipDAO shipDAO = new ShipDAO();

    /**
     * test activate pvp in situation where it should be successful
     */
    @Test
    public void activatePVPSuccess() {
        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        try {
            shipDAO.persist(s);
        }
        catch(Exception e ) {
            Assert.fail();
        }

        ResponseObject response = service.activatePVP(s);
        Assert.assertTrue(response.isValidRequest());
        entityManager.getTransaction().begin();
        Ship s1 = entityManager.find(Ship.class, s.getId());
        entityManager.getTransaction().commit();
        Assert.assertTrue(s1.isPlayingPVP());
    }

    /**
     * test whether a ship the server does not know can activate pvp
     */
    @Test
    public void activatePVPUnknownShip() {
        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());

        ResponseObject response = service.activatePVP(s);
        Assert.assertFalse(response.isValidRequest());
    }

    /**
     * test initiate pvp in situation that should be successful
     */
    @Test
    public void initiatePVPSuccess() {
        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        Ship s1 = new Ship();
        s1.setId(UUID.randomUUID().hashCode());
        s1.setAssociatedUser("testuser5");
        try {
            shipDAO.persist(s);
            shipDAO.persist(s1);
        }
        catch(Exception e ) {
            Assert.fail();
        }

        ResponseObject response = service.activatePVP(s);
        Assert.assertTrue(response.isValidRequest());
        entityManager.getTransaction().begin();
        Ship s2 = entityManager.find(Ship.class, s.getId());
        Ship s3 = entityManager.find(Ship.class, s1.getId());
        entityManager.getTransaction().commit();
        Assert.assertTrue(s2.isInvitedToPVP());
        Assert.assertTrue(s2.isInCombat());
        Assert.assertTrue(s3.isInvitedToPVP());
        Assert.assertFalse(s3.isInCombat());
    }

    /**
     * test initiate pvp when the other is already invited
     */
    @Test
    public void initiatePVPAlreadyInPVP() {
        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        Ship s1 = new Ship();
        s1.setId(UUID.randomUUID().hashCode());
        s1.setAssociatedUser("testuser4");
        s1.setInvitedToPVP(true);
        try {
            shipDAO.persist(s);
            shipDAO.persist(s1);
        } catch (Exception e) {
            Assert.fail();
        }


        ResponseObject response = service.initiatePVP(s, "testuser4");
        Assert.assertFalse(response.isValidRequest());
    }

    /**
     * test initiate pvp when you are already invited
     */
    @Test
    public void initiatePVPAlreadyInPVPSelf() {
        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        s.setInvitedToPVP(true);
        Ship s1 = new Ship();
        s1.setId(UUID.randomUUID().hashCode());
        s1.setAssociatedUser("testuser3");
        try {
            shipDAO.persist(s);
            shipDAO.persist(s1);
        } catch (Exception e) {
            Assert.fail();
        }


        ResponseObject response = service.initiatePVP(s, "testuser3");
        Assert.assertFalse(response.isValidRequest());
    }

    /**
     * test initiate pvp when the other is already invited
     */
    @Test
    public void initiatePVPAlreadyInCombat() {
        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        Ship s1 = new Ship();
        s1.setId(UUID.randomUUID().hashCode());
        s1.setAssociatedUser("testuser2");
        s1.setInCombat(true);
        try {
            shipDAO.persist(s);
            shipDAO.persist(s1);
        } catch (Exception e) {
            Assert.fail();
        }


        ResponseObject response = service.initiatePVP(s, "testuser2");
        Assert.assertFalse(response.isValidRequest());
    }

    /**
     * test initiate pvp when you are already invited
     */
    @Test
    public void initiatePVPAlreadyInCombatSelf() {
        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());
        s.setInCombat(true);
        Ship s1 = new Ship();
        s1.setId(UUID.randomUUID().hashCode());
        s1.setAssociatedUser("testuser1");
        try {
            shipDAO.persist(s);
            shipDAO.persist(s1);
        } catch (Exception e) {
            Assert.fail();
        }


        ResponseObject response = service.initiatePVP(s, "testuser1");
        Assert.assertFalse(response.isValidRequest());
    }

    /**
     * test initiate pvp with unknown ships
     */
    @Test
    public void initiatePVPUnknownShip() {
        Ship s = new Ship();
        s.setId(UUID.randomUUID().hashCode());

        ResponseObject response = service.initiatePVP(s, "testuser");
        Assert.assertFalse(response.isValidRequest());
    }
}
