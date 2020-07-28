package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Events.EventGUI;

/**
 * button for moving on to the next page in an event ui
 */
public class EventPageButton extends ImButton {

    /**
     * Click sound effect
     */
    private Sound clickSound;

    /**
     * the ui the button is on
     */
    private EventGUI eventgui;

    /**
     * what happens when there is a left click on the button
     */
    public void leftClick() {
        eventgui.nextPage();
    }

    /**
     * constructor
     * @param x x position
     * @param y y position
     * @param width button width
     * @param height button height
     * @param eventgui ui this button is on
     */
    public EventPageButton(float x, float y, float width, float height, EventGUI eventgui) {
        super(new Texture("ingame_continue.png"), x, y, width, height);
        this.eventgui = eventgui;
        this.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                leftClick();
            }
        });

    }
}
