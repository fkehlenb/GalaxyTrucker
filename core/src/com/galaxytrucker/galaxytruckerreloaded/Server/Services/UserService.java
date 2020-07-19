package com.galaxytrucker.galaxytruckerreloaded.Server.Services;

import com.galaxytrucker.galaxytruckerreloaded.Model.User;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.DuplicateUserException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.UserNotFoundException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.UserDAO;

import javax.persistence.EntityManager;

public class UserService {

    /** Instance */
    private static UserService instance;

    /** Get the instance */
    public static UserService getInstance(){
        if (instance == null){
            instance = new UserService();
        }
        return instance;
    }

    /**
     * The userDAO used to store and fetch data
     */
    private UserDAO userDAO = UserDAO.getInstance();

    /** Entity Manager */
    private EntityManager entityManager = userDAO.entityManager;

    /**
     * add a new user to the database
     *
     * @param username - the username of the user to add
     *
     * @throws DuplicateUserException if the user already exists
     */
    public void addUser(String username) throws DuplicateUserException {
        userDAO.persist(new User(username));
    }

    /**
     * Fetch a user from the database
     *
     * @param username - the username of the user to fetch
     *
     * @throws UserNotFoundException if the user couldn't be found
     */
    public User getUser(String username) throws UserNotFoundException {
        entityManager.getTransaction().begin();
        User u = entityManager.find(User.class,username);
        entityManager.getTransaction().commit();
        return u;
    }

    /**
     * Update a user in the database
     *
     * @param u - the user to update
     * @throws UserNotFoundException if the user cannot be found in the database
     */
    public void updateUser(User u) throws UserNotFoundException {
        userDAO.update(u);
    }

    /**
     * Remove a user using his username
     *
     * @param username - the username of the user to remove
     *
     * @throws UserNotFoundException if the user cannot be found
     */
    public void removeUserByUsername(String username) throws UserNotFoundException {
        entityManager.getTransaction().begin();
        entityManager.remove(getUser(username));
        entityManager.getTransaction().commit();
    }

    /**
     * Save game
     *
     * @param username - the username of the user
     */
    public void saveGame(String username) throws UserNotFoundException {
    }
}
