package com.galaxytrucker.galaxytruckerreloaded.Server.Services;

import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.ShipDAO;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.WeaponDAO;
import lombok.*;

/** This class handles reward handing to players */
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
public class RewardService {

    /** ShipDAO */
    @NonNull
    private ShipDAO shipDAO;

    /** WeaponDAO */
    @NonNull
    private WeaponDAO weaponDAO;

    /** Weapon reward
     * @param s - the ship to give reward to
     * @param dropTable - possible weapon drops
     * @return the weapon given */
    public Weapon weaponReward(Ship s, List<Weapon> dropTable){
        return null;
    }

    /** Coin reward
     * @param s - the ship to give reward to
     * @param c - the coins to give it */
    public void coinsReward(Ship s, int c){

    }

    /** Fuel reward
     * @param s - the ship to give reward to
     * @param f - the fuel to give it*/
    public void fuelReward(Ship s,int f){

    }

    /** Rocket reward
     * @param s - the ship to give reward to
     * @param r - the rockets to give it */
    public void rocketReward(Ship s,int r){

    }
}
