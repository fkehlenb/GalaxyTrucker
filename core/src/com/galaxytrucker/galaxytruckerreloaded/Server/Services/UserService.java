package com.galaxytrucker.galaxytruckerreloaded.Server.Services;

import com.galaxytrucker.galaxytruckerreloaded.Model.User;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.UserDAO;
import com.j256.ormlite.support.ConnectionSource;

public class UserService {

    /**
     * The userDAO used to store and fetch data
     */
    private UserDAO userDAO;

    /**
     * add a new user to the database
     *
     * @param user - the user to add
     */
    public void addUser(User user) {
    }

    /**
     * Fetch a user from the database
     *
     * @param username - the username of the user to fetch
     */
    public User getUser(String username) {
        return null;
    }

    /**
     * Remove a user from the database
     *
     * @param u - the user to remove
     */
    public void removeUser(User u) {

    }

    /**
     * Remove a user using his username
     *
     * @param username - the username of the user to remove
     */
    public void removeUserByUsername(String username) {
    }

    /**
     * Constructor
     *
     * @param source - the database connections source
     */
    public UserService(ConnectionSource source) {

    }

    /**
     * Save game
     *
     * @param username - the username of the user
     */
    public void saveGame(String username) {
    }

    /** Login
     * @param username - the username of the user
     * @return true - if the login was successful else return false */
    public boolean login(String username){
        return false;
    }
}
