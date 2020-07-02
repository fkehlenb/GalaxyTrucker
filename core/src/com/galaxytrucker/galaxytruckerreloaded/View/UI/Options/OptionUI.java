package com.galaxytrucker.galaxytruckerreloaded.View.UI.Options;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.OptionButtons.General.GeneralButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.OptionButtons.OptionenBackButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.OptionButtons.Video.VideoButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.GamePlay;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.MainMenu;

/**
 * Ingame Option screen
 */
public class OptionUI {

    /**
     * Options ui background
     */
    private Texture optionsBackgroundTexture;

    private VideoButton videoButton;

    private OptionenBackButton optionenBackButton;

    private GeneralButton generalButton;

    private Main main;

    private GamePlay game;

    private PauseMenuUI pauseMenuUI;

    private Stage stage;

    private float x, y;

    private MainMenu mainMenu;

    /**
     * Constructor
     *
     * @param main - main class
     */
    public OptionUI(Main main, Stage stage, GamePlay game, MainMenu mainMenu) {
        this.main = main;
        this.game = game;
        this.stage = stage;
        this.mainMenu = mainMenu;
        if(game != null){this.pauseMenuUI = game.getPauseMenuUI(); }
        optionsBackgroundTexture = new Texture("options/options.png");

        x = main.WIDTH/2 - optionsBackgroundTexture.getWidth()/2;
        y = main.HEIGHT/2 - optionsBackgroundTexture.getHeight()/2;

        optionenBackButton = new OptionenBackButton(x+220, y+100, 128, 24, this, pauseMenuUI);
        videoButton = new VideoButton(x+220, y+220, 128, 24, this);
        generalButton = new GeneralButton(x+220, y+320, 128, 24, this);

        stage.addActor(optionenBackButton);
        stage.addActor(generalButton);
        stage.addActor(videoButton);
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
        stage.draw();
    }

    /**
     * Dispose of options ui
     */
    public void disposeOptionsUI() {
        optionsBackgroundTexture.dispose();
        optionenBackButton.remove();
        generalButton.remove();
        videoButton.remove();
        if(mainMenu ==null){
            game.deleteOptions();
        }
        else{
            mainMenu.deleteOptions();
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
    public void showOptionsUI() {
        generalButton.setVisible(true);
        optionenBackButton.setVisible(true);
        videoButton.setVisible(true);
    }

    /**
     * Close the options menu
     */
    public void hideOptionsUI() {
        generalButton.setVisible(false);
        optionenBackButton.setVisible(false);
        videoButton.setVisible(false);
    }

    /**
     * handles input to pause game, open options
     */
    public void updateInput() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            disposeOptionsUI();
            if(pauseMenuUI!=null) {
                pauseMenuUI.showPauseMenuUI();
            }
        }
    }

    public void openGeneral() {
        this.hideOptionsUI();
        if(game!=null) {
            game.createGeneralUI();
        }
        else if(mainMenu!=null) {
            mainMenu.createGeneralUI();
        }
    }

    public void openVideo() {
        this.hideOptionsUI();
        if(game!=null) {
            game.createVideoUI();
        }
        else if(mainMenu!=null) {
            mainMenu.createVideoUI();
        }
    }
}
