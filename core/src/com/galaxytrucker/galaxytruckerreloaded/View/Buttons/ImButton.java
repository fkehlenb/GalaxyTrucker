package com.galaxytrucker.galaxytruckerreloaded.View.Buttons;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Image Button for the game
 */
public abstract class ImButton extends ImageButton {

    private Sound clickSound;

    /**
     * constructor with one texture for buttons that do not change appearance
     * @param texture the texture
     * @param x x position
     * @param y y position
     * @param width button width
     * @param height button height
     */
    public ImButton(Texture texture, float x, float y, float width, float height) {
        super(new TextureRegionDrawable(new TextureRegion(texture)));
        setPosition(x, y);
        setSize(width, height);
    }

    /**
     * constructor with three textures for buttons that change appearance
     * @param texture normal texture
     * @param texture1 second texture, unused
     * @param texture2 third texture, can be applied by calling button.isChecked
     * @param x x position
     * @param y y position
     * @param width button width
     * @param height button height
     */
    public ImButton(Texture texture, Texture texture1, Texture texture2, float x, float y, float width, float height) {
        super (new TextureRegionDrawable(new TextureRegion(texture)), new TextureRegionDrawable(new TextureRegion(texture1)), new TextureRegionDrawable(new TextureRegion(texture2)));
        setPosition(x, y);
        setSize(width, height);
    }

    /**
     * left click action
     */
    public abstract void leftClick();
}
