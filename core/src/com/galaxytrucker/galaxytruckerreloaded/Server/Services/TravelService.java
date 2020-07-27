package com.galaxytrucker.galaxytruckerreloaded.Server.Services;

import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Overworld;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Planet;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.PlanetEvent;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.SystemType;
import com.galaxytrucker.galaxytruckerreloaded.Model.User;
import com.galaxytrucker.galaxytruckerreloaded.Server.Database.Database;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.*;
import com.galaxytrucker.galaxytruckerreloaded.Server.ResponseObject;
import com.galaxytrucker.galaxytruckerreloaded.Server.ServerServiceCommunicator;
import lombok.*;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Used to move user from one star to another
 */
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
public class TravelService implements Serializable {

    /**
     * Instance
     */
    private static TravelService instance;

    /**
     * Get instance
     */
    public static TravelService getInstance() {
        if (instance == null) {
            instance = new TravelService();
        }
        return instance;
    }

    /**
     * Entity Manager
     */
    private EntityManager entityManager = Database.getEntityManager();

    /**
     * Battle service dao
     */
    private BattleServiceDAO battleServiceDAO = BattleServiceDAO.getInstance();

    /**
     * Overworld DAO
     */
    private OverworldDAO overworldDAO = OverworldDAO.getInstance();

    /**
     * ShipDAO
     */
    private ShipDAO shipDAO = ShipDAO.getInstance();

    /**
     * User DAO
     */
    private UserDAO userDAO = UserDAO.getInstance();

    /**
     * PlanetDAO
     */
    private PlanetDAO planetDAO = PlanetDAO.getInstance();

