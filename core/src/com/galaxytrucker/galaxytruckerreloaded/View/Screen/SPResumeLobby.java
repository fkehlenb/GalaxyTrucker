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
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.ShipType;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons.*;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Options.*;

/**
 * the single player lobby for resuming a game
 */
public class SPResumeLobby implements Screen {

    /**
     * the background texture
     */
    private Texture background;

    /**
     * the main class extending game
     */
    private Main main;

    /**
     * the stage for the buttons
     */
    private Stage stage;

    /**
     * the viewport for the stage
     */
    private Viewport viewport;

    /**
     * Button which starts the game.
     */
    private SPResumeStartGame spResumeStartGame;

    /**
     * the glyph layout for easy centering of text
     */
    private GlyphLayout glyph = new GlyphLayout();

    /**
     * the glyph layout for easy centering of text
     */
    private GlyphLayout glyph2 = new GlyphLayout();

    /**
     * the glyph layout for easy centering of text
     */
    private GlyphLayout glyph3 = new GlyphLayout();

    /**
     * the font to draw text with
     */
    private BitmapFont font;


    //Müssen im Konstruktor durch echte, im Savegame gespeicherte Parameter ersetzt werden.
    /**
     * the name of the ship
     */
    private String shipName = "TestShip1";

    /**
     * the difficulty chosen
     */
    private String difficultyName = "TestDiff";

    /**
     * the seed of the map
     */
    private String seedName = "TestSeed";

    private SPResumeLobbyBackButton spResumeLobbyBackButton;

    /** Constructor  */
    public SPResumeLobby(Main main){
        this.main = main;
        background = new Texture("1080p.png");

        spResumeStartGame = new SPResumeStartGame(7*main.WIDTH/8 - Main.WIDTH/15.484f, main.HEIGHT/8 , Main.WIDTH/7.742f, Main.HEIGHT/21.6f, this);
        spResumeLobbyBackButton = new SPResumeLobbyBackButton(main.WIDTH/8 - Main.WIDTH/15.484f, main.HEIGHT/8, Main.WIDTH/7.742f, Main.HEIGHT/21.6f,this);

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
        glyph.setText(font, "Your Ship: " + main.getClient().getMyShip().getShipType());
        glyph2.setText(font, "Your Difficulty: " + main.getClient().getOverworld().getDifficulty());
        glyph3.setText(font, "Your Seed: " + main.getClient().getOverworld().getSeed());

        viewport = new FitViewport(main.WIDTH, main.HEIGHT);
        stage = new Stage(viewport);
        stage.addActor(spResumeStartGame);
        stage.addActor(spResumeLobbyBackButton);

        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Called when this screen becomes the current screen for a game
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
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        main.batch.begin();
        main.batch.draw(background, 0, 0, main.WIDTH, main.HEIGHT);
        font.draw(main.batch, glyph, main.WIDTH/4  - glyph.width/2, main.HEIGHT/2 + Main.HEIGHT/77.143f);
        font.draw(main.batch, glyph2, 3*main.WIDTH/4  - glyph.width/2, main.HEIGHT/2 + Main.HEIGHT/77.143f);
        font.draw(main.batch, glyph3, 2*main.WIDTH/4  - glyph.width/2, main.HEIGHT/2 + Main.HEIGHT/77.143f);
        main.batch.end();
        stage.draw();
    }

    /**
     * @param width the new width
     * @param height the new height
     */
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    /**
     * pauses
     */
    @Override
    public void pause() {

    }

    /**
     * resumes
     */
    @Override
    public void resume() {

    }

    /**
     * Called when this screen is no longer the current screen for a game
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

    /**
     * Called when the game starts.
     */
    public void startGame() {
        main.setScreen(new GamePlay(main));
        dispose();
    }

    /**
     * go back to previous screen
     */
    public void goBack() {
        main.setScreen(new LoginScreen(main, true));
        dispose();
    }

}
