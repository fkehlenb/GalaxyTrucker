package com.galaxytrucker.galaxytruckerreloaded.Model.Crew;

import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.System;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@DatabaseTable(tableName = "crew")
public class Crew implements Serializable {

    /**
     * ID
     */
    @DatabaseField(id = true, columnName = "ID")
    @NonNull
    private int id;

    /**
     * Name
     */
    @DatabaseField(columnName = "name")
    @NonNull
    private String name;

    /**
     * Health
     */
    @DatabaseField(columnName = "health")
    @NonNull
    private int health;

    /**
     * Max health
     */
    @DatabaseField(columnName = "maxhealth")
    @NonNull
    private int maxhealth;

    /**
     * Die Fähigkeiten des Crewmitglieds.
     * Das Array besteht aus
     * [Weapon, Shield, Engine, Repair, Combat]
     */
    @DatabaseField(columnName = "stats")
    @NonNull
    private int[] stats;

    /**
     * The room this crew member is in
     */
    @DatabaseField(foreign = true, columnName = "system")
    private Room currentRoom;

    /**
     * The user who owns this crew member
     */
    @DatabaseField(columnName = "associatedUser")
    private String associatedUser;

}
