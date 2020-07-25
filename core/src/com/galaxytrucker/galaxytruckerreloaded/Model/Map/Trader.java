package com.galaxytrucker.galaxytruckerreloaded.Model.Map;

import java.util.List;
import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@NamedQueries({
        @NamedQuery(name = "Trader.getById",query = "select t from Trader t where t.id =: id")
})
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
    @LazyCollection(LazyCollectionOption.FALSE)
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
    @LazyCollection(LazyCollectionOption.FALSE)
    @NonNull
    private List<Crew> crewStock;

}
