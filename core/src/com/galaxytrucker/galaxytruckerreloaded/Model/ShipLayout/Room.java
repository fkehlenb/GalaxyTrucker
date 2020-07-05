package com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout;


import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/** Rooms make up the spaceship */
@Getter
@Setter
@Entity
public abstract class Room implements Serializable {

    /**
     * ID
     */
    @Id
    @NonNull
    private int id;

    /**
     * Höhe des Raumes. Räume sind immer rechteckig.
     */
    @NonNull
    private int height;

    /**
     * Weite des Raumes. Räume sind immer rechteckig.
     */
    @NonNull
    private int width;

    /**
     * Wenn ein Raum Schaden kriegt kann ein Loch entstehen.
     * Die Zahl ist der Wert wie lange dieses repariert werden muss um geschlossen zu werden.
     */
    @NonNull
    private int breach;

    /**
     * Der Sauerstoffgehalt des Raumes
     */
    @NonNull
    private int oxygen;

    /** X position */
    @NonNull
    private int posX;

    /** Y position */
    @NonNull
    private int posY;

    /** Crew in this system */
    @NonNull
    @OneToMany(cascade = CascadeType.ALL)
    private List<Crew> crew;

    /** Tiles the room is made out of */
    @NonNull
    @OneToMany(cascade = CascadeType.ALL)
    private List<Tile> tiles;
}
