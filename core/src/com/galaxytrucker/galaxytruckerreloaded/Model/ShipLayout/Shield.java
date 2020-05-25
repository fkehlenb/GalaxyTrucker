package com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@DatabaseTable(tableName = "shield")
public class Shield extends System implements Serializable {

    /**
     * Ob ein Crewmitglied das System stärkt
     */
    @DatabaseField(columnName = "manned")
    @NonNull
    private boolean manned = false;

}
