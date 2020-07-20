package com.galaxytrucker.galaxytruckerreloaded.View.UI.Options;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.OptionButtons.BackButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.GamePlay;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.MainMenu;


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

    private MainMenu mainMenu;

    /**
     * OptionUI of the running Optionmenu.
     */
    private OptionUI optionUI;


    /**
     * Back Button
     */
    private BackButton backButton;

    private Stage stage;

    /**
     * Constructor
     * @param main current Main instance
     * @param stage current stage instance
     * @param gamePlay current gameplay instance
     */
    public GeneralUI (Main main, Stage stage, GamePlay gamePlay, MainMenu mainMenu) {
        this.main = main;
        this.gamePlay = gamePlay;
        this.mainMenu = mainMenu;
        this.stage = stage;
        if(gamePlay != null) {
            this.optionUI = gamePlay.getOptionUI();
        }
        else if(mainMenu != null) {
            this.optionUI = mainMenu.getOptionUI();
        }
        generalBackgroundTexture = new Texture("options/general.png");

        backButton = new BackButton(main.WIDTH/2 - (main.WIDTH/15)/2, main.HEIGHT/2 + (main.HEIGHT/40)/2 -main.HEIGHT/40*4, main.WIDTH/15, main.HEIGHT/40, optionUI, this);

        stage.addActor(backButton);
    }

    /**
     * render
     * no stage stuff
     */
    public void render() {
        updateInput();
        main.batch.begin();
        main.batch.draw(generalBackgroundTexture, main.WIDTH/2 - (main.WIDTH/3.1946f)/2, main.HEIGHT/2 - (main.HEIGHT/2.293f)/2, main.WIDTH/3.1946f, main.HEIGHT/2.293f);
        main.batch.end();

        stage.draw();
    }

    /**
     * Dispose of options ui
     */
    public void disposeGeneralUI() {
        generalBackgroundTexture.dispose();
        backButton.remove();
        if(gamePlay != null) {
            gamePlay.deleteGeneralUI();
        }
        else if(mainMenu != null) {
            mainMenu.deleteGeneralUI();
        }
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
