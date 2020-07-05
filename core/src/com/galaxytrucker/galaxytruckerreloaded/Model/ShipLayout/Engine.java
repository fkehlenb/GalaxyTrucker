package com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout;

import lombok.*;

import javax.persistence.Entity;
import java.io.Serializable;

@Getter
@Setter
@Entity
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@ToString(callSuper = true)
public class Engine extends System implements Serializable {

    /**
     * Ob ein Crew mitglied das System stärkt
     */
    @NonNull
    private boolean manned = false;
}
