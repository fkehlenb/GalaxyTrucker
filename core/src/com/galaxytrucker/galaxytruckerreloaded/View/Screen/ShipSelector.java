package com.galaxytrucker.galaxytruckerreloaded.View.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.galaxytrucker.galaxytruckerreloaded.Communication.ClientControllerCommunicator;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.ShipType;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons.BackButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons.CreateGameButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ShipSelectorButtons.LeftArrowButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ShipSelectorButtons.RightArrowButton;

/**
 * Ship selector screen when creating new game
 */
public class ShipSelector extends MenuScreen {

    /**
     * Username input text field
     */
    private TextField username;

    /**
     * the difficulty that was chosen
     */
    private int difficulty;

    /**
     * the glyph layout for easy centering of text
     */
    private GlyphLayout glyph = new GlyphLayout();

    /**
     * the glyph layout for easy centering of text
     */
    private GlyphLayout glyph2 = new GlyphLayout();

    /**
     * the font to draw text with
     */
    private BitmapFont font;

    /**
     * the ship that was selected
     */
    private ShipType ship;

    private Image shipImage;

    private float bildScale;

    /** Constructor
     * @param main - main class */
    public ShipSelector(Main main, int difficulty){
        super(main);
        this.difficulty = difficulty;

        bildScale = (float)Main.WIDTH/1920;

        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        username = new TextField("", skin);
        username.setSize(Main.WIDTH/7.74f, Main.HEIGHT/21.6f);
        username.setPosition(Main.WIDTH/2f - username.getWidth()/2, Main.HEIGHT/8f);

        font = main.getFont72();
        glyph.setText(font, "Please enter your username");
        glyph2.setText(font, "Please select your ship");

        CreateGameButton createGameButton = new CreateGameButton(7*Main.WIDTH/8f -Main.WIDTH/7.74f/2, Main.HEIGHT/8f, Main.WIDTH/7.74f, Main.HEIGHT/21.6f, this);
        BackButton backButton = new BackButton(Main.WIDTH/8f -Main.WIDTH/7.74f/2, Main.HEIGHT/8f,  Main.WIDTH/7.74f, Main.HEIGHT/21.6f, this);
        LeftArrowButton leftArrowButton = new LeftArrowButton(Main.WIDTH/4f -Main.HEIGHT/18f/2 , Main.HEIGHT/2f-Main.HEIGHT/21.6f/2+Main.HEIGHT/10.8f, Main.HEIGHT/18f, Main.HEIGHT/21.6f, this);
        RightArrowButton rightArrowButton = new RightArrowButton(3*Main.WIDTH/4f -Main.HEIGHT/18f/2 , Main.HEIGHT/2f-Main.HEIGHT/21.6f/2+Main.HEIGHT/10.8f, Main.HEIGHT/18f, Main.HEIGHT/21.6f, this);


        ship = ShipType.DEFAULT;
        shipImage = new Image(new Texture("ship/" + ship.toString().toLowerCase() + "base.png"));
        shipImage.setScale(bildScale);
        shipImage.setPosition(Main.WIDTH/2f - (shipImage.getWidth()*bildScale)/2, Main.HEIGHT/2f - (shipImage.getHeight()*bildScale)/2+Main.HEIGHT/10.8f);

        stage.addActor(shipImage);
        stage.addActor(createGameButton);
        stage.addActor(username);
        stage.addActor(backButton);
        stage.addActor(leftArrowButton);
        stage.addActor(rightArrowButton);


        Gdx.input.setInputProcessor(stage);
    }

    /**
     * update image
     */
    private void prepareUI() {
        shipImage.remove();

        shipImage = new Image(new Texture("ship/" + ship.toString().toLowerCase() + "base.png"));
        shipImage.setPosition(Main.WIDTH/2f - (shipImage.getWidth()*bildScale)/2, Main.HEIGHT/2f - (shipImage.getHeight()*bildScale)/2+Main.HEIGHT/10.8f);

        stage.addActor(shipImage);
    }

    /**
     * go back to last screen
     */
    @Override
    public void goBack() {
        main.setScreen(new ChooseDifficultyScreen(main));
        dispose();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        main.batch.begin();
        font.draw(main.batch, glyph, Main.WIDTH/2f - glyph.width/2, Main.HEIGHT/8f + Main.HEIGHT/16.6154f);
        font.draw(main.batch, glyph2, Main.WIDTH/2f - glyph2.width/2, Main.HEIGHT/2f + Main.HEIGHT/2.7f);
        main.batch.end();
        stage.draw();
    }

    /**
     * start the game
     */
    public void startGame() {
        if(!main.isMultiplayer()) {
            main.startServer("localhost",5050);
            main.startClient("localhost",5050);
            boolean success = ClientControllerCommunicator.getInstance(main.getClient()).login(username.getText(), ship, difficulty);
            if(success) {
                main.setScreen(new GamePlay(main));
            }
        }
        else {
            main.setScreen(new SelectLobbyScreen(main, ship, difficulty, username.getText()));
        }
        dispose();
    }

    /**
     * shows the next ship in shipselector
     */
    public void nextShip() {
        ship = ship.next();
        prepareUI();
    }

    /**
     * shows the prev ship in shipselector
     */
    public void prevShip() {
        ship = ship.previous();
        prepareUI();
    }
}
