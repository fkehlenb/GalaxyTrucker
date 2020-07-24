package com.galaxytrucker.galaxytruckerreloaded.View.UI.Events;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
     * the font used to draw text on the pages
     */
    private BitmapFont font15;

    /**
     * Constructor
     *
     * @param main - main class object for SpriteBatch
     * @param event what kind of event it is
     *
     */
    public EventGUI(Main main, PlanetEvent event, Stage stage, GamePlay game, BitmapFont font15) {
        this.main = main;
        this.event = event;
        this.game = game;
        this.font15 = font15;

        backgroundTexture = new Texture("event/eventbackground.png");

        nextPage = new EventPageButton(Main.WIDTH/2f + backgroundTexture.getWidth()/2f - 248, Main.HEIGHT/2f - backgroundTexture.getHeight()/2f, 248, 50, this);
        stage.addActor(nextPage);

        eventPages = new LinkedList<>();

        List<Texture> drawables = new LinkedList<>();

        float px = Main.WIDTH/2f;
        float py = Main.HEIGHT/2f;

        if(event == PlanetEvent.SHOP) {
            currentPage = new EventPage(main, drawables, "There is a trader at this planet", px, py, font15);
        }
        else if(event == PlanetEvent.COMBAT) {
            currentPage = new EventPage(main, drawables, "There is a hostile ship at this planet", px, py, font15);
        }
        else if(event == PlanetEvent.BOSS) {
            currentPage = new EventPage(main, drawables, "The boss is waiting on this planet", px, py, font15);
        }
        else if(event == PlanetEvent.METEORSHOWER) {
            currentPage = new EventPage(main, drawables, "Seems like you have gotten into a meteor shower!", px, py, font15);
        }
        else if(event == PlanetEvent.NEBULA) {
            currentPage = new EventPage(main, drawables, "You can barely see through the thick nebula", px, py, font15);
        }
        else if(event == PlanetEvent.PVP) {
            currentPage = new EventPage(main, drawables, "You have been intercepted! Wherever you are now, there seems to be a hostile ship here", px, py, font15);
        }
        else { //VOID
            currentPage = new EventPage(main, drawables, "There is nothing here", px, py, font15);
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
        main.batch.draw(backgroundTexture, Main.WIDTH/2f - backgroundTexture.getWidth()/2f, Main.HEIGHT/2f - backgroundTexture.getHeight()/2f, backgroundTexture.getWidth(), backgroundTexture.getHeight());
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
