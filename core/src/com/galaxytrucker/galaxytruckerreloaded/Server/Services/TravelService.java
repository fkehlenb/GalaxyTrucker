package com.galaxytrucker.galaxytruckerreloaded.Server.Services;

import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Planet;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Server.Persistence.ShipDAO;
import lombok.*;

/** Used to move user from one star to another */
@Getter
@Setter
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class TravelService {

    /** ShipDAO for database access */
    @NonNull
    private ShipDAO shipDAO;

    /** Validate the travel request
     * @param s - the ship that wisches to travel
     * @return true if the travel request is valid */
    public boolean validateJump(Ship s){
        return false;
    }

    /** Jump ship from one star to another
     * @param s - the ship to jump
     * @param dest - the destination star */
    public void jump(Ship s, Planet dest){}
}
