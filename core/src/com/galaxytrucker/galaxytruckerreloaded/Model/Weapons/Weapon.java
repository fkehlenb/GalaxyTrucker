package com.galaxytrucker.galaxytruckerreloaded.Model.Weapons;

import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.System;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Getter
@Setter
@Entity
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@NamedQueries({
        @NamedQuery(name = "Weapon.getById",query = "select w from Weapon w where w.id =: id")
})
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
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Integer> price;

    /** Weapon system this weapon belongs to */
    @ManyToOne(cascade = CascadeType.ALL)
    private System weaponSystem;

    /** Weapon name */
    @NonNull
    private String weaponName;

    /** Weapon price */
    @NonNull
    private int weaponPrice;

    /** Current cooldown counter */
    private int currentCooldown = 0;

    @Override
    public boolean equals(Object o){
        if (o==null||getClass()!=o.getClass()){
            return false;
        }
        return ((Weapon) o).getId() == this.getId();
    }

}
