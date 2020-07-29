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
 * option ui for credits
 */
public class CreditsUI {

    /**
     * background texture
     */
    private Texture CreditBackgroundTexture;

    /**
     * main class extending game
     */
    private Main main;

    /**
     * screen this is on, if during game
     */
    private GamePlay gamePlay;

    /**
     * screen this is on, if during main menu
     */
    private MainMenu mainMenu;

    /**
     * previous option ui
     */
    private OptionUI optionUI;

    /**
     * back button for returning to previous ui
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
    public CreditsUI(Main main, Stage stage, GamePlay gamePlay, MainMenu mainMenu) {
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

        CreditBackgroundTexture = new Texture("options/credits.png");

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
        main.batch.draw(CreditBackgroundTexture, Main.WIDTH/2f - (Main.WIDTH/3.1946f)/2, Main.HEIGHT/2f - (Main.HEIGHT/2.293f)/2, Main.WIDTH/3.1946f, Main.HEIGHT/2.293f);
        main.batch.end();

        stage.draw();
    }

    /**
     * Dispose of options ui
     */
    public void disposeCreditUI() {
        CreditBackgroundTexture.dispose();
        backButton.remove();
        if(gamePlay != null){
            gamePlay.deleteCreditUI();
        }
        else if(mainMenu != null){
            mainMenu.deleteCreditUI();
        }
    }

    /**
     * handles input to pause game, open options
     */
    private void updateInput() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            disposeCreditUI();;
            optionUI.showOptionsUI();
        }
    }


}
