package com.galaxytrucker.galaxytruckerreloaded.View.UI.ShipInformation;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.ShipType;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Ship.AbstractShip;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Ship.ShipView;

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
    float roomX, roomY;

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

    public void disposeEnemyCrewUI() {
        crewImage.dispose();
    }

    public void render() {
        main.batch.begin();
        main.batch.draw(crewImage, roomX, roomY, 50, 50);
        main.batch.end();
    }

    /**
     * the crew member is updated
     * @param crew the new crew member
     */
    public void update(Crew crew) {
        this.crew = crew;
    }

    /**
     * the crew member was moved to a new room
     * called by shipview
     *
     * @param roomX the x position of the lower left corner the crew member is in
     */
    public void crewMoved(float roomX, float roomY) {
        this.roomX = roomX;
        this.roomY = roomY;
    }

    /**
     * the crew member died
     * called by controller
     */
    public void crewDied() {
        this.disposeEnemyCrewUI();
    }

    /**
     * Setup called after initialisation
     */
    private void setup() {
    }

    /**
     * show the Crew ui
     */
    public void showCrewUI() {

    }

    /**
     * hide the Crew ui
     */
    public void hideCrewUI() {

    }
}
