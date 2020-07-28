package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.OptionButtons;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Options.*;

/**
 * button for going back to previous option page
 */
public class BackButton extends ImButton {

    /**
     * click sound
     */
    private Sound clickSound;

    /**
     * ui this could be on, or null
     */
    private OptionUI optionUI;

    /**
     * ui this could be on, or null
     */
    private ControlUI controlUI;

    /**
     * ui this could be on, or null
     */
    private CreditsUI creditUI;

    /**
     * ui this could be on, or null
     */
    private GeneralUI generalUI;

    /**
     * ui this could be on, or null
     */
    private VideoUI videoUI;

    /**
     * ui this could be on, or null
     */
    private AudioUI audioUI;

    /**
     * ui this could be on, or null
     */
    private PauseMenuUI pauseMenuUI;

    /**
     * constructor
     * @param x x postion
     * @param y y position
     * @param width button width
     * @param height button height
     * @param optionUI ui to go back to
     * @param controlUI current ui to be disposed
     */
    public BackButton(float x, float y, float width, float height, OptionUI optionUI, ControlUI controlUI) {
        super(new Texture("options/escape_back_on.png"), x, y, width, height);
        this.optionUI = optionUI;
        this.controlUI = controlUI;
        this.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                leftClick();
            }
        });
    }

    /**
     * constructor
     * @param x x postion
     * @param y y position
     * @param width button width
     * @param height button height
     * @param optionUI ui to go back to
     * @param creditsUI current ui to be disposed
     */
    public BackButton(float x, float y, float width, float height, OptionUI optionUI, CreditsUI creditsUI) {
        super(new Texture("options/escape_back_on.png"), x, y, width, height);
        this.optionUI = optionUI;
        this.creditUI = creditsUI;
        this.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                leftClick();
            }
        });
    }

    /**
     * constructor
     * @param x x postion
     * @param y y position
     * @param width button width
     * @param height button height
     * @param optionUI ui to go back to
     * @param generalUI current ui to be disposed
     */
    public BackButton(float x, float y, float width, float height, OptionUI optionUI, GeneralUI generalUI) {
        super(new Texture("options/escape_back_on.png"), x, y, width, height);
        this.optionUI = optionUI;
        this.generalUI = generalUI;
        this.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                leftClick();
            }
        });
    }

    /**
     * constructor
     * @param x x postion
     * @param y y position
     * @param width button width
     * @param height button height
     * @param optionUI ui to go back to
     * @param videoUI current ui to be disposed
     */
    public BackButton(float x, float y, float width, float height, OptionUI optionUI, VideoUI videoUI) {
        super(new Texture("options/escape_back_on.png"), x, y, width, height);
        this.optionUI = optionUI;
        this.videoUI = videoUI;
        this.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                leftClick();
            }
        });
    }

    /**
     * constructor
     * @param x x postion
     * @param y y position
     * @param width button width
     * @param height button height
     * @param optionUI ui to go back to
     * @param audioUI current ui to be disposed
     */
    public BackButton(float x, float y, float width, float height, OptionUI optionUI, AudioUI audioUI) {
        super(new Texture("options/escape_back_on.png"), x, y, width, height);
        this.optionUI = optionUI;
        this.audioUI = audioUI;
        this.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                leftClick();
            }
        });
    }

    /**
     * closes current Menu
     */
    @Override
    public void leftClick() {
        if (controlUI != null) {
            controlUI.disposeControlUI();
            optionUI.showOptionsUI();
        } else if (creditUI != null) {
            creditUI.disposeCreditUI();
            optionUI.showOptionsUI();
        } else if (generalUI != null) {
            generalUI.disposeGeneralUI();
            optionUI.showOptionsUI();
        } else if (videoUI != null) {
            videoUI.disposeVideoUI();
            optionUI.showOptionsUI();
        } else if (audioUI != null) {
            audioUI.disposeAudioUI();
            optionUI.showOptionsUI();
        } else if (optionUI != null) {
            optionUI.disposeOptionsUI();
            pauseMenuUI.showPauseMenuUI();
        }else if (pauseMenuUI != null){
            pauseMenuUI.disposePauseMenuUI();
        }
    }

}
