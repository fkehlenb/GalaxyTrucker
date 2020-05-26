package com.galaxytrucker.galaxytruckerreloaded.Model.Weapons;

import com.j256.ormlite.field.DatabaseField;
import lombok.*;

import java.io.Serializable;


@Getter
@Setter
public abstract class Weapon implements Serializable {

    /**
     * ID
     */
    @DatabaseField(id = true, columnName = "ID")
    @NonNull
    private int ID;

    /** Weapon level */
    @NonNull
    @DatabaseField(columnName = "weaponLevel")
    private int weaponLevel;

    /**
     * Weapon damage
     */
    @NonNull
    @DatabaseField(columnName = "damage")
    private int damage;

    /**
     * Amount of rounds for the weapon to be used again
     */
    @NonNull
    @DatabaseField(columnName = "cooldown")
    private int cooldown;

    /**
     * Weapon energy
     */
    @NonNull
    @DatabaseField(columnName = "energy")
    private int energy;

    /**
     * Amount of missiles spent per attack
     */
    @NonNull
    @DatabaseField(columnName = "missleCost")
    private int missileCost;

    /**
     * Weapon accuracy
     */
    @NonNull
    @DatabaseField(columnName = "accuracy")
    private float precision;

    /**
     * probability of randomly finding this weapon
     */
    @NonNull
    @DatabaseField(columnName = "dropChance")
    private float dropChance;

    /**
     * Amount of shield bars this Weapon can ignore
     */
    @NonNull
    @DatabaseField(columnName = "shieldPiercing")
    private int shieldPiercing;

    /**
     * Probability of causing a breach in the section on hit
     */
    @NonNull
    @DatabaseField(columnName = "breachChance")
    private float breachChance;

    /**
     * Damage taken by crew in the hit section
     */
    @NonNull
    @DatabaseField(columnName = "crewDamage")
    private int crewDamage;

    /**
     * How many projectiles are fired per burst
     */
    @NonNull
    @DatabaseField(columnName = "weaponBurst")
    private int burst;

}
