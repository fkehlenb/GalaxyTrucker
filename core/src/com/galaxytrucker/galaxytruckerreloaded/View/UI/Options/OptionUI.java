package com.galaxytrucker.galaxytruckerreloaded.View.UI.Options;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.ContinueButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.MainMenuButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.OptionButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.GamePlay;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.PauseMenu;

/**
 * Ingame Option screen
 */
public class OptionUI {

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

    private OptionButton optionButton;

    private float x, y;

    /**
     * Constructor
     *
     * @param main - main class
     */
    public OptionUI(Main main, Stage stage, GamePlay game, PauseMenuUI pauseMenuUI) {
        this.main = main;
        this.game = game;

        optionsBackgroundTexture = new Texture("options/options.png");

        x = main.WIDTH/2 - optionsBackgroundTexture.getWidth()/2;
        y = main.HEIGHT/2 - optionsBackgroundTexture.getHeight()/2;

        continueButton = new ContinueButton(x+220, y+220, 128, 24, pauseMenuUI);

        stage.addActor(continueButton);
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
        continueButton.remove();
        mainMenuButton.remove();
        optionButton.remove();
        game.deletePauseMenu();
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
