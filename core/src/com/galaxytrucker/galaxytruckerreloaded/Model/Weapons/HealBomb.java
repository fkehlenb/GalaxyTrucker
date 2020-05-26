package com.galaxytrucker.galaxytruckerreloaded.Model.Weapons;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.*;

import java.io.Serializable;

@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@DatabaseTable(tableName = "healBombWeapon")
public class HealBomb extends Weapon implements Serializable {

    /** Weapon name */
    @DatabaseField(columnName = "healBombWeaponName")
    @NonNull
    private String name;
}
