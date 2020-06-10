package com.galaxytrucker.galaxytruckerreloaded.Model.Map;


import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.io.Serializable;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@Entity
public class Planet implements Serializable {

    /**
     * Planet name
     */
    @Id
    @NonNull
    private String name;

    /**
     * Horizontale Position auf der Karte
     */
    @NonNull
    private float posX;

    /**
     * Vertikale Position auf der Karte
     */
    @NonNull
    private float posY;

    /**
     * Ereignis dass auf diesem Planeten eintrifft
     */
    @NonNull
    private Enum<PlanetEvent> event;

    /**
     * If already discovered set to true
     */
    @NonNull
    private boolean discovered = false;

    /** Ships at this planet */
    @NonNull
    @OneToMany
    private List<Ship> ships;

    /** Trader */
    @OneToOne
    private Trader trader;
}
