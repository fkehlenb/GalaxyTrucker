package com.galaxytrucker.galaxytruckerreloaded.Server.Services;

import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class BattleService {

    /** List of ships participating in the fight */
    @NonNull
    private List<Ship> participants;

    /** The ship which's round it is */
    private Ship currentRound;

    /** Change the ship which's round it is */
    public void nextRound(){}

    /** Validate user input by checking if it's his round to play
     * @param s - the ship which wants to play
     * @return true if it is it's round else false */
    private boolean validMove(Ship s){
        return false;
    }

    /** Make one ship attack another's section
     * @param attacker - the attacking ship
     * @param opponent - the opponent's ship
     * @param weapon - the weapon attacking */
    private void attack(Ship attacker, Ship opponent, Weapon weapon){}

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

    /** Receive data from server and turn into valid battle move
     * @param s - the string to turn to battle moves
     * @return the outcome of the battle moves as a string command */
    public String applyBattleMoves(String s){
        return null;
    }
}
