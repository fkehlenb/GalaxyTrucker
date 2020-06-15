package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.Button;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Options.OptionsUI;

/**
 * continue button for ingame options
 */
public class ContinueButton extends ImButton {

    /**
     * Click sound effect
     */
    private Sound clickSound;

    /**
     * the options ui this button is on
     */
    private OptionsUI ui;

    /**
     * Left-Click action of the Button.
     */
    public void leftClick() {
        ui.disposeOptionsUI();
    }

    /**
     * constructor
     * @param ui the ui this is on
     */
    public ContinueButton(float x, float y, float width, float height, OptionsUI ui) {
        super(new Texture("ingame_continue.png"), x, y, width, height);
        this.ui = ui;
        this.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                leftClick();
            }
        });
    }
}
