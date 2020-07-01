package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Weapons.Weapon;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.Button;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Ship.ShipView;

/**
 * Button to activate Autofire-Function during fights
 */
public class AutofireButton extends ImButton
{
    /**
     * Click sound effect
     */
    private Sound clickSound;

    /**
     * the ui this button is on
     */
    private ShipView ui;

    /**
     * Constructor
     *
     * @param ui the ui this button is on
     */
    public AutofireButton(float x, float y, float width, float height, ShipView ui) {
        super(new Texture("autofire_on.png"), x, y, width, height);
        this.ui = ui;
        this.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                leftClick();
            }
        });
    }

    public void leftClick()
    {
        //ui.autofire();
    }


}