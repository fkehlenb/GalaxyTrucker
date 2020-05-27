package com.galaxytrucker.galaxytruckerreloaded.Model.Map;

import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.j256.ormlite.field.DatabaseField;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
public class Planet implements Serializable {

    /**
     * Planet name
     */
    @DatabaseField(id = true, columnName = "name")
    @NonNull
    private String name;

    /**
     * Horizontale Position auf der Karte
     */
    @DatabaseField(columnName = "posX")
    @NonNull
    private float posX;

    /**
     * Vertikale Position auf der Karte
     */
    @DatabaseField(columnName = "posY")
    @NonNull
    private float posY;

    /**
     * Ereignis dass auf diesem Planeten eintrifft
     */
    @DatabaseField(columnName = "event", foreign = true)
    @NonNull
    private Enum<PlanetEvent> event;

    /**
     * If already discovered set to true
     */
    @DatabaseField(columnName = "discovered")
    @NonNull
    private boolean discovered = false;

    /** Ships at this planet */
    @DatabaseField(foreign = true, columnName = "ships")
    @NonNull
    private List<Ship> ships;
}
