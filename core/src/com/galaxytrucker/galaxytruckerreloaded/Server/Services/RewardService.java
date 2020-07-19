package com.galaxytrucker.galaxytruckerreloaded.Server.Services;

import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.CrewDAO;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.RoomDAO;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.ShipDAO;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.WeaponDAO;
import lombok.*;

/**
 * This class handles reward handing to players
 */
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
public class RewardService {

    /** Instance */
    private static RewardService instance;

    /** Get instance */
    public static RewardService getInstance(){
        if (instance == null){
            instance = new RewardService();
        }
        return instance;
    }

    /**
     * ShipDAO
     */
    private ShipDAO shipDAO = ShipDAO.getInstance();

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

    /**
     * Weapon reward
     *
     * @param s         - the ship to give reward to
     * @param dropTable - possible weapon drops
     * @return the weapon given
     */
    public Weapon weaponReward(Ship s, List<Weapon> dropTable,int seed) {
        return null;
    }

    /**
     * Coin reward
     *
     * @param s - the ship to give reward to
     * @param c - the coins to give it
     */
    public void coinsReward(Ship s, int c,int seed) {

    }

    /**
     * Fuel reward
     *
     * @param s - the ship to give reward to
     * @param f - the fuel to give it
     */
    public void fuelReward(Ship s, int f,int seed) {

    }

    /**
     * Rocket reward
     *
     * @param s - the ship to give reward to
     * @param r - the rockets to give it
     */
    public void rocketReward(Ship s, int r,int seed) {

    }

    /**
     * Give the player a crew member as reward
     *
     * @param s    - the ship to give the reward to
     * @param crew - a list of possible crew member drops
     */
    public void crewReward(Ship s, List<Crew> crew,int seed) {

    }
}
