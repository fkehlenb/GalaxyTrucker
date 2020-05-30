package com.galaxytrucker.galaxytruckerreloaded.Controller;

import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class BattleController extends Controller{
    @NonNull
    private Ship myself;

    /** Make one ship attack another's section
     * @param opponent - the opponent's ship
     * @param weapon - the weapon attacking */
    private void attack(Ship opponent, Weapon weapon){}

    /** Heal a ship
     * @param healingWeapon - the healing weapon */
    private void heal(Weapon healingWeapon){}

    /** Flee a fight, reward the winner
     * @param coward - the ship that wants to flee
     * @param opponent - the opponent that wins the fight */
    private void fleeFight(Ship coward, Ship opponent){}

}
