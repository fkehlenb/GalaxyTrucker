package com.galaxytrucker.galaxytruckerreloaded.Server.Services;

import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Overworld;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;

/**
 * Handles picking a ship, creating the game map and so on
 */
public class HangarService {

    /**
     * Generate a map
     *
     * @param username - the user who to generate a map for
     * @param seed     - seed for map generation
     * @return the generated map
     */
    public Overworld generateMap(String username, int seed) {
        return null;
    }

    /**
     * Make the user pick a ship
     *
     * @param username   - the user who wants to pick a ship
     * @param shipDesign - the ship design the user chose
     * @return a new ship object for that design
     */
    public Ship chooseShip(String username, String shipDesign) {
        return null;
    }
}
