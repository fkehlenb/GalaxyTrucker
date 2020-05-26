package com.galaxytrucker.galaxytruckerreloaded.Model;


import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Planet;

public class Ship {

    /**
     * HP
     */
    private int health;

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
