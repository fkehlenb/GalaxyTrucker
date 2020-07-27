package com.galaxytrucker.galaxytruckerreloaded.View.Buttons.InGameButtons;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.galaxytrucker.galaxytruckerreloaded.Communication.ClientControllerCommunicator;
import com.galaxytrucker.galaxytruckerreloaded.Controller.PlanetEventController;
import com.galaxytrucker.galaxytruckerreloaded.Model.Map.PlanetEvent;
import com.galaxytrucker.galaxytruckerreloaded.View.Buttons.ImButton;
import com.galaxytrucker.galaxytruckerreloaded.View.Screen.GamePlay;
import com.galaxytrucker.galaxytruckerreloaded.View.UI.Events.EventGUI;

public class EventPageButton extends ImButton {

    /**
     * Click sound effect
     */
    private Sound clickSound;

    private GamePlay gamePlay;

    /**
     * the ui the button is on
     */
    private EventGUI eventgui;

    /**
     * what happens when there is a left click on the button
     */
    public void leftClick() {
        if (PlanetEventController.getInstance(null).getClientShip().getPlanet().getEvent().equals(PlanetEvent.SHOP) ){
            System.out.println("TEEEEEEEEEEEEEEEEEEEEEEEEEEST");
            gamePlay.createShop(ClientControllerCommunicator.getInstance(null).getClientShip().getPlanet().getTrader());}
        eventgui.nextPage();
    }

    /**
     * constructor
     *
     * @param eventgui the ui the button is on
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
