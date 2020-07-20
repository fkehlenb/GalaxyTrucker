package com.galaxytrucker.galaxytruckerreloaded.Model.Weapons;

import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.System;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Getter
@Setter
@Entity
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Weapon implements Serializable {

    /**
     * ID
     */
    @NonNull
    @Id
    private int id;

    /** Weapon type */
    @NonNull
    private WeaponType weaponType;

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
    private float accuracy;

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

    /**
     * Weapon price, sorted by Weapon-level 1-6
     */
    @ElementCollection
    private List<Integer> price;

    /** Weapon system this weapon belongs to */
    @ManyToOne
    private System weaponSystem;

    /** Weapon name */
    @NonNull
    private String weaponName;

    /** Weapon price */
    @NonNull
    private int weaponPrice;

}
