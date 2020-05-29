package com.galaxytrucker.galaxytruckerreloaded.Server.Services;

import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.PlanetEvent;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Trader;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.CrewDAO;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.ShipDAO;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.TraderDAO;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.WeaponDAO;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class TraderService extends PlanetEventService {

    /**
     * Trader DAO
     */
    @NonNull
    private TraderDAO traderDAO;

    /** WeaponDAO */
    @NonNull
    private WeaponDAO weaponDAO;

    /** CrewDAO */
    @NonNull
    private CrewDAO crewDAO;

    /**
     * Validate purchase by checking if the client has enough money
     *
     * @param ship  - the ship which's purchase to validate
     * @param price - the price to pay in coins
     * @return true if the ship has enough money else false
     */
    public boolean validatePurchase(Ship ship, int price) {
        return false;
    }

    /**
     * Buy a weapon from the trader
     *
     * @param ship   - the ship that wishes to buy the weapon
     * @param trader - the trader to buy the weapon from
     * @param weapon - the weapon to buy
     */
    private void purchaseWeapon(Ship ship, Trader trader, Weapon weapon) {

    }

    /**
     * Buy crew from a trader
     *
     * @param ship   - the ship that wants to buy a crew member
     * @param trader - the trader to buy from
     * @param crew   - the crew to buy
     */
    public void purchaseCrew(Ship ship, Trader trader, Crew crew) {
    }

    /**
     * Buy rockets from the trader
     *
     * @param ship   - the ship hat wishes to buy rockets
     * @param trader - the trader to buy from
     * @param amount - the amount of rockets to buy
     */
    public void purchaseRockets(Ship ship, Trader trader, int amount) {

    }

    /**
     * Buy fuel from the trader
     *
     * @param ship   - the ship that wants to buy stuff
     * @param trader - the trader to buy from
     * @param amount - the amount of fuel to buy
     */
    public void purchaseFuel(Ship ship, Trader trader, int amount) {
    }

    /**
     * Buy health from the trader
     *
     * @param ship   - the ship that wishes to buy health
     * @param trader - the trader to buy from
     * @param amount - the amount to buy
     */
    public void purchaseHP(Ship ship, Trader trader, int amount) {

    }
}
