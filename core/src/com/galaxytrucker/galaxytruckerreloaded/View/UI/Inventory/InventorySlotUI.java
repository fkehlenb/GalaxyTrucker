package com.galaxytrucker.galaxytruckerreloaded.View.UI.Inventory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.galaxytrucker.galaxytruckerreloaded.Main;

public abstract class InventorySlotUI {

    /**
     * Inventory slot position x
     */
    protected float posX;

    /**
     * Inventory slot position y
     */
    protected float posY;

    protected Main main;

    protected BitmapFont font;

    /**
     * Constructor
     *
     * @param main - main class
     */
    public InventorySlotUI(Main main, float x, float y) {
        this.main = main;

        //font generator to get bitmapfont from .ttf file
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.local("fonts/JustinFont11Bold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
        //setting parameters of font
        params.borderWidth = 1;
        params.borderColor = Color.BLACK;
        params.characters = FreeTypeFontGenerator.DEFAULT_CHARS;
        params.magFilter = Texture.TextureFilter.Nearest;
        params.minFilter = Texture.TextureFilter.Nearest;
        params.genMipMaps = true;
        params.size = 15;

        font = generator.generateFont(params);

        posX = x;
        posY = y;
    }

    /**
     * render
     * without stage stuff
     */
    public void render() {
    }

    /**
     * Dispose inventory slot ui
     */
    public void disposeInventorySlotUI() {
        //inventorySlotTexture.dispose();
    }

    /**
     * show the ui
     */
    public abstract void showInventorySlotUI();

    /**
     * Hide inventory slot ui
     */
    public abstract void hideInventorySlotUI();

}
