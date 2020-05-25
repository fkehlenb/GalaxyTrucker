package com.galaxytrucker.galaxytruckerreloaded.Model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@DatabaseTable(tableName = "ship")
public class Ship implements Serializable {

    /**
     * The user this ship belongs to
     * (uses the user's username)
     */
    @DatabaseField(columnName = "ID", id = true)
    @NonNull
    private String associatedUser;

    /**
     * HP
     */
    @NonNull
    @DatabaseField(columnName = "HP")
    private int hp;

    /**
     * Shields
     */
    @NonNull
    @DatabaseField(columnName = "SHIELDS")
    private int shields;

    /**
     * Take damage
     */
    private void takeDamage(int amount) {
    }

    /**
     * Add hp
     */
    private void heal(int hp) {
    }

    /**
     * Add shields
     */
    private void applyShields(int shields) {
    }

}
