package com.galaxytrucker.galaxytruckerreloaded.View.Screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.ShipType;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons.LobbyScreenHostBackButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons.StartButton;

import java.util.ArrayList;
import java.util.List;

/**
 * the screen for the multiplayer lobby from the view of the host
 */
public class LobbyScreenHost implements Screen {

    /**
     * the main class extending game
     */
    private Main main;

    /**
     * the stage for the buttons
     */
    private Stage stage;

    /**
     * the viewport
     */
    private Viewport viewport;

    /**
     * the texture for the background
     */
    private Texture background;

    /**
     * the button to return to the last screen
     */
    private LobbyScreenHostBackButton backButton;

    /**
     * the button to start the game
     */
    private StartButton startButton;

    /**
     * font for header
     */
    private BitmapFont font;

    /**
     * font for text
     */
    private BitmapFont textFont;

    /**
     * the list of the names of the users already in the lobby
     */
    private List<String> users;

    /**
     * the type of ship the player has selected
     */
    private ShipType ship;

    /**
     * whether this comes from resuming a game or starting a new one (relevant for back)
     */
    private boolean resume;

    /**
     * the difficulty the player chose
     */
    private int difficulty;

    /**
     * the username the player entered
     */
    private String username;

    /**
     * the constructor
     * @param main main class extending game
     * @param ship the ship type the player chose
     * @param resume whether or not the players are resuming an old game
     *                 used for going back to the correct screen
     */
    public LobbyScreenHost(Main main, ShipType ship, boolean resume, int diff, String username) {
        this.main = main;
        this.ship = ship;
        this.resume = resume;
        this.difficulty = diff;
        this.username = username;

        background = new Texture("1080p.png");

        users = new ArrayList<>();
        users.add("test");

        //font generator to get bitmapfont from .ttf file
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.local("fonts/JustinFont11Bold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
        //setting parameters of font
        params.borderWidth = 1;
        params.borderColor = Color.GOLD;
        params.characters = FreeTypeFontGenerator.DEFAULT_CHARS;
        params.magFilter = Texture.TextureFilter.Nearest;
        params.minFilter = Texture.TextureFilter.Nearest;
        params.genMipMaps = true;
        params.size = 40;

        font = generator.generateFont(params);

        params.size = 20;
        params.borderColor = Color.BLACK;

        textFont = generator.generateFont(params);

        viewport = new FitViewport(main.WIDTH, main.HEIGHT);
        stage = new Stage(viewport);

        backButton = new LobbyScreenHostBackButton(0, 90, 512, 48, this);
        startButton = new StartButton(main.WIDTH - 400, 90, 512, 48, this);

        stage.addActor(backButton);
        stage.addActor(startButton);

        Gdx.input.setInputProcessor(stage);

    }

    /**
     * go back to the last screen
     */
    public void goBack() {
        if(resume) {
            main.setScreen(new LoginScreen(main, false));
        }
        else {
            main.setScreen(new CreateOrJoinServer(main, ship, difficulty, username));
        }
        dispose();
    }

    /**
     * update the users that are logged into this lobby
     * @param users the new list
     */
    public void updateUsers(List<String> users) {
        this.users = users;
    }

    /**
     * continue the game with the players that are in the lobby
     */
    public void resumeGame() {
        main.setScreen(new GamePlay(main)); //TODO irgendwas an server kommunizieren?
    }

    /**
     * Called when this screen becomes the current screen for a
     */
    @Override
    public void show() {

    }

    /**
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        main.batch.begin();
        main.batch.draw(background, 0, 0, main.WIDTH, main.HEIGHT);
        float y = main.HEIGHT/2;
        for(String u : users) {
            textFont.draw(main.batch, u, main.WIDTH/2, y);
            y-=10;
        }
        font.draw(main.batch, "L O B B Y", 75, main.HEIGHT - 100);
        textFont.draw(main.batch, "Click Continue to resume your game with the players currently in the lobby, or wait for more!", 75, main.HEIGHT - 175);
        main.batch.end();

        stage.draw();
    }

    /**
     * @param width
     * @param height
     */
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    /**
     */
    @Override
    public void pause() {

    }

    /**
     */
    @Override
    public void resume() {

    }

    /**
     * Called when this screen is no longer the current screen for a
     */
    @Override
    public void hide() {

    }

    /**
     * Called when this screen should release all resources.
     */
    @Override
    public void dispose() {
        background.dispose();
        stage.dispose();
        font.dispose();
    }
}
