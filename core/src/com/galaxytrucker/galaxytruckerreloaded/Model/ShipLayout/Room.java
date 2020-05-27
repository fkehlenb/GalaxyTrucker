package com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout;


import com.j256.ormlite.field.DatabaseField;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
public abstract class Room implements Serializable {

    /**
     * Höhe des Raumes. Räume sind immer rechteckig.
     */
    @DatabaseField(columnName = "height")
    @NonNull
    private int height;

    /**
     * Weite des Raumes. Räume sind immer rechteckig.
     */
    @DatabaseField(columnName = "width")
    @NonNull
    private int width;

    /**
     * Wenn ein Raum Schaden kriegt kann ein Loch entstehen.
     * Die Zahl ist der Wert wie lange dieses repariert werden muss um geschlossen zu werden.
     */
    @DatabaseField(columnName = "breach")
    @NonNull
    private int breach;

    /**
     * Der Sauerstoffgehalt des Raumes
     */
    @DatabaseField(columnName = "oxygen")
    @NonNull
    private int oxygen;

    /** X position */
    @NonNull
    @DatabaseField(columnName = "posX")
    private int posX;

    /** Y position */
    @NonNull
    @DatabaseField(columnName = "posY")
    private int posY;
}
