package com.galaxytrucker.galaxytruckerreloaded.Model.Crew;

import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Tile;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
public class Crew implements Serializable {

    /**
     * ID
     */
    @Id
    @NonNull
    private int id;

    /**
     * Name
     */
    @NonNull
    private String name;

    /**
     * Health
     */
    @NonNull
    private int health;

    /**
     * Max health
     */
    @NonNull
    private int maxhealth;

    /**
     * Die Fähigkeiten des Crewmitglieds.
     * Das Array besteht aus
     * [Weapon, Shield, Engine, Repair, Combat]
     */
    @NonNull
    @ElementCollection
    private List<Integer> stats;

    /**
     * The room this crew member is in
     */
    @OneToOne
    private Room currentRoom;

    /** Tile the crew member is standing on */
    @OneToOne
    private Tile tile;

    /**
     * The price of the different crew-members
     */
    @NonNull
    private int price;

    /**
     * The user who owns this crew member
     */
    @NonNull
    private String associatedUser;

}
