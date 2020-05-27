package com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@DatabaseTable(tableName = "blankRoom")
public class BlankRoom extends Room {

    /** ID */
    @NonNull
    @DatabaseField(id = true)
    private int id;
}
