package com.galaxytrucker.galaxytruckerreloaded.Server.Services;

import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Overworld;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Planet;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.System;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.SystemType;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.WeaponType;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.*;
import com.galaxytrucker.galaxytruckerreloaded.Server.PreviousRoundAction;
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

    /** Battle service dao */
    @Transient
    private BattleServiceDAO battleServiceDAO = BattleServiceDAO.getInstance();

    /** Overworld DAO */
    @Transient
    private OverworldDAO overworldDAO = OverworldDAO.getInstance();

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

    /** Last action carried out */
    private PreviousRoundAction previousRoundAction;

    /** Previous weapon type used */
    private WeaponType previousWeaponUsed;

    /** Give updated ships to clients
     * @param clientShip - the client's ship
     * @param overworld - the clien't overworld
     * @return a responseObject containing the updated ships */
    @SuppressWarnings("Duplicates")
    public ResponseObject getUpdatedData(Ship clientShip, Overworld overworld){
        ResponseObject responseObject = new ResponseObject();
        // While not your turn, stuck in waiting loop
        while (clientShip.getId()!=currentRound){
            continue;
        }
        // Set valid request
        responseObject.setValidRequest(true);
        // Put in the previous action
        if (previousRoundAction != null){
            responseObject.setPreviousRoundAction(previousRoundAction);
        }
        // Set previous weapon used
        if (previousWeaponUsed != null){
            responseObject.setWeaponUsed(previousWeaponUsed);
        }
        // Set the updated data
        if (clientShip.getId()==playerOne.getId()){
            if (playerOne.getHp()<=0){
                responseObject.setCombatOver(true);
                responseObject.setDead(true);
                responseObject.setPreviousRoundAction(PreviousRoundAction.YOU_DEAD);
                try {
                    shipDAO.remove(playerOne);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
            else if (playerTwo.getHp()<=0){
                responseObject.setCombatOver(true);
                responseObject.setCombatWon(true);
                responseObject.setPreviousRoundAction(PreviousRoundAction.OPPONENT_DEAD);
                List<Planet> planets = overworld.getPlanetMap();
                if (planets.contains(playerOne.getPlanet())){
                    Planet current = playerOne.getPlanet();
                    List<Ship> ships = current.getShips();
                    ships.remove(playerTwo);
                    current.setShips(ships);
                    for (Planet p : planets){
                        if (p.getId() == current.getId()){
                            planets.set(planets.indexOf(p),current);
                        }
                    }
                    overworld.setPlanetMap(planets);
                    try {
                        overworldDAO.update(overworld);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
                try {
                    shipDAO.remove(playerTwo);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
            else if (previousRoundAction!=null && previousRoundAction==PreviousRoundAction.FLEE_FIGHT){
                responseObject.setCombatWon(true);
                responseObject.setCombatOver(true);
                // Todo add rewards
            }
            responseObject.setResponseShip(playerOne);
            responseObject.setOpponent(playerTwo);
            responseObject.setMyRound(true);
        }
        else{
            if (playerOne.getHp()<=0){
                responseObject.setCombatOver(true);
                responseObject.setCombatWon(true);
                responseObject.setPreviousRoundAction(PreviousRoundAction.OPPONENT_DEAD);
                List<Planet> planets = overworld.getPlanetMap();
                if (planets.contains(playerOne.getPlanet())){
                    Planet current = playerOne.getPlanet();
                    List<Ship> ships = current.getShips();
                    ships.remove(playerOne);
                    current.setShips(ships);
                    for (Planet p : planets){
                        if (p.getId() == current.getId()){
                            planets.set(planets.indexOf(p),current);
                        }
                    }
                    overworld.setPlanetMap(planets);
                    try {
                        overworldDAO.update(overworld);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    try {
                        shipDAO.remove(playerOne);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
            else if (playerTwo.getHp()<=0){
                responseObject.setCombatOver(true);
                responseObject.setDead(true);
                responseObject.setPreviousRoundAction(PreviousRoundAction.YOU_DEAD);
                try {
                    shipDAO.remove(playerTwo);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
            else if (previousRoundAction!=null && previousRoundAction==PreviousRoundAction.FLEE_FIGHT){
                responseObject.setCombatWon(true);
                responseObject.setCombatOver(true);
                // Todo add rewards
            }
            responseObject.setResponseShip(playerTwo);
            responseObject.setOpponent(playerOne);
            responseObject.setMyRound(true);
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
     * @param room - room to attack
     * @param seed - world seed */
    public ResponseObject attackShip(Ship ship,Weapon weapon,Ship opponent,Room room,int seed){
        ResponseObject responseObject = new ResponseObject();
        try {
            if (currentRound == ship.getId()) {
                if (weapon.getMissileCost()>0 && ship.getMissiles() >= weapon.getMissileCost()){
                    ship.setMissiles(ship.getMissiles()-weapon.getMissileCost());
                }
                else{
                    return responseObject;
                }
                if (cooldowns.containsKey(weapon)){
                    return responseObject;
                }
                // TODO check energy requirement
                // Damage shields
                int weaponDamange = (int) (weapon.getDamage() * weapon.getWeaponLevel() * weapon.getAccuracy());
                Random random = new Random(seed);
                // TODO remove me, just in case atm
                if (weaponDamange == 0) {
                    weaponDamange = 1;
                }
                weaponDamange = weaponDamange - random.nextInt(weaponDamange / 2);
                int piercing = weapon.getShieldPiercing();
                // ===== Damage shields =====
                int shieldDamage = opponent.getShields() - piercing;
                while (shieldDamage > 0) {
                    if (weaponDamange > 0) {
                        weaponDamange -= 1;
                        opponent.setShields(opponent.getShields() - 1);
                        shieldDamage -= 1;
                    } else {
                        break;
                    }
                }
                weaponDamange -= shieldDamage;
                if (piercing == 0){
                    while (opponent.getShields()>0){
                        if (weaponDamange>0){
                            opponent.setShields(opponent.getShields()-1);
                            weaponDamange -= 1;
                        }
                        else {
                            break;
                        }
                    }
                }
                // ===== Damage hull =====
                while (weaponDamange > 0) {
                    opponent.setHp(opponent.getHp() - 1);
                    weaponDamange -= 1;
                }
                // ===== Damage crew in room =====
                for (Room r : opponent.getSystems()) {
                    if (r.getId() == room.getId()) {
                        List<Crew> crewInRoom = r.getCrew();
                        for (Crew c : crewInRoom) {
                            c.setHealth(c.getHealth() - weapon.getCrewDamage());
                            if (c.getHealth() <= 0) {
                                crewInRoom.remove(c);
                            }
                        }
                        r.setCrew(crewInRoom);
                    }
                }
                // ===== Attempt to cause a breach =====
                if (random.nextInt(5) == 0) {
                    for (Room r : opponent.getSystems()) {
                        if (r.getId() == room.getId()) {
                            r.setBreach(random.nextInt(3) + 2);
                        }
                    }
                }
                // ===== Add weapon cooldown =====
                cooldowns.put(weapon,weapon.getCooldown());
                // ===== Combat Won ===== //TODO add all crew dead
                if (opponent.getHp() <= 0) {
                    responseObject.setCombatWon(true);
                    responseObject.setCombatOver(true);
                    ship.setInCombat(false);
                    // TODO add rewards
                }
                // ===== Update data =====
                shipDAO.update(ship);
                shipDAO.update(opponent);
                battleServiceDAO.update(this);
                // ===== Switch round =====
                if (!responseObject.isCombatOver()) {
                    if (playerOne.getId() == ship.getId()){
                        playerOne = ship;
                        playerTwo = opponent;
                    }
                    else{
                        playerOne = opponent;
                        playerTwo = ship;
                    }
                    nextRound();
                    responseObject.setMyRound(false);
                }
                if (ship.getId() == playerOne.getId()){
                    responseObject.setResponseShip(playerOne);
                    responseObject.setOpponent(playerTwo);
                }
                else{
                    responseObject.setResponseShip(playerTwo);
                    responseObject.setOpponent(playerOne);
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
            responseObject.setValidRequest(false);
        }
        return responseObject;
    }


    /** Flee fight
     * @param ship - the ship to flee the fight */
    public ResponseObject fleeFight(Ship ship){
        ResponseObject responseObject = new ResponseObject();

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
            battleServiceDAO.update(this);
            return ship;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
    }
}
