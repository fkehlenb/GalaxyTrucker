package com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout;

import lombok.*;

import javax.persistence.Entity;
import java.io.Serializable;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@Entity
public class Engine extends System implements Serializable {

    /**
     * Ob ein Crew mitglied das System stärkt
     */
    @NonNull
    private boolean manned = false;
}
