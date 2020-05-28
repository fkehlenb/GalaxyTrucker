package com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout;

import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.*;

@DatabaseTable(tableName = "weaponSystem")
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
public class WeaponSystem extends System {

    /** whether or not its manned */
    @NonNull
    @DatabaseField(columnName = "manned")
    private boolean manned = false;

    /** List of weapons this ship has */
    @DatabaseField(foreign = true,columnName = "shipWeapons")
    private List<Weapon> shipWeapons;

}
