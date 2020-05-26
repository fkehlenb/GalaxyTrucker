package com.galaxytrucker.galaxytruckerreloaded.Model.Map;

import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@DatabaseTable
public class Trader extends Planet implements Serializable {

    /** Weapons for sale */
    @DatabaseField(foreign = true)
    @NonNull
    private List<Weapon> weaponsForSale;

}
