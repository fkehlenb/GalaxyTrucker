package com.galaxytrucker.galaxytruckerreloaded.Server.Persistence;

import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.CrewNotFoundException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.DuplicateCrewException;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;

public class CrewDAO extends ObjectDAO<Crew> {

    /** Database connection */
    private ConnectionSource source;

    /** Crew dao */
    private Dao<Crew,String> crewDao;

    /**
     * Add a new crew member to the database
     *
     * @param c - the crew to persist
     * @throws DuplicateCrewException if the crew member already exists in the database
     */
    @Override
    public void persist(Crew c) throws DuplicateCrewException {

    }

    /**
     * Remove an existing crew member from the database
     *
     * @param c - the crew member to remove
     * @throws CrewNotFoundException if the crew member couldn't be found in the database
     */
    @Override
    public void remove(Crew c) throws CrewNotFoundException {

    }

    /** Constructor
     * @param source - database connection source */
    public CrewDAO(ConnectionSource source){}
}
