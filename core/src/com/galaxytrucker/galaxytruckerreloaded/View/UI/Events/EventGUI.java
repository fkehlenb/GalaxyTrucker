package com.galaxytrucker.galaxytruckerreloaded.View.UI.Events;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.galaxytrucker.galaxytruckerreloaded.Main;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.PlanetEvent;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons.EventPageButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.GamePlay;

import java.util.LinkedList;
import java.util.List;

/**
 * Shows an event
 *
 * dialogue window for the events that can occur at a planet
 */
public class EventGUI {

    /**
     * Event pages
     */
    private List<EventPage> eventPages;

    /**
     * what kind of event it is
     */
    private PlanetEvent event;

    /**
     * button to click on for next page
     *
     * if new page is openend, it is taken out of the list
     * therefore, the next is always the front of the list
     */
    private EventPageButton nextPage;

    /**
     * Event background texture
     */
    private Texture backgroundTexture;

    /**
     * the main class extending game
     */
    private Main main;

    /**
     * the current page
     */
    private EventPage currentPage;

    /**
     * the game play screen
     */
    private GamePlay game;

    /**
     * Constructor
     *
     * @param main - main class object for SpriteBatch
     * @param event what kind of event it is
     *
     */
    public EventGUI(Main main, PlanetEvent event, Stage stage, GamePlay game) {
        this.main = main;
        this.event = event;
        this.game = game;

        backgroundTexture = new Texture("event/eventbackground.png");

        nextPage = new EventPageButton(0, 0, 10, 10, this); //TODO whxy (Text/Planet info)
        stage.addActor(nextPage);

        eventPages = new LinkedList<>();

        List<Texture> drawables = new LinkedList<>();

        if(event == PlanetEvent.SHOP) {
            currentPage = new EventPage(main, drawables, "this is a shop", 0, 0); //TODO drawables, xy
            eventPages.add(currentPage);
        }
        else if(event == PlanetEvent.COMBAT) {
            currentPage = new EventPage(main, drawables, "this is a fight", 0, 0); //TODO drawables, xy
            eventPages.add(currentPage);
        }
        else if(event == PlanetEvent.BOSS) {
            currentPage = new EventPage(main, drawables, "this is the boss", 0, 0);
            eventPages.add(currentPage);
        }
        else if(event == PlanetEvent.METEORSHOWER) {
            currentPage = new EventPage(main, drawables, "this is a meteor shower", 0, 0);
            eventPages.add(currentPage);
        }
        else if(event == PlanetEvent.NEBULA) {
            currentPage = new EventPage(main, drawables, "this is a nebula", 0, 0);
            eventPages.add(currentPage);
        }
        else if(event == PlanetEvent.PVP) {
            currentPage = new EventPage(main, null, "this is a pvp fight", 0, 0);
            eventPages.add(currentPage);
        }
        else { //VOID
            currentPage = new EventPage(main, null, "this planet is empty", 0, 0);
            eventPages.add(currentPage);
        }
    }

    /**
     * Dispose eventGUI
     */
    public void disposeEventGUI() {
        backgroundTexture.dispose();
        nextPage.remove();
        for(EventPage p : eventPages) {
            p.disposePage();
        }
        game.deleteEvent();
    }

    /**
     * render
     * no stage stuff
     */
    public void render() {
        main.batch.begin();
        main.batch.draw(backgroundTexture, 0, 0, 15, 15);
        main.batch.end();
        currentPage.render();
    }

    /**
     * Switch event page
     * if there is no next page hide the event gui
     * possibly open Shop ui, if the event is a shop, or start fight
     *
     * called by button
     */
    public void nextPage() {
        currentPage.disposePage();
        if(eventPages.size() > 0) {
            currentPage = eventPages.remove(0);
        }
        else {
            disposeEventGUI();
        }
    }


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
}
