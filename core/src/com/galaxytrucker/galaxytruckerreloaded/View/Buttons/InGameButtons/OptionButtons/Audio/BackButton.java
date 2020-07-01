package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.OptionButtons.Audio;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Options.AudioUI;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Options.GeneralUI;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Options.OptionUI;

public class BackButton extends ImButton {

    private Sound clickSound;

    private OptionUI optionUI;

    private AudioUI audioUI;

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
        // Einkommentieren wenn disposeAudioUI implementiert// audioUI.disposeAudioUI();
        optionUI.showOptionsUI();
    }
}
