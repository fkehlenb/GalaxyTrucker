package com.galaxytrucker.galaxytruckerreloaded.Model.Map;


import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@Entity
public class Planet implements Serializable {

    /** ID */
    @Id
    @NonNull
    private int id;

    /**
     * Planet name
     */
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
    private PlanetEvent event;

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
    @OneToOne(cascade = CascadeType.ALL)
    private Trader trader;

    /** The overWorld this planet belongs to */
    @ManyToOne
    private Overworld overworld;
}
