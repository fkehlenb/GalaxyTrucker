package com.galaxytrucker.galaxytruckerreloaded.Model.Planet;

public abstract class Planet {

    /** Planet name */
    private String name;
    /** Horizontale Position auf der Karte */
    private float posX;
    /** Vertikale Position auf der Karte */
    private float posY;
    /** Ereignis dass auf diesem Planeten eintrifft */
    private  PlanetEvent event;
}
