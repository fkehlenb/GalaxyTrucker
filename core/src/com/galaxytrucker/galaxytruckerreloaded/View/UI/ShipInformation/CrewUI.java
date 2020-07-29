package com.galaxytrucker.galaxytruckerreloaded.View.UI.ShipInformation;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.ShipType;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.CrewSelectButton;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Ship.AbstractShip;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Ship.ShipView;
import lombok.Getter;

/**
 * shows the crew on board
 */
public class CrewUI extends EnemyCrewUI{

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
     * the x position of the picture of the crew member in the upper left corner
     */
    private float x;

    /**
     * the y position of the picture of the crew member in the upper left corner
     */
    @Getter
    private float y;

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
     * @param main main class extending game
     * @param crew crew to be displayed
     * @param stage stage for buttons
     * @param shipView shipview this crew ui belongs to
     * @param x x position for ui on the left
     * @param y y position for ui on the left
     * @param font font for name text
     * @param rX x position of room
     * @param rY y position of room
     * @param type type of ship this crew member belongs to
     */
    public CrewUI(Main main, Crew crew, Stage stage, AbstractShip shipView, float x, float y, BitmapFont font, float rX, float rY, ShipType type) {
        super(main, crew, shipView, rX, rY, type);
        this.x = x;
        this.y = y;
        this.font = font;

        glyph.setText(font, crew.getName());

        crewButton = new CrewSelectButton(crewImage, new Texture("crew/"+type.toString().toLowerCase()+"on.png"), x, y, 50, 50, this);
        stage.addActor(crewButton);

        crewStatus = new Texture("gameuis/energybar.png");

        box = new Texture("crew/health_box.png");

        background = new Texture("crew/background.png");

        currentTexture = 10;
    }

    /**
     * the crew was chosen to be moved
     * called by button crewSelect
     * now waiting for the user to choose a room on the ship
     */
    public void crewMoving() {
        ((ShipView) shipView).crewMoving(crew);
    }

    /**
     * the crew member was moved to a new room
     * called by shipview
     *
     * @param roomX the x position of the lower left corner the crew member is in
     * @param roomY the y position of the lower left corner the crew member is in
     */
    @Override
    public void crewMoved(float roomX, float roomY) {
        super.crewMoved(roomX, roomY);
        undoCrewButton();
    }

    /**
     * change texture of crew button
     */
    public void undoCrewButton() {
        crewButton.moved();
    }

    /**
     * dispose of the crew UI
     */
    @Override
    public void disposeEnemyCrewUI() {
        super.disposeEnemyCrewUI();
        box.dispose();
        crewStatus.dispose();
        crewButton.remove();
        background.dispose();
    }

    /**
     * render
     * no stage stuff
     */
    @Override
    public void render() {
        super.render();
        main.batch.begin();
        main.batch.draw(background, x, y, 200, 55);
        font.draw(main.batch, glyph, x + 55, y + 40);
        main.batch.draw(box, x + 35, y +5, 140, 20);
        float ex=x+55;
        for(int i=0;i<=currentTexture;i++) {
             main.batch.draw(crewStatus, ex, y+13, 8.75f, 5);
            ex+=8.75;
        }
        main.batch.end();
    }

    /**
     * the crew member is updated
     * @param crew the new crew member
     */
    @Override
    public void update(Crew crew, float roomX, float roomY) {
        super.update(crew, roomX, roomY);
        statusUpdate(crew.getHealth());
    }

    /**
     * update the position of the ui stuff on the most left side
     * box, button, everything
     * @param x the new x
     * @param y the new y
     */
    public void updateLeftPosition(float x, float y) {
        this.x = x;
        this.y = y;
        crewButton.setPosition(x, y);
    }

    /**
     * the crew member was hit and the status needs to be updated
     * called by controller
     * @param status the new status
     */
    private void statusUpdate(int status) {
        int percent = status/crew.getMaxhealth();
        currentTexture = percent * 10;
    }
}
