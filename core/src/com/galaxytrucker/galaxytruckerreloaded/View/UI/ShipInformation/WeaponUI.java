package com.galaxytrucker.galaxytruckerreloaded.View.UI.ShipInformation;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.LinkedList;
import java.util.List;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.ShipLayout.Room;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.WeaponActivateButton;

/**
 * shows the weapons of the ship
 */
//TODO als subklasse zu subsystemui sobald gemergt mit master
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
     * the texure to display the weapon background
     */
    private Texture weaponBackground;

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
     * constructor
     * @param main the main class
     * @param weapon the weapon
     */
    public WeaponUI(Main main, Weapon weapon, Stage stage, RoomUI roomUI) {
        super(main, weapon.getWeaponSystem(), stage, roomUI);

        cooldown = weapon.getCooldown();
        energy = weapon.getEnergy();
        burst = weapon.getBurst();
        id = weapon.getId();

        //weaponBackground = new Texture("shipsys/weapon/smallbox.png");

        //weaponCooldown = new LinkedList<>();
        //weaponCooldown.add(new Texture(""));
        //currentTexture = 0;

        activateButton = new WeaponActivateButton(new Texture("shipsys/weapon/minibox.png"), 0, 0, 10, 10, this);
        stage.addActor(activateButton);
    }

    /**
     * render
     * no stage stuff
     */
    public void render() {
        super.render();

        /*main.batch.begin();
        main.batch.draw(weaponBackground, 0, 0, 0, 0); //TODO whxy
        //main.batch.draw(weaponCooldown.get(currentTexture), 0, 0, 0, 0); //TODO whxy
        main.batch.end();*/
    }

    /**
     * dispose of everything
     */
    public void disposeWeaponUI() {
        super.disposeSubsystemUI();

        //weaponBackground.dispose();

        /*for(Texture t : weaponCooldown) {
            t.dispose();
        }*/

        activateButton.remove();
    }

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
     * the weapon is activated. now, a room needs to be selected
     * if weapon is active, it is deactivated
     * called by weaponactivatebutton
     */
    public void weaponactivated() {
        //TODO do this
    }
}
