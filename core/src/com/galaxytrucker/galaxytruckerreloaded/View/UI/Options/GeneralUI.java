package com.galaxytrucker.galaxytruckerreloaded.View.UI.Options;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.OptionButtons.General.BackButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.GamePlay;


/**
 * ingame options/general screen
 */
public class GeneralUI {

    /**
     * background
     */
    private Texture generalBackgroundTexture;

    /**
     * Main instance of the running game.
      */
    private Main main;

    /**
     * Gameplay instance of the running game;
     */
    private GamePlay gamePlay;

    /**
     * OptionUI of the running Optionmenu.
     */
    private OptionUI optionUI;

    /**
     * Position of the Window
     */
    private float x, y;

    /**
     * Back Button
     */
    private BackButton backButton;

    /**
     * Constructor
     * @param main current Main instance
     * @param stage current stage instance
     * @param gamePlay current gameplay instance
     */
    public GeneralUI (Main main, Stage stage, GamePlay gamePlay) {
        this.main = main;
        this.gamePlay = gamePlay;
        this.optionUI = gamePlay.getOptionUI();
        generalBackgroundTexture = new Texture("options/general.png");

        x = main.WIDTH / 2 - generalBackgroundTexture.getWidth() / 2;
        y = main.HEIGHT / 2 - generalBackgroundTexture.getHeight() / 2;

        backButton = new BackButton(x + 220, y + 100, 128, 24, optionUI, this);

        stage.addActor(backButton);
    }

    /**
     * render
     * no stage stuff
     */
    public void render() {
        updateInput();
        main.batch.begin();
        main.batch.draw(generalBackgroundTexture, x, y, 601, 471);
        main.batch.end();
    }

    /**
     * Dispose of options ui
     */
    public void disposeGeneralUI() {
        generalBackgroundTexture.dispose();
        backButton.remove();
        gamePlay.deleteGeneralUI();
    }

    /**
     * Setup called after initialisation
     */
    private void setup() {
    }

    /**
     * Open the options menu
     */
    public void showGeneralUI() {
        backButton.setVisible(true);
    }

    /**
     * Close the options menu
     */
    public void hideGeneralUI() {
        backButton.setVisible(false);
    }

    /**
     * handles input to pause game, open options
     */
    public void updateInput() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            disposeGeneralUI();
            optionUI.showOptionsUI();
        }
    }



}
