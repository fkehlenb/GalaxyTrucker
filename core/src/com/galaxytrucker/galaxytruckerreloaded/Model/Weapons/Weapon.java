package com.galaxytrucker.galaxytruckerreloaded.Model.Weapons;

public abstract class Weapon {

    /** Weapon damage */
    private int damage;

    /** Weapon coolDown */
    private int cooldown;

    /** Weapon energy */
    private int energy;

    private int missileCost;
    private float dropchance;
    private int crewdamage;
    /**
     * Amount of shields ignored by attack
     */
    private int piercing;
    /**
     * How many projectiles are fired per burst
     */
    private int burst;
    private float precision;
}
