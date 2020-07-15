package com.galaxytrucker.galaxytruckerreloaded.View.UI.Inventory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;

public class InventoryWeaponSlotUI extends InventorySlotUI {

    /**
     * the texture for the weapon
     */
    private Texture weaponTexture;

    private GlyphLayout glyphDamage = new GlyphLayout();
    private GlyphLayout glyphCooldown = new GlyphLayout();
    private GlyphLayout glyphMissile = new GlyphLayout();
    private GlyphLayout glyphCrewDamage = new GlyphLayout();
    private GlyphLayout glyphBurst = new GlyphLayout();
    private GlyphLayout glyphPrecision = new GlyphLayout();

    private GlyphLayout glyphName = new GlyphLayout();

    /**
     * constructor
     * @param main the main class
     * @param weapon the weapon to be displayed
     */
    public InventoryWeaponSlotUI(Main main, Weapon weapon, float x, float y, BitmapFont font) {
        super(main, x, y, font);
        glyphDamage.setText(font, "Damage: "+weapon.getDamage());
        glyphCooldown.setText(font, "Cooldown: "+weapon.getCooldown());
        glyphMissile.setText(font, "Missile Cost: "+weapon.getMissileCost());
        glyphCrewDamage.setText(font, "Crew Damage: "+weapon.getCrewDamage());
        glyphBurst.setText(font, "Burst: "+weapon.getBurst());
        glyphPrecision.setText(font, "Precision: "+weapon.getAccuracy());

        String name = weapon.getWeaponName();
        weaponTexture = new Texture("shipsys/weapon_system/"+name.toLowerCase()+".png");
        glyphName.setText(font, name);
    }

    /**
     * Dispose inventory slot ui
     */
    @Override
    public void disposeInventorySlotUI() {
        super.disposeInventorySlotUI();
        weaponTexture.dispose();
    }

    public void render() {
        super.render();
        main.batch.begin();
        font.draw(main.batch, glyphName, posX, posY + weaponTexture.getHeight() + main.HEIGHT/76.8f);
        font.draw(main.batch, glyphBurst, posX+Main.WIDTH/26.67f, posY+Main.HEIGHT/32);
        font.draw(main.batch, glyphCooldown, posX+Main.WIDTH/26.67f, posY+Main.HEIGHT/48);
        font.draw(main.batch, glyphCrewDamage, posX+Main.WIDTH/26.67f, posY+Main.HEIGHT/96);
        font.draw(main.batch, glyphDamage, posX+Main.WIDTH/8.727f, posY+Main.HEIGHT/32);
        font.draw(main.batch, glyphMissile, posX+Main.WIDTH/8.727f, posY+Main.HEIGHT/48);
        font.draw(main.batch, glyphPrecision, posX+Main.WIDTH/8.727f, posY+Main.HEIGHT/96);
        main.batch.draw(weaponTexture, posX, posY, weaponTexture.getWidth(), weaponTexture.getHeight());
        main.batch.end();
    }

    /**
     * show the ui
     */
    @Override
    public void showInventorySlotUI() {

    }

    /**
     * Hide inventory slot ui
     */
    @Override
    public void hideInventorySlotUI() {

    }

    /**
     * Setup called after initialisation
     */
    private void setup()
    {

    }
}
