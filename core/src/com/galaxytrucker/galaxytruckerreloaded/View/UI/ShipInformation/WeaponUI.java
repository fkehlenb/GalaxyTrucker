package com.galaxytrucker.galaxytruckerreloaded.View.UI.ShipInformation;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.LinkedList;
import java.util.List;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.System;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.WeaponActivateButton;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Ship.ShipView;

/**
 * shows the weapons of the ship
 */
public class WeaponUI extends SubsystemUI {

    /** Weapon coolDown */
    private int cooldown;

    /** Weapon energy */
    private int energy;

    /**
     * How many projectiles are fired per burst
     */
    private int burst;

    private int id;

    /**
     * the textures to display the cooldown until the weapon can be used again
     */
    private List<Texture> weaponCooldown;

    /**
     * the current texture in weapon cooldown
     */
    private int currentTexture;

    /**
     * button used to activate/deactive weapon in the bottom left corner
     * after weapon is activated, a room in the enemy ship needs to be selected
     */
    private WeaponActivateButton activateButton;

    /**
     * the position the weapon button should be at
     */
    private float wx, wy;

    /**
     * constructor
     * @param main the main class
     * @param weapon the weapon
     */
    public WeaponUI(Main main, Stage stage, ShipView ship, float x, float y, Weapon weapon, float sx, float wx, float wy) {
        super(main, stage, ship, x, y, weapon.getWeaponSystem(), sx);

        this.wx = wx;
        this.wy = wy;

        cooldown = weapon.getCooldown();
        energy = weapon.getEnergy();
        burst = weapon.getBurst();
        id = weapon.getId();

        //weaponCooldown = new LinkedList<>();
        //weaponCooldown.add(new Texture(""));
        //currentTexture = 0;

        activateButton = new WeaponActivateButton(new Texture("shipsys/weapon/minibox.png"), wx, wy, 248, 50, this);

        stage.addActor(activateButton);
    }

    /**
     * render
     * no stage stuff
     */
    public void render() {
        super.render();

    }

    /**
     * dispose of everything
     */
    public void disposeRoomUI() {
        super.disposeRoomUI();

        /*for(Texture t : weaponCooldown) {
            t.dispose();
        }*/

        activateButton.remove();
    }

    /**
     * animation for when the weapon was shot
     */
    public void weaponShotAnimation() {

    }

    /**
     * the weapon is activated. now, a room needs to be selected
     * if weapon is active, it is deactivated
     * called by weaponactivatebutton
     */
    public void weaponactivated() {
        //TODO do this
    }
}
