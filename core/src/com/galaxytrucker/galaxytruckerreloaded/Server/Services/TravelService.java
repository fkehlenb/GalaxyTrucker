package com.galaxytrucker.galaxytruckerreloaded.Server.Services;

import com.galaxytrucker.galaxytruckerreloaded.Controller.BattleController;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Overworld;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Planet;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.PlanetEvent;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.User;
import com.galaxytrucker.galaxytruckerreloaded.Server.Database.Database;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.BattleServiceDAO;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.OverworldDAO;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.PlanetDAO;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.ShipDAO;
import com.galaxytrucker.galaxytruckerreloaded.Server.ResponseObject;
import com.galaxytrucker.galaxytruckerreloaded.Server.ServerServiceCommunicator;
import lombok.*;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Used to move user from one star to another
 */
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
public class TravelService {

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

    /** Battle service dao */
    private BattleServiceDAO battleServiceDAO = BattleServiceDAO.getInstance();

    /** Overworld DAO */
    private OverworldDAO overworldDAO = OverworldDAO.getInstance();

    /**
     * ShipDAO
     */
    private ShipDAO shipDAO = ShipDAO.getInstance();

    /**
     * PlanetDAO
     */
    private PlanetDAO planetDAO = PlanetDAO.getInstance();

    // TODO DONT USE THIS WHEN IN COMBAT, USE FLEEFIGHT INSTEAD
    // TODO DONT USE THIS FOR PVP
    /**
     * Jump ship from one star to another
     *
     * @param s    - the ship to jump
     * @param dest - the destination star
     */
    // TODO check fuel
    public ResponseObject jump(Ship s, Planet dest) {
        ResponseObject responseObject = new ResponseObject();
        try {
            // Check for fuel and ftlCharge
            if (s.getFuel() > 0 && s.getFTLCharge() == 100) {
                Planet currentPlanet = s.getPlanet();
                s.setFuel(s.getFuel() - 1);
                // Todo distance validation
                // ===== Ships at current planet =====
                List<Ship> ships = currentPlanet.getShips();
                // ===== No Trusting the Client =====
                Ship currentAtPlanet = null;
                for (Ship a : ships) {
                    if (s.getId() == a.getId()) {
                        currentAtPlanet = a;
                    }
                }
                // ===== Combat planet =====
                if (!(dest.getEvent().equals(PlanetEvent.VOID) || dest.getEvent().equals(PlanetEvent.METEORSHOWER)
                        || dest.getEvent().equals(PlanetEvent.SHOP) || dest.getEvent().equals(PlanetEvent.NEBULA))) {
                    s.setFTLCharge(0);
                    Ship enemyShip = null;
                    try {
                        enemyShip = dest.getShips().get(0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (enemyShip != null) {
                        try {
                            s.setInCombat(true);
                            // ===== Create new Battle Service =====
                            BattleService battleService = new BattleService(UUID.randomUUID(), s.getId(), s, enemyShip);
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
                    }
                }
                // ===== Remove ship from planet =====
                ships.remove(currentAtPlanet);
                planetDAO.update(currentPlanet);
                // ===== Add ship to destination =====
                List<Ship> shipsAtDestination = dest.getShips();
                shipsAtDestination.add(s);
                dest.setShips(shipsAtDestination);
                dest.setDiscovered(true);
                // ===== Update ship planet =====
                s.setPlanet(dest);
                planetDAO.update(dest);
                // ===== Set valid =====
                responseObject.setValidRequest(true);
                responseObject.setResponseShip(s);
                responseObject.setResponseOverworld(UserService.getInstance().getUser(s.getAssociatedUser()).getOverworld());
            }
        }
        catch (Exception f){
            f.printStackTrace();
        }
        return responseObject;
    }
}
