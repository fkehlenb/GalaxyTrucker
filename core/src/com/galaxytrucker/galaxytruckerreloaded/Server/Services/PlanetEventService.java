package com.galaxytrucker.galaxytruckerreloaded.Server.Services;

import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.PlanetEvent;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.ShipDAO;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class PlanetEventService {

    /**
     * Type of planet event currently happening
     */
    @NonNull
    private Enum<PlanetEvent> currentEvent;

    /**
     * Ship DAO
     */
    @NonNull
    private ShipDAO shipDAO;

    /**
     * RewardService
     */
    private RewardService rewardService;

    /**
     * Round counter (meteorShower/Nebula)
     */
    private int roundCounter = 0;

    /**
     * Give the player some coins
     *
     * @param s      - the ship to give coins to
     * @param amount - amount of coins to add
     */
    public void giveCoins(Ship s, int amount) {
    }

    /**
     * Take coins away from player
     *
     * @param s      - the ship to remove coins from
     * @param amount - the amount of coins to remove
     */
    public void removeCoins(Ship s, int amount) {
    }

    /**
     * Give the player some loot
     *
     * @param s             - the ship to give the reward to
     * @param dropTable     - list of possible drops
     * @param crewDropTable - list of possible crew drops
     */
    public void giveLoot(Ship s, List<Weapon> dropTable, List<Crew> crewDropTable) {
    }

    /**
     * MeteorShower damage
     *
     * @param s - the player in the meteorShower
     */
    private void meteorShower(Ship s) {
    }

    /**
     * Disable systems when in nebula
     *
     * @param s - the ship which's systems to disable
     */
    public void disableSystemsInNebula(Ship s, int duration) {
    }
}
