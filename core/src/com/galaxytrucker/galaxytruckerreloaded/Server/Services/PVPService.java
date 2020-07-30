package com.galaxytrucker.galaxytruckerreloaded.Server.Services;

import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Planet;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.PlanetEvent;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.PlanetDAO;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.ShipDAO;
import com.galaxytrucker.galaxytruckerreloaded.Server.ResponseObject;
import com.galaxytrucker.galaxytruckerreloaded.Server.ServerServiceCommunicator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Manages pvp
 */
public class PVPService {

    /**
     * ShipDAO
     */
    private ShipDAO shipDAO = ShipDAO.getInstance();

    /**
     * Planet DAO
     */
    private PlanetDAO planetDAO = PlanetDAO.getInstance();

    /** Instance */
    private static PVPService instance;

    /** Get instance */
    public static PVPService getInstance(){
        if (instance == null){
            instance = new PVPService();
        }
        return instance;
    }

    /**
     * Activate pvp
     *
     * @param ship - the client's ship
     * @return response
     */
    public ResponseObject activatePVP(Ship ship) {
        ResponseObject responseObject = new ResponseObject();
        try {
            ship = shipDAO.getById(ship.getId());
            if (!ship.isPlayingPVP()) {
                ship.setPlayingPVP(true);
                shipDAO.update(ship);
                responseObject.setValidRequest(true);
                responseObject.setResponseShip(ship);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseObject;
    }

    /**
     * Initiate a pvp fight
     *
     * @param ship     - the client's ship
     * @param opponent - the opponent to fight against
     */
    public ResponseObject initiatePVP(Ship ship, String opponent) {
        ResponseObject responseObject = new ResponseObject();
        try {
            ship = shipDAO.getById(ship.getId());
            Ship opponentShip = shipDAO.getShipByUser(opponent);
            if (!ship.isInvitedToPVP() && !ship.isInCombat()
                    && !opponentShip.isInvitedToPVP() && opponentShip.isPlayingPVP()) {
                opponentShip.setInvitedToPVP(true);
                shipDAO.update(opponentShip);
                ship.setInvitedToPVP(true);
                shipDAO.update(ship);
                Planet planet = new Planet(UUID.randomUUID().hashCode(),"PVP",20,20, PlanetEvent.PVP,new ArrayList<Ship>(),"map/planet_sun1.png");
                planetDAO.persist(planet);
                planet.getShips().add(ship);
                planet.getShips().add(opponentShip);
                ship.setPlanet(planet);
                ship.setInCombat(true);
                ship.setFTLCharge(0);
                planetDAO.update(planet);
                shipDAO.update(ship);
                responseObject.setValidRequest(true);
                responseObject.setResponseShip(ship);
                responseObject.setOpponent(opponentShip);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseObject;
    }

    /**
     * Get a list of all connected pvp clients
     *
     * @param ship - the client's ship
     * @return a responseObject containing all pvp clients
     */
    public ResponseObject getPVPClients(Ship ship) {
        ResponseObject responseObject = new ResponseObject();
        try {
            String currentUser = ship.getAssociatedUser();
            List<String> connectedClients = ServerServiceCommunicator.getInstance().getPvpClients();
            connectedClients.remove(currentUser);
            List<String> pvpClients = new ArrayList<>();
            for (String s : connectedClients) {
                if (shipDAO.getShipByUser(s).isPlayingPVP() && !shipDAO.getShipByUser(s).isInvitedToPVP()) {
                    pvpClients.add(s);
                }
            }
            if (connectedClients.size() > 0) {
                responseObject.setValidRequest(true);
                responseObject.setPvpClients(pvpClients);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseObject;
    }
}
