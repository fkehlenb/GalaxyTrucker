package com.galaxytrucker.galaxytruckerreloaded.Model.Map;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@NamedQueries({
        @NamedQuery(name = "Overworld.getByUsername", query = "select o from Overworld o where o.associatedUser =: name")
})
@Getter
@Setter
public class Overworld implements Serializable {

    /** ID */
    @NonNull
    @Id
    private int id;

    /** Seed */
    @NonNull
    private int seed;

    /**
     * the difficulty of the map
     * 0 = easy
     * 1 = middle
     * 2 = hard
     */
    private int difficulty;

    /** Username used as ID */
    @NonNull
    private String associatedUser;

    /** Stores planet and their location on the map */
    @OneToMany(cascade = CascadeType.ALL)
    private List<Planet> planetMap;

    /** The start planet */
    @OneToOne(cascade = CascadeType.ALL)
    private Planet startPlanet;
}
