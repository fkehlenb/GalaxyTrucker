package com.galaxytrucker.galaxytruckerreloaded.Model.Map;


import com.j256.ormlite.field.DatabaseField;
import lombok.NonNull;

import java.io.Serializable;
import java.util.HashMap;

public class Overworld implements Serializable {

    /** Username used as ID */
    @NonNull
    @DatabaseField(columnName = "user",id = true)
    private String associatedUser;

    /** Stores planet and their location on the map */
    @NonNull
    @DatabaseField(columnName = "planetMap")
    private HashMap<float[],Planet> planetMap;

    /** Constructor */
    public Overworld(int seed) {
        planetMap = new HashMap<>();
    }
}
