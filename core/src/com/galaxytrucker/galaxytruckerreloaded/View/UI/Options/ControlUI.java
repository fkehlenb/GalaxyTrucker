package com.galaxytrucker.galaxytruckerreloaded.View.UI.Options;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.OptionButtons.General.BackButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.GamePlay;

public class ControlUI {

    private Texture controleBackgroundTextuere;
    private Main main;
    private GamePlay gamePlay;
    private OptionUI optionUI;
    private float x, y;
    private BackButton backButton;
    private Stage stage;

    /**
     * Constructor
     * @param main
     * @param stage
     * @param gamePlay
     */
    public ControlUI(Main main, Stage stage, GamePlay gamePlay) {
        this.main = main;
        this.gamePlay = gamePlay;
        this.stage = stage;
        this.optionUI = gamePlay.getOptionUI();
        controleBackgroundTextuere = new Texture("options/control.png");

        x = main.WIDTH / 2 - controleBackgroundTextuere.getWidth() / 2;
        y = main.HEIGHT / 2 - controleBackgroundTextuere.getHeight() / 2;

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
        main.batch.draw(controleBackgroundTextuere, x, y, 601, 471);
        main.batch.end();

        stage.draw();
    }

    /**
     * Dispose of options ui
     */
    public void disposeControlUI() {
        controleBackgroundTextuere.dispose();
        backButton.remove();
        gamePlay.deleteConterolUI();
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
