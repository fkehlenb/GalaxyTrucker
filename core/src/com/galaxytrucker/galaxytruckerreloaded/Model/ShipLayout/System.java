package com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout;

import com.j256.ormlite.field.DatabaseField;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public abstract class System extends Room implements Serializable {

    /**
     * ID
     */
    @DatabaseField(columnName = "ID", id = true)
    @NonNull
    private int id;

    /**
     * A system's energy level
     */
    @DatabaseField(columnName = "energy")
    @NonNull
    private int energy;

    /**
     * Maximum energy a system can take
     */
    @DatabaseField(columnName = "maxEnergy")
    @NonNull
    private int maxEnergy;

    /**
     * Damage the system has taken
     */
    @DatabaseField(columnName = "damage")
    @NonNull
    private int damage;
}
