package com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout;

import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import lombok.*;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@Entity
public class WeaponSystem extends System {

    /** whether or not its manned */
    @NonNull
    private boolean manned = false;

    /** List of weapons this ship has */
    @OneToMany
    private List<Weapon> shipWeapons;

}
