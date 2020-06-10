package com.galaxytrucker.galaxytruckerreloaded.Server.Persistence;

import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import com.galaxytrucker.galaxytruckerreloaded.Server.Database.Database;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.CrewNotFoundException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.DuplicateCrewException;

import javax.persistence.EntityManager;

public class CrewDAO extends ObjectDAO<Crew> {

    /**
     * Add a new crew member to the database
     *
     * @param c - the crew to persist
     * @throws DuplicateCrewException if the crew member already exists in the database
     */
    @Override
    public void persist(Crew c) throws DuplicateCrewException {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(c);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new DuplicateCrewException();
        }
    }

    /**
     * Update a crew member in the database
     *
     * @param c - the crew member to update in the database
     * @throws CrewNotFoundException if the crew cannot be found in the database
     */
    public void update(Crew c) throws CrewNotFoundException {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(c);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new CrewNotFoundException();
        }
    }

    /**
     * Remove an existing crew member from the database
     *
     * @param c - the crew member to remove
     * @throws CrewNotFoundException if the crew member couldn't be found in the database
     */
    @Override
    public void remove(Crew c) throws CrewNotFoundException {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(c);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new CrewNotFoundException();
        }
    }
}
