package com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout;

import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
public abstract class System extends Room implements Serializable {

    /**
     * A system's energy level
     */
    @NonNull
    private int energy;

    /**
     * Maximum energy a system can take
     */
    @NonNull
    private int maxEnergy;

    /**
     * Damage the system has taken
     */
    @NonNull
    private int damage;

    /** Whether or not the system is disabled */
    @NonNull
    private boolean disabled = false;
}
