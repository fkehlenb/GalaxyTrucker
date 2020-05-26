package com.galaxytrucker.galaxytruckerreloaded.View.UI.Events;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.EventPageButton;

/** Includes all content shown on a single event page */
public class EventPage {

    /**
     * SpriteBatch
     */
    private SpriteBatch batch;

    /**
     * Orthographic camera
     */
    private OrthographicCamera camera;

    /**
     * Icons to draw (such as rewards)
     */
    private List<Texture> drawables;

    //TODO same wie in eventgui, hier muss noch inhalt als attribut

    /**
     * Setup called after initialisation
     */
    private void setup() {
    }


    /**
     * Draw on top of event gui
     */
    public void showPage() {
    }

    /**
     * Hide the page
     */
    public void hidePage() {
    }

    /**
     * Dispose of page
     */
    public void disposePage() {
    }

    /**
     * Constructor
     *
     * @param main - the main class object
     */
    public EventPage(Main main) {
    }
}
