package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.OptionButtons;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Options.OptionUI;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Options.PauseMenuUI;

public class GeneralButton extends ImButton {

    private Sound clickSound;

    private OptionUI optionUI;

    private PauseMenuUI pauseMenuUI;

    public GeneralButton(float x, float y, float width, float height, OptionUI optionUI) {
        super(new Texture("options/escape_general_on.png"), x, y, width, height);
        this.optionUI = optionUI;
        this.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                leftClick();
            }
        });
    }

    @Override
    public void leftClick() {
        optionUI.openGeneral();
    }
}
