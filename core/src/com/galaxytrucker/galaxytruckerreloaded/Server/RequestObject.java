package com.galaxytrucker.galaxytruckerreloaded.Server;

import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Planet;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Trader;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.System;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import lombok.*;

import javax.persistence.*;
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
    @ManyToOne
    private Ship ship;

    /** Opponent ship */
    @ManyToOne
    private Ship opponentShip;

    /** Trader */
    @ManyToOne
    private Trader trader;

    /** Planet */
    @ManyToOne
    private Planet planet;

    /** Crew */
    @ManyToOne
    private Crew crew;

    /** List of crew */
    @ManyToMany
    private List<Crew> moreCrew;

    /** Room */
    @ManyToOne
    private Room room;

    /** System */
    @ManyToOne
    private System system;

    /** Weapon */
    @ManyToOne
    private Weapon weapon;

    /** Heal amount */
    private int healAmount;

    /** Damage amount */
    private int damageAmount;

    /** int - eg fuel, rockets, hp for trader */
    private int intAmount;

    /** Is player currently in a pvp situation? */
    private boolean pvp = false;
}
