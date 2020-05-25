package com.galaxytrucker.galaxytruckerreloaded.Server.Persistence;

import com.galaxytrucker.galaxytruckerreloaded.Model.User;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;

public class UserDAO extends ObjectDAO<User> {

    /**
     * Database connection source
     */
    private ConnectionSource source;

    /**
     * UserDAO
     */
    private Dao<User, String> userDao;

    /**
     * Constructor
     *
     * @param source - database connection source
     */
    public UserDAO(ConnectionSource source) {

    }

    /**
     * Add a new user to the database
     *
     * @param u - the user to add
     */
    public void persist(User u) {

    }

    /**
     * Get a user from the database using his username
     *
     * @param username - the username of the user
     */
    private User getUserByUsername(String username) {
        return null;
    }

    /**
     * Remove a user from the database
     *
     * @param u - the user to delete
     */
    public void remove(User u) {

    }

    /**
     * Remove a user using his username
     *
     * @param username - the username of the user to delete
     */
    private void removeUserByUsername(String username) {

    }

}
