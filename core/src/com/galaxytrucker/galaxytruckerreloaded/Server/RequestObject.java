package com.galaxytrucker.galaxytruckerreloaded.Server;

import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestObject {

    /** Type of Request */
    private RequestType requestType;

    /** Username */
    private String username;

    /** Ship */
    private Ship ship;
}
