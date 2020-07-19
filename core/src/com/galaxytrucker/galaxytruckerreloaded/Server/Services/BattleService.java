package com.galaxytrucker.galaxytruckerreloaded.Server.Services;

import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.System;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.SystemType;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.CrewDAO;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.RoomDAO;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.ShipDAO;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.WeaponDAO;
import com.galaxytrucker.galaxytruckerreloaded.Server.ResponseObject;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * This class handles battle logic on the server side
 */
@Entity
@Getter
@Setter
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@NamedQueries({
        @NamedQuery(name = "BattleService.fetchAll", query = "select b from BattleService b")
})
public class BattleService implements Serializable {

    /**
     * ID
     */
    @Id
    @NonNull
    private UUID id;

    /**
     * ShipDAO
     */
    @Transient
    private ShipDAO shipDAO = ShipDAO.getInstance();

    /**
     * ID of current round ship
     */
    @NonNull
    private int currentRound;

    /**
     * HashMap containing weapons and their current coolDowns
     */
    @ElementCollection
    private Map<Weapon, Integer> cooldowns = new HashMap<>();

    /**
     * Player 1
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    @NonNull
    private Ship playerOne;

    /**
     * Player 2
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    @NonNull
    private Ship playerTwo;

    /** Give updated ships to clients
     * @return a responseObject containing the updated ships */
    public ResponseObject getUpdatedData(Ship clientShip){
        ResponseObject responseObject = new ResponseObject();
        if (playerOne.getId()==clientShip.getId()){
            responseObject.setResponseShip(playerOne);
            responseObject.setOpponent(playerTwo);
        }
        else{
            responseObject.setResponseShip(playerTwo);
            responseObject.setOpponent(playerOne);
        }
        return responseObject;
    }

    /**
     * Next round
     */
    private void nextRound() {
        playerOne = passiveChanges(playerOne);
        playerTwo = passiveChanges(playerTwo);
        if (playerOne.getId() == currentRound) {
            currentRound = playerTwo.getId();
        } else {
            currentRound = playerOne.getId();
        }
    }

    /** Attack a ship
     * @param ship - own ship
     * @param weapon - the weapon to attack with
     * @param opponent - opponent ship
     * @param room - room to attack */
    public ResponseObject attackShip(Ship ship,Weapon weapon,Ship opponent,Room room){
        ResponseObject responseObject = new ResponseObject();
        if (currentRound == ship.getId()) {

            nextRound();
            // TODO add rewards and kick client if he died
        }
        return responseObject;
    }


    /**
     * Passive stages
     *
     * @param ship - the user's ship
     * @return the updated ship
     */
    private Ship passiveChanges(Ship ship) throws IllegalArgumentException {
        try {
            // ========== Remove oxygen from rooms with breach ==========
            List<Room> rooms = ship.getSystems();
            for (Room r : rooms) {
                if (r.getBreach() > 0) {
                    if (r.getCrew().size() > 1) {
                        r.setBreach(r.getBreach() - r.getCrew().size());
                        if (r.getBreach() < 0) {
                            r.setBreach(0);
                        }
                    }
                    if (r.getOxygen() >= 25) {
                        r.setOxygen(r.getOxygen() - 25);
                    }
                    List<Crew> crewInRoom = r.getCrew();
                    for (Crew c : crewInRoom) {
                        if (c.getHealth() > 1) {
                            c.setHealth(c.getHealth() - 1);
                        }
                        // Crew dead
                        if (c.getHealth() <= 0) {
                            crewInRoom.remove(c);
                            c.getTile().setStandingOnMe(null);
                        }
                    }
                    r.setCrew(crewInRoom);
                }
            }
            // ========== Refresh weapon cooldowns ==========
            Iterator iterator = cooldowns.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry pair = (Map.Entry) iterator.next();
                cooldowns.put((Weapon) pair.getKey(), (int) pair.getValue() - 1);
                if ((int) pair.getValue() <= 0) {
                    cooldowns.remove((Weapon) pair.getKey());
                }
                iterator.remove();
            }
            // ========== Replenish shields ==========
            if (ship.getShieldCharge() > 0 && ship.getShields() == 0) {
                if (ship.getShieldCharge() > 4) {
                    ship.setShields(3);
                } else if (ship.getShieldCharge() > 2) {
                    ship.setShields(2);
                } else {
                    ship.setShields(1);
                }
            }
            // ========== Fix systems ==========
            for (Room r : ship.getSystems()) {
                if (r.isSystem()) {
                    if (r.getCrew().size() > 0) {
                        if (((System) r).getDamage() > 0) {
                            ((System) r).setDamage(((System) r).getDamage() - r.getCrew().size());
                            if (((System) r).getDamage() < 0) {
                                ((System) r).setDamage(0);
                            }
                        }
                    }
                }
            }
            // ========== Heal Crew in Medbay ==========
            for (Room r : ship.getSystems()) {
                if (r.isSystem()) {
                    if (((System) r).getSystemType().equals(SystemType.MEDBAY)) {
                        for (Crew c : r.getCrew()) {
                            if (c.getHealth() < 8) {
                                c.setHealth(c.getHealth() + 1);
                            }
                        }
                    }
                }
            }
            // ========== Charge FTL Drive ==========
            if (ship.getFTLCharge() < 100) {
                ship.setFTLCharge(ship.getFTLCharge() + 10);
            }
            shipDAO.update(ship);
            return ship;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
    }
}
