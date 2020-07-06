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
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons.DifficultyBackButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons.DifficultyButton;

public class ChooseDifficultyScreen implements Screen {

    private Main main;

    private Stage stage;

    private Viewport viewport;

    private DifficultyButton easy;

    private DifficultyButton middle;

    private DifficultyButton hard;

    private DifficultyBackButton back;

    private Texture background;


    private BitmapFont font;

    private GlyphLayout glyph = new GlyphLayout();

    public  ChooseDifficultyScreen(Main main) {
        this.main = main;

        background = new Texture("1080p.png");

        viewport = new FitViewport(main.WIDTH, main.HEIGHT);
        stage = new Stage(viewport);

        easy = new DifficultyButton(new Texture("shipselector/button_easy.png"), main.WIDTH/2 - 256, main.HEIGHT/2 - 24, 512, 48, this, 0);
        middle = new DifficultyButton(new Texture("shipselector/button_normal.png"), main.WIDTH/2 - 256, main.HEIGHT/2 - 24 - 96, 512, 48, this, 1);
        hard = new DifficultyButton(new Texture("shipselector/button_hard.png"), main.WIDTH/2 - 256, main.HEIGHT/2 - 24 - 96*2, 512, 48, this, 2);

        back = new DifficultyBackButton(main.WIDTH/2 - 256, main.HEIGHT/2 - 24 - 96*3, 512, 48, this, main);

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
        glyph.setText(font, "Choose A Difficulty");

        stage.addActor(easy);
        stage.addActor(middle);
        stage.addActor(hard);
        stage.addActor(back);

        Gdx.input.setInputProcessor(stage);
    }

    public void setDifficulty(int difficulty) {
        //call controller
        main.setScreen(new ShipSelector(main));
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
