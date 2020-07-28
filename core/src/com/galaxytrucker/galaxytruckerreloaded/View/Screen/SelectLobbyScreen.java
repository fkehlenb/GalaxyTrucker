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
import com.galaxytrucker.galaxytruckerreloaded.Communication.ClientControllerCommunicator;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.ShipType;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons.SelectLobbyBackButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons.SelectLobbyButton;

/**
 * the screen to enter a server port on to continue an existing game
 */
public class SelectLobbyScreen implements Screen {

    /**
     * the main class extending game
     */
    private Main main;

    /**
     * the viewport
     */
    private Viewport viewport;

    /**
     * the stage for the buttons
     */
    private Stage stage;

    /**
     * the background
     */
    private Texture background;

    /**
     * the textfield for user input
     */
    private TextField lobby;

    /**
     * the textfield for entering port number
     */
    private TextField port;

    /**
     * the button to return back to the last screen
     */
    private SelectLobbyBackButton backButton;

    /**
     * the button with which to attempt to connect to the server
     */
    private SelectLobbyButton selectLobbyButton;

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
        this.main = main;
        this.ship = ship;
        this.difficulty = diff;
        this.username = username;

        viewport = new FitViewport(Main.WIDTH, Main.HEIGHT);
        stage = new Stage(viewport);

        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        lobby = new TextField("", skin);
        lobby.setSize(Main.WIDTH/7.74f, Main.HEIGHT/21.6f);
        lobby.setPosition(Main.WIDTH/2f - lobby.getWidth()/2, Main.HEIGHT/2f + Main.HEIGHT/21.6f/2 -Main.HEIGHT/21.6f*0);

        port = new TextField("", skin);
        port.setSize(Main.WIDTH/7.74f, Main.HEIGHT/21.6f);
        port.setPosition(Main.WIDTH/2f - port.getWidth()/2, Main.HEIGHT/2f + Main.HEIGHT/21.6f/2 - Main.HEIGHT/21.6f*1);

        backButton = new SelectLobbyBackButton(Main.WIDTH/2f - Main.WIDTH/7.74f/2, Main.HEIGHT/2f + Main.HEIGHT/21.6f/2 -Main.HEIGHT/21.6f*3, Main.WIDTH/7.74f, Main.HEIGHT/21.6f, this);
        selectLobbyButton = new SelectLobbyButton(Main.WIDTH/2f - Main.WIDTH/7.74f/2, Main.HEIGHT/2f + Main.HEIGHT/21.6f/2 -Main.HEIGHT/21.6f*2, Main.WIDTH/7.74f, Main.HEIGHT/21.6f, this);

        stage.addActor(lobby);
        stage.addActor(port);
        stage.addActor(backButton);
        stage.addActor(selectLobbyButton);

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
        params.size = Main.HEIGHT/48;

        font = generator.generateFont(params);
        glyph.setText(font, "Please enter a server address and port");

        background = new Texture("1080p.png");

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
    public void goBack() {
        main.setScreen(new ShipSelector(main, false, difficulty));
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
        main.batch.draw(background, 0, 0, main.WIDTH, main.HEIGHT);
        font.draw(main.batch, glyph, main.WIDTH/2 - glyph.width/2, main.HEIGHT/2 + main.HEIGHT/21.6f*2);
        main.batch.end();

        stage.draw();
    }

    /**
     * apply the resizing by the user to the viewport
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
