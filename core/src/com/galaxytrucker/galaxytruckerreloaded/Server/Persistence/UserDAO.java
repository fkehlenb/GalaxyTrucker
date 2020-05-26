package com.galaxytrucker.galaxytruckerreloaded.Server.Persistence;

import com.galaxytrucker.galaxytruckerreloaded.Model.User;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.DuplicateUserException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.UserNotFoundException;
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
    @Override
    public void persist(User u) throws DuplicateUserException {

    }

    /**
     * Get a user from the database using his username
     *
     * @param username - the username of the user
     */
    private User getUserByUsername(String username) throws UserNotFoundException {
        return null;
    }

    /**
     * Remove a user from the database
     *
     * @param u - the user to delete
     */
    @Override
    public void remove(User u) throws UserNotFoundException {

    }

    /**
     * Remove a user using his username
     *
     * @param username - the username of the user to delete
     */
    private void removeUserByUsername(String username) throws UserNotFoundException {

    }

}
