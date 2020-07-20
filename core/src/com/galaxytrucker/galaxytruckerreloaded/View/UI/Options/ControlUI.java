package com.galaxytrucker.galaxytruckerreloaded.View.UI.Options;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.OptionButtons.BackButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.GamePlay;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.MainMenu;

public class ControlUI {

    private Texture controleBackgroundTextuere;
    private Main main;
    private GamePlay gamePlay;
    private OptionUI optionUI;
    private BackButton backButton;
    private Stage stage;
    private MainMenu mainMenu;

    /**
     * Constructor
     * @param main
     * @param stage
     * @param gamePlay
     */
    public ControlUI(Main main, Stage stage, GamePlay gamePlay, MainMenu mainMenu) {
        this.main = main;
        this.gamePlay = gamePlay;
        this.stage = stage;
        //this.optionUI = gamePlay.getOptionUI();
        this.mainMenu = mainMenu;

        if(gamePlay!=null) {
            this.optionUI = gamePlay.getOptionUI();
        }
        else if(mainMenu != null) {
            this.optionUI = mainMenu.getOptionUI();
        }
        controleBackgroundTextuere = new Texture("options/control.png");


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
        main.batch.draw(controleBackgroundTextuere, main.WIDTH/2 - (main.WIDTH/3.1946f)/2, main.HEIGHT/2 - (main.HEIGHT/2.293f)/2, main.WIDTH/3.1946f, main.HEIGHT/2.293f);
        main.batch.end();

        stage.draw();
    }

    /**
     * Dispose of options ui
     */
    public void disposeControlUI() {
        controleBackgroundTextuere.dispose();
        backButton.remove();
        if(gamePlay != null){
            gamePlay.deleteControlUI();
        }
        else if(mainMenu != null){
            mainMenu.deleteControlUI();
        }
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
            disposeControlUI();
            optionUI.showOptionsUI();
        }
    }


}
