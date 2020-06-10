package com.galaxytrucker.galaxytruckerreloaded.Model.Map;

import java.util.List;
import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;

@Getter
@Setter
@Entity
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Trader extends Planet implements Serializable {

    /** ID */
    @Id
    private int id;

    /** Associated user */
    private Planet planet;

    /** Weapons for sale */
    @NonNull
    @OneToMany
    private List<Weapon> weaponStock;

    /** Rockets for sale */
    @NonNull
    private int missileStock;

    /** Fuel for sale */
    @NonNull
    private int fuelStock;

    /** Crew for sale */
    @OneToMany
    @NonNull
    private List<Crew> crewStock;

}
