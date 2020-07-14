package com.galaxytrucker.galaxytruckerreloaded.Server;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
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
}
