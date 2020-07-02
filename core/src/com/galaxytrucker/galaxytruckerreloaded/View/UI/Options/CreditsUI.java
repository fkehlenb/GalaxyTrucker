package com.galaxytrucker.galaxytruckerreloaded.View.UI.Options;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.OptionButtons.General.BackButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.GamePlay;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.MainMenu;

public class CreditsUI {
    private Texture CreditBackgroundTexture;
    private Main main;
    private GamePlay gamePlay;
    private OptionUI optionUI;
    private float x, y;
    private BackButton backButton;
    private Stage stage;
    private MainMenu mainMenu;

    /**
     * Constructor
     * @param main
     * @param stage
     * @param gamePlay
     */
    public CreditsUI(Main main, Stage stage, GamePlay gamePlay, MainMenu mainMenu) {
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

        CreditBackgroundTexture = new Texture("options/credits.png");

        x = main.WIDTH / 2 - CreditBackgroundTexture.getWidth() / 2;
        y = main.HEIGHT / 2 - CreditBackgroundTexture.getHeight() / 2;

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
        main.batch.draw(CreditBackgroundTexture, x, y, 601, 471);
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
     * Open the options menu
     */
    public void showCreditUI() {
        backButton.setVisible(true);
    }
    /**
     * Close the options menu
     */
    public void hideCreditUI() {
        backButton.setVisible(false);
    }

    /**
     * handles input to pause game, open options
     */
    public void updateInput() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            disposeCreditUI();;
            optionUI.showOptionsUI();
        }
    }


}
