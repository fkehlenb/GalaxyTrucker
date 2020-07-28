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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.galaxytrucker.galaxytruckerreloaded.Communication.Client;
import com.galaxytrucker.galaxytruckerreloaded.Communication.ClientControllerCommunicator;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.ShipType;
import com.galaxytrucker.galaxytruckerreloaded.Server.Server;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons.CreateOrJoinBackButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons.JoinServerButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons.StartServerButton;

/**
 * the screen on which the player choses to be the one
 * creating or joining a server for multiplayer
 */
public class CreateOrJoinServer implements Screen {

    /**
     * the main class extending game
     */
    private Main main;

    /**
     * the stage for buttons
     */
    private Stage stage;

    /**
     * the viewport
     */
    private Viewport viewport;

    /**
     * the background texture
     */
    private Texture background;

    /**
     * the font
     */
    private BitmapFont font;

    /**
     * the glyphLayout for centering fonts
     */
    private GlyphLayout glyph = new GlyphLayout();

    /**
     * the type of ship selected
     */
    private ShipType ship;

    /**
     * the difficulty the player chose
     */
    private int difficulty;

    /**
     * the username the player entered
     */
    private String username;


    private String port, address;

    /**
     * the constructor
     * @param main the class extending game
     */
    public CreateOrJoinServer(Main main, ShipType ship, int diff, String username, String port, String address) {
        this.main = main;
        this.ship = ship;
        this.difficulty = diff;
        this.username = username;
        this.port = port;
        this.address = address;

        background = new Texture("1080p.png");

        font = main.getFont48();
        glyph.setText(font, "Do you want to start the server or join one?");

        viewport = new FitViewport(Main.WIDTH, Main.HEIGHT);
        stage = new Stage(viewport);

        JoinServerButton join = new JoinServerButton(Main.WIDTH/2f - Main.WIDTH/7.74f/2, Main.HEIGHT/2f + Main.HEIGHT/21.6f/2 -Main.HEIGHT/21.6f*0, Main.WIDTH/7.74f, Main.HEIGHT/21.6f, this);
        StartServerButton start = new StartServerButton(Main.WIDTH/2f - Main.WIDTH/7.74f/2, Main.HEIGHT/2f + Main.HEIGHT/21.6f/2 -Main.HEIGHT/21.6f*1, Main.WIDTH/7.74f, Main.HEIGHT/21.6f, this);
        CreateOrJoinBackButton back = new CreateOrJoinBackButton(Main.WIDTH/2f - Main.WIDTH/7.74f/2, Main.HEIGHT/2f + Main.HEIGHT/21.6f/2 -Main.HEIGHT/21.6f*2, Main.WIDTH/7.74f, Main.HEIGHT/21.6f, this);

        stage.addActor(join);
        stage.addActor(start);
        stage.addActor(back);

        Gdx.input.setInputProcessor(stage);
    }

    /**
     * join a server
     */
    public void joinServer() {
        main.startClient(address, Integer.parseInt(port));
        boolean success = ClientControllerCommunicator.getInstance(main.getClient()).login(username, ship, difficulty);
        if(success) {
            main.setScreen(new GamePlay(main));
        }
    }

    /**
     * start a game on a new server
     */
    public void startServer() {
        main.startServer();
        main.startClient(address, Integer.parseInt(port));
        boolean success = ClientControllerCommunicator.getInstance(main.getClient()).login(username, ship, difficulty);
        if(success) {
            main.setScreen(new GamePlay(main));
        }
    }

    /**
     * go back to last screen
     */
    public void goBack() {
        main.setScreen(new SelectLobbyScreen(main, ship, difficulty, username));
        dispose();
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
        main.batch.draw(background, 0, 0, Main.WIDTH, Main.HEIGHT);
        font.draw(main.batch, glyph, Main.WIDTH/2f - glyph.width/2, Main.HEIGHT/2f + Main.HEIGHT/21.6f*2);
        main.batch.end();

        stage.draw();
    }

    /**
     * apply the changes of the window size to the viewport for correct display
     * @param width the new width
     * @param height the new height
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
     * Called when this screen is no longer the current screen for
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
