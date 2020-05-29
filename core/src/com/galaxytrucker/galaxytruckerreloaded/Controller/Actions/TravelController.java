package com.galaxytrucker.galaxytruckerreloaded.Controller.Actions;

import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Planet;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class TravelController extends Controller{
    /**
     * My own ship
     */
    @NonNull
    private Ship myself;

    /**
     * travels from one location to another
     * @param destination - the destination
     */
    public void travel(Planet destination){
    }
}
