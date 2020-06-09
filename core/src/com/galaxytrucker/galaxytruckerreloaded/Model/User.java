package com.galaxytrucker.galaxytruckerreloaded.Model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@Entity
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
    private Ship userShip;

    /** Whether or not the user is logged in */
    @NonNull
    private boolean loggedIn = false;
}
