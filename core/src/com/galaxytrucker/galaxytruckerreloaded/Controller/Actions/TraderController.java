package com.galaxytrucker.galaxytruckerreloaded.Controller.Actions;


import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Trader;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class TraderController extends Controller{
    /** My own ship */
    @NonNull
    private Ship myself;
    /** the trader */
    @NonNull
    private Trader trader;

    /**
     * Buy a weapon from the trader
     * @param weapon - the weapon to buy
     */
    private void purchaseWeapon(Weapon weapon) {
    }

    /**
     * Buy crew from a trader
     * @param crew   - the crew to buy
     */
    public void purchaseCrew(Crew crew) {
    }

    /**
     * Buy rockets from the trader
     * @param amount - the amount of rockets to buy
     */
    public void purchaseRockets(int amount) {
    }

    /**
     * Buy fuel from the trader
     * @param amount - the amount of fuel to buy
     */
    public void purchaseFuel( int amount) {
    }

    /**
     * Buy health from the trader
     * @param amount - the amount to buy
     */
    public void purchaseHP(int amount) {
    }


}
