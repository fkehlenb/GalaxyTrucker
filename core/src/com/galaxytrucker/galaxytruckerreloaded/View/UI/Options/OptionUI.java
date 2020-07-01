package com.galaxytrucker.galaxytruckerreloaded.View.UI.Options;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.OptionButtons.Audio.AudioButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.OptionButtons.Control.ControlButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.OptionButtons.Credits.CreditsButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.OptionButtons.General.GeneralButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.OptionButtons.OptionenBackButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.OptionButtons.Video.VideoButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.GamePlay;

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

    private ControlButton controlButton;

    private CreditsButton creditsButton;

    private AudioButton audioButton;

    private Main main;

    private GamePlay game;

    private PauseMenuUI pauseMenuUI;

    private Stage stage;

    private float x, y;

    /**
     * Constructor
     *
     * @param main - main class
     */
    public OptionUI(Main main, Stage stage, GamePlay game) {
        this.main = main;
        this.game = game;
        this.stage = stage;
        this.pauseMenuUI = game.getPauseMenuUI();
        optionsBackgroundTexture = new Texture("options/options.png");

        x = main.WIDTH/2 - optionsBackgroundTexture.getWidth()/2;
        y = main.HEIGHT/2 - optionsBackgroundTexture.getHeight()/2;

        optionenBackButton = new OptionenBackButton(x+220, y+(y/6), 128, 24, this, pauseMenuUI);
        videoButton = new VideoButton(x+220, y+(2*y/6), 128, 24, this);
        generalButton = new GeneralButton(x+220, y+(3*y/6), 128, 24, this);
        controlButton = new ControlButton(x+ 220, y+ (4*y/6), 128, 24, this );
        creditsButton = new CreditsButton(x + 220, y + (5*y/6), 128, 24, this);
        audioButton = new AudioButton(x +220, y+(6*y/6), 128, 24, this);

        stage.addActor(optionenBackButton);
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
        optionenBackButton.remove();
        generalButton.remove();
        videoButton.remove();
        audioButton.remove();
        controlButton.remove();
        creditsButton.remove();
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
        generalButton.setVisible(true);
        optionenBackButton.setVisible(true);
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
        optionenBackButton.setVisible(false);
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
            disposeOptionsUI();
            pauseMenuUI.showPauseMenuUI();
        }
    }

    public void openGeneral() {
        this.hideOptionsUI();
        game.createGeneralUI();
    }

    public void openVideo() {
        this.hideOptionsUI();
        game.createVideoUI();
    }

    public void openCredits(){
        this.hideOptionsUI();
        game.createCreditUI();
    }

    public void openAudio(){
        this.hideOptionsUI();
        game.createAudioUI();
    }

    public void openControl(){
        this.hideOptionsUI();
        game.createControlUI();
    }
}
