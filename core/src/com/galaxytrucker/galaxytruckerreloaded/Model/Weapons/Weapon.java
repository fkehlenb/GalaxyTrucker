package com.galaxytrucker.galaxytruckerreloaded.Model.Weapons;

import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;

public abstract class Weapon {

    /** Weapon damage */
    private int damage;

    /** Amount of rounds for the weapon to be used again */
    private int cooldown;

    /** Weapon energy */
    private int energy;

    /** Amount of missiles spent per attack */
    private int missileCost;
    private float precision;
    /** probability of randomly finding this weapon */
    private float dropChance;
    /** Amount of shield bars this Weapon can ignore */
    private int shieldPiercing;
    /** Probability of causing a breach in the section on hit */
    private float breachChance;
    /** Damage taken by crew in the hit section */
    private int crewDamage;
    /** How many projectiles are fired per burst */
    private int burst;

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public float getDropChance() {
        return dropChance;
    }

    public void setDropChance(float dropChance) {
        this.dropChance = dropChance;
    }

    public void attack(Ship target){}
}
