package com.galaxytrucker.galaxytruckerreloaded.View.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons.CreateGameButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons.ShipSelectButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons.ShipSelectorBackButton;

import java.util.List;

/**
 * Ship selector screen when creating new game
 */
public class ShipSelector implements Screen {

    /**
     * the main class extending game
     */
    private Main main;

    /**
     * the background texture
     */
    private Texture background;

    /**
     * the stage for the buttons
     */
    private Stage stage;

    /**
     * the textures of the ships to choose from
     */
    private List<Texture> ships;

    /**
     * the buttons for the ships
     */
    private List<ShipSelectButton> shipButtons;

    /**
     * button to create game
     */
    private CreateGameButton createGameButton;

    /**
     * the viewport
     */
    private Viewport viewport;

    /**
     * Username input text field
     */
    private TextField username;

    private boolean singleplayer;

    private int difficulty;

    /**
     * the glyph layout for easy centering of text
     */
    private GlyphLayout glyph = new GlyphLayout();

    /**
     * the font to draw text with
     */
    private BitmapFont font;

    private Ship ship;

    private ShipSelectorBackButton backButton;


    /** Constructor
     * @param main - main class */
    public ShipSelector(Main main, boolean singleplayer, int difficulty){
        this.main = main;
        this.singleplayer = singleplayer;
        this.difficulty = difficulty;

        background = new Texture("1080p.png");

        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        username = new TextField("", skin);
        username.setSize(248, 50);
        username.setPosition(main.WIDTH/2 - username.getWidth()/2, main.HEIGHT/2 - 100);

        viewport = new FitViewport(main.WIDTH, main.HEIGHT);
        stage = new Stage(viewport);

        //font generator to get bitmapfont from .ttf file
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.local("fonts/JustinFont11Bold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
        //setting parameters of font
        params.borderWidth = 1;
        params.borderColor = Color.BLACK;
        params.characters = FreeTypeFontGenerator.DEFAULT_CHARS;
        params.magFilter = Texture.TextureFilter.Nearest;
        params.minFilter = Texture.TextureFilter.Nearest;
        params.genMipMaps = true;
        params.size = 15;

        font = generator.generateFont(params);
        glyph.setText(font, "Please enter your username");

        createGameButton = new CreateGameButton(main.WIDTH/2 - 256, main.HEIGHT/2 - 240, 512, 48, this);
        backButton = new ShipSelectorBackButton(0, 90, 512, 48, this);

        stage.addActor(createGameButton);
        stage.addActor(username);
        stage.addActor(backButton);

        //get ships from server, for each one texture and one button

        Gdx.input.setInputProcessor(stage);
    }

    /**
     * go back to last screen
     */
    public void goBack() {
        main.setScreen(new ChooseDifficultyScreen(main, singleplayer));
        dispose();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        main.batch.begin();
        main.batch.draw(background, 0, 0, main.WIDTH, main.HEIGHT);
        font.draw(main.batch, glyph, main.WIDTH/2 - glyph.width/2, main.HEIGHT/2 -30);
        main.batch.end();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        background.dispose();
        stage.dispose();
    }

    /**
     * the ship is selected
     * @param ship the index of the ship in the list of possible ships
     */
    public void setShip(Ship ship) {
        this.ship = ship;
        //TODO set difficulty
    }

    /**
     * start the game
     */
    public void startGame() {
        if(singleplayer) {
            main.setScreen(new GamePlay(main)); //TODO call controller to start game
        }
        else {
            main.setScreen(new CreateOrJoinServer(main, ship));
        }
        dispose();
    }
}
