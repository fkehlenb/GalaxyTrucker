package com.galaxytrucker.galaxytruckerreloaded.Server.Services;

import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.System;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.SystemType;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Tile;
import com.galaxytrucker.galaxytruckerreloaded.Model.User;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.WeaponType;
import com.galaxytrucker.galaxytruckerreloaded.Server.Opponent.NormalAI;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.*;
import com.galaxytrucker.galaxytruckerreloaded.Server.PreviousRoundAction;
import com.galaxytrucker.galaxytruckerreloaded.Server.RequestObject;
import com.galaxytrucker.galaxytruckerreloaded.Server.RequestType;
import com.galaxytrucker.galaxytruckerreloaded.Server.ResponseObject;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

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
@SuppressWarnings("Duplicates")
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
     * Battle service dao
     */
    @Transient
    private BattleServiceDAO battleServiceDAO = BattleServiceDAO.getInstance();

    /**
     * Overworld DAO
     */
    @Transient
    private OverworldDAO overworldDAO = OverworldDAO.getInstance();

    /**
     * CrewDAO
     */
    @Transient
    private CrewDAO crewDAO = CrewDAO.getInstance();

    /**
     * Room DAO
     */
    @Transient
    private RoomDAO roomDAO = RoomDAO.getInstance();

    /**
     * Tile DAO
     */
    @Transient
    private TileDAO tileDAO = TileDAO.getInstance();

    /**
     * Weapon DAO
     */
    @Transient
    private WeaponDAO weaponDAO = WeaponDAO.getInstance();

    /**
     * Crew service
     */
    @Transient
    private CrewService crewService = CrewService.getInstance();

    /**
     * ID of current round ship
     */
    @NonNull
    private int currentRound;

    /**
     * Battle participants
     */
    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Ship> combatants = new ArrayList<>();

    /**
     * Combat over?
     */
    private boolean combatOver = false;

    /** Winner */
    private int winner;

    /**
     * AI simulating enemy
     */
    @Transient
    private NormalAI ai = NormalAI.getInstance();

    /**
     * Last action carried out
     */
    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<PreviousRoundAction> previousRoundActions = new ArrayList<>();

    /**
     * Previous weapon type used
     */
    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<WeaponType> previousWeaponsUsed = new ArrayList<>();

    /**
     * Attack object queue
     */
    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<RequestObject> roundActions = new ArrayList<>();

    /**
     * Is it your round?
     *
     * @param ship - the client's ship
     * @return yourRound
     */
    private boolean myRound(Ship ship) {
        int id = ship.getId();
        java.lang.System.out.println("\n[Waiting-In-Queue]:[Ship]:" + ship.getId());
        while (true) {
            if (currentRound == id) {
                break;
            }
        }
        java.lang.System.out.println("[Done-Waiting]:[Ship]:" + ship.getId());
        return true;
    }

    /**
     * Add a new request to the queue
     *
     * @param requestObject - the request to add
     * @return valid
     */
    public ResponseObject addToQueue(RequestObject requestObject) {
        ResponseObject responseObject = new ResponseObject();
        try {
            if (requestObject.getShip().getId() == currentRound) {
                roundActions.add(requestObject);
                responseObject.setValidRequest(true);
                java.lang.System.out.println("[Ship]:" + requestObject.getShip().getId() +
                        "[Added-Action]:" + requestObject.getRequestType() + ":[To Queue]");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseObject;
    }

    /** Flee fight
     * @param coward - the ship that wants to flee fight
     * @return valid */


    /**
     * Get updated data
     *
     * @param ship - the client's ship
     * @return the updated data
     */
    public ResponseObject getUpdatedData(Ship ship) {
        ResponseObject responseObject = new ResponseObject();
        try {
            // Fetch new data
            ship = shipDAO.getById(ship.getId());
            // Wait your round
            if (myRound(ship)) {
                // Set valid
                responseObject.setValidRequest(true);
                responseObject.setResponseShip(ship);
                // Load overworld if player
                if (!ship.getAssociatedUser().equals("[ENEMY]")) {
                    responseObject.setResponseOverworld(UserService.getInstance()
                            .getUser(ship.getAssociatedUser()).getOverworld());
                }
                // Set combat over
                responseObject.setCombatOver(combatOver);
                // If combat over
                if (combatOver) {
                    // Check if combat won
                    if (ship.getId() == winner){
                        responseObject.setCombatWon(true);
                        responseObject.setDead(false);
                        // todo rewards
                    }
                    else{
                        responseObject.setCombatWon(false);
                        responseObject.setDead(true);
                    }
                    // Clear the combatants
                    combatants.clear();
                    // Update data
                    battleServiceDAO.update(this);
                }
                if (!previousRoundActions.isEmpty()) {
                    // Set previous round actions
                    responseObject.setPreviousRoundAction(previousRoundActions);
                }
                if (!previousWeaponsUsed.isEmpty()) {
                    // Set weapons used
                    responseObject.setWeaponUsed(previousWeaponsUsed);
                }
                // Update ships in list
                for (Ship s : combatants){
                    if (s.getId()==ship.getId()){
                        // update ships in local list
                        combatants.set(combatants.indexOf(ship),ship);
                    }
                    if (s.getId()!=ship.getId()){
                        responseObject.setOpponent(s);
                    }
                }
                java.lang.System.out.println("\n[Get-Updated-Data]:[Ship]:" + ship.getId()+":[HP]:" +
                        ship.getHp() + ":[Shields]:" + ship.getShields());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseObject;
    }

    /**
     * Play the moves when round over
     *
     * @param ship - the client's ship
     */
    public ResponseObject playMoves(Ship ship) {
        ResponseObject responseObject = new ResponseObject();
        try {
            // Fetch data
            ship = shipDAO.getById(ship.getId());
            if (ship.getId() == currentRound) {
                java.lang.System.out.println("\n[PRE]:[Play-Moves]:[Current-Round]:" + currentRound);
                // ===== Passive Changes =====
                for (Ship s : combatants) {
                    passiveChanges(s);
                }
                // ===== Clear old data =====
                previousRoundActions.clear();
                previousWeaponsUsed.clear();
                // ===== Play battle moves =====
                int difficulty = 1;
                if (ship.getAssociatedUser().equals("[ENEMY]")){
                    for (Ship s : combatants){
                        if (!s.getAssociatedUser().equals("[ENEMY]")){
                            difficulty = UserService.getInstance().getUser(s.getAssociatedUser()).getOverworld().getDifficulty();
                            break;
                        }
                    }
                }
                else{
                    difficulty = UserService.getInstance().getUser(ship.getAssociatedUser()).getOverworld().getDifficulty();
                }
                java.lang.System.out.println("[ACTIONS]:" + roundActions.size());
                if (!roundActions.isEmpty()) {
                    for (RequestObject move : roundActions) {
                        if (move.getRequestType().equals(RequestType.ATTACK_SHIP)) {
                            boolean success = attackOpponent(move.getShip(), move.getOpponentShip(), move.getWeapon(), move.getRoom(),
                                    difficulty);
                            if (success) {
                                previousRoundActions.add(PreviousRoundAction.ATTACK_SHIP);
                                previousWeaponsUsed.add(move.getWeapon().getWeaponType());
                            }
                        }
                    }
                }
                roundActions.clear();
                // ===== Switch round =====
                for (Ship s : combatants) {
                    if (s.getId() != ship.getId()) {
                        currentRound = s.getId();
                        break;
                    }
                }
                responseObject.setValidRequest(true);
                responseObject.setResponseShip(shipDAO.getById(ship.getId()));
                if (!ship.getAssociatedUser().equals("[ENEMY]")) {
                    responseObject.setResponseOverworld(UserService.getInstance().getUser(ship.getAssociatedUser()).getOverworld());
                }
                java.lang.System.out.println("[Play-Moves]:[Ship]:" + ship.getId());
                java.lang.System.out.println("[POST]:[Play-Moves]:[Current-Round]:" + currentRound);
                if (shipDAO.getById(currentRound).getAssociatedUser().equals("[ENEMY]")){
                    for (Ship s : combatants){
                        if (s.getId()==currentRound){
                            ai.nextMove(s,ship,this);
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseObject;
    }

    /**
     * Attack opponent ship
     *
     * @param ship       - the client's ship
     * @param opponent   - the opponent
     * @param weapon     - the weapon used
     * @param room       - the room to attack
     * @param difficulty - the game difficulty
     */
    private boolean attackOpponent(Ship ship, Ship opponent, Weapon weapon, Room room, int difficulty) {
        try {
            Random random = new Random();
            // ===== Fetch data =====
            ship = shipDAO.getById(ship.getId());
            opponent = shipDAO.getById(opponent.getId());
            weapon = weaponDAO.getById(weapon.getId());
            room = roomDAO.getById(room.getId());
            // ===== Check existence of data =====
            boolean weaponEquipped = false;
            boolean existsInShip = false;
            int energyInWeaponSystem = 0;
            for (Room r : ship.getSystems()) {
                if (r.isSystem() && ((System) r).getSystemType().equals(SystemType.WEAPON_SYSTEM)) {
                    if (((System) r).getShipWeapons().contains(weapon)) {
                        weaponEquipped = true;
                        energyInWeaponSystem = ((System) r).getEnergy();
                    }
                }
            }
            for (Room r : opponent.getSystems()) {
                if (r.equals(room)) {
                    existsInShip = true;
                }
            }
            if (existsInShip && weaponEquipped && energyInWeaponSystem > 0) {
                // ===== Check for cooldown =====
                if (weapon.getCurrentCooldown() == 0) {
                    // ===== Check for rocket cost =====
                    if (ship.getMissiles() >= weapon.getMissileCost()) {
                        // ===== Set cooldown =====
                        weapon.setCurrentCooldown(weapon.getCooldown());
                        weaponDAO.update(weapon);
                        // ===== Remove rockets =====
                        ship.setMissiles(ship.getMissiles() - weapon.getMissileCost());
                        shipDAO.update(ship);
                        // ===== Weapon burst =====
                        for (int i = 0; i < weapon.getBurst(); i++) {
                            // ===== Compute weapon damage =====
                            int damage = (int) ((float) weapon.getDamage() * weapon.getAccuracy() * ((float) weapon.getWeaponLevel()) / (float) energyInWeaponSystem);
                            java.lang.System.out.println("[Damage]:" + damage);
                            // ===== Pierce =====
                            if (weapon.getShieldPiercing() < opponent.getShields()) {
                                damage -= (opponent.getShields() - weapon.getShieldPiercing());
                            }
                            // Remove pierce
                            int shieldLevel = opponent.getShields() - weapon.getShieldPiercing();
                            if (shieldLevel < 0) {
                                shieldLevel = 0;
                            }
                            // Cause shield damage
                            opponent.setShields(shieldLevel);
                           while (damage>0){
                                if (opponent.getShields() > 0) {
                                    opponent.setShields(opponent.getShields() - 1);
                                    damage-=1;
                                } else {
                                    break;
                                }
                            }
                            opponent.setShields(weapon.getShieldPiercing());
                            // Damage ship hull
                            while (damage>0){
                                opponent.setHp(opponent.getHp()-1);
                                damage-=1;
                            }
                            shipDAO.update(opponent);
                            // Damage system and disable it
//                            if (damage > 0 && room.isSystem()) {
//                                System s = (System) room;
//                                s.setDamage(damage);
//                                if (damage > 5) {
//                                    s.setDisabled(true);
//                                } todo
//                            }
                            // Damage crew in room
                            List<Crew> crewInRoom = room.getCrew();
                            for (Crew c : crewInRoom) {
                                c.setHealth(c.getHealth() - weapon.getCrewDamage());
                                if (c.getHealth() <= 0) {
                                    crewInRoom.remove(c);
                                    Tile t = c.getTile();
                                    t.setStandingOnMe(null);
                                    c.setTile(null);
                                    tileDAO.update(t);
                                    crewDAO.update(c);
                                }
                            }
                            room.setCrew(crewInRoom);
                            // Attempt to cause a breach
                            if (difficulty<=0){
                                difficulty=1;
                            } // todo negative bound
                            int randomInt = random.nextInt((int) (weapon.getBreachChance() * (float) difficulty * 10f));
                            if (randomInt == 0) {
                                room.setBreach(5);
                            }
                            roomDAO.update(room);
                            shipDAO.update(opponent);
                        }
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Passive changes
     *
     * @param ship - the ship to apply passive changes to
     */
    private void passiveChanges(Ship ship) {
        try {
            // todo
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
