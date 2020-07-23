package com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout;


import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Rooms make up the spaceship
 */
@Getter
@Setter
@Entity
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@NamedQueries({
        @NamedQuery(name = "Room.getById", query = "select r from Room r where r.id =: id")
})
public class Room implements Serializable {

    /**
     * ID
     */
    @Id
    @NonNull
    private int id;

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

    /**
     * Room id in ship
     */
    @NonNull
    private int interiorID;

    /**
     * Crew in this system
     */
    @NonNull
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Crew> crew;

    /**
     * Tiles the room is made out of
     */
    @NonNull
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Tile> tiles;

    /**
     * Whether or not it is a system
     */
    @NonNull
    private boolean isSystem = false;

    @Override
    public boolean equals(Object o) {
        if (o == null || o.getClass() != this.getClass()) {
            return false;
        }
        return ((Room) o).getId() == this.getId();
    }
}
