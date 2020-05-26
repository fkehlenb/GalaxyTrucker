package com.galaxytrucker.galaxytruckerreloaded.Model.Weapons;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.*;

import java.io.Serializable;

@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@DatabaseTable(tableName = "bomb")
public class Bomb extends Weapon implements Serializable {

    /** Weapon name */
    @DatabaseField(columnName = "bombWeaponName")
    @NonNull
    private String name;

}
