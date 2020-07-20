package com.galaxytrucker.galaxytruckerreloaded.View.UI.Options;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.OptionButtons.Audio.AudioButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.OptionButtons.BackButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.OptionButtons.Control.ControlButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.OptionButtons.Credits.CreditsButton;
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

    private BackButton backButton;

    private GeneralButton generalButton;

    private ControlButton controlButton;

    private CreditsButton creditsButton;

    private AudioButton audioButton;

    private Main main;

    private GamePlay game;

    private PauseMenuUI pauseMenuUI;

    private Stage stage;

    private float x, y;

    private MainMenu mainMenu;

    public boolean startedFromMain = false;

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
        if(game != null){
            this.pauseMenuUI = game.getPauseMenuUI();
            startedFromMain = true;
        }
        optionsBackgroundTexture = new Texture("options/options.png");

        x = main.WIDTH/2 - optionsBackgroundTexture.getWidth()/2;
        y = main.HEIGHT/2 - optionsBackgroundTexture.getHeight()/2;

        backButton = new BackButton(x+220, y+(y/6), 128, 24, this, pauseMenuUI);
        videoButton = new VideoButton(x+220, y+(2*y/6), 128, 24, this);
        generalButton = new GeneralButton(x+220, y+(3*y/6), 128, 24, this);
        controlButton = new ControlButton(x+ 220, y+ (4*y/6), 128, 24, this );
        creditsButton = new CreditsButton(x + 220, y + (5*y/6), 128, 24, this);
        audioButton = new AudioButton(x +220, y+(6*y/6), 128, 24, this);

        stage.addActor(backButton);
        stage.addActor(generalButton);
        stage.addActor(videoButton);
        stage.addActor(controlButton);
        stage.addActor(creditsButton);
        stage.addActor(audioButton);
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
        backButton.remove();
        generalButton.remove();
        videoButton.remove();
        audioButton.remove();
        controlButton.remove();
        creditsButton.remove();
        if(mainMenu == null){
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
        backButton.setVisible(true);
        videoButton.setVisible(true);
        audioButton.setVisible(true);
        creditsButton.setVisible(true);
        controlButton.setVisible(true);

    }

    /**
     * Close the options menu
     */
    public void hideOptionsUI() {
        generalButton.setVisible(false);
        backButton.setVisible(false);
        videoButton.setVisible(false);
        audioButton.setVisible(false);
        creditsButton.setVisible(false);
        controlButton.setVisible(false);
    }

    /**
     * handles input to pause game, open options
     */
    public void updateInput() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            backButton.leftClick();
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

    public void openCredits(){
        this.hideOptionsUI();
        if(game !=null){
            game.createCreditUI();
        }
        else if(mainMenu != null){
            mainMenu.createCreditUI();
        }
    }

    public void openAudio(){
        this.hideOptionsUI();
        if(game != null){
            game.createAudioUI();
        }
        else if(mainMenu != null){
            mainMenu.createAudioUI();
        }
    }

    public void openControl(){
        this.hideOptionsUI();
        if(game != null){
            game.createControlUI();
        }
        else if(mainMenu != null){
            mainMenu.createControlUI();
        }
    }

}
