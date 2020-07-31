package com.galaxytrucker.galaxytruckerreloaded.View.UI.Inventory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.EquipmentAndUpgradesMenu.EquipButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.EquipmentAndUpgradesMenu.UnequipButton;

/**
 * to display a weapon in the inventory
 */
public class InventoryWeaponSlotUI extends InventorySlotUI {

    /**
     * the texture for the weapon
     */
    private Texture weaponTexture;

    /**
     * the glyph layout for damage
     */
    private GlyphLayout glyphLVL = new GlyphLayout();

    /**
     * the glyph layout for damage
     */
    private GlyphLayout glyphDamage = new GlyphLayout();

    /**
     * the glyph layout for cooldown
     */
    private GlyphLayout glyphCooldown = new GlyphLayout();

    /**
     * the glyph layout for missile cost
     */
    private GlyphLayout glyphMissile = new GlyphLayout();

    /**
     * the glyph layout for crew damage
     */
    private GlyphLayout glyphCrewDamage = new GlyphLayout();

    /**
     * the glyph layout for burst
     */
    private GlyphLayout glyphBurst = new GlyphLayout();

    /**
     * the glyph layout for precision
     */
    private GlyphLayout glyphPrecision = new GlyphLayout();

    /**
     * the glyph layout for the name
     */
    private GlyphLayout glyphName = new GlyphLayout();

    /***
     * button for equipping this weapon (or null if equipped)
     */
    private EquipButton equipButton;

    /**
     * button for unequipping this weapon (or null if already unequipped)
     */
    private UnequipButton unequipButton;

    /**
     * ui this is on
     */
    private InventoryUI ui;

    /**
     * stage for buttons
     */
    private Stage stage;

    /**
     * constructor
     * @param main main class extending game
     * @param weapon weapon this should display
     * @param x x position
     * @param y y position
     * @param stage stage for buttons
     * @param font font for text
     * @param ui ui this is on
     * @param equipped whether the weapon is currently equipped
     */
    public InventoryWeaponSlotUI(Main main, Weapon weapon, float x, float y, Stage stage, BitmapFont font, InventoryUI ui, boolean equipped) {
        super(main, x, y, font);
        this.ui = ui;
        this.stage = stage;

        glyphLVL.setText(font, "lvl "+weapon.getWeaponLevel());
        glyphDamage.setText(font, "Damage: "+weapon.getDamage());
        glyphCooldown.setText(font, "Cooldown: "+weapon.getCooldown());
        glyphMissile.setText(font, "Missile Cost: "+weapon.getMissileCost());
        glyphCrewDamage.setText(font, "Crew Damage: "+weapon.getCrewDamage());
        glyphBurst.setText(font, "Burst: "+weapon.getBurst());
        glyphPrecision.setText(font, "Precision: "+weapon.getAccuracy());

        String name = weapon.getWeaponType().toString();
        weaponTexture = new Texture("shipsys/weapon_system/"+name.toLowerCase()+".png");
        glyphName.setText(font, weapon.getWeaponName());

        if(equipped) {
            unequipButton = new UnequipButton(posX+Main.WIDTH/20.67f, posY+Main.HEIGHT/25f, Main.WIDTH/24f, Main.HEIGHT/45f, weapon, this);
            stage.addActor(unequipButton);
        }
        else {
            equipButton = new EquipButton(posX + Main.WIDTH / 20.67f, posY + Main.HEIGHT / 25f, Main.WIDTH / 24f, Main.HEIGHT / 45f, weapon, this);
            stage.addActor(equipButton);
        }

    }

    /**
     * Dispose inventory slot ui
     */
    @Override
    public void disposeInventorySlotUI() {
        weaponTexture.dispose();
        if(equipButton != null) {
            equipButton.remove();
        }
        if(unequipButton != null) {
            unequipButton.remove();
        }
    }

    /**
     * render everything to the screen
     */
    public void render() {
        main.batch.begin();
        font.draw(main.batch, glyphLVL, posX+Main.WIDTH/8.727f, posY + glyphLVL.height + Main.HEIGHT / 25f);
        font.draw(main.batch, glyphName, posX, posY + weaponTexture.getHeight() + Main.HEIGHT/76.8f);
        font.draw(main.batch, glyphBurst, posX+Main.WIDTH/26.67f, posY+Main.HEIGHT/32f);
        font.draw(main.batch, glyphCooldown, posX+Main.WIDTH/26.67f, posY+Main.HEIGHT/48f);
        font.draw(main.batch, glyphCrewDamage, posX+Main.WIDTH/26.67f, posY+Main.HEIGHT/96f);
        font.draw(main.batch, glyphDamage, posX+Main.WIDTH/8.727f, posY+Main.HEIGHT/32f);
        font.draw(main.batch, glyphMissile, posX+Main.WIDTH/8.727f, posY+Main.HEIGHT/48f);
        font.draw(main.batch, glyphPrecision, posX+Main.WIDTH/8.727f, posY+Main.HEIGHT/96f);
        main.batch.draw(weaponTexture, posX, posY, weaponTexture.getWidth(), weaponTexture.getHeight());
        main.batch.end();
    }

    /**
     * update the view
     * possibly change from equip to unequip button or the other way around
     * @param weapon the weapon
     * @param equipped whether it is currently equipped
     */
    public void update(Weapon weapon, boolean equipped) {
        if(equipped && unequipButton == null) {
            if(equipButton != null) {
                equipButton.remove();
                equipButton = null;
            }
            unequipButton = new UnequipButton(posX + Main.WIDTH / 20.67f, posY + Main.HEIGHT / 25f, Main.WIDTH / 24f, Main.HEIGHT / 45f, weapon, this);
            stage.addActor(unequipButton);
        }
        if(!equipped && equipButton == null) {
            if(unequipButton != null) {
                unequipButton.remove();
                unequipButton = null;
            }
            equipButton = new EquipButton(posX+Main.WIDTH/20.67f, posY+Main.HEIGHT/25f, Main.WIDTH/24f, Main.HEIGHT/45f, weapon, this);
            stage.addActor(equipButton);
        }
    }

    /**
     * equip a weapon
     * @param weapon the weapon
     */
    public void equipWeapon(Weapon weapon){
        ui.equipWeapon(weapon);
    }

    /***
     * unequip a weapon
     * @param weapon the weapon
     */
    public void unequipWeapon(Weapon weapon) {
        ui.unequipWeapon(weapon);
    }
}
