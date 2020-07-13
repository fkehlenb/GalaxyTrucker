package com.galaxytrucker.galaxytruckerreloaded.Server.Services;

import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Planet;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.User;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.PlanetDAO;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.ShipDAO;
import lombok.*;

import java.util.List;

/**
 * Used to move user from one star to another
 */
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
public class TravelService {

    /**
     * ShipDAO
     */
    private ShipDAO shipDAO = new ShipDAO();

    /**
     * PlanetDAO
     */
    private PlanetDAO planetDAO = new PlanetDAO();

    /**
     * Validate the travel request
     *
     * @param s - the ship that wishes to travel
     * @param p - the destination planet
     * @return true if the travel request is valid
     */
    public boolean validateJump(Ship s, Planet p, User u) {
        try {
            //TODO
//            if (u.getOverworld().getPlanetMap().containsValue(p)) {
//                return !s.getPlanet().equals(p) && s.getFTLCharge() == 100;
//            }
            return true;
        } catch (Exception e) {
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
    public boolean jump(Ship s, Planet dest) {
        try {
            Planet current = s.getPlanet();
            try {
                // Remove ship from current planet
                List<Ship> ships = current.getShips();
                ships.remove(s);
                current.setShips(ships);
                planetDAO.update(current);
                // Set ship to target planet
                s.setPlanet(dest);
                shipDAO.update(s);
                // Update target planet
                ships = dest.getShips();
                ships.add(s);
                dest.setShips(ships);
                planetDAO.update(dest);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                // Revert charnges
                try {
                    // Revert current planet
                    List<Ship> ships = current.getShips();
                    ships.add(s);
                    current.setShips(ships);
                    planetDAO.update(current);
                    // Revert ship
                    s.setPlanet(current);
                    shipDAO.update(s);
                    // Revert target planet
                    ships = dest.getShips();
                    ships.remove(s);
                    dest.setShips(ships);
                    planetDAO.update(dest);
                } catch (Exception f) {
                    f.printStackTrace();
                }
                return false;
            }
        }
        catch (Exception g){
            g.printStackTrace();
            return false;
        }
    }
}
