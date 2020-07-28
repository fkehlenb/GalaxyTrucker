package com.galaxytrucker.galaxytruckerreloaded.View.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.ShipType;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons.BackButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons.SelectLobbyButton;

/**
 * the screen to enter a server port on to continue an existing game
 */
public class SelectLobbyScreen extends MenuScreen {

    /**
     * the textfield for user input
     */
    private TextField lobby;

    /**
     * the textfield for entering port number
     */
    private TextField port;

    /**
     * the font to draw text with
     */
    private BitmapFont font;

    /**
     * the glyph layout for easy centering of text
     */
    private GlyphLayout glyph = new GlyphLayout();

    /**
     * the type of ship that was selected
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

    /**
     * constructor
     * @param main main class extending game
     */
    public SelectLobbyScreen(Main main, ShipType ship, int diff, String username) {
        super(main);
        this.ship = ship;
        this.difficulty = diff;
        this.username = username;

        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        lobby = new TextField("", skin);
        lobby.setSize(Main.WIDTH/7.74f, Main.HEIGHT/21.6f);
        lobby.setPosition(Main.WIDTH/2f - lobby.getWidth()/2, Main.HEIGHT/2f + Main.HEIGHT/21.6f/2 -Main.HEIGHT/21.6f*0);

        port = new TextField("", skin);
        port.setSize(Main.WIDTH/7.74f, Main.HEIGHT/21.6f);
        port.setPosition(Main.WIDTH/2f - port.getWidth()/2, Main.HEIGHT/2f + Main.HEIGHT/21.6f/2 - Main.HEIGHT/21.6f*1);

        BackButton backButton = new BackButton(Main.WIDTH/2f - Main.WIDTH/7.74f/2, Main.HEIGHT/2f + Main.HEIGHT/21.6f/2 -Main.HEIGHT/21.6f*3, Main.WIDTH/7.74f, Main.HEIGHT/21.6f, this);
        SelectLobbyButton selectLobbyButton = new SelectLobbyButton(Main.WIDTH/2f - Main.WIDTH/7.74f/2, Main.HEIGHT/2f + Main.HEIGHT/21.6f/2 -Main.HEIGHT/21.6f*2, Main.WIDTH/7.74f, Main.HEIGHT/21.6f, this);

        stage.addActor(lobby);
        stage.addActor(port);
        stage.addActor(backButton);
        stage.addActor(selectLobbyButton);

        font = main.getFont48();
        glyph.setText(font, "Please enter a server address and port");

        Gdx.input.setInputProcessor(stage);
    }

    /**
     * join a lobby by taking the text from the text field
     */
    public void joinLobby() {
        main.setScreen(new CreateOrJoinServer(main, ship, difficulty, username, port.getText(), lobby.getText()));
        dispose();
    }

    /**
     * return to previous screen
     */
    @Override
    public void goBack() {
        main.setScreen(new ShipSelector(main, difficulty));
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
