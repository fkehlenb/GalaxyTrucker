package com.galaxytrucker.galaxytruckerreloaded.View.UI.Options;

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

    /**
     * main class extending game
     */
    private Main main;

    /**
     * game screen is on
     */
    private GamePlay game;

    /**
     * stage for buttons
     */
    private Stage pauseStage;

    /**
     * Options button
     */
    private OptionButton optionButton;

    private float x, y;

    /**
     * Constructor
     *
     * @param main - main class
     * @param game screen this is on
     * @param pauseStage stage for buttons
     */
    public PauseMenuUI(Main main, Stage pauseStage, GamePlay game) {
        this.main = main;
        this.game = game;
        this.pauseStage = pauseStage;

        optionsBackgroundTexture = new Texture("options/pause.png");

        x = Main.WIDTH/2f - optionsBackgroundTexture.getWidth()/2f;
        y = Main.HEIGHT/2f - optionsBackgroundTexture.getHeight()/2f;

        continueButton = new ContinueButton(x+220, y+220, 128, 24, this);
        mainMenuButton = new MainMenuButton(x+220, y+270, 128, 24, main);
        optionButton = new OptionButton(x+220,y+320,128,24, this);

        pauseStage.addActor(continueButton);
        pauseStage.addActor(mainMenuButton);
        pauseStage.addActor(optionButton);
    }


    /**
     * render
     * no stage stuff
     */
    public void render() {
        main.batch.begin();
        main.batch.draw(optionsBackgroundTexture, x, y, 601, 471);
        main.batch.end();

        pauseStage.draw();
    }

    /**
     * Dispose of options ui
     */
    public void disposePauseMenuUI() {
        optionsBackgroundTexture.dispose();
        pauseStage.dispose();
        game.deletePauseMenu();

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
    private void hidePauseMenuUI() {
        continueButton.setVisible(false);
        mainMenuButton.setVisible(false);
        optionButton.setVisible(false);
    }

    /**
     * open options
     */
    public void openOptions () {
        this.hidePauseMenuUI();
        game.createOptions();
    }
}
