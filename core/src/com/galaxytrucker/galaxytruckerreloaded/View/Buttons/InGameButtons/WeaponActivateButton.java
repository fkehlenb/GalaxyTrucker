package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.Button;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.ShipInformation.WeaponUI;

import java.io.BufferedWriter;

/**
 * button used to activate/deactivate weapon
 */
public class WeaponActivateButton extends ImButton {

    /**
     * Click sound effect
     */
    private Sound clickSound;

    /**
     * the ui this button belongs to
     */
    private WeaponUI ui;

    /**
     * Left-Click action of the Button.
     */
    @Override
    public void leftClick() {

    }

    /**
     * constructor
     * @param ui the ui this button belongs to
     */
    public WeaponActivateButton(Texture texture, float x, float y, float width, float height, WeaponUI ui) {
        super(texture, x, y, width, height);
        this.ui = ui;
    }
}
