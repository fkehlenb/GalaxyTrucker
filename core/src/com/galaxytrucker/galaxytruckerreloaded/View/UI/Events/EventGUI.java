package com.galaxytrucker.galaxytruckerreloaded.View.UI.Events;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.galaxytrucker.galaxytruckerreloaded.Main;

/**
 * Shows an event
 *
 * dialogue window for the events that can occur at a planet
 */
public class EventGUI {

    /**
     * SpriteBatch
     */
    private SpriteBatch batch;

    /**
     * Orthographic camera
     */
    private OrthographicCamera camera;

    /**
     * Event pages
     */
    private List<EventPage> eventPages;

    /**
     * First event page
     */
    private EventPage firstPage;

    /**
     * Event background texture
     */
    private Texture backgroundTexture;

    /**
     * Setup called after initialisation
     */
    private void setup() {
    }

    /**
     * Show the event gui
     */
    public void showEventGUI() {
    }

    /**
     * Hide the event gui
     */
    public void hideEventGUI() {
    }

    /**
     * Dispose eventGUI
     */
    public void disposeEventGUI() {
    }

    /**
     * Switch event page
     * if there is no next page hide the event gui
     */
    private void nextPage() {
    }

    /**
     * Constructor
     *
     * @param main - main class object for SpriteBatch
     */
    public EventGUI(Main main) {
    }
}
