package com.galaxytrucker.galaxytruckerreloaded.Server.Services;

import com.galaxytrucker.galaxytruckerreloaded.Controller.BattleController;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Planet;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.PlanetEvent;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.User;
import com.galaxytrucker.galaxytruckerreloaded.Server.Database.Database;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.PlanetDAO;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.ShipDAO;
import com.galaxytrucker.galaxytruckerreloaded.Server.ResponseObject;
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

    /** Entity Manager */
    private EntityManager entityManager = Database.getEntityManager();

    /**
     * ShipDAO
     */
    private ShipDAO shipDAO = ShipDAO.getInstance();

    /**
     * PlanetDAO
     */
    private PlanetDAO planetDAO = PlanetDAO.getInstance();

    /**
     * Validate the travel request
     *
     * @param s - the ship that wishes to travel
     * @param p - the destination planet
     * @return true if the travel request is valid
     */
    public boolean validateJump(Ship s, Planet p, User u) {
        try {
            if (s.isInCombat()){
                //TODO rewards for opponent
            }
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Jump ship from one star to another
     *
     * @param s    - the ship to jump
     * @param dest - the destination star
     */
    public ResponseObject jump(Ship s, Planet dest) {
        ResponseObject responseObject = new ResponseObject();
        Planet current = s.getPlanet();
        try {
            User u = UserService.getInstance().getUser(s.getAssociatedUser());
            if (validateJump(s, dest, u)) {
                // Remove ship from current planet
                List<Ship> ships = current.getShips();
                ships.remove(s);
                current.setShips(ships);
                s.setPlanet(dest);
                ships = new ArrayList<>(dest.getShips());
                ships.add(s);
                dest.setShips(ships);
                dest.setDiscovered(true);
                if (dest.getEvent().equals(PlanetEvent.COMBAT) || dest.getEvent().equals(PlanetEvent.BOSS)
                        || dest.getEvent().equals(PlanetEvent.MINIBOSS) || dest.getEvent().equals(PlanetEvent.PVP)){
                    s.setInCombat(true);
                    if (dest.getEvent().equals(PlanetEvent.PVP)){
                        // Todo service
                    }
                    else {
                        s.setBattleService(new BattleService(UUID.randomUUID(), dest.getShips()));
                    }
                }
                shipDAO.update(s);
                planetDAO.update(dest);
                planetDAO.update(current);
                responseObject.setResponseShip(s);
                responseObject.setResponseOverworld(UserService.getInstance().getUser(s.getAssociatedUser()).getOverworld());
                responseObject.setValidRequest(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            List<Ship> ships = new ArrayList<>(dest.getShips());
            if (ships.contains(s)){
                ships.remove(s);
            }
            dest.setShips(ships);
            s.setPlanet(current);
            dest.setDiscovered(false);
            ships = current.getShips();
            if (!ships.contains(s)){

            }
        }
        return responseObject;
    }
}
