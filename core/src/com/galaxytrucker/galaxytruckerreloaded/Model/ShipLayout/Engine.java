package com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@DatabaseTable(tableName = "engine")
public class Engine extends System implements Serializable {

    /**
     * Ob ein Crew mitglied das System stärkt
     */
    @DatabaseField(columnName = "manned")
    @NonNull
    private boolean manned = false;
}
