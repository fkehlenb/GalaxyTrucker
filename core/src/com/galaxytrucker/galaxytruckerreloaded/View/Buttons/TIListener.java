package com.galaxytrucker.galaxytruckerreloaded.View.Buttons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.awt.*;

public class TIListener extends TextField {

    public TIListener(Texture texture, float x, float y, float width, float height) {
        super(new TextureRegionDrawable(new TextureRegion(texture)));
        setPosition(x, y);
        setSize(width, height);
    }

    public abstract void leftClick();
}
