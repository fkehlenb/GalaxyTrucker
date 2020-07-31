package com.galaxytrucker.galaxytruckerreloaded.Test.Server.Persistence;

import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Planet;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Trader;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.ShipType;
import com.galaxytrucker.galaxytruckerreloaded.Model.User;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import com.galaxytrucker.galaxytruckerreloaded.Server.Database.Database;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.DuplicateUserException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.UserNotFoundException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.UserDAO;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.awt.*;
import java.util.Objects;

public class UserDAOTest {

    /**
     * entity manager
     */
    private EntityManager entityManager = Database.getEntityManager();

    /**
     * user dao to be tested
     */
    private UserDAO userDAO = new UserDAO();

    /**
     * test persisting a new user to the data base
     */
    @Test
    public void testPersist() {
        User user = new User("testuser");
        user.setFirstGame(true);
        user.setLoggedIn(true);
        try{
            userDAO.persist(user);
            entityManager.getTransaction().begin();
            User user1 = entityManager.find(User.class, user.getUsername());
            entityManager.getTransaction().commit();
            Assert.assertEquals(user1.isFirstGame(), user.isFirstGame());
            Assert.assertEquals(user1.isLoggedIn(), user.isLoggedIn());
        }
        catch(DuplicateUserException e) {
            Assert.fail();
        }
    }

    /**
     * test persisting a user that already exists
     */
    @Test
    public void testPersistDuplicate() {
        User user = new User("testuser2");
        user.setFirstGame(true);
        user.setLoggedIn(true);
        User user2 = new User("testuser2");
        user2.setFirstGame(false);
        user2.setLoggedIn(false);
        try{
            userDAO.persist(user);
        }
        catch(DuplicateUserException e) {
            Assert.fail();
        }
        try {
            userDAO.persist(user2);
        }
        catch (DuplicateUserException e) {
            entityManager.getTransaction().begin();
            User user1 = entityManager.find(User.class, user.getUsername());
            entityManager.getTransaction().commit();
            Assert.assertTrue(user1.isLoggedIn());
            Assert.assertTrue(user1.isFirstGame());
        }
    }

    /**
     * test removing an existing user from the database
     */
    @Test
    public void testRemove() {
        User user = new User("testuser3");
        try{
            userDAO.persist(user);
        }
        catch(DuplicateUserException e) {
            Assert.fail();
        }

        try {
            userDAO.remove(user);
            entityManager.getTransaction().begin();
            User user1 = entityManager.find(User.class, user.getUsername());
            entityManager.getTransaction().commit();
            Assert.assertNull(user1);
        }
        catch(UserNotFoundException e) {
            Assert.fail();
        }
    }

    /**
     * test removing a non existing user from the database
     */
    @Test(expected = UserNotFoundException.class)
    public void testRemoveNonExisting() throws UserNotFoundException{
        User user = new User("testuser4");
        userDAO.remove(user);
    }

    /**
     * test upating an existing user user in the database
     */
    @Test
    public void testEdit() {
        User user = new User("testuser5");
        user.setFirstGame(true);
        user.setLoggedIn(true);
        try{
            userDAO.persist(user);
        }
        catch(DuplicateUserException e) {
            Assert.fail();
        }

        user.setFirstGame(false);
        user.setLoggedIn(false);
        try {
            userDAO.update(user);
            entityManager.getTransaction().begin();
            User user1 = entityManager.find(User.class, user.getUsername());
            entityManager.getTransaction().commit();
            Assert.assertFalse(user1.isFirstGame());
            Assert.assertFalse(user1.isLoggedIn());
        }
        catch(UserNotFoundException e) {
            Assert.fail();
        }
    }

    /**
     * test editing a non existing user in the database
     */
    @Test(expected = UserNotFoundException.class)
    public void testEditNonExisting() throws UserNotFoundException{
        User user = new User("testuser6");
        userDAO.update(user);
    }
}
