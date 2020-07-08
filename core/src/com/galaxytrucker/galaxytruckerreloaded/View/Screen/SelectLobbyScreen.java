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
import com.badlogic.gdx.utils.Select;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.ShipType;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons.SelectLobbyBackButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons.SelectLobbyButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.LobbyScreenHost;

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
     * constructor
     * @param main main class extending game
     */
    public SelectLobbyScreen(Main main, ShipType ship) {
        this.main = main;
        this.ship = ship;

        viewport = new FitViewport(main.WIDTH, main.HEIGHT);
        stage = new Stage(viewport);

        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        lobby = new TextField("", skin);
        lobby.setPosition(main.WIDTH/2 - lobby.getWidth()/2, main.HEIGHT/2 - lobby.getHeight()/2 + 50);

        backButton = new SelectLobbyBackButton(main.WIDTH/2 - 256, main.HEIGHT/2 - 100, 512, 48, this);
        selectLobbyButton = new SelectLobbyButton(main.WIDTH/2 - 256, main.HEIGHT/2 - 50, 512, 48, this);

        stage.addActor(lobby);
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
        params.size = 40;

        font = generator.generateFont(params);
        glyph.setText(font, "Please enter a server port");

        background = new Texture("1080p.png");

        Gdx.input.setInputProcessor(stage);
    }

    /**
     * join a lobby by taking the text from the text field
     */
    public void joinLobby() {
        //controller call
        main.setScreen(new LobbyScreenNormal(main, ship, false));
        dispose();
    }

    public void goBack() {
        main.setScreen(new CreateOrJoinServer(main, ship));
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
        font.draw(main.batch, glyph, main.WIDTH/2 - glyph.width/2, main.HEIGHT/2 + 100);
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
    }
}
