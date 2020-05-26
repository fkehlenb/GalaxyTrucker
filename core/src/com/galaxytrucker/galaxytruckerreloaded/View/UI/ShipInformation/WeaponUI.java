package com.galaxytrucker.galaxytruckerreloaded.View.UI.ShipInformation;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.WeaponActivateButton;

/**
 * shows the weapons of the ship
 */
//TODO warum ist weapon in model nicht system subklasse?
public class WeaponUI {

    /**
     * the weapon
     */
    private Weapon weapon;

    /**
     * the texure to display the weapon background
     */
    private Texture weaponBackground;

    /**
     * the textures to display the cooldown until the weapon can be used again
     */
    private List<Texture> weaponCooldown;

    /**
     * button used to activate/deactive weapon in the bottom left corner
     * after weapon is activated, a room in the enemy ship needs to be selected
     */
    private WeaponActivateButton activateButton;

    /**
     * Setup called after initialisation
     */
    private void setup() {
    }

    /**
     * animation for when the weapon was shot
     */
    public void weaponShotAnimation() {

    }

    /**
     * constructor
     * @param main the main class
     * @param weapon the weapon
     */
    public WeaponUI(Main main, Weapon weapon) {
    }
}
