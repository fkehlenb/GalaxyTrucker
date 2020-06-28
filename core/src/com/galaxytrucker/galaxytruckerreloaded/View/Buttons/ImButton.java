package com.galaxytrucker.galaxytruckerreloaded.View.Buttons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public abstract class ImButton extends ImageButton {

    public ImButton(Texture texture, float x, float y, float width, float height) {
        super(new TextureRegionDrawable(new TextureRegion(texture)));
        setPosition(x, y);
        setSize(width, height);
    }

    public abstract void leftClick();
}
