package com.galaxytrucker.galaxytruckerreloaded.Model.Weapons;

import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.System;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;


@Getter
@Setter
@Entity
public abstract class Weapon implements Serializable {

    /**
     * ID
     */
    @NonNull
    @Id
    private int id;

    /** Weapon level */
    @NonNull
    private int weaponLevel;

    /**
     * Weapon damage
     */
    @NonNull
    private int damage;

    /**
     * Amount of rounds for the weapon to be used again
     */
    @NonNull
    private int cooldown;

    /**
     * Weapon energy
     */
    @NonNull
    private int energy;

    /**
     * Amount of missiles spent per attack
     */
    @NonNull
    private int missileCost;

    /**
     * Weapon accuracy
     */
    @NonNull
    private float precision;

    /**
     * probability of randomly finding this weapon
     */
    @NonNull
    private float dropChance;

    /**
     * Amount of shield bars this Weapon can ignore
     */
    @NonNull
    private int shieldPiercing;

    /**
     * Probability of causing a breach in the section on hit
     */
    @NonNull
    private float breachChance;

    /**
     * Damage taken by crew in the hit section
     */
    @NonNull
    private int crewDamage;

    /**
     * How many projectiles are fired per burst
     */
    @NonNull
    private int burst;

    /** Weapon system this weapon belongs to */
    @OneToOne
    private System weaponSystem;

}
