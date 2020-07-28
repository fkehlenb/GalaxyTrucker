package com.galaxytrucker.galaxytruckerreloaded.View.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons.BackButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.MenuButtons.SPResumeStartGame;

/**
 * the single player lobby for resuming a game
 */
public class SPResumeLobby extends MenuScreen {

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

    /** Constructor  */
    public SPResumeLobby(Main main){
        super(main);

        SPResumeStartGame spResumeStartGame = new SPResumeStartGame(7*Main.WIDTH/8f - Main.WIDTH/15.484f, Main.HEIGHT/8f , Main.WIDTH/7.742f, Main.HEIGHT/21.6f, this);
        BackButton spResumeLobbyBackButton = new BackButton(Main.WIDTH/8f - Main.WIDTH/15.484f, Main.HEIGHT/8f, Main.WIDTH/7.742f, Main.HEIGHT/21.6f,this);

        font = main.getFont15();
        glyph.setText(font, "Your Ship: " + main.getClient().getMyShip().getShipType());
        glyph2.setText(font, "Your Difficulty: " + main.getClient().getOverworld().getDifficulty());
        glyph3.setText(font, "Your Seed: " + main.getClient().getOverworld().getSeed());

        stage.addActor(spResumeStartGame);
        stage.addActor(spResumeLobbyBackButton);

        Gdx.input.setInputProcessor(stage);
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
        font.draw(main.batch, glyph, Main.WIDTH/4f  - glyph.width/2, Main.HEIGHT/2f + Main.HEIGHT/77.143f);
        font.draw(main.batch, glyph2, 3*Main.WIDTH/4f  - glyph.width/2, Main.HEIGHT/2f + Main.HEIGHT/77.143f);
        font.draw(main.batch, glyph3, 2*Main.WIDTH/4f  - glyph.width/2, Main.HEIGHT/2f + Main.HEIGHT/77.143f);
        main.batch.end();
        stage.draw();
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
    @Override
    public void goBack() {
        main.setScreen(new LoginScreen(main));
        dispose();
    }

}
