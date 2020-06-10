package com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout;

import lombok.*;

import javax.persistence.Entity;
import java.io.Serializable;

@Getter
@Setter
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
public class Shield extends System implements Serializable {

    /**
     * Ob ein Crewmitglied das System stärkt
     */
    @NonNull
    private boolean manned = false;

}
