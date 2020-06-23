package com.galaxytrucker.galaxytruckerreloaded.Model;

import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Planet;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@Entity
@NamedQueries({
        @NamedQuery(name = "Ship.getByUsername", query = "select s from Ship s where s.associatedUser =: username")
})
public class Ship implements Serializable {

    /** ID */
    @Id
    @NonNull
    private int id;

    /**
     * The user this ship belongs to
     * (uses the user's username)
     */
    @NonNull
    private String associatedUser;

    /**
     * HP
     */
    @NonNull
    private int hp;


    /** Coins */
    @NonNull
    private int coins;

    /** Amount of available missiles */
    @NonNull
    private int missiles;

    /** Amount of fuel left*/
    @NonNull
    private int fuel;

    /**
     * Energy to be distributed
     */
    @NonNull
    private int energy;

    /**
     * Shields that are currently active
     */
    @NonNull
    private int shieldCharge;

    /**
     * Total number of Shields that are powered. Possibly redundant through Shield.getEnergy/2
     */
    @NonNull
    private int maxShieldCharge;

    /**
     * chance for the ship to dodge incoming attacks
     */
    @NonNull
    private float evasionChance;

    /**
     * The planet the ship is currently at
     */
    @NonNull
    @ManyToOne
    private Planet planet;

    /** Shields */
    @NonNull
    private int shields;

    /**
     * time needed until position can be changed again
     */
    @NonNull
    private int FTLCharge;

    /** This ship's systems */
    @NonNull
    @OneToMany
    private List<Room> systems;

    /** Inventory */
    @NonNull
    @OneToMany
    private List<Weapon> inventory;

    /** Whether or not the ship is in combat */
    @NonNull
    private boolean inCombat = false;

}
