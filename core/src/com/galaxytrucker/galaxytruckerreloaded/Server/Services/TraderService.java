package com.galaxytrucker.galaxytruckerreloaded.Server.Services;

import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Trader;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.CrewDAO;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.ShipDAO;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.TraderDAO;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.WeaponDAO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class TraderService extends PlanetEventService {

    /**
     * Trader DAO
     */
    @NonNull
    private TraderDAO traderDAO = new TraderDAO();

    /**
     * WeaponDAO
     */
    @NonNull
    private WeaponDAO weaponDAO = new WeaponDAO();

    /**
     * CrewDAO
     */
    @NonNull
    private CrewDAO crewDAO = new CrewDAO();

    /**
     * ship DAO
     */
    @NonNull
    private ShipDAO shipDAO = new ShipDAO();

    /**
     * Validate purchase by checking if the client has enough money
     *
     * @param ship  - the ship which's purchase to validate
     * @param price - the price to pay in coins
     * @return true if the ship has enough money else false
     */
    public boolean validatePurchase(Ship ship, int price) {
        return ship.getCoins() >= price;
    }

    /**
     * Buy a weapon from the trader
     *
     * @param ship   - the ship that wishes to buy the weapon
     * @param trader - the trader to buy the weapon from
     * @param weapon - the weapon to buy
     */
    public boolean purchaseWeapon(Ship ship, Trader trader, Weapon weapon) {
        try {
            List<Weapon> traderWeapons = trader.getWeaponStock();
            List<Weapon> shipWeapons = ship.getInventory();
            int shipCoins = ship.getCoins();
            try {
                //remove weapon from trader
                traderWeapons.remove(weapon);
                trader.setWeaponStock(traderWeapons);
                traderDAO.update(trader);
                //add weapon to ship
                shipWeapons.add(weapon);
                ship.setInventory(shipWeapons);
                ship.setCoins(shipCoins - weapon.getPrice().get(weapon.getWeaponLevel()));
                shipDAO.update(ship);
                //muss was an weapon geändert werden??
                return true;
            } catch (Exception e) { //exception in daos
                e.printStackTrace();
                //revert changes
                try {
                    //revert trader changes
                    trader.setWeaponStock(traderWeapons);
                    traderDAO.update(trader);
                    //revert ship inventory changes
                    ship.setInventory(shipWeapons);
                    ship.setCoins(shipCoins);
                    shipDAO.update(ship);
                    //was an weapon?
                } catch (Exception f) { //exception in daos
                    f.printStackTrace();
                }
                return false;
            }
        }
        catch(Exception g) { //nullpointer
            g.printStackTrace();
            return false;
        }
    }

    /**
     * Buy crew from a trader
     *
     * @param ship   - the ship that wants to buy a crew member
     * @param trader - the trader to buy from
     * @param crew   - the crew to buy
     */
    public boolean purchaseCrew(Ship ship, Trader trader, Crew crew) {
        try {
            List<Crew> traderCrew = trader.getCrewStock();
            int shipCoins = ship.getCoins();
            String user = crew.getAssociatedUser();
            try {
                //remove from trader
                traderCrew.remove(crew);
                trader.setCrewStock(traderCrew);
                traderDAO.update(trader);
                //add to ship
                ship.setCoins(shipCoins - crew.getPrice());
                //List<Crew> shipCrew = ship. TODO wie crew von schiff?
                //set current Room
                crew.setCurrentRoom(ship.getSystems().get(0));
                crew.setAssociatedUser(ship.getAssociatedUser());
                crewDAO.update(crew);
                return true;
            } catch (Exception e) { //exception in daos
                e.printStackTrace();
                //revert changes
                try {
                    //add to trader
                    trader.setCrewStock(traderCrew);
                    traderDAO.update(trader);
                    //set current Room
                    crew.setCurrentRoom(null);
                    crew.setAssociatedUser(user);
                    crewDAO.update(crew);
                    //remove from ship?
                    ship.setCoins(shipCoins);
                    shipDAO.update(ship);
                } catch (Exception f) { //exception in daos
                    f.printStackTrace();
                }
                return false;
            }
        }
        catch(Exception g) { //nullpointer
            g.printStackTrace();
            return false;
        }
    }

    /**
     * Buy rockets from the trader
     *
     * @param ship   - the ship hat wishes to buy rockets
     * @param trader - the trader to buy from
     * @param amount - the amount of rockets to buy
     */
    public boolean purchaseRockets(Ship ship, Trader trader, int amount) {
        try {
            int traderAmount = trader.getMissileStock();
            int shipAmount = ship.getMissiles();
            int shipCoins = ship.getCoins();
            try {
                //remove from trader
                if (traderAmount < amount) {
                    return false;
                }
                traderAmount -= amount;
                trader.setMissileStock(traderAmount);
                traderDAO.update(trader);
                //add to ship
                shipAmount += amount;
                ship.setMissiles(shipAmount);
                ship.setCoins(shipCoins - 5*amount); //TODO was für einen festpreis?
                shipDAO.update(ship);
                return true;
            } catch (Exception e) { //exception in daos
                e.printStackTrace();
                try {
                    //undo
                    //add to trader
                    trader.setMissileStock(traderAmount);
                    traderDAO.update(trader);
                    //remove from ship
                    ship.setMissiles(shipAmount);
                    ship.setCoins(shipCoins);
                    shipDAO.update(ship);
                } catch (Exception f) { //exception in daos
                    f.printStackTrace();
                }
                return false;
            }
        }
        catch(Exception g) { //nullpointer
            g.printStackTrace();
            return false;
        }
    }

    /**
     * Buy fuel from the trader
     *
     * @param ship   - the ship that wants to buy stuff
     * @param trader - the trader to buy from
     * @param amount - the amount of fuel to buy
     */
    public boolean purchaseFuel(Ship ship, Trader trader, int amount) {
        try {
            int traderAmount = trader.getFuelStock();
            int shipAmount = ship.getFuel();
            int shipCoins = ship.getCoins();
            try {
                //remove from trader
                if (traderAmount < amount) {
                    return false;
                }
                traderAmount -= amount;
                trader.setFuelStock(traderAmount);
                traderDAO.update(trader);
                //add to ship
                shipAmount += amount;
                ship.setFuel(shipAmount);
                ship.setCoins(shipCoins - 5*amount); //TODO was für ein festpreis?
                shipDAO.update(ship);
                return true;
            } catch (Exception e) { //exception in daos
                e.printStackTrace();
                try {
                    //undo
                    //add to trader
                    trader.setFuelStock(traderAmount);
                    traderDAO.update(trader);
                    //remove from ship
                    ship.setFuel(shipAmount);
                    ship.setCoins(shipCoins);
                    shipDAO.update(ship);
                } catch (Exception f) { //exception in daos
                    f.printStackTrace();
                }
                return false;
            }
        }
        catch(Exception g) { //nullpointer
            g.printStackTrace();
            return false;
        }
    }

    /**
     * Buy health from the trader
     *
     * @param ship   - the ship that wishes to buy health
     * @param trader - the trader to buy from
     * @param amount - the amount to buy
     */
    public boolean purchaseHP(Ship ship, Trader trader, int amount) {
        try {
            int traderAmount = trader.getHpStock();
            int shipAmount = ship.getHp();
            int coins = ship.getCoins();
            try {
                //remove from trader
                if (traderAmount < amount) {
                    return false;
                }
                traderAmount -= amount;
                trader.setHpStock(traderAmount);
                traderDAO.update(trader);
                //add to ship
                shipAmount += amount;
                ship.setHp(shipAmount);
                ship.setCoins(coins - 5*amount); //TODO festpreis?
                shipDAO.update(ship);
                return true;
            } catch (Exception e) { //exception in daos
                e.printStackTrace();
                try {
                    ship.setHp(shipAmount);
                    ship.setCoins(coins);
                    shipDAO.update(ship);
                    trader.setHpStock(traderAmount);
                    traderDAO.update(trader);

                } catch (Exception f) { //exception in daos
                    f.printStackTrace();
                }
                return false;
            }
        }
        catch(Exception g) { // Nullpointer
            g.printStackTrace();
            return false;
        }
    }

    /**
     * Sell weapons
     *
     * @param ship   - the ship that wants to sell weapons
     * @param trader - the trader to sell the weapons to
     * @param weapon - the weapon to sell
     */
    public boolean sellWeapon(Ship ship, Trader trader, Weapon weapon) {
        try {
            List<Weapon> traderWeapon = trader.getWeaponStock();
            List<Weapon> shipWeapon = ship.getInventory();
            int shipCoins = ship.getCoins();
            try {
                //add to trader
                traderWeapon.add(weapon);
                trader.setWeaponStock(traderWeapon);
                traderDAO.update(trader);
                //remove from ship
                shipWeapon.remove(weapon);
                ship.setInventory(shipWeapon);
                ship.setCoins(shipCoins + weapon.getPrice().get(weapon.getWeaponLevel()));
                shipDAO.update(ship);
                return true;
            } catch (Exception e) { // exception in daos
                e.printStackTrace();
                try {
                    //undo
                    trader.setWeaponStock(traderWeapon);
                    traderDAO.update(trader);
                    ship.setInventory(shipWeapon);
                    ship.setCoins(shipCoins);
                    shipDAO.update(ship);
                } catch (Exception f) { //exception in daos
                    f.printStackTrace();
                }
                return false;
            }
        }
        catch(Exception g) { //nullpointer
            g.printStackTrace();
            return false;
        }
    }

    /**
     * Sell rockets
     *
     * @param ship   - the ship that wants to sell rockets
     * @param trader - the trader to sell the rockets to
     * @param amount - the amount of rockets to sell
     */
    public boolean sellRockets(Ship ship, Trader trader, int amount) {
        try {
            int traderAmount = trader.getMissileStock();
            int shipAmount = ship.getMissiles();
            int shipCoins = ship.getCoins();
            try {
                //add to trader
                traderAmount += amount;
                trader.setMissileStock(traderAmount);
                traderDAO.update(trader);
                //remove from ship
                if (shipAmount < amount) {
                    return false;
                }
                shipAmount -= amount;
                ship.setMissiles(shipAmount);
                ship.setCoins(ship.getCoins() + 5*amount); //TODO festpreis?
                shipDAO.update(ship);
                return true;
            } catch (Exception e) { //exception in daos
                e.printStackTrace();
                try {
                    //undo
                    trader.setMissileStock(traderAmount);
                    traderDAO.update(trader);
                    ship.setMissiles(shipAmount);
                    ship.setCoins(shipCoins);
                    shipDAO.update(ship);
                } catch (Exception f) { //exception in daos
                    f.printStackTrace();
                }
                return false;
            }
        }
        catch(Exception g) { //nullpointer
            g.printStackTrace();
            return false;
        }
    }
}
