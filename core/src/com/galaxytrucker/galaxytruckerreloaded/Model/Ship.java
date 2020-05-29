package com.galaxytrucker.galaxytruckerreloaded.Model;

import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Planet;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@DatabaseTable(tableName = "ship")
public class Ship implements Serializable {

    /** ID */
    @DatabaseField(id = true,columnName = "ID")
    @NonNull
    private int id;

    /**
     * The user this ship belongs to
     * (uses the user's username)
     */
    @DatabaseField(columnName = "associatedUser")
    @NonNull
    private String associatedUser;

    /**
     * HP
     */
    @NonNull
    @DatabaseField(columnName = "HP")
    private int hp;


    /** Coins */
    @NonNull
    @DatabaseField(columnName = "coins")
    private int coins;

    /** Amount of available missiles */
    @NonNull
    @DatabaseField(columnName = "missles")
    private int missiles;

    /** Amount of fuel left*/
    @NonNull
    @DatabaseField(columnName = "fuel")
    private int fuel;

    /**
     * Energy to be distributed
     */
    @NonNull
    @DatabaseField(columnName = "energy")
    private int energy;

    /**
     * Shields that are currently active
     */
    @NonNull
    @DatabaseField(columnName = "shieldCharge")
    private int shieldCharge;

    /**
     * Total number of Shields that are powered. Possibly redundant through Shield.getEnergy/2
     */
    @NonNull
    @DatabaseField(columnName = "maximumShieldCharge")
    private int maxShieldCharge;

    /**
     * chance for the ship to dodge incoming attacks
     */
    @NonNull
    @DatabaseField(columnName = "evasionChance")
    private float evasionChance;

    /**
     * The planet the ship is currently at
     */
    @NonNull
    @DatabaseField(foreign = true,columnName = "planet")
    private Planet planet;

    /** Shields */
    @NonNull
    @DatabaseField(columnName = "shields")
    private int shields;

    /**
     * time needed until position can be changed again
     */
    @NonNull
    @DatabaseField(columnName = "FTLCharge")
    private int FTLCharge;

    /** This ship's systems */
    @NonNull
    @DatabaseField(columnName = "systems",foreign = true)
    private List<Room> systems;

    /** Inventory */
    @NonNull
    @DatabaseField(foreign = true, columnName = "inventory")
    private List<Weapon> inventory;

    /** Whether or not the ship is in combat */
    @DatabaseField(columnName = "inCombat")
    @NonNull
    private boolean inCombat = false;

}
