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

    private Stage stage;

    private GamePlay game;

    /**
     * Constructor
     *
     * @param main - main class
     */
    public OptionsUI(Main main, Stage stage, GamePlay game) {
        this.main = main;
        this.stage = stage;
        this.game = game;

        continueButton = new ContinueButton(0, 0, 10, 10, this);
        mainMenuButton = new MainMenuButton(0, 0, 10, 10, main); //TODO whxy

        stage.addActor(continueButton);
        stage.addActor(mainMenuButton);

        optionsBackgroundTexture = new Texture("options/options.png");
    }

    /**
     * render
     * no stage stuff
     */
    public void render() {
        main.batch.begin();
        main.batch.draw(optionsBackgroundTexture, 0, 0, 10, 10); //TODO whxy
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
