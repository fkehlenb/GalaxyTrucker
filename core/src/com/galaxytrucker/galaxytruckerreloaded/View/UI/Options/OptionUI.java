package com.galaxytrucker.galaxytruckerreloaded.View.UI.Options;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.ContinueButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.MainMenuButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.OptionButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.OptionenBackButton;
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
     * main menu button
     */
    private MainMenuButton mainMenuButton;

    private OptionenBackButton optionenBackButton;

    private Main main;

    private GamePlay game;

    private PauseMenuUI pauseMenuUI;

    private float x, y;

    /**
     * Constructor
     *
     * @param main - main class
     */
    public OptionUI(Main main, Stage stage, GamePlay game) {
        this.main = main;
        this.game = game;
        this.pauseMenuUI = game.getPauseMenuUI();
        optionsBackgroundTexture = new Texture("options/options.png");

        x = main.WIDTH/2 - optionsBackgroundTexture.getWidth()/2;
        y = main.HEIGHT/2 - optionsBackgroundTexture.getHeight()/2;

        optionenBackButton = new OptionenBackButton(x+220, y+270, 128, 24, this, pauseMenuUI);

        stage.addActor(optionenBackButton);
    }

    /**
     * render
     * no stage stuff
     */
    public void render() {
        updateInput();
        main.batch.begin();
        main.batch.draw(optionsBackgroundTexture, x, y, 601, 471);
        main.batch.end();
    }

    /**
     * Dispose of options ui
     */
    public void disposeOptionsUI() {
        optionsBackgroundTexture.dispose();
        optionenBackButton.remove();
        game.deleteOptions();
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

    /**
     * handles input to pause game, open options
     */
    public void updateInput() {
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            disposeOptionsUI();
            pauseMenuUI.showPauseMenuUI();
        }
    }
}
