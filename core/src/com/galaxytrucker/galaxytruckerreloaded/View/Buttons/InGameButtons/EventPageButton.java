package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.Button;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Events.EventGUI;

public class EventPageButton extends Button {

    /**
     * Sprite batch
     */
    private SpriteBatch batch;
    /**
     * Orthographic camera
     */
    private OrthographicCamera camera;
    /**
     * Background
     */
    private Texture background;
    /**
     * Click sound effect
     */
    private Sound clickSound;

    boolean down = false;

    /**
     * the ui the button is on
     */
    private EventGUI eventgui;

    /**
     * what happens when there is a left click on the button
     */
    public void leftClick() {down=!down;}

    /**
     * constructor
     *
     * @param main the main class
     *
     * @param eventgui the ui the button is on
     */
    public EventPageButton(Main main, EventGUI eventgui) {

    }
}