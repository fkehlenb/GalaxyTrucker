package com.galaxytrucker.galaxytruckerreloaded.Model.Map;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

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
public class Overworld implements Serializable {

    /** ID */
    @NonNull
    @Id
    private int id;

    /** Username used as ID */
    @NonNull
    private String associatedUser;

    /** Stores planet and their location on the map */
    @OneToMany
    private Map<String,Planet> planetMap;

    /** The start planet */
    @NonNull
    @OneToOne
    private Planet startPlanet;

    /** Constructor */
    public Overworld(int seed) {
        planetMap = new HashMap<>();
    }
}
