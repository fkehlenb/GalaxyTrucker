package com.galaxytrucker.galaxytruckerreloaded.View.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.galaxytrucker.galaxytruckerreloaded.Communication.ClientControllerCommunicator;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.ShipType;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons.BackButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons.JoinServerButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons.StartServerButton;

/**
 * the screen on which the player choses to be the one
 * creating or joining a server for multiplayer
 */
public class CreateOrJoinServer extends MenuScreen {

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
        super(main);
        this.ship = ship;
        this.difficulty = diff;
        this.username = username;
        this.port = port;
        this.address = address;

        font = main.getFont48();
        glyph.setText(font, "Do you want to start the server or join one?");

        JoinServerButton join = new JoinServerButton(Main.WIDTH/2f - Main.WIDTH/7.74f/2, Main.HEIGHT/2f + Main.HEIGHT/21.6f/2 -Main.HEIGHT/21.6f*0, Main.WIDTH/7.74f, Main.HEIGHT/21.6f, this);
        StartServerButton start = new StartServerButton(Main.WIDTH/2f - Main.WIDTH/7.74f/2, Main.HEIGHT/2f + Main.HEIGHT/21.6f/2 -Main.HEIGHT/21.6f*1, Main.WIDTH/7.74f, Main.HEIGHT/21.6f, this);
        BackButton back = new BackButton(Main.WIDTH/2f - Main.WIDTH/7.74f/2, Main.HEIGHT/2f + Main.HEIGHT/21.6f/2 -Main.HEIGHT/21.6f*2, Main.WIDTH/7.74f, Main.HEIGHT/21.6f, this);

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
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        super.render(delta);

        main.batch.begin();
        font.draw(main.batch, glyph, Main.WIDTH/2f - glyph.width/2, Main.HEIGHT/2f + Main.HEIGHT/21.6f*2);
        main.batch.end();

        stage.draw();
    }
}
