package com.galaxytrucker.galaxytruckerreloaded.Model;

import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Planet;
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

    /**
     * The user this ship belongs to
     * (uses the user's username)
     */
    @DatabaseField(columnName = "ID", id = true)
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

    /** This ship's weapons */
    @NonNull
    @DatabaseField(columnName = "weapons", foreign = true)
    private List<Weapon> weapons;

    /** This ship's systems */
    @NonNull
    @DatabaseField(columnName = "systems",foreign = true)
    private List<System> systems;

    /** List of crew */
    @NonNull
    @DatabaseField(foreign = true,columnName = "crew")
    private List<Crew> crew;

    /** Inventory */
    @NonNull
    @DatabaseField(foreign = true, columnName = "inventory")
    private List<Weapon> inventory;

    /**
     * Take damage
     */
    private void takeDamage(int amount) {
    }

    /**
     * Add hp
     */
    private void heal(int hp) {
    }

    /**
     * Add shields
     */
    private void applyShields(int shields) {
    }

}
