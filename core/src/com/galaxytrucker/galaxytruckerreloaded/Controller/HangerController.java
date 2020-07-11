package com.galaxytrucker.galaxytruckerreloaded.Controller;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class HangerController extends Controller {

    private static HangerController singleton;

    public static HangerController getInstance() {
        if(singleton == null) {
            singleton = new HangerController();
        }
        return singleton;
    }

    /**
     * Picks a Shipmodel
     * @param username - the specified username
     */
    public void pickShip(String username){
    }
}
