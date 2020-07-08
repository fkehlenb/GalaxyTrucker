package com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout;

import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.io.Serializable;

/** Tiles make up the rooms */
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@Entity
public class Tile implements Serializable {

    /** ID */
    @Id @NonNull
    private int id;

    /** Position x in room */
    @NonNull
    private int posX;

    /** Position y in room */
    @NonNull
    private int posY;

    /** Crew member on this tile */
    @OneToOne(cascade = CascadeType.ALL)
    private Crew standingOnMe = null;

    /** If the tile is empty */
    public boolean isEmpty(){
        return standingOnMe != null;
    }
}
