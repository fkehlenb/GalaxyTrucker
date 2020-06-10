package com.galaxytrucker.galaxytruckerreloaded.Server.Services;

import com.galaxytrucker.galaxytruckerreloaded.Model.User;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.DuplicateUserException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.UserNotFoundException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.UserDAO;

public class UserService {

    /**
     * The userDAO used to store and fetch data
     */
    private UserDAO userDAO;

    /**
     * add a new user to the database
     *
     * @param username - the username of the user to add
     *
     * @throws DuplicateUserException if the user already exists
     */
    public void addUser(String username) throws DuplicateUserException {
    }

    /**
     * Fetch a user from the database
     *
     * @param username - the username of the user to fetch
     *
     * @throws UserNotFoundException if the user couldn't be found
     */
    public User getUser(String username) throws UserNotFoundException {
        return null;
    }

    /**
     * Update a user in the database
     *
     * @param u - the user to update
     * @throws UserNotFoundException if the user cannot be found in the database
     */
    public void updateUser(User u) throws UserNotFoundException {
    }

    /**
     * Remove a user using his username
     *
     * @param username - the username of the user to remove
     *
     * @throws UserNotFoundException if the user cannot be found
     */
    public void removeUserByUsername(String username) throws UserNotFoundException {
    }

    /**
     * Save game
     *
     * @param username - the username of the user
     */
    public void saveGame(String username) throws UserNotFoundException {
    }

    /**
     * Login
     *
     * @param username - the username of the user
     * @return true - if the login was successful else return false
     *
     */
    public boolean login(String username) {
        return false;
    }
}
