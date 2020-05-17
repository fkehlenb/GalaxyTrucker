package com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout;

public abstract class Room {
    /**
     * Höhe des Raumes. Räume sind immer rechteckig.
     */
    private int height;

    /**
     * Weite des Raumes. Räume sind immer rechteckig.
     */
    private int width;

    /**
     * Wenn ein Raum Schaden kriegt kann ein Loch entstehen.
     * Die Zahl ist der Wert wie lange dieses repariert werden muss um geschlossen zu werden.
     */
    private int breach;

    /**
     * Der Sauerstoffgehalt des Raumes
     */
    private int oxygen;
}
