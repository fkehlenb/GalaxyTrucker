package com.galaxytrucker.galaxytruckerreloaded.Server;

import lombok.Getter;
import lombok.Setter;

/** Response sent from the server to the client */
@Getter
@Setter
public class ResponseObject {

    /** If the request was accepted */
    private boolean validRequest = false;
}
