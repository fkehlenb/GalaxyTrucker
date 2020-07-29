package com.galaxytrucker.galaxytruckerreloaded.View.UI.ShipInformation;

import com.badlogic.gdx.graphics.Texture;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.ShipType;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Ship.AbstractShip;

/**
 * ui for displaying a crew member on the enemy ship
 */
public class EnemyCrewUI {

    /** image of the crew member for the side bar **/
    Texture crewImage;

    /**
     * the crew member displayed with this ui
     */
    Crew crew;

    /**
     * the main class
     */
    Main main;

    /**
     * the ship view this crew ui belongs to
     */
    AbstractShip shipView;

    /**
     * the position of the room the crew member is in
     */
    private float roomX, roomY;

    /**
     * constructor
     * @param main the main class
     * @param crew the crew member
     */
    public EnemyCrewUI(Main main, Crew crew, AbstractShip shipView, float rX, float rY, ShipType type) {
        this.main = main;
        this.crew = crew;
        this.shipView = shipView;
        this.roomX = rX;
        this.roomY = rY;


        crewImage = new Texture("crew/"+type.toString().toLowerCase()+".png");
    }

    /**
     * dispose the ui
     */
    public void disposeEnemyCrewUI() {
        crewImage.dispose();
    }

    /**
     * render everything to the screen
     */
    public void render() {
        main.batch.begin();
        main.batch.draw(crewImage, roomX, roomY, 50, 50);
        main.batch.end();
    }

    /**
     * the crew member is updated
     * @param crew the new crew member
     */
    public void update(Crew crew, float roomX, float roomY) {
        this.crew = crew;
        crewMoved(roomX, roomY);
    }

    /**
     * the crew member was moved to a new room
     * called by shipview
     *
     * @param roomX the x position of the lower left corner the crew member is in
     */
    public void crewMoved(float roomX, float roomY) {
        this.roomX = roomX + crew.getTile().getPosX()*48;
        this.roomY = roomY + crew.getTile().getPosY()*48;
    }

    /**
     * the crew member died
     * called by controller
     */
    public void crewDied() {
        this.disposeEnemyCrewUI();
    }
}
