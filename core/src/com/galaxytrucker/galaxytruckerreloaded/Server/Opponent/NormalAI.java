package com.galaxytrucker.galaxytruckerreloaded.Server.Opponent;

import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.System;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.SystemType;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import com.galaxytrucker.galaxytruckerreloaded.Server.RequestObject;
import com.galaxytrucker.galaxytruckerreloaded.Server.ResponseObject;
import com.galaxytrucker.galaxytruckerreloaded.Server.Services.BattleService;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Controls the opponent ship during combat
 */
public class NormalAI {

    /**
     * Targeting modes
     */
    private List<TargetMode> targets = new ArrayList<>();

    /**
     * Crew health
     */
    int[] crewHealth;

    /**
     * Previous crew health
     */
    int[] previousCrewHealth;

    /**
     * Instance
     */
    private static NormalAI instance;

    /**
     * Get instance
     */
    public static NormalAI getInstance() {
        if (instance == null) {
            instance = new NormalAI();
        }
        return instance;
    }

    /**
     * Get the client's last move and use it to learn
     *
     * @param responseObject - responseObject containing the last move
     * @param weapons        - the ship's weapons
     */
    private synchronized void learn(ResponseObject responseObject, List<Weapon> weapons) {
        try {
            if (responseObject.getPreviousRoundAction() != null) {
                if (previousCrewHealth != null) {
                    List<Crew> crew = new ArrayList<>();
                    for (Room r : responseObject.getResponseShip().getSystems()) {
                        crew.addAll(r.getCrew());
                    }
                    crewHealth = new int[crew.size()];
                    for (int i = 0; i < crewHealth.length; i++) {
                        crewHealth[i] = crew.get(i).getHealth();
                    }
                }
                switch (responseObject.getPreviousRoundAction()) {
                    case ATTACK_SHIP:
                        if (previousCrewHealth != null && crewHealth != null && sum(previousCrewHealth) < sum(crewHealth)) {
                            targets.add(TargetMode.CREW);
                        } else {
                            targets.add(TargetMode.WEAPON_SYSTEM);
                        }
                        previousCrewHealth = crewHealth;
                        targets.add(TargetMode.SHIELDS);
                        break;
                    case HEAL_CREW:
                        targets.add(TargetMode.CREW);
                        targets.add(TargetMode.O2);
                        targets.add(TargetMode.MEDBAY);
                        targets.add(TargetMode.ENGINE);
                        break;
                    case HEAL_CREW_IN_ROOM:
                        targets.add(TargetMode.CREW);
                        targets.add(TargetMode.O2);
                        targets.add(TargetMode.SHIELDS);
                        targets.add(TargetMode.MEDBAY);
                        break;
                    case MOVE_CREW:
                        targets.add(TargetMode.CREW);
                        targets.add(TargetMode.WEAPON_SYSTEM);
                        targets.add(TargetMode.COCKPIT);
                        break;
                    case HEAL_SHIP:
                        targets.add(TargetMode.CREW);
                        targets.add(TargetMode.O2);
                        targets.add(TargetMode.MEDBAY);
                        targets.add(TargetMode.SHIELDS);
                        targets.add(TargetMode.WEAPON_SYSTEM);
                        targets.add(TargetMode.ENGINE);
                        break;
                    default:
                        targets.add(TargetMode.WEAPON_SYSTEM);
                        targets.add(TargetMode.SHIELDS);
                        targets.add(TargetMode.COCKPIT);
                        targets.add(TargetMode.ENGINE);
                        targets.add(TargetMode.MEDBAY);
                        break;
                }
                for (Room r : responseObject.getOpponent().getSystems()) {
                    if (r.getCrew().size() > 0) {
                        if (r.isSystem() && (r.getBreach() <= 0 || !((System) r).isDisabled())) {
                            SystemType systemType = ((System) r).getSystemType();
                            switch (systemType) {
                                case MEDBAY:
                                    targets.add(TargetMode.MEDBAY);
                                    break;
                                case O2:
                                    targets.add(TargetMode.O2);
                                    break;
                                case ENGINE:
                                    targets.add(TargetMode.ENGINE);
                                    break;
                                case SHIELDS:
                                    targets.add(TargetMode.SHIELDS);
                                    break;
                                case CAMERAS:
                                    targets.add(TargetMode.CAMERAS);
                                    break;
                                case WEAPON_SYSTEM:
                                    targets.add(TargetMode.WEAPON_SYSTEM);
                                    break;
                                case COCKPIT:
                                    targets.add(TargetMode.COCKPIT);
                                    break;
                                default:
                                    targets.add(TargetMode.ROOM);
                                    break;
                            }
                        } else {
                            targets.add(TargetMode.ROOM);
                        }
                    }
                }
                if (responseObject.getResponseShip().getShields() > 0) {
                    targets.add(TargetMode.SHIELDS);
                    targets.add(TargetMode.SHIELDS);
                } else {
                    targets.add(TargetMode.COCKPIT);
                    targets.add(TargetMode.ENGINE);
                    targets.add(TargetMode.SHIELDS);
                }
                if (responseObject.getResponseShip().getFTLCharge() >= 50) {
                    targets.add(TargetMode.ENGINE);
                    targets.add(TargetMode.COCKPIT);
                }
                for (Weapon w : weapons) {
                    switch (w.getWeaponType()) {
                        case ROCKET:
                            targets.add(TargetMode.SHIELDS);
                            break;
                        case LASER:
                            targets.add(TargetMode.WEAPON_SYSTEM);
                            targets.add(TargetMode.ENGINE);
                            break;
                        case RADIO:
                            targets.add(TargetMode.CREW);
                            break;
                        case BOMB:
                            targets.add(TargetMode.WEAPON_SYSTEM);
                            targets.add(TargetMode.CREW);
                            break;
                        case RADIO_BOMB:
                            targets.add(TargetMode.CREW);
                            targets.add(TargetMode.COCKPIT);
                            break;
                    }
                }
                // Todo more intelligence
            }
            Collections.shuffle(targets);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the sum of numbers in an array
     *
     * @param array - the array
     * @return the sum of the array
     */
    private int sum(@NonNull int[] array) {
        int sum = 0;
        for (Integer i : array) {
            sum += i;
        }
        return sum;
    }

    /**
     * Determines the next move
     *
     * @param ship          - the ai's ship
     * @param opponent      - the opponent's ship
     * @param weapons       - the ship's weapons
     * @param battleService - the battleService used
     */
    public void nextMove(Ship ship, Ship opponent, List<Weapon> weapons, BattleService battleService) {
        try {
            List<RequestObject> nextMoves = new ArrayList<>();
            Random random = new Random();
            // Wait your turn
            ResponseObject responseObject = battleService.getUpdatedData(ship);
            // Battle over?
            if (!responseObject.isCombatOver()) {
                // Make the ai learn
                learn(responseObject, weapons);
                // ATTACK
                for (Weapon w : weapons) {
                    if (w.getCurrentCooldown() <= 0) {
                        Room targetRoom = null;
                        TargetMode target = targets.get(random.nextInt(targets.size() - 1));
                        if (target.equals(TargetMode.CREW)) {
                            int roomWithHighestCrew = 0;
                            for (Room r : ship.getSystems()) {
                                if (r.getCrew().size() > roomWithHighestCrew) {
                                    roomWithHighestCrew = r.getCrew().size();
                                }
                            }
                            for (Room r : ship.getSystems()) {
                                if (r.getCrew().size() == roomWithHighestCrew) {
                                    targetRoom = r;
                                }
                            }
                        } else if (target.equals(TargetMode.ROOM)) {
                            List<Room> shipRooms = ship.getSystems();
                            targetRoom = shipRooms.get(random.nextInt(shipRooms.size() - 1));
                        } else {
                            for (Room r : opponent.getSystems()) {
                                if (r.isSystem()) {
                                    if (((System) r).getSystemType().toString().equals(target.toString())) {
                                        targetRoom = r;
                                        break;
                                    }
                                }
                            }
                        }
                        RequestObject requestObject = new RequestObject();
                        requestObject.setShip(ship);
                        requestObject.setWeapon(w);
                        requestObject.setOpponentShip(opponent);
                        requestObject.setRoom(targetRoom);
                        nextMoves.add(requestObject);
                    }
                }
                for (RequestObject r : nextMoves) {
                    battleService.attackShip(r.getShip(), r.getWeapon(), r.getOpponentShip(), r.getRoom());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructor
     */
    public NormalAI() {
        for (int i = 0; i < 9; i++) {
            targets.add(TargetMode.SHIELDS);
        }
        for (int i = 0; i < 8; i++) {
            targets.add(TargetMode.WEAPON_SYSTEM);
        }
        for (int i = 0; i < 7; i++) {
            targets.add(TargetMode.CREW);
        }
        for (int i = 0; i < 6; i++) {
            targets.add(TargetMode.O2);
        }
        for (int i = 0; i < 5; i++) {
            targets.add(TargetMode.ENGINE);
        }
        for (int i = 0; i < 4; i++) {
            targets.add(TargetMode.COCKPIT);
        }
        for (int i = 0; i < 3; i++) {
            targets.add(TargetMode.MEDBAY);
        }
        for (int i = 0; i < 2; i++) {
            targets.add(TargetMode.CAMERAS);
        }
        targets.add(TargetMode.ROOM);
        Collections.shuffle(targets);
    }
}
