package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.OptionButtons.General;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Options.*;

public class BackButton extends ImButton {

    private Sound clickSound;

    private OptionUI optionUI;

    private ControlUI controlUI;

    private CreditsUI creditUI;

    private GeneralUI generalUI;

    private VideoUI videoUI;

    private AudioUI audioUI;

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

    @Override
    public void leftClick() {

        if(controlUI != null){controlUI.disposeControlUI();}
        else if(creditUI != null){creditUI.disposeCreditUI();}
        else if(generalUI != null){generalUI.disposeGeneralUI();}
        else if(videoUI != null){videoUI.disposeVideoUI();}
        else if(audioUI != null){audioUI.disposeAudioUI();}
        optionUI.showOptionsUI();
    }
}
