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

    /**
     * button used to activate/deactive weapon in the bottom left corner
     * after weapon is activated, a room in the enemy ship needs to be selected
     */
    private WeaponActivateButton activateButton;

    /**
     * the general background for the weapon display in the bottom left corner next to the energy status display
     */
    private Texture weaponGeneralBackground;

    /**
     * constructor
     * @param main the main class
     * @param weapon the weapon
     */
    public WeaponUI(Main main, Stage stage, ShipView ship, float x, float y, System weapon, float sx, Stage normalStage) {
        super(main, stage, ship, x, y, weapon, sx, normalStage);

        weaponGeneralBackground = new Texture("shipsys/weapon_system/generalbox.png");

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
        //main.batch.draw(weaponGeneralBackground, 700, 100, 328, 90);
    }

    /**
     * dispose of everything
     */
    public void disposeRoomUI() {
        super.disposeRoomUI();

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
