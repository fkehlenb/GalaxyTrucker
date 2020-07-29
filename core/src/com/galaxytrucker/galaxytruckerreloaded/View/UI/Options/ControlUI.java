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
 * ui for control optinos
 */
public class ControlUI {

    /**
     * background texture
     */
    private Texture controleBackgroundTextuere;

    /**
     * main class extending game
     */
    private Main main;

    /**
     * screen this is on, if during game
     */
    private GamePlay gamePlay;

    /**
     * screen this is on, if on main menu
     */
    private MainMenu mainMenu;

    /**
     * previous option ui
     */
    private OptionUI optionUI;

    /**
     * button for returning to previous option ui
     */
    private BackButton backButton;

    /**
     * stage for buttons
     */
    private Stage stage;

    /**
     * Constructor
     * @param main main class extending game
     * @param stage stage for buttons
     * @param gamePlay screen, if during game
     * @param mainMenu screen, if during main menu
     */
    public ControlUI(Main main, Stage stage, GamePlay gamePlay, MainMenu mainMenu) {
        this.main = main;
        this.gamePlay = gamePlay;
        this.stage = stage;
        this.mainMenu = mainMenu;

        if(gamePlay!=null) {
            this.optionUI = gamePlay.getOptionUI();
        }
        else if(mainMenu != null) {
            this.optionUI = mainMenu.getOptionUI();
        }
        controleBackgroundTextuere = new Texture("options/control.png");


        backButton = new BackButton(Main.WIDTH/2f - (Main.WIDTH/15f)/2, Main.HEIGHT/2f + (Main.HEIGHT/40f)/2 -Main.HEIGHT/40f*4, Main.WIDTH/15f, Main.HEIGHT/40f, optionUI, this);

        stage.addActor(backButton);
    }

    /**
     * render
     * no stage stuff
     */
    public void render() {
        updateInput();
        main.batch.begin();
        main.batch.draw(controleBackgroundTextuere, Main.WIDTH/2f - (Main.WIDTH/3.1946f)/2, Main.HEIGHT/2f - (Main.HEIGHT/2.293f)/2, Main.WIDTH/3.1946f, Main.HEIGHT/2.293f);
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
     * handles input to pause game, open options
     */
    private void updateInput() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            disposeControlUI();
            optionUI.showOptionsUI();
        }
    }

}
