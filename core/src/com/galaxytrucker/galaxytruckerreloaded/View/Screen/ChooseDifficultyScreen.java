package com.galaxytrucker.galaxytruckerreloaded.View.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons.BackButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons.DifficultyButton;

/**
 * the screen on which the difficulty for a new game is chosen
 */
public class ChooseDifficultyScreen extends MenuScreen {

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
        super(main);

        DifficultyButton easy = new DifficultyButton(new Texture("buttons/easy_button.png"), Main.WIDTH/2f - Main.WIDTH/7.74f/2, Main.HEIGHT/2f + Main.HEIGHT/21.6f/2 -Main.HEIGHT/21.6f*0, Main.WIDTH/7.74f, Main.HEIGHT/21.6f, this, 1);
        DifficultyButton middle = new DifficultyButton(new Texture("buttons/medium_button.png"), Main.WIDTH/2f - Main.WIDTH/7.74f/2, Main.HEIGHT/2f + Main.HEIGHT/21.6f/2 -Main.HEIGHT/21.6f*1, Main.WIDTH/7.74f, Main.HEIGHT/21.6f, this, 2);
        DifficultyButton hard = new DifficultyButton(new Texture("buttons/hard_button.png"), Main.WIDTH/2f - Main.WIDTH/7.74f/2, Main.HEIGHT/2f + Main.HEIGHT/21.6f/2 -Main.HEIGHT/21.6f*2, Main.WIDTH/7.74f, Main.HEIGHT/21.6f, this, 3);

        BackButton back = new BackButton(Main.WIDTH/2f - Main.WIDTH/7.74f/2, Main.HEIGHT/2f + Main.HEIGHT/21.6f/2 -Main.HEIGHT/21.6f*3, Main.WIDTH/7.74f, Main.HEIGHT/21.6f, this);

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
     * @param difficulty the difficulty that was chosen
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
    @Override
    public void goBack() {
        main.setScreen(new SPNewOrResume(main));
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
