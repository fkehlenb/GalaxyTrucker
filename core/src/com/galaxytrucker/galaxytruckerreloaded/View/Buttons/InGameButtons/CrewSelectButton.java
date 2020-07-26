package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Crew.Crew;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.Button;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.ShipInformation.CrewUI;

/**
 * Button for dismissing a Crew Member
 */
public class CrewSelectButton extends ImButton
{
    /**
     * Background
     */
    private Texture background;
    /**
     * Click sound effect
     */
    private Sound clickSound;

    /**
     * the ui with this button
     */
    private CrewUI ui;

    private boolean chosen = false;

    /**
     * Constructor
     *
     */
    public CrewSelectButton(Texture texture, Texture texture1, float x, float y, float width, float height, CrewUI ui) {
        super(texture, texture, texture1, x, y, width, height);
        this.ui = ui;
        this.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                leftClick();
            }
        });
    }

    public void leftClick()
    {
        ui.crewMoving();
        moved();
    }

    public void moved() {
        chosen = !chosen;
        this.setChecked(chosen);
    }
}

