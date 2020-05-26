package com.galaxytrucker.galaxytruckerreloaded.Model.Map;

public abstract class Planet {

    /** Map name */
    private String name;
    /** Horizontale Position auf der Karte */
    private float posX;
    /** Vertikale Position auf der Karte */
    private float posY;

    private float[] position;
    /** Ereignis dass auf diesem Planeten eintrifft */
    private  PlanetEvent event;
}
