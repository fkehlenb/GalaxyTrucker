package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.ShipInformation.WeaponUI;


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
     * the weapon this belongs to
     */
    private Weapon weapon;

    /**
     * whether this button is currently chosen
     */
    private boolean chosen = false;

    /**
     * Left-Click action of the Button.
     */
    @Override
    public void leftClick() {
        ui.weaponactivated(weapon);
        shot();
    }

    /**
     * change the texture of the button
     */
    public void shot() {
        chosen = !chosen;
        this.setChecked(chosen);
    }

    /**
     * constructor
     * @param texture texture for when the button is not chosen
     * @param texture2 texture for when the button is chosen
     * @param x x position
     * @param y y position
     * @param width button width
     * @param height button height
     * @param ui ui this button is on
     * @param weapon weapon this button is associated with
     */
    public WeaponActivateButton(Texture texture, Texture texture2,  float x, float y, float width, float height, WeaponUI ui, Weapon weapon) {
        super(texture, texture, texture2, x, y, width, height);
        this.ui = ui;
        this.weapon = weapon;
        this.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                leftClick();
            }
        });
    }
}
