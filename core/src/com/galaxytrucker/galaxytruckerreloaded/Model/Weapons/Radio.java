package com.galaxytrucker.galaxytruckerreloaded.Model.Weapons;

import lombok.*;

import javax.persistence.Entity;
import java.io.Serializable;

@Getter
@Setter
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
public class Radio extends Weapon implements Serializable {

    /** Weapon name */
    @NonNull
    private String name;
}
