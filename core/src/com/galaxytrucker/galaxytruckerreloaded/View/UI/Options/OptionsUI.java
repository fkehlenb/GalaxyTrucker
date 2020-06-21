package com.galaxytrucker.galaxytruckerreloaded.View.UI.Options;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.ContinueButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.MainMenuButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.GamePlay;

/**
 * Ingame options UI
 */
public class OptionsUI {

    /**
     * Options ui background
     */
    private Texture optionsBackgroundTexture;

    /**
     * continue button
     */
    private ContinueButton continueButton;

    /**
     * main menu button
     */
    private MainMenuButton mainMenuButton;

    private Main main;

    private GamePlay game;

    private float x, y;

    /**
     * Constructor
     *
     * @param main - main class
     */
    public OptionsUI(Main main, Stage stage, GamePlay game) {
        this.main = main;
        this.game = game;

        optionsBackgroundTexture = new Texture("options/options.png");

        x = main.WIDTH/2 - optionsBackgroundTexture.getWidth()/2;
        y = main.HEIGHT/2 - optionsBackgroundTexture.getHeight()/2;

        continueButton = new ContinueButton(x+10, y+20, 10, 10, this);
        mainMenuButton = new MainMenuButton(x+10, y+50, 10, 10, main); //TODO whxy

        stage.addActor(continueButton);
        stage.addActor(mainMenuButton);
    }

    /**
     * render
     * no stage stuff
     */
    public void render() {
        main.batch.begin();
        main.batch.draw(optionsBackgroundTexture, x, y, 601, 471);
        main.batch.end();
    }

    /**
     * Dispose of options ui
     */
    public void disposeOptionsUI() {
        optionsBackgroundTexture.dispose();
        game.deleteOptions();
        continueButton.remove();
        mainMenuButton.remove();
    }

    /**
     * Setup called after initialisation
     */
    private void setup() {
    }

    /**
     * Open the options menu
     */
    public void showOptionsUI() {
    }

    /**
     * Close the options menu
     */
    public void hideOptionsUI() {
    }
}
