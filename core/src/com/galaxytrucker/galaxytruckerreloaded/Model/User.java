package com.galaxytrucker.galaxytruckerreloaded.Model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.*;

import java.io.Serializable;

@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@DatabaseTable(tableName = "USER")
public class User implements Serializable {

    /**
     * Username
     */
    @DatabaseField(id = true, columnName = "USERNAME")
    @NonNull
    private String username;

    /**
     * The user's ship
     */
    @DatabaseField(columnName = "userShip", foreign = true)
    private Ship userShip;
}
