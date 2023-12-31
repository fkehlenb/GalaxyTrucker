package com.galaxytrucker.galaxytruckerreloaded.Model.Map;


import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Entity
@AllArgsConstructor
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
     * 1 = easy
     * 2 = middle
     * 3 = hard
     */
    private int difficulty;

    /** Username used as ID */
    @NonNull
    private String associatedUser;

    /** Stores planet and their location on the map */
    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Planet> planetMap;

    /** The start planet */
    @ManyToOne
    private Planet startPlanet;

    /** The boss planet */
    @ManyToOne
    private Planet bossPlanet;

    /** Randomizer */
    private Random random = new Random(seed);
}
