package com.galaxytrucker.galaxytruckerreloaded.Test.Server.Services;

import com.galaxytrucker.galaxytruckerreloaded.Model.User;
import com.galaxytrucker.galaxytruckerreloaded.Server.Database.Database;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.DuplicateUserException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.UserNotFoundException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.OverworldDAO;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.PlanetDAO;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.ShipDAO;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.UserDAO;
import com.galaxytrucker.galaxytruckerreloaded.Server.ResponseObject;
import com.galaxytrucker.galaxytruckerreloaded.Server.Services.TravelService;
import com.galaxytrucker.galaxytruckerreloaded.Server.Services.UserService;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManager;

public class UserServiceTest {

    private EntityManager entityManager = Database.getEntityManager();

    private UserService service = new UserService();

    private ShipDAO shipDAO = new ShipDAO();

    private PlanetDAO planetDAO = new PlanetDAO();

    private UserDAO userDAO = new UserDAO();

    private OverworldDAO overworldDAO = new OverworldDAO();

    /**
     * add a new user
     */
    @Test
    public void addUserSuccess() {
        try {
            service.addUser("testuser");
        }
        catch(Exception e) {
            Assert.fail();
        }
    }

    /**
     * try adding existing user
     */
    @Test(expected = DuplicateUserException.class)
    public void addUserAlreadyThere() throws DuplicateUserException {
        try {
            service.addUser("testuser1");
        }
        catch(Exception e) {
            Assert.fail();
        }
        service.addUser("testuser1");
    }

    /**
     * get a user
     */
    @Test
    public void getUserSuccess() {
        User u = new User();
        u.setUsername("testuser2");
        u.setLoggedIn(true);
        u.setFirstGame(true);
        try {
            userDAO.persist(u);
        }
        catch(Exception e) {
            Assert.fail();
        }
        try {
            User u1 = service.getUser("testuser2");
            Assert.assertTrue(u1.isLoggedIn());
            Assert.assertTrue(u1.isFirstGame());
        }
        catch(Exception e) {
            Assert.fail();
        }
    }

    /**
     * try getting non existing user
     */
    @Test
    public void getUserNotThere() throws UserNotFoundException {
        User u = service.getUser("testuser3");
        Assert.assertNull(u);
    }

    /**
     * update user
     */
    @Test
    public void updateUserSuccess() {
        User u = new User();
        u.setUsername("testuser4");
        try {
            userDAO.persist(u);
        }
        catch(Exception e) {
            Assert.fail();
        }
        u.setFirstGame(true);
        u.setLoggedIn(true);
        try {
            service.updateUser(u);
            entityManager.getTransaction().begin();
            User u1 = entityManager.find(User.class, "testuser4");
            entityManager.getTransaction().commit();
            Assert.assertTrue(u1.isFirstGame());
            Assert.assertTrue(u1.isLoggedIn());
        }
        catch(Exception e) {
            Assert.fail();
        }
    }

    /**
     * try updating non existing user
     */
    @Test(expected = UserNotFoundException.class)
    public void updateUserNotThere() throws UserNotFoundException{
        User u = new User();
        u.setUsername("testuser5");
        service.updateUser(u);
    }
}
