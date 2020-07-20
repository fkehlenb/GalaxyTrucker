package com.galaxytrucker.galaxytruckerreloaded.Model;

import com.galaxytrucker.galaxytruckerreloaded.Model.Map.Overworld;
import com.galaxytrucker.galaxytruckerreloaded.Server.Services.BattleService;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@Entity
@NamedQueries({
        @NamedQuery(name = "User.getByUsername",query = "select u from User u where u.username =: username")
})
public class User implements Serializable {

    /**
     * Username
     */
    @Id
    @NonNull
    private String username;

    /**
     * The user's ship
     */
    @ManyToOne(cascade = CascadeType.ALL)
    private Ship userShip;

    /** The user's overWorld map */
    @ManyToOne(cascade = CascadeType.ALL)
    private Overworld overworld;

    /** Whether or not the user is logged in */
    private boolean loggedIn = false;

    /** First game */
    private boolean firstGame = true;
}
