package com.galaxytrucker.galaxytruckerreloaded.View.UI.ShipInformation;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.HashMap;
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

    /**
     * the weapons belonging to the system
     */
    private List<Weapon> weapons;

    /**
     * the stage for buttons
     */
    private Stage stage;

    /**
     * the font for text
     */
    private BitmapFont font;

    /**
     * the hashmap saving the texts for cooldown with the ids of the weapons
     */
    private HashMap<Integer, GlyphLayout> glyphs = new HashMap<>();

    /**
     * constructor
     * @param main main class extending game
     * @param stage stage for tile buttons
     * @param ship ship ui this belongs to
     * @param x x position of the room
     * @param y y position of the room
     * @param weapon the weapon system
     * @param sx the position of the energy button in the lower left corner
     * @param normalStage the stage for normal buttons
     * @param font the fonts for text
     */
    public WeaponUI(Main main, Stage stage, ShipView ship, float x, float y, System weapon, float sx, Stage normalStage, BitmapFont font) {
        super(main, stage, ship, x, y, weapon, sx, normalStage);
        this.stage = normalStage;
        this.font = font;
        weaponGeneralBackground = new Texture("shipsys/weapon_system/generalbox.png");
        wx = sx;
        wy = 100;

        weapons = weapon.getShipWeapons();
    }

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
        boolean createGlyphs = false;
        if(glyphs.size() == 0) {
            createGlyphs = true;
        }
        for(Weapon w : ws) {
            String name = w.getWeaponType().toString().toLowerCase();
            buttons.put(w.getId(), new WeaponActivateButton(new Texture("shipsys/weapon/"+name+"buttonoff.png"), new Texture("shipsys/weapon/"+name+"button.png"),bwx, wy, 100, 100, this, w));
            stage.addActor(buttons.get(w.getId()));
            bwx += 110;
            if(createGlyphs) {
                glyphs.put(w.getId(), new GlyphLayout(font, Integer.toString(w.getCurrentCooldown())));
            }
        }

    }

    /**
     * delete the buttons
     */
    private void deleteButtons() {
        for(int i : buttons.keySet()) {
            buttons.get(i).remove();
            glyphs.remove(i);
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
        for(Weapon w : sys.getShipWeapons()) {
            if(glyphs.containsKey(w.getId())) {
                glyphs.get(w.getId()).setText(font, Integer.toString(w.getCurrentCooldown()));
            }
        }
    }

    /**
     * render
     * no stage stuff
     */
    public void render() {
        super.render();
        main.batch.begin();
        main.batch.draw(weaponGeneralBackground, wx, wy, 450, 90);
        for(int i : glyphs.keySet()) {
            font.draw(main.batch, glyphs.get(i), buttons.get(i).getX(), buttons.get(i).getY());
        }
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
     * the weapon is activated. now, a room needs to be selected
     * if weapon is active, it is deactivated
     * called by weaponactivatebutton
     */
    public void weaponactivated(Weapon weapon) {
        ((ShipView) ship).weaponChosen(weapon);
    }

    /**
     * a weapon was shot. the button needs to be updated
     * @param weapon the weapon that was shot
     */
    public void weaponShot(Weapon weapon) {
        buttons.get(weapon.getId()).shot();
    }
}
