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
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Ship;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.ShipType;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons.CreateOrJoinBackButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons.JoinServerButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons.StartServerButton;

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

    private StartServerButton start;

    private JoinServerButton join;

    private CreateOrJoinBackButton back;

    private ShipType ship;

    /**
     * the constructor
     * @param main the class extending game
     */
    public CreateOrJoinServer(Main main, ShipType ship) {
        this.main = main;
        this.ship = ship;

        background = new Texture("1080p.png");

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
        glyph.setText(font, "Do you want to start the server or join one?");

        viewport = new FitViewport(main.WIDTH, main.HEIGHT);
        stage = new Stage(viewport);

        join = new JoinServerButton(main.WIDTH/2 - 256, main.HEIGHT/2 - 24, 512, 48, this);
        start = new StartServerButton(main.WIDTH/2 - 256, main.HEIGHT/2 + 70, 512, 48, this);
        back = new CreateOrJoinBackButton(main.WIDTH/2 - 256, main.HEIGHT/2 - 70, 512, 48, this);

        stage.addActor(join);
        stage.addActor(start);
        stage.addActor(back);

        Gdx.input.setInputProcessor(stage);
    }

    /**
     * join a server
     */
    public void joinServer() {
        main.setScreen(new SelectLobbyScreen(main, ship));
        dispose();
    }

    /**
     * start a game on a new server
     */
    public void startServer() {
        main.setScreen(new LobbyScreenHost(main, ship, false));
        dispose();
    }

    /**
     * go back to last screen
     */
    public void goBack() {
        main.setScreen(new ShipSelector(main, false, 0)); //TODO set difficulty once that is a ship attribute
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
        font.draw(main.batch, glyph, main.WIDTH/2 - glyph.width/2, main.HEIGHT - 400);
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
    }
}
