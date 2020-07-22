package com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout;

import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@ToString(callSuper = true)
public class System extends Room implements Serializable {

    /**
     * A system's energy level
     */
    @NonNull
    private int energy;

    /**
     * Maximum energy a system can take
     */
    @NonNull
    private int maxEnergy;

    /**
     * Damage the system has taken
     */
    @NonNull
    private int damage;

    /** Whether or not the system is disabled */
    @NonNull
    private boolean disabled = false;

    /**
     * Ob ein Crew mitglied das System stärkt
     */
    @NonNull
    private boolean manned = false;

    /** System type */
    @NonNull
    private SystemType systemType;

    /** List of weapons this ship has */
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Weapon> shipWeapons;

    /** Whether or not the system is unlocked */
    private boolean unlocked = true;

    /** Required args constructor
     * @param id - the database id
     * @param breach - breach
     * @param oxygen - oxygen
     * @param interiorID - intertior id
     * @param crew - crew
     * @param tiles - tiles
     * @param energy - energy
     * @param maxEnergy - max energy
     * @param damage - damage
     * @param systemType - system type
     * @param shipWeapons - weapons */
    public System(int id,int breach,int oxygen,int interiorID,List<Crew> crew,List<Tile> tiles,
                  int energy,int maxEnergy,int damage,SystemType systemType,List<Weapon> shipWeapons){
        super(id,breach,oxygen,interiorID,crew,tiles);
        this.energy = energy;
        this.maxEnergy = maxEnergy;
        this.damage = damage;
        this.systemType = systemType;
        this.shipWeapons = shipWeapons;
        super.setSystem(true);
    }
}
