package com.galaxytrucker.galaxytruckerreloaded.Server;

import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Overworld;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.io.Serializable;

/** Response sent from the server to the client */
@Getter
@Setter
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
public class ResponseObject implements Serializable {

    /** ID */
    @Id
    private int id;

    /** If the request was accepted */
    private boolean validRequest = false;

    /** The updated ship */
    @ManyToOne
    private Ship responseShip;

    /** The updated overworld */
    @ManyToOne
    private Overworld responseOverworld;
}
