package com.galaxytrucker.galaxytruckerreloaded.Server;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

/** Response sent from the server to the client */
@Getter
@Setter
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
public class ResponseObject {

    /** ID */
    @Id
    private int id;

    /** If the request was accepted */
    private boolean validRequest = false;
}
