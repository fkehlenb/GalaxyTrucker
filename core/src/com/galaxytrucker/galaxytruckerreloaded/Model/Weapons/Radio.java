package com.galaxytrucker.galaxytruckerreloaded.Model.Weapons;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@DatabaseTable(tableName = "radioWeapon")
public class Radio extends Weapon implements Serializable {

    /** Weapon name */
    @NonNull
    @DatabaseField(columnName = "radioWeaponName")
    private String name;
}
