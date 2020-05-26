package com.galaxytrucker.galaxytruckerreloaded.View.UI.Events;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.EventPageButton;

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
     * button to click on for next page
     */
    private EventPageButton nextPage;

    /**
     * Event background texture
     */
    private Texture backgroundTexture;

    //TODO das event was stattfindet als parameter

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
     * possibly open Shop ui, if the event is a shop, or start fight
     */
    private void nextPage() {
    }

    /**
     * starts a fight, if the event is a fight
     */
    private void startFight() {

    }

    /**
     * opens a shop, if the event is a shop
     */
    private void openShop() {

    }

    /**
     * Constructor
     *
     * @param main - main class object for SpriteBatch
     */
    public EventGUI(Main main) {
    }
}
