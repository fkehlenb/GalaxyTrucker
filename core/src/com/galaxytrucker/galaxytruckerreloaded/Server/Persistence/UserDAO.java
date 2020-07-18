package com.galaxytrucker.galaxytruckerreloaded.Server.Persistence;

import com.galaxytrucker.galaxytruckerreloaded.Model.User;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.DuplicateUserException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.UserNotFoundException;
import lombok.NonNull;

/** Manages the user objects in the database */
public class UserDAO extends ObjectDAO<User> {

    /** Instance */
    private static UserDAO instance = null;

    /** Get the DAO instance */
    public static UserDAO getInstance(){
        if (instance == null){
            instance = new UserDAO();
        }
        return instance;
    }

    /**
     * Add a new user to the database
     *
     * @param u - the user to add
     */
    @Override
    public void persist(User u) throws DuplicateUserException {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(u);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new DuplicateUserException();
        }
    }

    /**
     * Update a user in the database
     *
     * @param u - the user to update
     * @throws UserNotFoundException if the user cannot be found in the database
     */
    public void update(User u) throws UserNotFoundException {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(u);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new UserNotFoundException();
        }
    }

    /**
     * Get a user from the database using his username
     *
     * @param username - the username of the user
     */
    private User getUserByUsername(String username) throws UserNotFoundException {
        try {
            entityManager.getTransaction().begin();
            @NonNull User u = entityManager.createNamedQuery("User.getByUsername",User.class).setParameter("username",username).getSingleResult();
            entityManager.getTransaction().commit();
            return u;
        } catch (Exception e) {
            e.printStackTrace();
            throw new UserNotFoundException();
        }
    }

    /**
     * Remove a user from the database
     *
     * @param u - the user to delete
     */
    @Override
    public void remove(User u) throws UserNotFoundException {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(u);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new UserNotFoundException();
        }
    }

    /**
     * Remove a user using his username
     *
     * @param username - the username of the user to delete
     */
    private void removeUserByUsername(String username) throws UserNotFoundException {
        User u = getUserByUsername(username);
        remove(u);
    }

}
