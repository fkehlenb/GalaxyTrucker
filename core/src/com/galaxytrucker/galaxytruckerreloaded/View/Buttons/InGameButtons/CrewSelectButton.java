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

    private int crewID;

    /**
     * the ui with this button
     */
    private CrewUI ui;

    /**
     * Constructor
     *
     * @param crew the crew member
     */
    public CrewSelectButton(Texture texture, float x, float y, float width, float height, int crew, CrewUI ui) {
        super(texture, x, y, width, height);
        this.crewID = crew;
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
    }
}

