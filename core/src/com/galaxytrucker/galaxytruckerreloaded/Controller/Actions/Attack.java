package com.galaxytrucker.galaxytruckerreloaded.Controller.Actions;

import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;

public class Attack {

    /** Make one ship attack another's section
     * @param opponent - the opponent's ship
     * @param weapon - the weapon attacking */
    private void attack(Ship opponent, Weapon weapon){}

    /** Heal a ship
     * @param ship - the ship to heal
     * @param healingWeapon - the healing weapon */
    private void heal(Ship ship,Weapon healingWeapon){}

    /** Flee a fight, reward the winner
     * @param coward - the ship that wants to flee
     * @param opponent - the opponent that wins the fight */
    private void fleeFight(Ship coward, Ship opponent){}

    /** Give winner reward and end the fight (or the game)
     * @param loser - the ship that lost
     * @param victor - the ship that won */
    private void endFight(Ship loser, Ship victor){}

}
