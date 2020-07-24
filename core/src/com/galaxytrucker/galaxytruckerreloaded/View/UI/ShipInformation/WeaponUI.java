package com.galaxytrucker.galaxytruckerreloaded.View.UI.ShipInformation;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.HashMap;
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
     * the general background for the weapon display in the bottom left corner next to the energy status display
     */
    private Texture weaponGeneralBackground;

    /**
     * the list of all the buttons for all the weapons this system has
     */
    private HashMap<Integer, WeaponActivateButton> buttons = new HashMap<>();

    /**
     * the base position for the box and buttons
     */
    private float wx, wy;

    private List<Weapon> weapons;

    private Stage stage;

    /**
     * constructor
     * @param main the main class
     * @param weapon the weapon
     */
    public WeaponUI(Main main, Stage stage, ShipView ship, float x, float y, System weapon, float sx, Stage normalStage) {
        super(main, stage, ship, x, y, weapon, sx, normalStage);
        this.stage = normalStage;
        weaponGeneralBackground = new Texture("shipsys/weapon_system/generalbox.png");
        wx = sx;
        wy = 100;

        weapons = weapon.getShipWeapons();
    } //TODO: equip/unequip + what ever needs to be done to make the firing of weapons possible

    /**
     * sets the position of the box
     * called after the system ui is created, and all other are through, so that it is at the end
     * which is why it is okay to only create the buttons here
     * @param wx the x
     * @param wy the y
     */
    public void setBoxPosition(float wx, float wy) {
        this.wx = wx;
        this.wy = wy;
        createButtons(weapons);
    }

    /**
     * create the buttons
     * @param ws the list of weapons
     */
    private void createButtons(List<Weapon> ws) {
        buttons = new HashMap<>();
        float bwx = wx+15;
        for(Weapon w : ws) {
            String name = w.getWeaponType().toString().toLowerCase();
            buttons.put(w.getId(), new WeaponActivateButton(new Texture("shipsys/weapon/"+name+"button.png"), bwx, wy, 100, 100, this, w));
            stage.addActor(buttons.get(w.getId()));
            bwx += 110;

        }
    }

    /**
     * delete the buttons
     */
    private void deleteButtons() {
        for(int i : buttons.keySet()) {
            buttons.get(i).remove();
        }
    }

    /**
     * the room was updated in the backend and the display needs to be updated
     *
     * @param room the room with updated stats
     */
    @Override
    public void update(Room room) {
        super.update(room);
        System sys = (System) room;
        deleteButtons();
        createButtons(sys.getShipWeapons());
    }

    /**
     * render
     * no stage stuff
     */
    public void render() {
        super.render();
        main.batch.begin();
        main.batch.draw(weaponGeneralBackground, wx, wy, 450, 90);
        main.batch.end();
    }

    /**
     * dispose of everything
     */
    public void disposeRoomUI() {
        super.disposeRoomUI();
        for(WeaponActivateButton b : buttons.values()) {
            b.remove();
        }
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
    public void weaponactivated(Weapon weapon) {
        ((ShipView) ship).weaponChosen(weapon);
    }
}
