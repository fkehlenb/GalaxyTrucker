package com.galaxytrucker.galaxytruckerreloaded.Server.Services;

import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Planet;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.PlanetEvent;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Trader;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Tile;
import com.galaxytrucker.galaxytruckerreloaded.Model.User;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.*;
import com.galaxytrucker.galaxytruckerreloaded.Server.ResponseObject;
import lombok.*;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@SuppressWarnings("Duplicates")
public class TraderService extends PlanetEventService {

    /**
     * Trader DAO
     */
    private TraderDAO traderDAO = TraderDAO.getInstance();

    /**
     * WeaponDAO
     */
    private WeaponDAO weaponDAO = WeaponDAO.getInstance();

    /**
     * CrewDAO
     */
    private CrewDAO crewDAO = CrewDAO.getInstance();

    /**
     * ship DAO
     */
    private ShipDAO shipDAO = ShipDAO.getInstance();

    /** Room DAO */
    private RoomDAO roomDAO = RoomDAO.getInstance();

    /** Tile DAO */
    private TileDAO tileDAO = TileDAO.getInstance();

    /**
     * Buy a weapon from the trader
     *
     * @param ship   - the ship that wishes to buy the weapon
     * @param trader - the trader to buy the weapon from
     * @param weapon - the weapon to buy
     */
    public ResponseObject purchaseWeapon(Ship ship, Trader trader, Weapon weapon) {
        ResponseObject responseObject = new ResponseObject();
        try {
            // Fetch data from database
            ship = shipDAO.getById(ship.getId());
            trader = traderDAO.getById(trader.getId());
            weapon = weaponDAO.getById(weapon.getId());
            // Manual verification
            System.out.println("\n==================== Action Buy Weapon ====================");
            System.out.println("[PRE]:[Ship]:" + ship.getId() + ":[Weapon]:" + weapon.getId() + ":[Trader]:" + trader.getId() + ":[Coins]:" + ship.getCoins());
            // Trader stock
            List<Weapon> stock = trader.getWeaponStock();
            System.out.println("[PRE]:[Trader-Stock-Size]:" + stock.size());
            // Ship inventory
            List<Weapon> inventory = ship.getInventory();
            System.out.println("[PRE]:[Ship-Inventory-Size]:" + inventory.size());
            // inventory space max check
            if (inventory.size()<5){
                // check for weapon existance in stock
                boolean exists = false;
                for (Weapon w : stock){
                    if (w.getId() == weapon.getId()){
                        exists = true;
                        System.out.println("[Weapon-In-Stock]");
                        break;
                    }
                }
                if (exists && ship.getCoins() >= weapon.getWeaponPrice()){
                    // Subtract price from ship coins
                    ship.setCoins(ship.getCoins()-weapon.getWeaponPrice());
                    // Remove weapon from stock
                    stock.remove(weapon);
                    trader.setWeaponStock(stock);
                    // Add weapon to inventory
                    inventory.add(weapon);
                    ship.setInventory(inventory);
                    // Update data
                    shipDAO.update(ship);
                    traderDAO.update(trader);
                    // Verification
                    System.out.println("[POST]:[Ship-Inventory-Size]:" + inventory.size() + ":[Coins]:" + ship.getCoins());
                    System.out.println("[POST]:[Trader-Stock-Size]:" + stock.size());
                    System.out.println("===========================================================");
                    // Set valid data
                    responseObject.setValidRequest(true);
                    responseObject.setResponseShip(ship);
                    responseObject.setResponseOverworld(UserService.getInstance().getUser(ship.getAssociatedUser()).getOverworld());
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return responseObject;
    }

    /**
     * Buy crew from a trader
     *
     * @param ship   - the ship that wants to buy a crew member
     * @param trader - the trader to buy from
     * @param crew   - the crew to buy
     */
    public ResponseObject purchaseCrew(Ship ship, Trader trader, Crew crew) {
        ResponseObject responseObject = new ResponseObject();
        try {
            // Fetch data from database
            ship = shipDAO.getById(ship.getId());
            trader = traderDAO.getById(trader.getId());
            crew = crewDAO.getById(crew.getId());
            // Manual verification
            System.out.println("\n==================== Action Buy Crew ====================");
            System.out.println("[PRE]:[Ship]:" + ship.getId() + ":[Crew]:" + crew.getId() + ":[Trader]:" + trader.getId() + ":[Coins]:" + ship.getCoins());
            // Get trader stock
            List<Crew> stock = trader.getCrewStock();
            // Check if the crew exists in the stock
            boolean exists = false;
            for (Crew c : stock){
                if (c.getId()==crew.getId()){
                    exists = true;
                    break;
                }
            }
            // Check ship for empty tile
            boolean spaceInShip = false;
            for (Room r : ship.getSystems()){
                for (Tile t : r.getTiles()){
                    if (t.isEmpty()){
                        spaceInShip = true;
                        break;
                    }
                }
            }
            if (exists && spaceInShip && ship.getCoins()>=crew.getPrice()){
                // Subtract money
                ship.setCoins(ship.getCoins()-crew.getPrice());
                // Remove crew from stock
                stock.remove(crew);
                trader.setCrewStock(stock);
                // Add crew to first empty spot in ship
                List<Room> rooms = ship.getSystems();
                for (Room r : rooms){
                    List<Tile> tiles = r.getTiles();
                    for (Tile t : tiles){
                        if (t.isEmpty()){
                            // Tiles
                            t.setStandingOnMe(crew);
                            tiles.set(tiles.indexOf(t),t);
                            r.setTiles(tiles);
                            crew.setTile(t);
                            // Room
                            List<Crew> crewInRoom = r.getCrew();
                            crewInRoom.add(crew);
                            r.setCrew(crewInRoom);
                            crew.setCurrentRoom(r);
                            // Update data
                            tileDAO.update(t);
                            roomDAO.update(r);
                            crewDAO.update(crew);
                            shipDAO.update(ship);
                            traderDAO.update(trader);
                            // Manual verification
                            System.out.println("[POST]:[Ship]:" + ship.getId() + ":[Crew]:" + crew.getId() + ":[Trader]:" + trader.getId() + ":[Coins]:" + ship.getCoins());
                            System.out.println("=========================================================");
                            // Set valid data
                            responseObject.setValidRequest(true);
                            responseObject.setResponseShip(ship);
                            responseObject.setResponseOverworld(UserService.getInstance().getUser(ship.getAssociatedUser()).getOverworld());
                            return responseObject;
                        }
                    }
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return responseObject;
    }

    /**
     * Buy rockets from the trader
     *
     * @param ship   - the ship hat wishes to buy rockets
     * @param trader - the trader to buy from
     * @param amount - the amount of rockets to buy
     */
    public ResponseObject purchaseRockets(Ship ship, Trader trader, int amount) {
        ResponseObject responseObject = new ResponseObject();
        try {
            // Fetch data from database
            ship = shipDAO.getById(ship.getId());
            trader = traderDAO.getById(trader.getId());
            // Manual verification
            System.out.println("\n==================== Action Buy Rockets ====================");
            System.out.println("[PRE]:[Ship]:" + ship.getId() + ":[Rockets]:" + ship.getMissiles() + ":[Amount]:" + amount + ":[Trader]:" + trader.getId()
                    + ":[Missile-Stock]:" + trader.getMissileStock() + ":[Coins]:" + ship.getCoins());
            // Check for enough money and stock
            if (ship.getCoins()>=amount*6 && trader.getMissileStock()>=amount){
                // Subtract coins
                ship.setCoins(ship.getCoins()-amount*6);
                // Remove from stock
                trader.setMissileStock(trader.getMissileStock()-amount);
                // Add missles to ship
                ship.setMissiles(ship.getMissiles()+amount);
                // Update data
                traderDAO.update(trader);
                shipDAO.update(ship);
                // Manual verification
                System.out.println("[POST]:[Ship]:" + ship.getId() + ":[Rockets]:" + ship.getMissiles() + ":[Trader]:" + trader.getId()
                        + ":[Missile-Stock]:" + trader.getMissileStock() + ":[Coins]:" + ship.getCoins());
                System.out.println("============================================================");
                // Set valid data
                responseObject.setValidRequest(true);
                responseObject.setResponseShip(ship);
                responseObject.setResponseOverworld(UserService.getInstance().getUser(ship.getAssociatedUser()).getOverworld());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return responseObject;
    }

    /**
     * Buy fuel from the trader
     *
     * @param ship   - the ship that wants to buy stuff
     * @param trader - the trader to buy from
     * @param amount - the amount of fuel to buy
     */
    public ResponseObject purchaseFuel(Ship ship, Trader trader, int amount) {
        ResponseObject responseObject = new ResponseObject();
        try {
            // Fetch data from database
            ship = shipDAO.getById(ship.getId());
            trader = traderDAO.getById(trader.getId());
            // Manual verification
            System.out.println("\n==================== Action Buy Fuel ====================");
            System.out.println("[PRE]:[Ship]:" + ship.getId() + ":[Fuel]:" + ship.getFuel() + ":[Amount]:" + amount + ":[Trader]:" + trader.getId()
                    + ":[Fuel-Stock]:" + trader.getFuelStock() + ":[Coins]:" + ship.getCoins());
            // Check for money and stock
            if (ship.getCoins()>=3*amount && trader.getFuelStock()>=amount){
                // Subtract money
                ship.setCoins(ship.getCoins()-3*amount);
                // Remove from stock
                trader.setFuelStock(trader.getFuelStock()-amount);
                // Add fuel to ship
                ship.setFuel(ship.getFuel()+amount);
                // Update data
                shipDAO.update(ship);
                traderDAO.update(trader);
                // Manual verification
                System.out.println("[POST]:[Ship]:" + ship.getId() + ":[Fuel]:" + ship.getFuel() + ":[Amount]:" + amount + ":[Trader]:" + trader.getId()
                        + ":[Fuel-Stock]:" + trader.getFuelStock() + ":[Coins]:" + ship.getCoins());
                System.out.println("=========================================================");
                // Set valid data
                responseObject.setValidRequest(true);
                responseObject.setResponseShip(ship);
                responseObject.setResponseOverworld(UserService.getInstance().getUser(ship.getAssociatedUser()).getOverworld());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return responseObject;
    }

    /**
     * Buy health from the trader
     *
     * @param ship   - the ship that wishes to buy health
     * @param trader - the trader to buy from
     * @param amount - the amount to buy
     */
    public ResponseObject purchaseHP(Ship ship, Trader trader, int amount) {
        ResponseObject responseObject = new ResponseObject();
        try {
            // Fetch data
            ship = shipDAO.getById(ship.getId());
            trader = traderDAO.getById(trader.getId());
            // Manual verification
            System.out.println("\n==================== Action Buy HP ====================");
            System.out.println("[PRE]:[Ship]:" + ship.getId() + ":[HP]:" + ship.getHp() + ":[Amount]:" + amount + ":[Trader]:" + trader.getId()
                    + ":[HP-Stock]:" + trader.getHpStock() + ":[Coins]:" + ship.getCoins());
            // Check for enough money
            if (ship.getCoins()>=2*amount && trader.getHpStock()>=amount){
                // Subtract money
                ship.setCoins(ship.getCoins()-2*amount);
                // Subtract from stock
                trader.setHpStock(trader.getHpStock()-amount);
                // Add hp to ship
                ship.setHp(ship.getHp()+amount);
                // Update data
                shipDAO.update(ship);
                traderDAO.update(trader);
                // Manual verification
                System.out.println("[POST]:[Ship]:" + ship.getId() + ":[HP]:" + ship.getHp() + ":[Amount]:" + amount + ":[Trader]:" + trader.getId()
                        + ":[HP-Stock]:" + trader.getHpStock() + ":[Coins]:" + ship.getCoins());
                System.out.println("=======================================================");
                // Set valid data
                responseObject.setValidRequest(true);
                responseObject.setResponseShip(ship);
                responseObject.setResponseOverworld(UserService.getInstance().getUser(ship.getAssociatedUser()).getOverworld());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return responseObject;
    }

    /**
     * Sell weapons
     *
     * @param ship   - the ship that wants to sell weapons
     * @param trader - the trader to sell the weapons to
     * @param weapon - the weapon to sell
     */
    public ResponseObject sellWeapon(Ship ship, Trader trader, Weapon weapon) {
        ResponseObject responseObject = new ResponseObject();
        try {
            // Fetch data
            ship = shipDAO.getById(ship.getId());
            trader = traderDAO.getById(trader.getId());
            weapon = weaponDAO.getById(weapon.getId());
            // Manual verification
            System.out.println("\n==================== Action Sell Weapon ====================");
            System.out.println("[PRE]:[Ship]:" + ship.getId() + ":[Weapon]:" + weapon.getId() + ":[Trader]:" + trader.getId() + ":[Coins]:" + ship.getCoins());
            // Trader stock
            List<Weapon> stock = trader.getWeaponStock();
            System.out.println("[PRE]:[Trader-Stock-Size]:" + stock.size());
            // Ship inventory
            List<Weapon> inventory = ship.getInventory();
            System.out.println("[PRE]:[Ship-Inventory-Size]:" + inventory.size());
            // Check for weapon existence in inventory
            boolean exists = false;
            for (Weapon w : inventory){
                if (w.getId() == weapon.getId()){
                    exists = true;
                    System.out.println("[Weapon-Exists]");
                    break;
                }
            }
            if (exists){
                // Remove weapon from inventory
                inventory.remove(weapon);
                ship.setInventory(inventory);
                // Add coins to ship
                ship.setCoins(ship.getCoins()+weapon.getWeaponPrice());
                // Add weapon to trader stock
                stock.add(weapon);
                trader.setWeaponStock(stock);
                // Update data
                traderDAO.update(trader);
                weaponDAO.update(weapon);
                shipDAO.update(ship);
                // Manual verification
                System.out.println("[POST]:[Trader-Stock-Size]:" + stock.size());
                System.out.println("[POST]:[Ship-Inventory-Size]:" + inventory.size());
                System.out.println("============================================================");
                // Set valid data
                responseObject.setValidRequest(true);
                responseObject.setResponseShip(ship);
                responseObject.setResponseOverworld(UserService.getInstance().getUser(ship.getAssociatedUser()).getOverworld());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return responseObject;
    }

    /**
     * Sell rockets
     *
     * @param ship   - the ship that wants to sell rockets
     * @param trader - the trader to sell the rockets to
     * @param amount - the amount of rockets to sell
     */
    public ResponseObject sellRockets(Ship ship, Trader trader, int amount) {
        ResponseObject responseObject = new ResponseObject();
        try {
            // Fetch data
            ship = shipDAO.getById(ship.getId());
            trader = traderDAO.getById(trader.getId());
            // Manual verification
            System.out.println("\n==================== Action Sell Rockets ====================");
            System.out.println("[PRE]:[Ship]:" + ship.getId() + ":[Rockets]:" + ship.getMissiles() + ":[Amount]:" + amount + ":[Trader]:" + trader.getId()
                    + ":[Missile-Stock]:" + trader.getMissileStock() + ":[Coins]:" + ship.getCoins());
            // Check for enough rockets in ship
            if (ship.getMissiles()>=amount){
                // Remove missiles from ship
                ship.setMissiles(ship.getMissiles()-amount);
                // Add coins to ship
                ship.setCoins(ship.getCoins() + amount*6);
                // Add missiles to trader stock
                trader.setMissileStock(trader.getMissileStock()+amount);
                // Update data
                shipDAO.update(ship);
                traderDAO.update(trader);
                // Manual verification
                System.out.println("[POST]:[Ship]:" + ship.getId() + ":[Rockets]:" + ship.getMissiles() + ":[Trader]:" + trader.getId()
                        + ":[Missile-Stock]:" + trader.getMissileStock() + ":[Coins]:" + ship.getCoins());
                System.out.println("=============================================================");
                // Set valid data
                responseObject.setValidRequest(true);
                responseObject.setResponseShip(ship);
                responseObject.setResponseOverworld(UserService.getInstance().getUser(ship.getAssociatedUser()).getOverworld());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return responseObject;
    }
}
