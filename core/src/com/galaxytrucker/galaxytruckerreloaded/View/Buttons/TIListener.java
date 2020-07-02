package com.galaxytrucker.galaxytruckerreloaded.View.Buttons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.awt.*;

public abstract class TIListener extends TextField {

    public TIListener(String text, float x, float y, float width, float height) {
        super();
    }

    public abstract void leftClick();
}
