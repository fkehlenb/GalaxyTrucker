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
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.ShipType;
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

    /**
     * the position of the room the crew member is in
     */
    private float roomX, roomY;

    /**
     * the font used to draw the name
     */
    private BitmapFont font;

    /**
     * the glyph layout used for better positioning
     */
    private GlyphLayout glyph = new GlyphLayout();

    /**
     * constructor
     * @param main the main class
     * @param crew the crew member
     */
    public CrewUI(Main main, Crew crew, Stage stage, ShipView shipView, float x, float y, BitmapFont font, float rX, float rY, ShipType type) {
        this.main = main;
        this.crew = crew;
        this.shipView = shipView;
        this.x = x;
        this.y = y;
        this.font = font;
        this.roomX = rX;
        this.roomY = rY;

        glyph.setText(font, crew.getName());

        crewImage = new Texture("crew/"+type.toString().toLowerCase()+".png");

        crewButton = new CrewSelectButton(crewImage, x, y, 50, 50, crew.getId(), this);
        stage.addActor(crewButton);

        crewInShip = crewImage;

        crewStatus = new Texture("gameuis/energybar.png");

        box = new Texture("crew/health_box.png");

        background = new Texture("crew/background.png");

        currentTexture = 10;
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
        main.batch.draw(crewInShip, roomX, roomY, 50, 50);
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
     * called by shipview
     *
     * @param room the new room
     */
    public void crewMoved(Room room) {

    }

    /**
     * the crew was chosen to be moved
     * called by button crewSelect
     * now waiting for the user to choose a room on the ship
     */
    public void crewMoving() {
        shipView.crewMoving(crew);
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
