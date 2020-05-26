package com.galaxytrucker.galaxytruckerreloaded.Server.Services;

import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.CrewNotFoundException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Exception.DuplicateCrewException;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.CrewDAO;

public class CrewService {

    /**
     * DAO
     */
    private CrewDAO crewDAO;

    /**
     * Add a new crew member
     *
     * @param c - the crew member to add
     * @throws DuplicateCrewException if the crew already exists
     */
    public void addCrew(Crew c) throws DuplicateCrewException {
    }

    /**
     * Edit an existing crew member
     *
     * @param c - the crew member to edit
     * @throws CrewNotFoundException if the crew cannot be found
     */
    public void editCrew(Crew c) throws CrewNotFoundException {
    }

    /**
     * Remove an existing crew member
     *
     * @param c - the crew to remove
     * @throws CrewNotFoundException if the crew cannot be found
     */
    public void removeCrew(Crew c) throws CrewNotFoundException {
    }
}
