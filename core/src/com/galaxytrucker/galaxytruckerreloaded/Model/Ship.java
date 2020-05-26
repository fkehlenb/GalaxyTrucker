package com.galaxytrucker.galaxytruckerreloaded.Model;

import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Planet;
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


    /**
     * Resources are stored in ship
     */
    private int money;
    private int missiles;
    private int fuel;

    /**
     * Energy to be distributed
     */
    private int energy;

    /**
     * Shields that are currently active
     */
    private int shieldCharge;

    /**
     * Total number of Shields that are powered. Possibly redundant through Shield.getEnergy/2
     */
    private int maxShieldCharge;

    /**
     * chance for the ship to dodge incoming attacks
     */
    private float evasion;

    /**
     * X and Y coordinates on the map
     */
    private Planet position;

    /**
     * time needed until position can be changed again
     */
    @NonNull
    @DatabaseField(columnName = "SHIELDS")
    private int shields;

    private int FTLcharge;


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
