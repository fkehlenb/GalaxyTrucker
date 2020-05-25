package com.galaxytrucker.galaxytruckerreloaded.Model.Map;

import jdk.internal.util.xml.impl.Pair;

import java.util.HashMap;

public class Overworld {
    private HashMap<Pair,Planet> planetMap;

    public Overworld(int seed) {
        planetMap = new HashMap<>();
    }
}
