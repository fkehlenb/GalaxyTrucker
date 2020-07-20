package com.galaxytrucker.galaxytruckerreloaded.Model.Map;

import java.util.List;
import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Trader implements Serializable {

    /** ID */
    @NonNull @Id
    private int id;

    /** Planet the trader is located at */
    @ManyToOne
    @NonNull
    private Planet planet;

    /** Weapons for sale */
    @NonNull
    @ManyToMany
    private List<Weapon> weaponStock;

    /** Rockets for sale */
    @NonNull
    private int missileStock;

    /** Fuel for sale */
    @NonNull
    private int fuelStock;

    /** HP for sale */
    @NonNull
    private int hpStock;

    /** Crew for sale */
    @ManyToMany
    @NonNull
    private List<Crew> crewStock;

}
