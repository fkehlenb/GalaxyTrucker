package com.galaxytrucker.galaxytruckerreloaded.View.UI.ShipInformation;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;

import java.util.List;

import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.CrewSelectButton;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Ship.ShipView;

/**
 * shows the crew on board
 */
public class CrewUI {

    /** image of the crew member for the side bar **/
    private Texture crewImage;

    /**
     * the crew texture that is used to display the crew member in the ship
     */
    private Texture crewInShip;

    /**
     * texture for the status of the crew member
     */
    private Texture crewStatus;

    /**
     * health box (empty)
     */
    private Texture box;

    /**
     * the current texture for status
     */
    private int currentTexture;

    /**
     * button to click to send the crew to another room. After the button is clicked,
     * the player needs to click on a room in their own ship
     */
    private CrewSelectButton crewButton;

    /**
     * the background box
     */
    private Texture background;

    /**
     * the crew member displayed with this ui
     */
    private Crew crew;

    /**
     * the main class
     */
    private Main main;

    /**
     * rooms of the ship
     */
    private List<Room> rooms;

    /**
     * the shipview of this crewui
     */
    private ShipView shipView;

    /**
     * the x position of the picture of the crew member in the upper left corner
     */
    private float x;

    /**
     * the y position of the picture of the crew member in the upper left corner
     */
    private float y;

    private BitmapFont font;

    private GlyphLayout glyph = new GlyphLayout();

    /**
     * constructor
     * @param main the main class
     * @param crew the crew member
     */
    public CrewUI(Main main, Crew crew, Stage stage, Ship ship, ShipView shipView, float x, float y, BitmapFont font) {
        this.main = main;
        this.crew = crew;
        this.shipView = shipView;
        this.x = x;
        this.y = y;
        this.font = font;

        glyph.setText(font, crew.getName());

        if(crew.getName().equals("ana")) {
            crewImage = new Texture("crew/anaerobic.png");
        }
        else if(crew.getName().equals("battle")) {
            crewImage = new Texture("crew/battle.png");
        }
        else {
            crewImage = new Texture("crew/energy.png");
        }

        crewButton = new CrewSelectButton(crewImage, x, y+15, 50, 50, crew.getId(), this);
        stage.addActor(crewButton);

        crewInShip = crewImage;

        crewStatus = new Texture("gameuis/energybar.png");

        box = new Texture("crew/health_box.png");

        background = new Texture("crew/background.png");

        currentTexture = 10;

        rooms = ship.getSystems();
    }

    /**
     * dispose of the crew UI
     */
    public void disposeCrewUI() {
        crewImage.dispose();
        crewInShip.dispose();
        box.dispose();
        crewStatus.dispose();

        crewButton.remove();
    }

    /**
     * render
     * no stage stuff
     */
    public void render() {
        main.batch.begin();
        main.batch.draw(background, x, y, 200, 55);
        font.draw(main.batch, glyph, x + 55, y + 40);
        main.batch.draw(crewInShip, 0, 0, 0, 0); //TODO whxy
        main.batch.draw(box, x + 35, y +5, 140, 20);
        float ex=x+55;
        for(int i=0;i<=currentTexture;i++) {
             main.batch.draw(crewStatus, ex, y+13, 8.75f, 5);
            ex+=8.75;
        }
        main.batch.end();
    }

    /**
     * the crew member was moved to a new room
     *
     *
     * @param room the new room
     */
    public void crewMoved(Room room) {
        shipView.crewMoved(crew, room);
    }

    /**
     * the crew was chosen to be moved
     * called by button crewdismiss
     * now waiting for the user to choose a room on the ship
     */
    public void crewMoving() {
        boolean chosen = false;
        while(!chosen) {
            if(Gdx.input.justTouched()) {
                int tx = Gdx.input.getX();
                int ty = Gdx.input.getY();
                for(Room r : rooms) {
                    //TODO
//                    if (r.getPosX() <= tx && r.getPosX() + r.getWidth() >= tx //TODO ist die berechnung richtig
//                        && r.getPosY() <= ty && r.getPosY()+r.getHeight() >= ty) { //TODO wo sind die x und y koordinaten des schiffes?
//                        chosen = true;
//                        crewMoved(r);
//                        break;
//                    }
                }
            }
        }
    }

    /**
     * the crew member died
     * called by controller
     */
    public void crewDied() {
        this.disposeCrewUI();
    }

    /**
     * animation showing the crew member is currently repairing something
     * called by controller?
     */
    public void crewRepairAnimation() {

    }

    /**
     * the crew member was hit and the status needs to be updated
     * called by controller
     * @param status the new status
     */
    public void statusUpdate(int status) {
        int percent = status/crew.getMaxhealth();
        currentTexture = percent * 10;
        //adapt currentTexture according to amount of textures we end up having
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
