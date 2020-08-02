package com.galaxytrucker.galaxytruckerreloaded.Server.Services;

import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Planet;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Tile;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.WeaponType;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.*;
import com.galaxytrucker.galaxytruckerreloaded.Server.ResponseObject;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * This class handles reward handing to players
 */
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
public class PlanetRewardService {

    /**
     * Instance
     */
    private static PlanetRewardService instance;

    /**
     * Get instance
     */
    public static PlanetRewardService getInstance() {
        if (instance == null) {
            instance = new PlanetRewardService();
        }
        return instance;
    }

    /**
     * ShipDAO
     */
    private ShipDAO shipDAO = ShipDAO.getInstance();

    /**
     * Planet DAO
     */
    private PlanetDAO planetDAO = PlanetDAO.getInstance();

    /**
     * WeaponDAO
     */
    private WeaponDAO weaponDAO = WeaponDAO.getInstance();

    /**
     * Room DAO
     */
    private RoomDAO roomDAO = RoomDAO.getInstance();

    /**
     * Crew DAO
     */
    private CrewDAO crewDAO = CrewDAO.getInstance();

    /** Tile DAO */
    private TileDAO tileDAO = TileDAO.getInstance();

    /**
     * Get rewards at current planet
     *
     * @param ship   - the client's ship
     * @param planet - the planet the client is at
     */
    @SuppressWarnings("Duplicates")
    public ResponseObject getRewards(Ship ship, Planet planet) {
        ResponseObject responseObject = new ResponseObject();
        try {
            ship = shipDAO.getById(ship.getId());
            planet = planetDAO.getById(planet.getId());
            if (!planet.isLooted()) {
                responseObject.setValidRequest(true);
                planet.setLooted(true);
                planetDAO.update(planet);
                Random random = new Random(UserService.getInstance().getUser(ship.getAssociatedUser()).getOverworld().getSeed());
                int weaponRandomizer = random.nextInt(7);
                int crewRandomizer = random.nextInt(49);
                boolean spaceForCrew = false;
                for (Room r : ship.getSystems()) {
                    for (Tile t : r.getTiles()) {
                        if (t.isEmpty()) {
                            spaceForCrew = true;
                            break;
                        }
                    }
                }
                List<WeaponType> weaponTypes = new ArrayList<>();
                weaponTypes.add(WeaponType.BOMB);
                weaponTypes.add(WeaponType.LASER);
                weaponTypes.add(WeaponType.RADIO);
                weaponTypes.add(WeaponType.RADIO_BOMB);
                weaponTypes.add(WeaponType.ROCKET);
                List<Integer> weaponPrices = new ArrayList<>();
                weaponPrices.add(15);
                weaponPrices.add(25);
                weaponPrices.add(40);
                weaponPrices.add(60);
                weaponPrices.add(80);
                if (weaponRandomizer == 0 && ship.getInventory().size() < 4) {
                    WeaponType weaponType = weaponTypes.get(weaponTypes.size()-1);
                    Weapon w = null;
                    if (weaponType.equals(WeaponType.BOMB) || weaponType.equals(WeaponType.ROCKET)) {
                        w = new Weapon(UUID.randomUUID().hashCode(), weaponType, random.nextInt(3) + 1, random.nextInt(5) + 1, random.nextInt(2) + 1,
                                random.nextInt(3) + 1,1,random.nextFloat()+0.3f,random.nextFloat()+0.1f,random.nextInt(4),random.nextFloat()+0.2f,random.nextInt(3)+1,
                                random.nextInt(2)+1,weaponNameGenerator(),random.nextInt(100)+10);
                        w.setPrice(weaponPrices);
                    }
                    else{
                        w = new Weapon(UUID.randomUUID().hashCode(), weaponType, random.nextInt(3) + 1, random.nextInt(5) + 1, random.nextInt(2) + 1,
                                random.nextInt(3) + 1,0,random.nextFloat()+0.3f,random.nextFloat()+0.1f,random.nextInt(4),random.nextFloat()+0.2f,random.nextInt(3)+1,
                                random.nextInt(2)+1,weaponNameGenerator(),random.nextInt(100)+10);
                        w.setPrice(weaponPrices);
                    }
                    weaponDAO.persist(w);
                    List<Weapon> inventory = ship.getInventory();
                    inventory.add(w);
                    ship.setInventory(inventory);
                    shipDAO.update(ship);
                    List<Weapon> rewardWeapon = new ArrayList<>();
                    rewardWeapon.add(w);
                    responseObject.setRewardWeapons(rewardWeapon);
                }
                if (crewRandomizer == 0 && spaceForCrew) {
                    List<Integer> crewStats = new ArrayList<>();
                    crewStats.add(random.nextInt(10));
                    crewStats.add(random.nextInt(10));
                    crewStats.add(random.nextInt(10));
                    crewStats.add(random.nextInt(10));
                    crewStats.add(random.nextInt(10));
                    Crew c = new Crew(UUID.randomUUID().hashCode(),crewNameGenerator(),8,8,crewStats,random.nextInt(40)+5,ship.getAssociatedUser());
                    crewDAO.persist(c);
                    for (Room r : ship.getSystems()){
                        boolean done = false;
                        for (Tile t : r.getTiles()){
                            if (t.isEmpty()){
                                t.setStandingOnMe(c);
                                c.setTile(t);
                                r.getCrew().add(c);
                                c.setCurrentRoom(r);
                                tileDAO.update(t);
                                crewDAO.update(c);
                                roomDAO.update(r);
                                shipDAO.update(ship);
                                done = true;
                                break;
                            }
                        }
                        if (done){
                            break;
                        }
                    }
                    responseObject.setRewardCrew(c);
                }
                int cash = 0;
                int rockets = 0;
                int fuel = 0;
                switch (planet.getEvent()) {
                    case VOID:
                        cash = random.nextInt(100);
                        rockets = random.nextInt(10);
                        fuel = random.nextInt(10);
                        ship.setCoins(ship.getCoins()+cash);
                        ship.setMissiles(ship.getMissiles()+rockets);
                        ship.setFuel(ship.getFuel()+fuel);
                        shipDAO.update(ship);
                        responseObject.setRewardCash(cash);
                        responseObject.setRewardRockets(rockets);
                        responseObject.setRewardFuel(fuel);
                        responseObject.setResponseShip(ship);
                        break;
                    case NEBULA:
                        cash = random.nextInt(40);
                        rockets =  random.nextInt(15);
                        fuel = random.nextInt(12);
                        ship.setCoins(ship.getCoins()+cash);
                        ship.setMissiles(ship.getMissiles()+rockets);
                        ship.setFuel(ship.getFuel()+fuel);
                        shipDAO.update(ship);
                        responseObject.setRewardCash(cash);
                        responseObject.setRewardRockets(rockets);
                        responseObject.setRewardFuel(fuel);
                        break;
                    case METEORSHOWER:
                        cash = random.nextInt(20) - random.nextInt(30);
                        if (ship.getCoins()-cash<0){
                            ship.setCoins(0);
                        }
                        else{
                            ship.setCoins(ship.getCoins()+cash);
                        }
                        fuel = -1 * random.nextInt(5) + random.nextInt(5);
                        if (ship.getFuel()+fuel<0){
                            ship.setFuel(0);
                        }
                        else {
                            ship.setFuel(ship.getFuel() + fuel);
                        }
                        shipDAO.update(ship);
                        responseObject.setRewardCash(cash);
                        responseObject.setRewardFuel(fuel);
                        break;
                    default:
                        break;
                }
                responseObject.setResponseShip(ship);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseObject;
    }

    /** Crew name generator
     * @return a random name */
    private String crewNameGenerator(){
        String[] names = {"Trumpinator","Arnold Schwarzenegger", "Katie","Peter","Brian","Anakin","General Grievous",
                "Bob","Noob","Steve"};
        Random random = new Random();
        return names[random.nextInt(names.length-1)];
    }

    /** Name generator
     * @return a random name */
    private String weaponNameGenerator(){
        String[] names = {"BLASTER 9000","DESTRUCTION","Bombs Away","YO MOMMA", "U are DEAD", "GAME OVER", "Silly Billy"};
        Random random = new Random();
        return names[random.nextInt(names.length-1)];
    }
}
