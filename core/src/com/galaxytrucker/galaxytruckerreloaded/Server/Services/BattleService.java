package com.galaxytrucker.galaxytruckerreloaded.Server.Services;

import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.CrewDAO;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.RoomDAO;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.ShipDAO;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.WeaponDAO;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/** This class handles battle logic on the server side */
@Entity
@Getter
@Setter
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@NamedQueries({
        @NamedQuery(name = "BattleService.fetchAll",query = "select b from BattleService b")
})
public class BattleService implements Serializable {

    /** ID */
    @Id
    @NonNull
    private UUID id;

    /** ShipDAO */
    @Transient
    private ShipDAO shipDAO = ShipDAO.getInstance();

    /** WeaponDAO */
    @Transient
    private WeaponDAO weaponDAO = WeaponDAO.getInstance();

    /** CrewDAO */
    @Transient
    private CrewDAO crewDAO = CrewDAO.getInstance();

    /** RoomDAO */
    @Transient
    private RoomDAO roomDAO = RoomDAO.getInstance();

    /** List of ships participating in the fight */
    @NonNull
    @OneToMany(cascade = CascadeType.DETACH)
    private List<Ship> participants;

    /** The ship which's round it is */
    @ManyToOne(cascade = CascadeType.MERGE)
    private Ship currentRound;

    /** Disabled system round counter */
    private int disabledSystemCounter = 3;

    /** Reward service */
    @Transient
    private RewardService rewardService = RewardService.getInstance();

    /** Change the ship which's round it is */
    public void nextRound(){
        for (Ship s : participants){
            if (!s.getAssociatedUser().equals(currentRound.getAssociatedUser())){
                currentRound = s;
            }
        }
    }

    /** Validate user input by checking if it's his round to play
     * @param s - the ship which wants to play
     * @return true if it is it's round else false */
    public boolean validMove(Ship s){
        return currentRound.getAssociatedUser().equals(s.getAssociatedUser());
    }

    /** Make one ship attack another's section
     * @param attacker - the attacking ship
     * @param opponent - the opponent's ship
     * @param weapon - the weapon attacking
     * @param room - the room being attacked */
    public void attack(Ship attacker, Ship opponent, Weapon weapon, Room room){
        if (validMove(attacker)){
        }
    }

    /** Heal a ship
     * @param ship - the ship to heal
     * @param healingWeapon - the healing weapon */
    public void heal(Ship ship,Weapon healingWeapon){}

    /** Flee a fight, reward the winner
     * @param coward - the ship that wants to flee
     * @param opponent - the opponent that wins the fight */
    public void fleeFight(Ship coward, Ship opponent){}

    /** Give winner reward and end the fight (or the game)
     * @param loser - the ship that lost
     * @param victor - the ship that won */
    public void endFight(Ship loser, Ship victor){}

    /** Check if a ship is dead
     * @param s - the ship
     * @return true if the ship is dead else false */
    public boolean isDead(Ship s){
        return false;
    }
}
