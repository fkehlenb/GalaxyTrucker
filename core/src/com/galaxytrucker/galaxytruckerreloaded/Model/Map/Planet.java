package com.galaxytrucker.galaxytruckerreloaded.Model.Map;


import com.badlogic.gdx.graphics.Texture;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;

import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.w3c.dom.Text;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@Entity
@NamedQueries({
        @NamedQuery(name = "Planet.getById",query = "select p from Planet p where p.id =: id")
})
public class Planet implements Serializable {

    /** ID */
    @Id
    @NonNull
    private int id;

    /**
     * Planet name
     */
    @NonNull
    private String name;

    /**
     * Horizontale Position auf der Karte
     */
    @NonNull
    private float posX;

    /**
     * Vertikale Position auf der Karte
     */
    @NonNull
    private float posY;

    /**
     * Ereignis dass auf diesem Planeten eintrifft
     */
    @NonNull
    private PlanetEvent event;

    /**
     * If already discovered set to true
     */
    @NonNull
    private boolean discovered = false;

    /** Ships at this planet */
    @NonNull
    @ManyToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Ship> ships;

    /** Trader */
    @ManyToOne(cascade = CascadeType.ALL)
    private Trader trader;

    @NonNull
    private String planetTexture;

    /** Whether or not the planet has been looted */
    private boolean looted = false;

    @Override
    public boolean equals(Object o){
        if (o==null){
            return false;
        }
        return ((Planet) o).getId()==this.getId();
    }
}
