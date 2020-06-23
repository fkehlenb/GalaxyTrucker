package com.galaxytrucker.galaxytruckerreloaded.Model.Map;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
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

    /** Username used as ID */
    @NonNull
    private String associatedUser;

    /** Stores planet and their location on the map */
    @OneToMany(cascade = CascadeType.ALL)
    private Map<String,Planet> planetMap;

    /** The start planet */
    @OneToOne(cascade = CascadeType.ALL)
    private Planet startPlanet;
}