    /**
     * Jump ship from one star to another
     *
     * @param s    - the ship to jump
     * @param dest - the destination star
     */
    public ResponseObject jump(Ship s, Planet dest) {
        ResponseObject responseObject = new ResponseObject();
        try {
            // Fetch data from database - client cannot be trusted
            s = shipDAO.getById(s.getId());
            dest = planetDAO.getById(dest.getId());
            Planet currentPlanet = s.getPlanet();

            // 1 Crew in cockpit with cockpit level 1, 1 energy in engine
            // or no crew in cockpit with level 2 and 1 energy in engine
            boolean crewInCockpit = false;
            boolean engineActive = false;
            for (Room r : s.getSystems()) {
                if (r.isSystem()) {
                    if (((com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.System) r).getSystemType().equals(SystemType.ENGINE)) {
                        engineActive = ((com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.System) r).getEnergy() > 0
                                && !((com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.System) r).isDisabled();
                    } else if (((com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.System) r).getSystemType().equals(SystemType.COCKPIT)
                            && !((com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.System) r).isDisabled()) {
                        if (((com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.System) r).getMaxEnergy() >= 2) {
                            crewInCockpit = true;
                        } else if (r.getCrew().size() >= 1) {
                            crewInCockpit = true;
                        }
                    }
                }
            }
            boolean allowedToJump = crewInCockpit && engineActive;
            // Check for fuel and ftlCharge
            if (s.getFuel() > 0 && s.getFTLCharge() == 100 && dest.getId() != s.getPlanet().getId() && allowedToJump) {
                // todo check me - just in case
                s.setInCombat(false);
                // User
                User user = UserService.getInstance().getUser(s.getAssociatedUser());
                // Overworld
                Overworld overworld = user.getOverworld();
                // Planet map
                List<Planet> planetMap = overworld.getPlanetMap();
                // Planet x values
                List<Float> xPositions = new ArrayList<>();
                // Planet y values
                List<Float> yPositions = new ArrayList<>();
                // Add values to map matrix
                for (Planet p : planetMap) {
                    xPositions.add(p.getPosX());
                    yPositions.add(p.getPosY());
                }
                // Get maximum x value in map
                float maxX = 0f;
                for (Float f : xPositions) {
                    if (f > maxX && f != 30f) {
                        maxX = f;
                    }
                }
                // Check for start planet
                boolean startPlanet = false;
                if (overworld.getStartPlanet().getId() == s.getPlanet().getId()) {
                    startPlanet = true;
                }
                // Check for boss planet
                boolean bossPlanet = false;
                if (overworld.getBossPlanet().getId() == dest.getId()) {
                    bossPlanet = true;
                }
                // Check for pvp planet
                boolean pvp = s.getPlanet().getEvent().equals(PlanetEvent.PVP);

                // Distance between current planet and next planet
                int distanceX = Math.round(dest.getPosX() - currentPlanet.getPosX());
                int distanceY = Math.round(dest.getPosY() - currentPlanet.getPosY());

                // Distance verification
                // Boss Planet / Start planet / planet in same or next matrix column / CANT RETURN TO START PLANET!
                if (((startPlanet && dest.getPosX() == 0f) || (bossPlanet && currentPlanet.getPosX() == maxX)
                        || (Math.abs(distanceX) <= 1 && Math.abs(distanceY) <= 1) || pvp) && dest.getPosX() != -1f) {
                    // Reduce fuel
                    s.setFuel(s.getFuel() - 1);
                    // ===== Ships at current planet =====
                    List<Ship> ships = currentPlanet.getShips();
                    // Get client ship at planet to remove it
                    Ship currentAtPlanet = null;
                    for (Ship a : ships) {
                        if (s.getId() == a.getId()) {
                            currentAtPlanet = a;
                        }
                    }
                    // ===== Combat planet =====
                    if (s.isInvitedToPVP()){
                        List<Planet> planets = planetDAO.getByName("PVP");
                        for (Planet p : planets){
                            if (p.getShips().contains(s)){
                                dest = p;
                                s.setInvitedToPVP(true);
                                break;
                            }
                        }
                    }
                    if (!(dest.getEvent().equals(PlanetEvent.VOID) || dest.getEvent().equals(PlanetEvent.METEORSHOWER)
                            || dest.getEvent().equals(PlanetEvent.SHOP) || dest.getEvent().equals(PlanetEvent.NEBULA))) {
                        s.setFTLCharge(0);
                        Ship enemyShip = null;
                        try {
                            if (dest.getEvent().equals(PlanetEvent.PVP)){
                                for (Ship shipp : dest.getShips()){
                                    if (shipp.getId()!=s.getId()){
                                        enemyShip = shipp;
                                        break;
                                    }
                                }
                            }
                            else {
                                enemyShip = dest.getShips().get(0);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (enemyShip != null) {
                            try {
                                s.setInCombat(true);
                                // ===== Create new Battle Service =====
                                BattleService battleService = new BattleService(UUID.randomUUID(), s.getId());
                                List<Ship> combatants = new ArrayList<>();
                                combatants.add(enemyShip);
                                combatants.add(s);
                                battleService.setCombatants(combatants);
                                battleServiceDAO.persist(battleService);
                                ServerServiceCommunicator serverServiceCommunicator = ServerServiceCommunicator.getInstance();
                                List<BattleService> battleServices = serverServiceCommunicator.getBattleServices();
                                battleServices.add(battleService);
                                synchronized (serverServiceCommunicator.getBattleServices()) {
                                    serverServiceCommunicator.setBattleServices(battleServices);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            s.setFTLCharge(100);
                        }
                    }
                    // ===== Remove ship from planet =====
                    ships.remove(currentAtPlanet);
                    currentPlanet.setShips(ships);
                    // ===== Add ship to destination =====
                    List<Ship> shipsAtDestination = dest.getShips();
                    shipsAtDestination.add(s);
                    dest.setShips(shipsAtDestination);
                    dest.setDiscovered(true);
                    // ===== Update ship planet =====
                    s.setPlanet(dest);
                    // ===== Update Data =====
                    planetDAO.update(dest);
                    planetDAO.update(currentPlanet);
                    shipDAO.update(s);
                    // ===== Update overworld =====
                    List<Planet> planets = overworld.getPlanetMap();
                    for (Planet p : planets) {
                        if (p.getId() == currentPlanet.getId()) {
                            planets.set(planets.indexOf(p), currentPlanet);
                        } else if (p.getId() == dest.getId()) {
                            planets.set(planets.indexOf(p), dest);
                        }
                    }
                    overworld.setPlanetMap(new ArrayList<Planet>(planets));
                    for (Planet p : planets) {
                        planetDAO.update(p);
                    }
                    user.setUserShip(s);
                    userDAO.update(user);
                    // ===== Set valid =====
                    responseObject.setValidRequest(true);
                    responseObject.setResponseShip(s);
                    responseObject.setResponseOverworld(overworld);
                }
            }
        } catch (Exception f) {
            f.printStackTrace();
        }
        return responseObject;
    }
}
