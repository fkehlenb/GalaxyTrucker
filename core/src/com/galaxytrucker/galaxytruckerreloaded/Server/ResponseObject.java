package com.galaxytrucker.galaxytruckerreloaded.Server;

import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Overworld;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.WeaponType;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/** Response sent from the server to the client */
@Getter
@Setter
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
public class ResponseObject implements Serializable {

    /** ID */
    @Id
    private int id;

    /** If the request was accepted */
    private boolean validRequest = false;

    /** The updated ship */
    @ManyToOne
    private Ship responseShip;

    /** Opponent ship */
    @ManyToOne
    private Ship opponent;

    /** The updated overworld */
    @ManyToOne
    private Overworld responseOverworld;

    /** Combat won */
    private boolean combatWon = false;

    /** Died? */
    private boolean dead = false;

    /** Fight over? */
    private boolean combatOver = false;

    /** Your round? */
    private boolean myRound = false;

    /** Previous action carried out */
    @ElementCollection
    private List<PreviousRoundAction> previousRoundAction;

    /** Weapon used for the above action */
    @ElementCollection
    private List<WeaponType> weaponUsed;

    /** Reward weapons */
    @ManyToMany(cascade = CascadeType.MERGE)
    private List<Weapon> rewardWeapons;

    /** Reward crew */
    @ManyToMany(cascade = CascadeType.MERGE)
    private List<Crew> rewardCrew;

    /** Successful flee fight */
    private boolean fledFight = false;
}
