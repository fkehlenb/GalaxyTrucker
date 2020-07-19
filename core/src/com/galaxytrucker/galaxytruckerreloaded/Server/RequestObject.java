package com.galaxytrucker.galaxytruckerreloaded.Server;

import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Planet;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Trader;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.io.Serializable;
import java.util.List;

/** Request sent to the server */
@Getter
@Setter
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
public class RequestObject implements Serializable {

    /** ID */
    @Id
    private int id;

    /** Type of Request */
    private RequestType requestType;

    /** Username */
    private String username;

    /** Ship */
    @OneToOne
    private Ship ship;

    /** Opponent ship */
    @OneToOne
    private Ship opponentShip;

    /** Trader */
    @OneToOne
    private Trader trader;

    /** Planet */
    @OneToOne
    private Planet planet;

    /** Crew */
    @OneToOne
    private Crew crew;

    /** List of crew */
    @OneToMany
    private List<Crew> moreCrew;

    /** Room */
    @OneToOne
    private Room room;

    /** Weapon */
    @OneToOne
    private Weapon weapon;

    /** Heal amount */
    private int healAmount;

    /** Damage amount */
    private int damageAmount;

    /** int - eg fuel, rockets, hp for trader */
    private int intAmount;
}
