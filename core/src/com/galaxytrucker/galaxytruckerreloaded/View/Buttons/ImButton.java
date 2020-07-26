package com.galaxytrucker.galaxytruckerreloaded.View.Buttons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public abstract class ImButton extends ImageButton {

    public ImButton(Texture texture, float x, float y, float width, float height) {
        super(new TextureRegionDrawable(new TextureRegion(texture)));
        setPosition(x, y);
        setSize(width, height);
    }

    public ImButton(Texture texture, Texture texture1, Texture texture2, float x, float y, float width, float height) {
        super (new TextureRegionDrawable(new TextureRegion(texture)), new TextureRegionDrawable(new TextureRegion(texture1)), new TextureRegionDrawable(new TextureRegion(texture2)));
        setPosition(x, y);
        setSize(width, height);
    }

    public abstract void leftClick();
}
