package com.galaxytrucker.galaxytruckerreloaded.View.UI.Options;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.ContinueButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.MainMenuButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.OptionButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.GamePlay;

/**
 * Ingame options UI
 */
public class PauseMenuUI {

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

    private OptionUI optionUI;

    /**
     * Options button
     */
    private OptionButton optionButton;

    private float x, y;

    /**
     * Constructor
     *
     * @param main - main class
     */
    public PauseMenuUI(Main main, Stage stage, GamePlay game) {
        this.main = main;
        this.game = game;

        optionsBackgroundTexture = new Texture("options/pause.JPG");

        x = main.WIDTH/2 - optionsBackgroundTexture.getWidth()/2;
        y = main.HEIGHT/2 - optionsBackgroundTexture.getHeight()/2;

        continueButton = new ContinueButton(x+220, y+220, 128, 24, this);
        mainMenuButton = new MainMenuButton(x+220, y+270, 128, 24, main);
        optionButton = new OptionButton(x+220,y+320,128,24, this);

        stage.addActor(continueButton);
        stage.addActor(mainMenuButton);
        stage.addActor(optionButton);
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
    public void disposePauseMenuUI() {
        optionsBackgroundTexture.dispose();
        continueButton.remove();
        mainMenuButton.remove();
        optionButton.remove();
        optionsBackgroundTexture.dispose();
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
    public void showPauseMenuUI() {
        continueButton.setVisible(true);
        mainMenuButton.setVisible(true);
        optionButton.setVisible(true);
    }

    /**
     * Close the options menu
     */
    public void hidePauseMenuUI() {
        continueButton.setVisible(false);
        mainMenuButton.setVisible(false);
        optionButton.setVisible(false);
    }

    public void openOptions () {
        this.hidePauseMenuUI();
        game.createOptions();
    }
}
