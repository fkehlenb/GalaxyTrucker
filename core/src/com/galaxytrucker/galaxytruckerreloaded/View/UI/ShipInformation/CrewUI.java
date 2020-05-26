package com.galaxytrucker.galaxytruckerreloaded.View.UI.ShipInformation;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;

import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.CrewDismissButton;

/**
 * shows the crew on board
 */
public class CrewUI {

    /**
     * Sprite batch
     */
    private SpriteBatch batch;

    /** Orthographic camera */
    private OrthographicCamera camera;

    /** image of the crew member for the side bar **/
    private Texture crewImage;

    /**
     * the crew texture that is used to display the crew member in the ship
     */
    private Texture crewInShip;

    /**
     * texture for the status of the crew member
     */
    private List<Texture> crewStatus;

    /**
     * button to click to send the crew to another room. After the button is clicked,
     * the player needs to click on a room in their own ship
     */
    private CrewDismissButton crewButton;

    /**
     * the crew member
     */
    private Crew crew;

    /**
     * the crew member was moved to a new room
     *
     * @param room the new room
     */
    public void crewMoved(Room room) {

    }

    /**
     * the crew member died
     */
    public void crewDied() {

    }

    /**
     * animation showing the crew member is currently repairing something
     */
    public void crewRepairAnimation() {

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

    /**
     * dispose of the crew UI
     */
    public void disposeCrewUI() {

    }

    /**
     * the crew member was hit and the status needs to be updated
     */
    public void statusUpdate() {

    }

    /**
     * constructor
     * @param main the main class
     * @param crew the crew member
     */
    public CrewUI(Main main, Crew crew) {

    }
}
