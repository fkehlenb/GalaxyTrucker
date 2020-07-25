package com.galaxytrucker.galaxytruckerreloaded.Model.Crew;

import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Tile;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@NamedQueries({
        @NamedQuery(name = "Crew.getById",query = "select c from Crew c where c.id =: id")
})
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
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Integer> stats;

    /**
     * The room this crew member is in
     */
    @ManyToOne
    private Room currentRoom;

    /** Tile the crew member is standing on */
    @ManyToOne
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

    /** Whether or not the crew member was just moved */
    private boolean justMoved = false;

    @Override
    public boolean equals(Object o){
        if (o==null||getClass()!=o.getClass()){
            return false;
        }
        return ((Crew) o).getId() == this.getId();
    }

}
