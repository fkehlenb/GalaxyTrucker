package com.galaxytrucker.galaxytruckerreloaded.Model;

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
    @OneToOne
    private Ship userShip;

    /** Whether or not the user is logged in */
    @NonNull
    private boolean loggedIn = false;
}
