package com.galaxytrucker.galaxytruckerreloaded.Server;

import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Planet;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Trader;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/** Request sent to the server */
@Getter
@Setter
public class RequestObject {

    /** Type of Request */
    private RequestType requestType;

    /** Username */
    private String username;

    /** Ship */
    private Ship ship;

    /** Trader */
    private Trader trader;

    /** Planet */
    private Planet planet;

    /** Crew */
    private Crew crew;

    /** List of crew */
    private List<Crew> moreCrew;

    /** Room */
    private Room room;
    /** Weapon */
    private Weapon weapon;

    /** Heal amount */
    private int healAmount;

    /** Damage amount */
    private int damageAmount;
    /** int - eg fuel, rockets, hp for trader */
    private int intAmount;


}