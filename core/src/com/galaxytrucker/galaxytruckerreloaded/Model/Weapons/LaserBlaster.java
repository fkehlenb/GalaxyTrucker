package com.galaxytrucker.galaxytruckerreloaded.Model.Weapons;

import lombok.*;

import javax.persistence.Entity;
import java.io.Serializable;

@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@Entity
public class LaserBlaster extends Weapon implements Serializable {

    /** Weapon name */
    @NonNull
    private String name;

}
