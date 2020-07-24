package com.galaxytrucker.galaxytruckerreloaded.Server.Services;

import com.galaxytrucker.galaxytruckerreloaded.Communication.ClientControllerCommunicator;
import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Planet;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.PlanetEvent;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.System;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.SystemType;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Tile;
import com.galaxytrucker.galaxytruckerreloaded.Model.User;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.WeaponType;
import com.galaxytrucker.galaxytruckerreloaded.Server.*;
import com.galaxytrucker.galaxytruckerreloaded.Server.Opponent.NormalAI;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.*;
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
     * Planet DAO
     */
    @Transient
    private PlanetDAO planetDAO = PlanetDAO.getInstance();

    /**
     * Travel service
     */
    @Transient
    private TravelService travelService = TravelService.getInstance();

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

    /**
     * Winner
     */
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
     * Reward weapons
     */
    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Weapon> rewardWeapons = new ArrayList<>();

    /**
     * Reward crew member
     */
    @ManyToOne
    private Crew rewardCrew;

    /**
     * Reward money
     */
    private int rewardCash = 0;

    /**
     * Reward rockets
     */
    private int rewardRockets = 0;

    /**
     * Reward fuel
     */
    private int rewardFuel = 0;

    /**
     * Is it your round?
     *
     * @param ship - the client's ship
     * @return yourRound
     */
    private boolean myRound(Ship ship) {
        int id = ship.getId();
        java.lang.System.out.println("[Waiting-In-Queue]:[Ship]:" + ship.getId());
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

    /**
     * Handles battle over
     */
    @SuppressWarnings("all")
    private void battleOver() {
        try {
            if (this.combatOver) {
                Random random = new Random();
                Ship loser = null;
                Ship winner = null;
                for (Ship s : combatants) {
                    if (s.getId() == this.winner) {
                        winner = s;
                    } else {
                        loser = s;
                    }
                }
                if (!winner.getAssociatedUser().equals("[ENEMY]")) {
                    if (!winner.getPlanet().getEvent().equals(PlanetEvent.PVP)) {
                        Planet p = winner.getPlanet();
                        p.getShips().remove(loser);
                        planetDAO.update(p);
                    } // todo else
                    // Possible weapon loot
                    List<Weapon> weaponLoot = new ArrayList<>();
                    weaponLoot.addAll(loser.getInventory());
                    for (Room r : loser.getSystems()) {
                        if (r.isSystem() && ((System) r).getSystemType().equals(SystemType.WEAPON_SYSTEM)) {
                            weaponLoot.addAll(((System) r).getShipWeapons());
                        }
                    }
                    if (winner.getInventory().size() < 4 && weaponLoot.size() > 1) {
                        Weapon temp = weaponLoot.get(random.nextInt(weaponLoot.size() - 1));
                        Weapon loot = new Weapon(UUID.randomUUID().hashCode(), temp.getWeaponType(), temp.getWeaponLevel(), temp.getDamage(), temp.getCooldown(),
                                temp.getEnergy(), temp.getMissileCost(), temp.getAccuracy(), temp.getDropChance(), temp.getShieldPiercing(), temp.getBreachChance(),
                                temp.getCrewDamage(), temp.getBurst(), temp.getWeaponName(), temp.getWeaponPrice());
                        try {
                            weaponDAO.persist(loot);
                            winner.getInventory().add(loot);
                            shipDAO.update(winner);
                            this.rewardWeapons.add(loot);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    // Possible fuel loot
                    this.rewardFuel = random.nextInt(loser.getFuel() + 1);
                    this.rewardCash = random.nextInt(loser.getCoins() + 1);
                    this.rewardRockets = random.nextInt(loser.getMissiles() + 1);
                    winner.setFuel(winner.getFuel() + rewardFuel);
                    winner.setCoins(winner.getCoins() + rewardCash);
                    winner.setMissiles(winner.getMissiles() + rewardRockets);
                    shipDAO.update(winner);
                    // possible crew loot
                    int randomNumber = random.nextInt(25);
                    if (randomNumber == 0) {
                        List<Integer> crewStats = new ArrayList<>();
                        crewStats.add(random.nextInt(10));
                        crewStats.add(random.nextInt(10));
                        crewStats.add(random.nextInt(10));
                        crewStats.add(random.nextInt(10));
                        crewStats.add(random.nextInt(10));
                        String[] crewNames = {"hALLO", "BOIIIIII", "Darth Vader", "Luke Skywalker", "Anakin", "Leia", "Jabba the Hutt"};
                        Crew tempCrew = new Crew(UUID.randomUUID().hashCode(), crewNames[random.nextInt(crewNames.length - 1)], 8, 8,
                                crewStats, random.nextInt(25), winner.getAssociatedUser());
                        try {
                            crewDAO.persist(tempCrew);
                            for (Room r : winner.getSystems()) {
                                for (Tile t : r.getTiles()) {
                                    if (t.isEmpty()) {
                                        t.setStandingOnMe(tempCrew);
                                        tempCrew.setTile(t);
                                        r.getCrew().add(tempCrew);
                                        shipDAO.update(winner);
                                        break;
                                    }
                                }
                            }
                            this.rewardCrew = tempCrew;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    // ===== Repair systems, heal crew, restore shields, replenish cooldowns, repair breaches =====
                    for (Ship s : combatants) {
                        if (s.getId() == this.winner) {
                            for (Room r : s.getSystems()) {
                                if (r.getBreach() > 0) {
                                    r.setBreach(0);
                                }
                                if (r.isSystem() && ((System) r).isDisabled()) {
                                    ((System) r).setDisabled(false);
                                }
                                if (r.isSystem() && ((System) r).getDamage() > 0) {
                                    ((System) r).setDamage(0);
                                }
                                if (r.isSystem() && ((System) r).getSystemType().equals(SystemType.MEDBAY)) {
                                    for (Crew c : r.getCrew()) {
                                        c.setHealth(c.getMaxhealth());
                                    }
                                }
                                if (r.isSystem() && ((System) r).getSystemType().equals(SystemType.WEAPON_SYSTEM)) {
                                    for (Weapon w : ((System) r).getShipWeapons()) {
                                        w.setCurrentCooldown(0);
                                        weaponDAO.update(w);
                                    }
                                }
                            }
                            s.setShields(s.getShieldCharge() / 2);
                            s.setFTLCharge(100);
                            java.lang.System.out.println("[COMBAT-OVER-WON]");
                        }
                        shipDAO.update(s);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
                java.lang.System.out.println("[COMBAT-OVER]:" + combatOver);
                responseObject.setCombatOver(combatOver);
                // If combat over
                if (combatOver) {
                    // Check if combat won
                    if (ship.getId() == winner) {
                        responseObject.setCombatWon(true);
                        responseObject.setDead(false);
                        responseObject.setRewardWeapons(rewardWeapons);
                        if (rewardCrew != null) {
                            responseObject.setRewardCrew(rewardCrew);
                        }
                        responseObject.setRewardCash(rewardCash);
                        responseObject.setRewardFuel(rewardFuel);
                        responseObject.setRewardRockets(rewardRockets);
                    } else {
                        responseObject.setCombatWon(false);
                        responseObject.setDead(true);
                    }
                    // Remove yourself
                    for (Ship s : combatants) {
                        if (s.getId() == ship.getId()) {
                            combatants.remove(s);
                            break;
                        }
                    }
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
                if (!combatants.isEmpty()) {
                    for (Ship s : combatants) {
                        if (s.getId() == ship.getId()) {
                            // update ships in local list
                            combatants.set(combatants.indexOf(ship), ship);
                        }
                        if (s.getId() != ship.getId()) {
                            responseObject.setOpponent(s);
                        }
                    }
                }
                java.lang.System.out.println("[Get-Updated-Data]:[Ship]:" + ship.getId() + ":[HP]:" +
                        ship.getHp() + ":[Shields]:" + ship.getShields());
                // If all ships removed, remove this from communicator list
                if (combatants.isEmpty()) {
                    ServerServiceCommunicator.getInstance().getBattleServices().remove(this);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseObject;
    }

    /**
     * Flee fight
     *
     * @param coward - the ship that wants to flee fight
     * @param planet - the planet to flee to
     * @return valid
     */
    public ResponseObject fleeFight(Ship coward, Planet planet) {
        ResponseObject responseObject = new ResponseObject();
        try {
            Random random = new Random();
            // fetch data
            coward = shipDAO.getById(coward.getId());
            planet = planetDAO.getById(planet.getId());
            if (currentRound == coward.getId() && coward.getFTLCharge() == 100) {
                // ===== Compute Evasion chance =====
                if (coward.getEvasionChance() == 0f) {
                    coward.setEvasionChance(0.33f);
                }
                int successfulAttempt = (int) (coward.getEvasionChance() * (float) random.nextInt(100));
                java.lang.System.out.println("[SHIP-EVASION]" + coward.getEvasionChance() + "[EVASION]:" + successfulAttempt);
                // ===== Successful evasion =====
                if (successfulAttempt >= 15) {
                    // Attempt the hyperJump
                    coward.setInCombat(false);
                    ResponseObject responseObject1 = travelService.jump(coward, planet);
                    // ===== Valid hyperJump =====
                    if (responseObject1.isValidRequest()) {
                        // ===== Repair systems, heal crew, restore shields, replenish cooldowns, repair breaches =====
                        for (Ship s : combatants) {
                            for (Room r : s.getSystems()) {
                                if (r.getBreach() > 0) {
                                    r.setBreach(0);
                                }
                                if (r.isSystem() && ((System) r).isDisabled()) {
                                    ((System) r).setDisabled(false);
                                }
                                if (r.isSystem() && ((System) r).getDamage() > 0) {
                                    ((System) r).setDamage(0);
                                }
                                if (r.isSystem() && ((System) r).getSystemType().equals(SystemType.MEDBAY)) {
                                    for (Crew c : r.getCrew()) {
                                        c.setHealth(c.getMaxhealth());
                                    }
                                }
                                if (r.isSystem() && ((System) r).getSystemType().equals(SystemType.WEAPON_SYSTEM)) {
                                    for (Weapon w : ((System) r).getShipWeapons()) {
                                        w.setCurrentCooldown(0);
                                        weaponDAO.update(w);
                                    }
                                }
                            }
                            s.setShields(s.getShieldCharge() / 2);
                            // Because of travel
                            if (!s.isInCombat()) {
                                s.setFTLCharge(100);
                            }
                            shipDAO.update(s);
                        }
                        // ===== Set valid data =====
                        responseObject1.setCombatWon(false);
                        responseObject1.setResponseShip(responseObject1.getResponseShip());
                        responseObject1.setCombatOver(true);
                        responseObject1.setDead(false);
                        // Set combat over
                        combatOver = true;
                        // Clear actions and set opponent round
                        roundActions.clear();
                        // Generate rewards for other player
                        battleOver();
                        // Remove yourself
                        this.combatants.remove(coward);
                        this.setCombatants(this.combatants);
                        // Set opponent as winner
                        winner = combatants.get(0).getId();
                        this.setWinner(this.winner);
                        battleServiceDAO.update(this);
                        java.lang.System.out.println("[REMOVED-BATTLE-SERVICE]:[BattleServices]:"
                                + ServerServiceCommunicator.getInstance().getBattleServices().size());
                        // Next round
                        playMoves(coward);
                        return responseObject1;
                    }
                } else {
                    roundActions.clear();
                    playMoves(coward);
                }
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
                java.lang.System.out.println("[PRE]:[Play-Moves]:[Ship]:" + ship.getId() + "[Current-Round]:" + currentRound);
                // ===== Clear old data =====
                previousRoundActions.clear();
                previousWeaponsUsed.clear();
                // ===== Play battle moves =====
                int difficulty = 1;
                if (ship.getAssociatedUser().equals("[ENEMY]")) {
                    for (Ship s : combatants) {
                        if (!s.getAssociatedUser().equals("[ENEMY]")) {
                            difficulty = UserService.getInstance().getUser(s.getAssociatedUser()).getOverworld().getDifficulty();
                            break;
                        }
                    }
                } else {
                    difficulty = UserService.getInstance().getUser(ship.getAssociatedUser()).getOverworld().getDifficulty();
                }
                // todo change diff 0 to 1
                if (difficulty == 0) {
                    difficulty = 1;
                }
                java.lang.System.out.println("[ACTIONS]:" + roundActions.size());
                if (!roundActions.isEmpty()) {
                    for (RequestObject move : roundActions) {
                        if (move.getRequestType().equals(RequestType.ATTACK_SHIP) && !combatOver) {
                            boolean success = attackOpponent(move.getShip(), move.getOpponentShip(), move.getWeapon(), move.getRoom(),
                                    difficulty);
                            if (success) {
                                previousRoundActions.add(PreviousRoundAction.ATTACK_SHIP);
                                previousWeaponsUsed.add(move.getWeapon().getWeaponType());
                            }
                        } // todo else add actions
                    }
                }
                roundActions.clear();
                // ===== Passive Changes =====
                if (!combatOver) {
                    for (Ship s : combatants) {
                        passiveChanges(s);
                    }
                } else {
                    // Check if combat won
                    if (ship.getId() == winner) {
                        responseObject.setCombatWon(true);
                        responseObject.setDead(false);
                        responseObject.setRewardWeapons(rewardWeapons);
                        if (rewardCrew != null) {
                            responseObject.setRewardCrew(rewardCrew);
                        }
                        responseObject.setRewardCash(rewardCash);
                        responseObject.setRewardFuel(rewardFuel);
                        responseObject.setRewardRockets(rewardRockets);
                    } else {
                        responseObject.setCombatWon(false);
                        responseObject.setDead(true);
                    }
                    // Remove yourself
                    for (Ship s : combatants) {
                        if (s.getId() == ship.getId()) {
                            combatants.remove(s);
                            break;
                        }
                    }
                    // Update data
                    battleServiceDAO.update(this);
                }
                // ===== Switch round =====
                if (!combatants.isEmpty()) {
                    for (Ship s : combatants) {
                        if (s.getId() != ship.getId()) {
                            currentRound = s.getId();
                            break;
                        }
                    }
                }
                // ===== Set valid =====
                responseObject.setValidRequest(true);
                responseObject.setResponseShip(shipDAO.getById(ship.getId()));
                if (!ship.getAssociatedUser().equals("[ENEMY]")) {
                    responseObject.setResponseOverworld(UserService.getInstance().getUser(ship.getAssociatedUser()).getOverworld());
                }
                // Verification
                java.lang.System.out.println("[POST]:[Play-Moves]:[Ship]:" + ship.getId() + "[Current-Round]:" + currentRound);
                // ===== AI Playing =====
                if (!combatants.isEmpty()) {
                    if (shipDAO.getById(currentRound).getAssociatedUser().equals("[ENEMY]")) {
                        // Fight still going on
                        if (combatants.size() > 1) {
                            for (Ship s : combatants) {
                                if (s.getId() == currentRound) {
                                    ai.nextMove(s, ship, this);
                                    break;
                                }
                            }
                        }
                        // Fight over
                        else {
                            combatants.clear();
                            battleServiceDAO.update(this);
                            ServerServiceCommunicator.getInstance().getBattleServices().remove(this);
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
                            while (damage > 0) {
                                if (opponent.getShields() > 0) {
                                    opponent.setShields(opponent.getShields() - 1);
                                    damage -= 1;
                                } else {
                                    break;
                                }
                            }
                            opponent.setShields(weapon.getShieldPiercing());
                            // todo remove me
                            damage = 1000;
                            // Damage ship hull
                            while (damage > 0) {
                                opponent.setHp(opponent.getHp() - 1);
                                damage -= 1;
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
                            if (difficulty <= 0) {
                                difficulty = 1;
                            } // todo negative bound exception
                            int randomInt = random.nextInt((int) (weapon.getBreachChance() * (float) difficulty * 10f));
                            if (randomInt == 0) {
                                room.setBreach(5);
                            }
                            roomDAO.update(room);
                            shipDAO.update(opponent);
                            List<Crew> crewOnBoard = new ArrayList<>();
                            for (Room r : opponent.getSystems()) {
                                crewOnBoard.addAll(r.getCrew());
                            }
                            if (opponent.getHp() <= 0 || crewOnBoard.isEmpty()) {
                                this.combatOver = true;
                                this.winner = ship.getId();
                                battleOver();
                            }
                        }
                        // ===== CoolDown =====
                        if (!this.combatOver) {
                            weapon.setCurrentCooldown(weapon.getCooldown());
                            weaponDAO.update(weapon);
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
            // fetch data
            ship = shipDAO.getById(ship.getId());
            // Verification
            java.lang.System.out.println("[PRE]:[PASSIVE-CHANGES]:[Ship]:" + ship.getId() + ":[FTL]:" + ship.getFTLCharge());
            // ===== Recharge FTL Drive =====
            for (Room r : ship.getSystems()) {
                if (r.isSystem() && ((System) r).getSystemType().equals(SystemType.ENGINE) && !((System) r).isDisabled()) {
                    if (ship.getFTLCharge() < 100) {
                        ship.setFTLCharge(ship.getFTLCharge() + 10);
                    }
                }
            }
            // ===== Replenish shields =====
            // todo correct amount
            if (ship.getShields() < ship.getShieldCharge() / 2) {
                for (Room r : ship.getSystems()) {
                    if (r.isSystem() && ((System) r).getSystemType().equals(SystemType.SHIELDS)
                            && !((System) r).isDisabled() && ((System) r).isUnlocked()) {
                        // todo system energy effect
                        // Crew effect: more shields
                        if (r.getCrew().size() > 0) {
                            if (ship.getShields() + r.getCrew().size() > ship.getShieldCharge() / 2) {
                                ship.setShields(ship.getShieldCharge() / 2);
                            } else {
                                ship.setShields(ship.getShields() + r.getCrew().size());
                            }
                        } else {
                            ship.setShields(ship.getShields() + 1);
                        }
                    }
                }
            }
            // ====== Remove 02 from rooms with breach =====
            for (Room r : ship.getSystems()) {
                if (r.getBreach() > 0 && r.getOxygen() > 0) {
                    r.setOxygen(r.getOxygen() - 25);
                }
            }
            // ===== Damage crew in rooms without o2 ======
            for (Room r : ship.getSystems()) {
                if (r.getBreach() > 0 && r.getOxygen() <= 0) {
                    for (Crew c : r.getCrew()) {
                        c.setHealth(c.getHealth() - 1);
                        if (c.getHealth() <= 0) {
                            r.getCrew().remove(c);
                        }
                    }
                }
            }
            // ===== Repair rooms with breach and crew =====
            for (Room r : ship.getSystems()) {
                if (r.getBreach() > 0 && r.getCrew().size() > 0) {
                    for (Crew c : r.getCrew()) {
                        if (!c.isJustMoved()) {
                            r.setBreach(r.getBreach() - 1);
                            if (r.getBreach() <= 0) {
                                r.setBreach(0);
                                break;
                            }
                        } else {
                            c.setJustMoved(false);
                        }
                    }
                }
            }
            // ===== Repair systems =====
            for (Room r : ship.getSystems()) {
                if (r.isSystem() && ((System) r).getDamage() > 0 && !r.getCrew().isEmpty()) {
                    ((System) r).setDamage(((System) r).getDamage() - r.getCrew().size());
                    if (((System) r).getDamage() < 0) {
                        ((System) r).setDamage(0);
                    }
                    // Re-enable system if disabled
                    if (((System) r).getDamage() == 0) {
                        ((System) r).setDisabled(false);
                    }
                }
            }
            // ===== Weapon coolDown replenishment =====
            for (Room r : ship.getSystems()) {
                if (r.isSystem() && ((System) r).getSystemType().equals(SystemType.WEAPON_SYSTEM)) {
                    for (Weapon w : ((System) r).getShipWeapons()) {
                        w.setCurrentCooldown(w.getCurrentCooldown() - 1);
                        if (w.getCurrentCooldown() < 0) {
                            w.setCurrentCooldown(0);
                        }
                        java.lang.System.out.println("[WEAPON]:" + w.getWeaponName() + ":[WEAPON-COOLDOWN]:" + w.getCurrentCooldown());
                    }
                    break;
                }
            }
            // ===== Check for all crew dead, if yes, game end =====
            List<Crew> shipCrew = new ArrayList<>();
            for (Room r : ship.getSystems()) {
                shipCrew.addAll(r.getCrew());
            }
            if (shipCrew.isEmpty()) {
                this.combatOver = true;
                for (Ship s : combatants) {
                    if (s.getId() != ship.getId()) {
                        this.winner = s.getId();
                        break;
                    }
                }
                // Battle over
                battleOver();
                battleServiceDAO.update(this);
            }
            // Update ship
            shipDAO.update(ship);
            // Verification
            java.lang.System.out.println("[POST]:[PASSIVE-CHANGES]:[Ship]:" + ship.getId() + ":[FTL]:" + ship.getFTLCharge());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || o.getClass() != this.getClass()) {
            return false;
        }
        return ((BattleService) o).getId() == this.id;
    }
}
