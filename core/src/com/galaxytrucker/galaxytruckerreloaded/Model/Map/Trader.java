package com.galaxytrucker.galaxytruckerreloaded.Model.Map;

import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@DatabaseTable(tableName = "trader")
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Trader extends Planet implements Serializable {

    /** ID */
    @DatabaseField(id = true)
    private int id;

    /** Associated user */
    @DatabaseField(foreign = true)
    private Planet planet;

    /** Weapons for sale */
    @DatabaseField(foreign = true)
    @NonNull
    private List<Weapon> weaponsForSale;

    /** Rockets for sale */
    @DatabaseField
    @NonNull
    private int rocketStock;

    /** Fuel for sale */
    @DatabaseField
    @NonNull
    private int fuelStock;

    /** Crew for sale */
    @DatabaseField(foreign = true)
    @NonNull
    private List<Crew> crewStock;

}
