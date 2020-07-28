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

/**
 * the screen on which the difficulty for a new game is chosen
 */
public class ChooseDifficultyScreen implements Screen {

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
     * the difficulty button for easy
     */
    private DifficultyButton easy;

    /**
     * the difficulty button for medium
     */
    private DifficultyButton middle;

    /**
     * the difficulty button for hard
     */
    private DifficultyButton hard;

    /**
     * the button to get back to the last screen
     */
    private DifficultyBackButton back;

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
     * constructor
     * @param main the main class extending game
     */
    public  ChooseDifficultyScreen(Main main) {
        this.main = main;

        background = new Texture("1080p.png");

        viewport = new FitViewport(main.WIDTH, main.HEIGHT);
        stage = new Stage(viewport);

        easy = new DifficultyButton(new Texture("buttons/easy_button.png"), main.WIDTH/2 - main.WIDTH/7.74f/2, main.HEIGHT/2 + main.HEIGHT/21.6f/2 -main.HEIGHT/21.6f*0, main.WIDTH/7.74f, main.HEIGHT/21.6f, this, 1);
        middle = new DifficultyButton(new Texture("buttons/medium_button.png"), main.WIDTH/2 - main.WIDTH/7.74f/2, main.HEIGHT/2 + main.HEIGHT/21.6f/2 -main.HEIGHT/21.6f*1, main.WIDTH/7.74f, main.HEIGHT/21.6f, this, 2);
        hard = new DifficultyButton(new Texture("buttons/hard_button.png"), main.WIDTH/2 - main.WIDTH/7.74f/2, main.HEIGHT/2 + main.HEIGHT/21.6f/2 -main.HEIGHT/21.6f*2, main.WIDTH/7.74f, main.HEIGHT/21.6f, this, 3);

        back = new DifficultyBackButton(main.WIDTH/2 - main.WIDTH/7.74f/2, main.HEIGHT/2 + main.HEIGHT/21.6f/2 -main.HEIGHT/21.6f*3, main.WIDTH/7.74f, main.HEIGHT/21.6f, this);

        font = main.getFont48();
        glyph.setText(font, "Choose A Difficulty");

        stage.addActor(easy);
        stage.addActor(middle);
        stage.addActor(hard);
        stage.addActor(back);

        Gdx.input.setInputProcessor(stage);
    }

    /**
     * set the difficulty and load new screen
     * @param difficulty
     */
    public void setDifficulty(int difficulty) {
        if(!main.isMultiplayer()) {
            main.setScreen(new ShipSelector(main, difficulty));
        }
        else {
            main.setScreen(new ShipSelector(main, difficulty));
        }
        dispose();
    }

    /**
     * return to last screen
     */
    public void goBack() {
        main.setScreen(new SPNewOrResume(main));
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
