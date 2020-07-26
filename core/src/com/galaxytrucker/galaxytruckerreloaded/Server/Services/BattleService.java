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
                responseObject.setResponseShip(requestObject.getShip());
                java.lang.System.out.println("[Ship]:" + requestObject.getShip().getId() +
                        "[Added-Action]:" + requestObject.getRequestType() + ":[To Queue]");
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
            // Update combatants
            for (Ship s : combatants) {
                combatants.set(combatants.indexOf(s), shipDAO.getById(s.getId()));
            }
            // ===== If your round =====
            if (ship.getId() == currentRound) {
                java.lang.System.out.println("[PRE]:[Play-Moves]:[Ship]:" + ship.getId() + "[Current-Round]:" + currentRound);
                // ===== Clear old data =====
                boolean fled = false;
                if (previousRoundActions.contains(PreviousRoundAction.FLEE_FIGHT)){
                     fled = true;
                }

                previousRoundActions.clear();
                previousWeaponsUsed.clear();

                if (fled){
                    previousRoundActions.add(PreviousRoundAction.FLEE_FIGHT);
                }
                // ===== Get world difficulty =====
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
                // ===== Play round actions =====
                java.lang.System.out.println("[ACTIONS]:" + roundActions.size());
                if (!roundActions.isEmpty()) {
                    for (RequestObject move : roundActions) {
                        if (move.getRequestType().equals(RequestType.ATTACK_SHIP) && !combatOver) { // todo check weapon type
                            boolean success = attackOpponent(move.getShip(), move.getOpponentShip(), move.getWeapon(), move.getRoom(),
                                    difficulty);
                            if (success) {
                                previousRoundActions.add(PreviousRoundAction.ATTACK_SHIP);
                                previousWeaponsUsed.add(move.getWeapon().getWeaponType());
                            }
                        }
                        else if (move.getRequestType().equals(RequestType.MoveCrew) && !combatOver){
                            boolean success = moveCrew(move.getShip(),move.getCrew(),move.getRoom());
                            if (success){
                                previousRoundActions.add(PreviousRoundAction.CREW_MOVED);
                            }
                        } // todo else add actions
                    }
                }
                // Clear round actions
                roundActions.clear();
                // ===== Passive Changes =====
                if (!combatOver) {
                    for (Ship s : combatants) {
                        passiveChanges(s);
                        if (s.getId()!=ship.getId()){
                            responseObject.setOpponent(shipDAO.getById(s.getId()));
                        }
                        if (combatOver){
                            break;
                        }
                    }
                } if (combatOver){ // todo check if battle over due to passive changes
                    responseObject.setCombatOver(true);
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
                if (!combatants.isEmpty() && !combatOver) {
                    if (shipDAO.getById(currentRound).getAssociatedUser().equals("[ENEMY]")) {
                        // Fight still going on
                        for (Ship s : combatants) {
                            if (s.getId() == currentRound) {
                                ServerServiceCommunicator.getInstance().getBattleServices().set(ServerServiceCommunicator.getInstance().getBattleServices().indexOf(this), this);
                                ai.nextMove(shipDAO.getById(s.getId()), shipDAO.getById(ship.getId()), this);
                                break;
                            }
                        }

                    }
                }
                battleServiceDAO.update(this);
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
                    }
                    // Possible weapon loot
                    List<Weapon> weaponLoot = new ArrayList<>();
                    weaponLoot.addAll(loser.getInventory());
                    for (Room r : loser.getSystems()) {
                        if (r.isSystem() && ((System) r).getSystemType().equals(SystemType.WEAPON_SYSTEM)) {
                            weaponLoot.addAll(((System) r).getShipWeapons());
                            break;
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
                                boolean found = false;
                                for (Tile t : r.getTiles()) {
                                    if (t.isEmpty()) {
                                        t.setStandingOnMe(tempCrew);
                                        tempCrew.setTile(t);
                                        r.getCrew().add(tempCrew);
                                        shipDAO.update(winner);
                                        found = true;
                                        break;
                                    }
                                }
                                if (found){
                                    break;
                                }
                            }
                            this.rewardCrew = tempCrew;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    // ===== Repair systems, heal crew, restore shields, replenish cooldowns, repair breaches =====
                    for (Ship s : combatants) {
                        s.setInCombat(false);
                        s.setInvitedToPVP(false);
                        if (s.getId() == this.winner) {
                            s.setHp(s.getHp()+random.nextInt(25));
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
                            this.combatOver = true;
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
            if (combatOver){
                ship = shipDAO.getById(ship.getId());
                responseObject.setValidRequest(true);
                responseObject.setCombatOver(true);
                responseObject.setResponseShip(ship);
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
                    responseObject.setOpponent(null);
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
                if (combatants.size()==0){
                    ServerServiceCommunicator.getInstance().getBattleServices().remove(this);
                }
                // Update data
                battleServiceDAO.update(this);
            }
            // Wait your round
            else if (myRound(ship)) {
                // Fetch new data
                ship = shipDAO.getById(ship.getId());
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
                            combatants.set(combatants.indexOf(s),shipDAO.getById(s.getId()));
                            s = combatants.get(combatants.indexOf(s));
                            responseObject.setOpponent(s);
                        }
                    }
                }
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

    /** Fetch opponent after client relog
     * @param ship - the client's ship */
    public ResponseObject fetchOpponentAfterRelog(Ship ship){
        ResponseObject responseObject = new ResponseObject();
        try {
            ship = shipDAO.getById(ship.getId());
            Planet p = ship.getPlanet();
            List<Ship> ships = p.getShips();
            for (Ship s : ships){
                if (s.getId()!=ship.getId()){
                    responseObject.setValidRequest(true);
                    responseObject.setResponseShip(ship);
                    responseObject.setOpponent(s);
                    break;
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return responseObject;
    }

    /** Move crew during combat
     * @param ship - the client's ship
     * @param crew - the crew to move
     * @param room - the room to move the crew to */
    private boolean moveCrew(Ship ship,Crew crew,Room room){
        try {
            ResponseObject responseObject = crewService.moveCrewToRoom(ship,crew,room);
            if (responseObject.isValidRequest()){
                crew = crewDAO.getById(crew.getId());
                crew.setJustMoved(true);
                crewDAO.update(crew);
                return true;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
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
            // Check for round and ftl charge
            if (currentRound == coward.getId() && coward.getFTLCharge() == 100 && !combatOver) {
                // ===== Compute Evasion chance =====
                if (coward.getEvasionChance() == 0f) {
                    coward.setEvasionChance(0.33f);
                }
                // ===== Check energy in engine - adds evasion chance =====
                int energyInEngine = 0;
                for (Room r : coward.getSystems()){
                    if (r.isSystem() && ((System) r).getSystemType().equals(SystemType.ENGINE)){
                        energyInEngine = ((System) r).getEnergy();
                        break;
                    }
                }
                // ===== Compute evasion chance =====
                int successfulAttempt = (int) (coward.getEvasionChance() * (float) random.nextInt(100 + energyInEngine));
                // ===== Successful evasion =====
                if (successfulAttempt >= 15) {
                    // Attempt the hyperJump
                    ResponseObject responseObject1 = travelService.jump(coward, planet);
                    // ===== Valid hyperJump =====
                    if (responseObject1.isValidRequest()) {
                        // ===== Repair systems, heal crew, restore shields, replenish cooldowns, repair breaches =====
                        for (Ship s : combatants){
                            combatants.set(combatants.indexOf(s),shipDAO.getById(s.getId()));
                        }
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
                                    }
                                }
                            }
                            if (s.getId() != coward.getId()) {
                                s.setInCombat(false);
                                this.winner = s.getId();
                            }
                            s.setShields(s.getShieldCharge() / 2);
                            // Because of travel
                            if (!s.isInCombat()) {
                                s.setFTLCharge(100);
                            }
                            shipDAO.update(s);
                        }
                        coward = shipDAO.getById(coward.getId());
                        // ===== Set valid data =====
                        responseObject1.setCombatWon(false);
                        responseObject1.setResponseShip(coward);
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
                        // Set opponent as winner
                        winner = combatants.get(0).getId();
                        battleServiceDAO.update(this);
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
            boolean manned = false;
            List<Crew> mannedCrew = new ArrayList<>();
            int energyInWeaponSystem = 0;
            for (Room r : ship.getSystems()) {
                if (r.isSystem() && ((System) r).getSystemType().equals(SystemType.WEAPON_SYSTEM)) {
                    for (Weapon w : ((System) r).getShipWeapons()) {
                        if (w.getId() == weapon.getId()) {
                            weaponEquipped = true;
                            energyInWeaponSystem = ((System) r).getEnergy();
                            if (r.getCrew().size() > 0) {
                                manned = true;
                                mannedCrew.addAll(r.getCrew());
                            }
                            break;
                        }
                    }
                }
            }
            for (Room r : opponent.getSystems()) {
                if (r.getId() == room.getId()) {
                    existsInShip = true;
                    break;
                }
            }
            if (existsInShip && weaponEquipped && energyInWeaponSystem > 0) {
                // ===== Check for cooldown =====
                if (weapon.getCurrentCooldown() <= 0) {
                    // ===== Check for rocket cost =====
                    if (ship.getMissiles() >= weapon.getMissileCost()) {
                        // ===== Set cooldown =====
                        for (Room r : ship.getSystems()){
                            if (r.isSystem() && ((System) r).getSystemType().equals(SystemType.WEAPON_SYSTEM)){
                                for (Weapon w : ((System) r).getShipWeapons()){
                                    if (w.getId() == weapon.getId()){
                                        w.setCurrentCooldown(w.getCooldown());
                                        weaponDAO.update(w);
                                        break;
                                    }
                                }
                            }
                        }
                        // ===== Remove rockets =====
                        ship.setMissiles(ship.getMissiles() - weapon.getMissileCost());
                        shipDAO.update(ship);
                        // ===== Weapon burst =====
                        for (int i = 0; i < weapon.getBurst(); i++) {
                            // ===== Compute weapon damage =====
                            int damage;
                            if (ship.getAssociatedUser().equals("[ENEMY]")){
                                damage = random.nextInt(difficulty*2)+1;
                            }
                            else{
                                damage = random.nextInt(10)+weapon.getDamage();
                            }
                            // ===== Add crew damage =====
                            if (manned) {
                                for (Crew c : mannedCrew) {
                                    damage += c.getStats().get(0);
                                }
                            }
                            // ===== Energy adds piercing =====
                            int weaponEnergy = 0;
                            for (Room r : ship.getSystems()){
                                if (r.isSystem()&&((System) r).getSystemType().equals(SystemType.WEAPON_SYSTEM)){
                                    weaponEnergy = ((System) r).getEnergy();
                                }
                            }
                            // ===== Pierce =====
                            if (weapon.getShieldPiercing() + weaponEnergy < opponent.getShields()) {
                                damage -= (opponent.getShields() - weapon.getShieldPiercing() - weaponEnergy);
                            }
                            // Remove pierce
                            int shieldLevel = opponent.getShields() - weapon.getShieldPiercing() - weaponEnergy;
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
                            java.lang.System.out.println("[DAMAGE]:" + damage);
                            // ===== Damage ship hull =====
                            while (damage > 0) {
                                opponent.setHp(opponent.getHp() - 1);
                                damage -= 1;
                            }
                            shipDAO.update(opponent);
                            // ===== Damage system and disable it =====
                            int systemDamage = weapon.getCrewDamage() - weapon.getShieldPiercing() + random.nextInt(weapon.getDamage()+1);
                            if (systemDamage<0){
                                systemDamage = 0;
                            }
                            if (systemDamage>10){
                                systemDamage = 10;
                            }
                            for (Room r : opponent.getSystems()){
                                if (r.getId() == room.getId()&&r.isSystem()){
                                    ((System) r).setDamage(damage);
                                    if (systemDamage > 5) {
                                        ((System) r).setDisabled(true);
                                    }
                                    roomDAO.update(r);
                                }
                            }
                            // ===== Damage crew in room =====
                            for (Room r : opponent.getSystems()){
                                if (r.getId() == room.getId()){
                                    for (Crew c : r.getCrew()) {
                                        c.setHealth(c.getHealth() - weapon.getCrewDamage());
                                        java.lang.System.out.println("[CREW]:" + c.getId() + ":[DAMAGE]:" + weapon.getCrewDamage() + ":[HP]:" + c.getHealth());
                                    }
                                    List<Crew> crewInRoom = new ArrayList<>(r.getCrew());
                                    for (Crew c : r.getCrew()){
                                        if (c.getHealth()<=0){
                                            try {
                                                for (Tile t : r.getTiles()){
                                                    if(t.getStandingOnMe().equals(c)){
                                                        t.setStandingOnMe(null);
                                                        tileDAO.update(t);
                                                        break;
                                                    }
                                                }
                                            }
                                            catch (Exception f){
                                                f.printStackTrace();
                                            }
                                            c.setTile(null);
                                            c.setCurrentRoom(null);
                                            crewDAO.update(c);
                                            crewInRoom.remove(c);
                                        }
                                    }
                                    r.setCrew(crewInRoom);
                                    roomDAO.update(r);
                                    break;
                                }
                            }
                            // ===== Attempt to cause a breach =====
                            int breachChance = (int) (weapon.getBreachChance() * (float) difficulty * 10f);
                            if (breachChance <= 0) {
                                breachChance = random.nextInt(20) + 1;
                            }
                            int randomInt = random.nextInt(breachChance);
                            if (randomInt == 0) {
                                for (Room r : opponent.getSystems()){
                                    if (r.getId() == room.getId()){
                                        r.setBreach(5);
                                        roomDAO.update(r);
                                        break;
                                    }
                                }
                            }
                            shipDAO.update(opponent);
                            List<Crew> crewOnBoard = new ArrayList<>();
                            for (Room r : opponent.getSystems()) {
                                crewOnBoard.addAll(r.getCrew());
                            }
                            java.lang.System.out.println("[AMOUNT OF CREW LEFT]:" + crewOnBoard.size());
                            if (opponent.getHp() <= 0 || crewOnBoard.isEmpty()) {
                                this.combatOver = true;
                                this.winner = ship.getId();
                                battleOver();
                            }
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
            // ===== Recharge FTL Drive =====
            for (Room r : ship.getSystems()) {
                if (r.isSystem() && ((System) r).getSystemType().equals(SystemType.ENGINE) && !((System) r).isDisabled()) {
                    if (ship.getFTLCharge() < 100) {
                        ship.setFTLCharge(ship.getFTLCharge() + 10);
                        for (Crew c : r.getCrew()){
                            ship.setFTLCharge(ship.getFTLCharge() + c.getStats().get(2));
                        }
                    }
                    if (ship.getFTLCharge()>100){
                        ship.setFTLCharge(100);
                    }
                }
            }
            shipDAO.update(ship);
            // ===== Remove oxygen if no energy in o2 system =====
            for (Room r : ship.getSystems()){
                if (r.isSystem() && ((System) r).getSystemType().equals(SystemType.O2)
                        && (((System) r).getEnergy()==0 || ((System) r).isDisabled())){
                    for (Room a : ship.getSystems()){
                        if (a.getBreach() <= 0){
                            a.setOxygen(r.getOxygen()-10);
                            if (a.getOxygen()<0){
                                a.setOxygen(0);
                            }
                            roomDAO.update(a);
                        }
                    }
                    break;
                }
            }
            // ===== Relenish oxygen if energy in o2 =====
            for (Room r : ship.getSystems()){
                if (r.isSystem() && ((System) r).getSystemType().equals(SystemType.O2) && ((System) r).getEnergy()>0
                    && !((System) r).isDisabled()){
                    for (Room a : ship.getSystems()){
                        if (a.getBreach()<=0&&a.getOxygen()<100){
                            a.setOxygen(a.getOxygen() + 10 * (((System) r).getEnergy()));
                            if (a.getOxygen()>100){
                                a.setOxygen(100);
                            }
                            roomDAO.update(a);
                        }
                    }
                    break;
                }
            }
            // ===== Heal crew in medbay =====
            for (Room r : ship.getSystems()){
                if (r.isSystem() && ((System) r).getSystemType().equals(SystemType.MEDBAY) && !((System) r).isDisabled()){
                    for (Crew c : r.getCrew()){
                        if (c.getHealth()<c.getMaxhealth()){
                            c.setHealth(c.getHealth() + ((System) r).getEnergy());
                            if (c.getHealth()>c.getMaxhealth()){
                                c.setHealth(c.getMaxhealth());
                            }
                            crewDAO.update(c);
                        }
                    }
                    break;
                }
            }
            ship = shipDAO.getById(ship.getId());
            // ===== Replenish shields =====
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
                        break;
                    }
                }
                shipDAO.update(ship);
            }
            // ====== Remove 02 from rooms with breach =====
            for (Room r : ship.getSystems()) {
                if (r.getBreach() > 0 && r.getOxygen() > 0) {
                    r.setOxygen(r.getOxygen() - 25);
                    if (r.getOxygen()<0){
                        r.setOxygen(0);
                    }
                }
                roomDAO.update(r);
            }
            // ===== Damage crew in rooms without o2 ======
            for (Room r : ship.getSystems()) {
                if (r.getBreach() > 0 && r.getOxygen() <= 0) {
                    List<Crew> crewInRoom = new ArrayList<>(r.getCrew());
                    for (Crew c : r.getCrew()) {
                        c.setHealth(c.getHealth() - 1);
                        crewDAO.update(c);
                        if (c.getHealth() <= 0) {
                            crewInRoom.remove(c);
                        }
                    }
                    r.setCrew(crewInRoom);
                    roomDAO.update(r);
                }
            }
            // ===== Repair rooms with breach and crew =====
            for (Room r : ship.getSystems()) {
                if (r.getBreach() > 0 && r.getCrew().size() > 0) {
                    for (Crew c : r.getCrew()) {
                        if (!c.isJustMoved()) {
                            r.setBreach(r.getBreach() - c.getStats().get(3));
                            if (r.getBreach() <= 0) {
                                r.setBreach(0);
                                break;
                            }
                        } else {
                            c.setJustMoved(false);
                        }
                        crewDAO.update(c);
                    }
                    roomDAO.update(r);
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
                    roomDAO.update(r);
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
                        weaponDAO.update(w);
                    }
                    break;
                }
            }
            ship = shipDAO.getById(ship.getId());
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
